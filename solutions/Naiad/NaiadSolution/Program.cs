﻿using NMF.Models.Changes;
using NMF.Models.Repository;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TTC2018.LiveContest;
using TTC2018.LiveContest.SocialNetwork;

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
                solution = new NaiadSolutionQ1();
            }
            else if (Query == "Q2")
            {
                solution = new NaiadSolutionQ2();
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