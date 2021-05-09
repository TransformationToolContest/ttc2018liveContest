using NMF.Models;
using NMF.Models.Changes;
using NMF.Models.Repository;
using System;
using System.Diagnostics;
using System.IO;
using TTC2018.LiveContest.SocialNetwork;

[assembly: ModelMetadata("https://www.transformation-tool-contest.eu/2018/social_media", "TTC2018.LiveContest.social_network.nmeta")]

namespace TTC2018.LiveContest
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
        private static string Mode;

        private static Stopwatch stopwatch = new Stopwatch();

        private static Solution solution;

        static void Main(string[] args)
        {
            if (args.Length == 0)
            {
                Mode = "Batch".ToUpperInvariant();
            }
            else
            {
                Mode = args[0].ToUpperInvariant();
            }
            Initialize();
            Load();
            Initial();
            for (int i = 1; i <= Sequences; i++)
            {
                Update(i);
            }
        }

        static void Load()
        {
            stopwatch.Restart();
            solution.SocialNetwork = repository.Resolve(Path.Combine(ChangePath, "initial.xmi")).RootElements[0] as SocialNetworkRoot;
            stopwatch.Stop();
            Report(BenchmarkPhase.Load);
        }

        static void Initialize()
        {
            stopwatch.Restart();
            repository = new ModelRepository();

            ChangePath = Environment.GetEnvironmentVariable(nameof(ChangePath));
            RunIndex = Environment.GetEnvironmentVariable(nameof(RunIndex));
            Sequences = int.Parse(Environment.GetEnvironmentVariable(nameof(Sequences)));
            Tool = Environment.GetEnvironmentVariable(nameof(Tool));
            ChangeSet = Environment.GetEnvironmentVariable(nameof(ChangeSet));
            Query = Environment.GetEnvironmentVariable(nameof(Query)).ToUpperInvariant();
            if (Query == "Q1")
            {
                switch (Mode)
                {
                    case "BATCH":
                        solution = new BatchSolutionQ1();
                        break;
                    case "INCREMENTAL":
                        solution = new IncrementalSolutionQ1();
                        break;
                    case "OPTIMIZED":
                        solution = new OptimizedIncrementalSolutionQ1();
                        break;
                    case "TRANSACTIONS":
                        solution = new TransactionalSolutionQ1();
                        break;
                    case "PARALLEL":
                        solution = new ParallelSolutionQ1();
                        break;
                    default:
                        break;
                }
            }
            else if (Query == "Q2")
            {
                switch (Mode)
                {
                    case "BATCH":
                        solution = new BatchSolutionQ2();
                        break;
                    case "INCREMENTAL":
                        solution = new IncrementalSolutionQ2();
                        break;
                    case "OPTIMIZED":
                        solution = new OptimizedIncrementalSolutionQ2();
                        break;
                    case "TRANSACTIONS":
                        solution = new TransactionalSolutionQ2();
                        break;
                    case "PARALLEL":
                        solution = new ParallelSolutionQ2();
                        break;
                    default:
                        break;
                }
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
            var result = solution.Initial();
            stopwatch.Stop();
            Report(BenchmarkPhase.Initial, null, result);
        }

        static void Update(int iteration)
        {
            var changes = repository.Resolve(Path.Combine(ChangePath, $"change{iteration:00}.xmi")).RootElements[0] as ModelChangeSet;
            stopwatch.Restart();
            var result = solution.Update(changes);
            stopwatch.Stop();
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