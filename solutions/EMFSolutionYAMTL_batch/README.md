# EMFSolutionYAMTL_batch

Solution with batch execution of model query for the [TTC'18 live contest](https://github.com/TransformationToolContest/ttc2018liveContest).

Requires Java 11.

To generate jar file, run `./gradlew clean fatJar` from command line.


## Differences with EMFSolutionYAMTL (incremental execution)

This solution shares the implementation of queries with the solution [EMFSolutionYAMTL](../EMFSolutionYAMTL/) and differs in how it is executed in files [SolutionQ1](src/main/java/ttc2018/SolutionQ1.xtend) and [SolutionQ2](src/main/java/ttc2018/SolutionQ2.xtend):
when propagating changes, the source update (delta) is applied to the source model and the query is applied from scratch.   