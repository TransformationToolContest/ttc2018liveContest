package de.tudresden.inf.st.ttc18live;

import de.tudresden.inf.st.ttc18live.jastadd.model.ModelChangeSet;
import de.tudresden.inf.st.ttc18live.jastadd.model.SocialNetwork;

import java.nio.file.Path;

/**
 * Abstract Driver for a JastAdd solution.
 *
 * @author rschoene - Initial contribution
 */
public abstract class AbstractLiveContestDriver {

  private final Boolean Debug;
  private final String ChangePath;
  private String RunIndex;
  private int Sequences;
  private String Tool;
  private final String ChangeSet;
  private final String Query;

  private long stopwatch;

  private Solution solution;
  private boolean traceEvents;
  private Path pathOfEventFile;

  AbstractLiveContestDriver() {
    ChangeSet = System.getenv("ChangeSet");
    ChangePath = System.getenv("ChangePath");
    Debug = Boolean.valueOf(System.getenv("Debug"));
    Query = System.getenv("Query").toUpperCase();
  }

  AbstractLiveContestDriver enableTracing(Path pathOfEventFile) {
    this.pathOfEventFile = pathOfEventFile;
    this.traceEvents = true;
    return this;
  }

  enum BenchmarkPhase {
    Initialization,
    Load,
    Initial,
    Update
  }

  public void mainImpl() {
    // remove console logger
    try {
      Initialize();
      Load();
      Initial();
      for (int i = 1; i <= Sequences; i++) {
        Update(i);
      }
      if (traceEvents) {
        solution.getSocialNetwork().writeTracingEvents(pathOfEventFile);
      }
      System.err.println();
    } catch (Exception e) {
      System.err.println();
      e.printStackTrace();
    }
  }

  String getChangePath() {
    return ChangePath;
  }

  Solution getSolution() {
    return solution;
  }

  String getChangeSet() {
    return ChangeSet;
  }

  String getQuery() {
    return Query;
  }

  private void Initialize() throws Exception {
    stopwatch = System.nanoTime();
    RunIndex = System.getenv("RunIndex");
    Sequences = Integer.parseInt(System.getenv("Sequences"));
    Tool = System.getenv("Tool");

    InitializeSpecial();

    if (Query.contentEquals("Q1")) {
      solution = new SolutionQ1();
    } else if (Query.contentEquals("Q2")) {
      solution = new SolutionQ2();
    } else {
      throw new Exception("Query is unknown");
    }

    stopwatch = System.nanoTime() - stopwatch;
    Report(BenchmarkPhase.Initialization, -1, null);
  }

  protected abstract void InitializeSpecial();

  private void Load() throws Exception {
    stopwatch = System.nanoTime();

    SocialNetwork socialNetwork = LoadImpl();
    socialNetwork.debug = Debug;
    if (traceEvents) {
      socialNetwork.enableTracing();
    }

    solution.setSocialNetwork(socialNetwork);
    stopwatch = System.nanoTime() - stopwatch;
    Report(BenchmarkPhase.Load, -1, null);
  }

  abstract SocialNetwork LoadImpl() throws Exception;

  public void Initial() {
    if (traceEvents) {
      solution.getSocialNetwork().insertCustomEvent("TTC_INITIAL", ChangeSet);
    }
    stopwatch = System.nanoTime();
    String result = solution.Initial();
    stopwatch = System.nanoTime() - stopwatch;
    Report(BenchmarkPhase.Initial, -1, result);

  }

  private void Update(int iteration) throws Exception {
    System.err.print(iteration + " ");

    String size_iteration = ChangeSet + iteration;
    if (traceEvents) {
      solution.getSocialNetwork().insertCustomEvent("TTC_TRANSFORMATION", size_iteration);
    }
    ModelChangeSet modelChangeSet = UpdateImpl(iteration);

    if (traceEvents) {
      solution.getSocialNetwork().insertCustomEvent("TTC_RECHECK", size_iteration);
    }
    stopwatch = System.nanoTime();
    String result = solution.Update(modelChangeSet);
    stopwatch = System.nanoTime() - stopwatch;
    Report(BenchmarkPhase.Update, iteration, result);
  }

  abstract ModelChangeSet UpdateImpl(int iteration) throws Exception;

  private void Report(BenchmarkPhase phase, int iteration, String result) {
    String iterationStr;
    if (iteration == -1) {
      iterationStr = "0";
    } else {
      iterationStr = Integer.toString(iteration);
    }
    System.out.printf("%s;%s;%s;%s;%s;%s;Time;%s%n",
        Tool, Query, ChangeSet, RunIndex, iterationStr, phase.toString(), stopwatch);
//    Runtime.getRuntime().gc();
//    Runtime.getRuntime().gc();
//    Runtime.getRuntime().gc();
//    Runtime.getRuntime().gc();
//    Runtime.getRuntime().gc();
//    long memoryUsed = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
//    System.out.printf("%s;%s;%s;%s;%s;%s;Memory;%s%n",
//        Tool, Query, ChangeSet, RunIndex, iterationStr, phase.toString(), memoryUsed);
    if (result != null) {
      System.out.printf("%s;%s;%s;%s;%s;%s;Elements;%s%n",
          Tool, Query, ChangeSet, RunIndex, iterationStr, phase.toString(), result);
    }
  }

}
