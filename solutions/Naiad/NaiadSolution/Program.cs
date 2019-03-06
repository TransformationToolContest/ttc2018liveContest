#define DO_NAIAD_STUFF
//#undef DO_NAIAD_STUFF
using Microsoft.Research.Naiad;
using Microsoft.Research.Naiad.Diagnostics;
using NMF.Models.Changes;
using NMF.Models.Repository;
using System;
using System.Diagnostics;
using System.IO;
using TTC2018.LiveContest.SocialNetwork;

//[assembly: ModelMetadata("https://www.transformation-tool-contest.eu/2018/social_media", "Naiad.social_network.nmeta")]

namespace Naiad
{
    class Program
    {
        private static ModelRepository repository;

        private static string ChangePath;
        private static string RunIndex;
        private static int Sequences;
        private static string Tool;
        private static string ChangeSet;
        private static string Query;
        private static bool Debug = false;

        private static Stopwatch stopwatch = new Stopwatch();

        private static NaiadSolutionBase solution;

        static void Main(string[] args)
        {
            Initialize(args);
            Load();
            Initial();
            for (int i = 1; i <= Sequences; i++)
            {
                Update(i);
            }
            //repository.Save(solution.SocialNetwork, "a.xml");
            solution.Dispose();
        }

        static void Load()
        {
            stopwatch.Restart();
            solution.SocialNetwork = repository.Resolve(Path.Combine(ChangePath, "initial.xmi")).RootElements[0] as SocialNetworkRoot;
            stopwatch.Stop();
            Report(BenchmarkPhase.Load);
        }

        static void Initialize(string[] args)
        {
            stopwatch.Restart();
            repository = new ModelRepository();

            ChangePath = Environment.GetEnvironmentVariable(nameof(ChangePath));
            RunIndex = Environment.GetEnvironmentVariable(nameof(RunIndex));
            Sequences = int.Parse(Environment.GetEnvironmentVariable(nameof(Sequences)));
            Tool = Environment.GetEnvironmentVariable(nameof(Tool));
            ChangeSet = Environment.GetEnvironmentVariable(nameof(ChangeSet));
            Query = Environment.GetEnvironmentVariable(nameof(Query)).ToUpperInvariant();
            var debug = Environment.GetEnvironmentVariable(nameof(Debug));

            if (!(debug is null))
            {
                Debug = bool.Parse(debug);
            }
            if (Debug)
            {
                Logging.LogStyle = LoggingStyle.File;
                Logging.LogLevel = LoggingLevel.Debug;
            }
            else
            {
                Logging.LogStyle = LoggingStyle.Console;
                Logging.LogLevel = LoggingLevel.Off;
            }
            if (Query == "Q1")
            {
                solution = new NaiadSolutionQ1(args);
            }
            else if (Query == "Q2")
            {
                solution = new NaiadSolutionQ2(args);
            }
            if (solution == null)
            {
                throw new ApplicationException("Query is unknown");
            }

            stopwatch.Stop();
            Report(BenchmarkPhase.Initialization);
        }

        static void Initial()
        {
            stopwatch.Restart();
#if DO_NAIAD_STUFF
            var result = solution.Initial();
#else
            var result = "";
#endif
            stopwatch.Stop();
            Report(BenchmarkPhase.Initial, null, result);
        }

        static void Update(int iteration)
        {
            var path = Path.Combine(ChangePath, $"change{iteration:00}.xmi");
            var modelRepos = repository.Resolve(path);
            var changes = modelRepos.RootElements[0] as ModelChangeSet;
            stopwatch.Restart();
#if DO_NAIAD_STUFF
            var result = solution.Update(changes);
#else
            var result = "";
#endif
            stopwatch.Stop();
            changes.Apply();
            Report(BenchmarkPhase.Update, iteration, result);
        }

        static void Report(BenchmarkPhase phase, int? iteration = null, string result = null)
        {
            GC.Collect();
            Console.WriteLine($"{Tool};{Query};{ChangeSet};{RunIndex};{iteration ?? 0};{phase};Time;{stopwatch.Elapsed.Ticks * 100}");
            Console.WriteLine($"{Tool};{Query};{ChangeSet};{RunIndex};{iteration ?? 0};{phase};Memory;{Environment.WorkingSet}");
            if (result != null)
            {
                Console.WriteLine($"{Tool};{Query};{ChangeSet};{RunIndex};{iteration ?? 0};{phase};Elements;{result}");
            }
        }
    }

    public enum BenchmarkPhase
    {
        Initialization,
        Load,
        Initial,
        Update
    }
}
