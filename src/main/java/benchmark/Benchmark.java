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

package benchmark;

import java.util.concurrent.TimeUnit;

/**
 * General benchmark execution class. Needs to be initialized with a BenchmarkConfig object.
 * Executes a BenchmarkDef that determines the actual actions to perform before, during, and after
 * the benchmark. Prints timing information for each single run of the benchmark, and stores any
 * results back in the BenchmarkDef object.
 * 
 * @author patrick.peschlow
 */
public class Benchmark {

    private final BenchmarkConfig config;

    public Benchmark(BenchmarkConfig config) {
	this.config = config;
    }

    public void execute(BenchmarkDef def) {
	def.beforeAll();

	runWarmup(def);

	runTimed(def);

	def.afterAll();
    }

    private void runWarmup(BenchmarkDef def) {
	System.out.println("Starting " + config.getNumWarmupRuns() + " warmup runs");

	for (int i = 0, num = config.getNumWarmupRuns(); i < num; i++) {
	    def.before();
	    def.execute();
	    def.after();
	}
    }

    private void runTimed(BenchmarkDef def) {
	System.out.println("Starting " + config.getNumTimedRuns() + " timed runs");

	for (int i = 0, num = config.getNumTimedRuns(); i < num; i++) {
	    def.before();

	    long startTime = System.nanoTime();

	    Object result = def.execute();

	    long endTime = System.nanoTime();

	    def.after();

	    long durationMillis = TimeUnit.MILLISECONDS.convert(endTime - startTime,
		    TimeUnit.NANOSECONDS);

	    System.out.println("Run " + i + " took " + durationMillis + " ms");

	    def.addResult(durationMillis, result);
	}
    }
}
