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

/**
 * Default configuration for the actor benchmark.
 * 
 * @author patrick.peschlow
 */
public class ActorBenchmarkConfig {

    public static enum Strategy {
	SEQUENTIAL, FORKJOIN, THREADPOOL;
    }

    /** Number of warmup runs. */
    public static int NUM_WARMUP_RUNS = 10;

    /** Number of timed runs. */
    public static int NUM_TIMED_RUNS = 20;

    /** Sequential/parallel execution strategy. */
    public static Strategy STRATEGY = Strategy.THREADPOOL;

    /** Whether the fork/join pool is started in async mode. */
    public static boolean FORKJOIN_ASYNC_MODE = false;

    /** Number of threads for parallel benchmarks. */
    public static int NUM_THREADS = 4;

    /** Maximum number of messages that may be consumed by an actor within one execution session. */
    public static int MAX_CONSUME_BURST = 1;

    /** Number of dummy work iterations performed by an actor during execution. */
    public static int NUM_DUMMY_WORK_ITERATIONS = 0;

    /** Whether debug mode is enabled. */
    public static final boolean DEBUG = false;

    /** Whether verbose logging is enabled. */
    public static final boolean VERBOSE = false;
}
