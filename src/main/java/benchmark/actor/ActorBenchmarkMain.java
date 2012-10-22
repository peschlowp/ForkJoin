/*
 * Copyright 2012 Patrick Peschlow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package benchmark.actor;

import util.Statistics;
import benchmark.Benchmark;
import benchmark.BenchmarkConfig;
import benchmark.BenchmarkDef;
import benchmark.actor.forkjoin.ForkJoinActorBenchmark;
import benchmark.actor.sequential.SequentialActorBenchmark;
import benchmark.actor.threadpool.ThreadpoolActorBenchmark;
import benchmark.run.MainWrapper;

/**
 * Main class for the actor benchmark. Defines and initializes the benchmark, runs the benchmark
 * using the configured strategy (sequential or one of the parallel variants) and finally prints
 * result statistics.
 * 
 * @author patrick.peschlow
 */
public class ActorBenchmarkMain {

    /**
     * Helper main method to directly run this benchmark without additional configuration. For real
     * tests, the recommend way is running the benchmark via the {@link MainWrapper} class.
     * 
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
	go();
    }

    public static void go(String configFile) {
	if (configFile != null) {
	    ActorConfigFileReader.evaluateConfigFile(configFile);
	}
	go();
    }

    public static void go() {
	BenchmarkDef benchmarkDef = defineBenchmark();

	Benchmark benchmark = initBenchmark();

	runBenchmark(benchmarkDef, benchmark);

	printStatistics(benchmarkDef);
    }

    private static BenchmarkDef defineBenchmark() {
	ActorGenerator actorGenerator = new ActorGenerator(new DefaultActorGeneratorConfig());

	switch (ActorBenchmarkConfig.STRATEGY) {
	case SEQUENTIAL:
	    return new SequentialActorBenchmark(actorGenerator);
	case FORKJOIN:
	    return new ForkJoinActorBenchmark(actorGenerator, ActorBenchmarkConfig.NUM_THREADS);
	case THREADPOOL:
	    return new ThreadpoolActorBenchmark(actorGenerator, ActorBenchmarkConfig.NUM_THREADS);
	default:
	    throw new IllegalStateException("Unknown benchmark strategy");
	}
    }

    private static Benchmark initBenchmark() {
	BenchmarkConfig benchmarkConfig = new BenchmarkConfig(ActorBenchmarkConfig.NUM_WARMUP_RUNS,
		ActorBenchmarkConfig.NUM_TIMED_RUNS);
	return new Benchmark(benchmarkConfig);
    }

    private static void runBenchmark(BenchmarkDef benchmarkDef, Benchmark benchmark) {
	System.out.println("Starting the benchmark");

	benchmark.execute(benchmarkDef);
    }

    private static void printStatistics(BenchmarkDef benchmarkDef) {
	double mean = Statistics.computeSampleMean(benchmarkDef.getTimes());
	double stdDev = Statistics.computeStdDev(benchmarkDef.getTimes(), mean);

	System.out.println("Benchmark took an average time of " + mean + " ms (standard deviation "
		+ stdDev + ")");
    }
}
