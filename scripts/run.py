#!/usr/bin/env python3
"""
@author: Zsolt Kovari, Georg Hinkel

"""
import argparse
import csv
import os
import shutil
import subprocess
import sys
import signal
import tempfile
try:
    import ConfigParser
except ImportError:
    import configparser as ConfigParser
import json

BASE_DIRECTORY = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
OUTPUT_FILE = os.path.join(BASE_DIRECTORY, "output", "output.csv")
print("Running benchmark with root directory " + BASE_DIRECTORY)

class JSONObject(object):
    def __init__(self, d):
        self.__dict__ = d


def build(conf, skip_tests=False):
    """
    Builds all solutions
    """
    for tool in conf.Tools:
        print("Building " + tool)
        config = ConfigParser.ConfigParser()
        config.read(os.path.join(BASE_DIRECTORY, "solutions", tool, "solution.ini"))
        set_working_directory("solutions", tool)
        if skip_tests:
            subprocess.check_call(config.get('build', 'skipTests'), shell=True)
        else:
            subprocess.check_call(config.get('build', 'default'), shell=True)


def benchmark(conf, error_on_timeout):
    """
    Runs measurements
    """
    success = True
    tool_combinations_run = []
    header = os.path.join(BASE_DIRECTORY, "output", "header.csv")
    # overwrite with the header
    shutil.copy(header, OUTPUT_FILE)
    os.environ['Sequences'] = str(conf.Sequences)
    os.environ['Runs'] = str(conf.Runs)
    for tool in conf.Tools:
        config = ConfigParser.ConfigParser()
        config.read(os.path.join(BASE_DIRECTORY, "solutions", tool, "solution.ini"))
        set_working_directory("solutions", tool)
        os.environ['Tool'] = tool
        for query in conf.Queries:
            cmd = config.get('run', query)
            if not cmd:
                print(f"Skipping: tool = {tool}, query = {query}")
                continue

            os.environ['Query'] = query
            change_set = -1
            try:
                for change_set in conf.ChangeSets:
                    full_change_path = os.path.abspath(os.path.join(BASE_DIRECTORY, "models", change_set))
                    os.environ['ChangeSet'] = change_set
                    os.environ['ChangePath'] = full_change_path

                    # Do a backup of initial.xmi before any runs
                    initial_xmi = os.path.join(full_change_path, "initial.xmi")
                    fbackup, initial_xmi_backup = tempfile.mkstemp(prefix="ttcbackup_", suffix=".xmi")
                    os.close(fbackup)
                    shutil.copy(initial_xmi, initial_xmi_backup)

                    for r in range(0, conf.Runs):
                        os.environ['RunIndex'] = str(r)

                        print(f"Running benchmark: tool = {tool}, change set = {change_set}, query = {query}")

                        # instead of subprocess.check_output()
                        # to enforce timeout before Python 3.7.5
                        # and kill sub-processes to avoid interference
                        # https://stackoverflow.com/a/36955420
                        # https://www.saltycrane.com/blog/2011/04/how-use-bash-shell-python-subprocess-instead-binsh/
                        with subprocess.Popen(cmd, shell=True, executable='/bin/bash',
                                              stdout=subprocess.PIPE, start_new_session=True) as process:
                            try:
                                stdout, stderr = process.communicate(timeout=conf.Timeout)
                                return_code = process.poll()
                                if return_code:
                                    raise subprocess.CalledProcessError(return_code, process.args,
                                                                        output=stdout, stderr=stderr)
                            except subprocess.TimeoutExpired:
                                os.killpg(process.pid, signal.SIGINT)  # send signal to the process group
                                raise
                            finally:
                                # Restore the backup no matter what
                                shutil.copy(initial_xmi_backup, initial_xmi)

                        with open(OUTPUT_FILE, "ab") as file:
                            file.write(stdout)

                        # collect lines to be expected in results file
                        tool_combinations_run.append((tool, query, change_set, str(r)))

                    # after the runs, delete the backup
                    os.unlink(initial_xmi_backup)

            except subprocess.TimeoutExpired as e:
                msg = f"Program reached the timeout set ({e.timeout} s): " \
                      f"tool = {tool}, change set = {change_set}, query = {query}, command: '{e.cmd}'"
                if error_on_timeout:
                    success = False
                    msg = "::error::" + msg

                print(msg)

    return success, tool_combinations_run


def clean_dir(*path):
    dir = os.path.join(BASE_DIRECTORY, *path)
    if os.path.exists(dir):
        shutil.rmtree(dir)
    os.mkdir(dir)


def set_working_directory(*path):
    dir = os.path.join(BASE_DIRECTORY, *path)
    os.chdir(dir)


def visualize():
    """
    Visualizes the benchmark results
    """
    clean_dir("diagrams")
    set_working_directory("reporting")
    subprocess.check_call(["Rscript", "visualize.R", "../config/reporting.json"])


def check_results(conf, expected_lines):
    """
    Checks the benchmark results
    """
    clean_dir("results")
    set_working_directory("reporting")
    subprocess.check_call(["Rscript", "check_results.R"])

    if expected_lines:
        last_measurement_filter = [str(conf.Sequences),
                                   'Initial' if conf.Sequences == 0 else 'Update',
                                   'Time']
        with open(OUTPUT_FILE) as output_csv:
            csv_reader = csv.reader(output_csv, delimiter=';')
            last_measurements = set(tuple(row[:4]) for row in csv_reader if row[4:7] == last_measurement_filter)
            missing_lines = [line for line in expected_lines if line not in last_measurements]
            if missing_lines:
                for line in missing_lines:
                    print("::error::Last measurement is missing: "
                          "tool = {0}, change set = {2}, query = {1}, run index = {3}".format(*line))
                return False

    return True


if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument("-b", "--build",
                        help="build the project",
                        action="store_true")
    parser.add_argument("-m", "--measure",
                        help="run the benchmark",
                        action="store_true")
    parser.add_argument("-s", "--skip-tests",
                        help="skip JUNIT tests",
                        action="store_true")
    parser.add_argument("-v", "--visualize",
                        help="create visualizations",
                        action="store_true")
    parser.add_argument("-c", "--check",
                        help="check results",
                        action="store_true")
    parser.add_argument("--error-on-timeout",
                        help="return non-zero exit code if timeout reached during measurement",
                        action="store_true")
    parser.add_argument("-t", "--test",
                        help="run test",
                        action="store_true")
    parser.add_argument("-d", "--debug",
                        help="set debug to true",
                        action="store_true")
    args = parser.parse_args()


    set_working_directory("config")
    with open("config.json", "r") as config_file:
        config = json.load(config_file, object_hook=JSONObject)

    # if there are no args, execute a full sequence
    # with the test and the visualization/reporting
    no_args = all(not val for val in vars(args).values())

    success = True
    expected_result_lines = set()

    if args.debug:
        os.environ['Debug'] = 'true'
    if args.build or args.test or no_args:
        build(config, args.skip_tests and not args.test)
    if args.measure or no_args:
        benchmark_success, expected_result_lines = benchmark(config, args.error_on_timeout)
        if not benchmark_success:
            success = False
    if args.visualize or no_args:
        visualize()
    if args.check or no_args:
        if not check_results(config, expected_result_lines):
            success = False

    if not success:
        sys.exit(os.EX_SOFTWARE)
