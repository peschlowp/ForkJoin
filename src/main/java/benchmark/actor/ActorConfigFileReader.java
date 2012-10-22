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

import java.util.Properties;

import util.PropertiesLoader;
import benchmark.actor.ActorBenchmarkConfig.Strategy;

/**
 * Reads and evaluates the config file for the actor scenario.
 * 
 * @author patrick.peschlow
 */
public class ActorConfigFileReader {

    public static void evaluateConfigFile(String configFile) {
	System.out.println("Trying to load properties from file " + configFile);

	Properties properties = PropertiesLoader.loadPropertiesFromFile(configFile);

	if (!properties.isEmpty()) {
	    System.out.println("Found properties! Updating configuration!");
	}

	evaluateBenchmarkProperties(properties);

	evaluateActorGeneratorProperties(properties);
    }

    private static void evaluateBenchmarkProperties(Properties properties) {
	Integer numThreads = PropertiesLoader.getIntegerProperty(properties, "numThreads");
	if (numThreads != null) {
	    ActorBenchmarkConfig.NUM_THREADS = numThreads.intValue();
	}

	Integer numWarmupRuns = PropertiesLoader.getIntegerProperty(properties, "numWarmupRuns");
	if (numWarmupRuns != null) {
	    ActorBenchmarkConfig.NUM_WARMUP_RUNS = numWarmupRuns.intValue();
	}

	Integer numTimedRuns = PropertiesLoader.getIntegerProperty(properties, "numTimedRuns");
	if (numTimedRuns != null) {
	    ActorBenchmarkConfig.NUM_TIMED_RUNS = numTimedRuns.intValue();
	}

	String strategy = PropertiesLoader.getStringProperty(properties, "strategy");
	if (strategy != null) {
	    ActorBenchmarkConfig.STRATEGY = (strategy.equals("sequential") ? Strategy.SEQUENTIAL
		    : (strategy.equals("threadpool") ? Strategy.THREADPOOL : Strategy.FORKJOIN));
	}

	Integer maxConsumeBurst = PropertiesLoader
		.getIntegerProperty(properties, "maxConsumeBurst");
	if (maxConsumeBurst != null) {
	    ActorBenchmarkConfig.MAX_CONSUME_BURST = maxConsumeBurst.intValue();
	}

	Boolean forkJoinAsyncMode = PropertiesLoader.getBooleanProperty(properties,
		"forkJoinAsyncMode");
	if (forkJoinAsyncMode != null) {
	    ActorBenchmarkConfig.FORKJOIN_ASYNC_MODE = forkJoinAsyncMode.booleanValue();
	}
    }

    private static void evaluateActorGeneratorProperties(Properties properties) {
	Integer numActors = PropertiesLoader.getIntegerProperty(properties, "numActors");
	if (numActors != null) {
	    DefaultActorGeneratorConfig.NUM_ACTORS = numActors.intValue();
	}

	Integer randomSeed = PropertiesLoader.getIntegerProperty(properties, "randomSeed");
	if (randomSeed != null) {
	    DefaultActorGeneratorConfig.RANDOM_SEED = randomSeed.intValue();
	}

	Integer numInitialMessages = PropertiesLoader.getIntegerProperty(properties,
		"numInitialMessages");
	if (numInitialMessages != null) {
	    DefaultActorGeneratorConfig.NUM_INITIAL_MESSAGES = numInitialMessages.intValue();
	}

	Integer messageTtl = PropertiesLoader.getIntegerProperty(properties, "messageTtl");
	if (messageTtl != null) {
	    DefaultActorGeneratorConfig.MESSAGE_TTL = messageTtl.intValue();
	}

	Integer numDummyWorkIterations = PropertiesLoader.getIntegerProperty(properties,
		"numDummyWorkIterations");
	if (numDummyWorkIterations != null) {
	    ActorBenchmarkConfig.NUM_DUMMY_WORK_ITERATIONS = numDummyWorkIterations.intValue();
	}
    }
}
