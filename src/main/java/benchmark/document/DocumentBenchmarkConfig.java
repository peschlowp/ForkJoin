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

package benchmark.document;

/**
 * Default configuration for the document benchmark.
 * 
 * @author patrick.peschlow
 */
public class DocumentBenchmarkConfig {

    public static enum Strategy {
	SEQUENTIAL, FORKJOIN, THREADPOOL;
    }

    /** Number of warmup runs. */
    public static int NUM_WARMUP_RUNS = 10;

    /** Number of timed runs. */
    public static int NUM_TIMED_RUNS = 20;

    /** Sequential/parallel execution strategy. */
    public static Strategy STRATEGY = Strategy.THREADPOOL;

    /** Number of threads for parallel benchmarks. */
    public static int NUM_THREADS = 4;

    /** Threshold for the number of documents below which a task will be computed directly. */
    public static int FORKJOIN_CUTOFF = 10_000;
}
