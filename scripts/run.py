#!/usr/bin/env python3
"""
@author: Zsolt Kovari, Georg Hinkel

"""
import argparse
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


def benchmark(conf):
    """
    Runs measurements
    """
    header = os.path.join(BASE_DIRECTORY, "output", "header.csv")
    result_file = os.path.join(BASE_DIRECTORY, "output", "output.csv")
    # overwrite with the header
    shutil.copy(header, result_file)
    os.environ['Sequences'] = str(conf.Sequences)
    os.environ['Runs'] = str(conf.Runs)
    for tool in conf.Tools:
        config = ConfigParser.ConfigParser()
        config.read(os.path.join(BASE_DIRECTORY, "solutions", tool, "solution.ini"))
        set_working_directory("solutions", tool)
        os.environ['Tool'] = tool
        for query in conf.Queries:
            os.environ['Query'] = query
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

                        print("Running benchmark: tool = " + tool + ", change set = " + change_set +
                              ", query = " + query)

                        # instead of subprocess.check_output()
                        # to enforce timeout before Python 3.7.5
                        # and kill sub-processes to avoid interference
                        # https://stackoverflow.com/a/36955420
                        # https://www.saltycrane.com/blog/2011/04/how-use-bash-shell-python-subprocess-instead-binsh/
                        with subprocess.Popen(config.get('run', query), shell=True, executable='/bin/bash',
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

                        with open(result_file, "ab") as file:
                            file.write(stdout)

                    # after the runs, delete the backup
                    os.unlink(initial_xmi_backup)

            except subprocess.TimeoutExpired as e:
                print("Program reached the timeout set ({0} seconds). The command we executed was '{1}'".format(e.timeout, e.cmd))


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
    set_working_directory("reporting2")
    subprocess.call(["Rscript", "-e", "rmarkdown::render('report.Rmd', output_format=rmarkdown::pdf_document())"])


def check_results():
    """
    Checks the benchmark results
    """
    clean_dir("results")
    set_working_directory("reporting")
    subprocess.call(["Rscript", "check_results.R"])


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

    if args.debug:
        os.environ['Debug'] = 'true'
    if args.build or args.test or no_args:
        build(config, args.skip_tests and not args.test)
    if args.measure or no_args:
        benchmark(config)
    if args.visualize or no_args:
        visualize()
    if args.check or no_args:
        check_results()
