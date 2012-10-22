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

/**
 * Benchmark configuration data holder class. Contains information about the number of warmup runs
 * and timed runs to execute for a benchmark.
 * 
 * @author patrick.peschlow
 */
public class BenchmarkConfig {

    private static final int DEFAULT_NUM_WARMUP_RUNS = 10;

    private static final int DEFAULT_NUM_TIMES_RUNS = 100;

    private final int numWarmupRuns;

    private final int numTimedRuns;

    public BenchmarkConfig() {
	this(DEFAULT_NUM_WARMUP_RUNS, DEFAULT_NUM_TIMES_RUNS);
    }

    public BenchmarkConfig(int numWarmupRuns, int numTimedRuns) {
	this.numWarmupRuns = numWarmupRuns;
	this.numTimedRuns = numTimedRuns;
    }

    public int getNumWarmupRuns() {
	return numWarmupRuns;
    }

    public int getNumTimedRuns() {
	return numTimedRuns;
    }
}
