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

import java.util.Properties;

import util.PropertiesLoader;
import benchmark.document.DocumentBenchmarkConfig.Strategy;
import document.DefaultDocumentGeneratorConfig;

/**
 * Reads and evaluates the config file for the document scenario.
 * 
 * @author patrick.peschlow
 */
public class DocumentConfigFileReader {

    public static void evaluateConfigFile(String configFile) {
	System.out.println("Trying to load properties from file " + configFile);

	Properties properties = PropertiesLoader.loadPropertiesFromFile(configFile);

	if (!properties.isEmpty()) {
	    System.out.println("Found properties! Updating configuration!");
	}

	evaluateBenchmarkProperties(properties);

	evaluateDocumentGeneratorProperties(properties);
    }

    private static void evaluateBenchmarkProperties(Properties properties) {
	Integer numThreads = PropertiesLoader.getIntegerProperty(properties, "numThreads");
	if (numThreads != null) {
	    DocumentBenchmarkConfig.NUM_THREADS = numThreads.intValue();
	}

	Integer numWarmupRuns = PropertiesLoader.getIntegerProperty(properties, "numWarmupRuns");
	if (numWarmupRuns != null) {
	    DocumentBenchmarkConfig.NUM_WARMUP_RUNS = numWarmupRuns.intValue();
	}

	Integer numTimedRuns = PropertiesLoader.getIntegerProperty(properties, "numTimedRuns");
	if (numTimedRuns != null) {
	    DocumentBenchmarkConfig.NUM_TIMED_RUNS = numTimedRuns.intValue();
	}

	Integer forkJoinCutoff = PropertiesLoader.getIntegerProperty(properties, "forkJoinCutoff");
	if (forkJoinCutoff != null) {
	    DocumentBenchmarkConfig.FORKJOIN_CUTOFF = forkJoinCutoff.intValue();
	}

	String strategy = PropertiesLoader.getStringProperty(properties, "strategy");
	if (strategy != null) {
	    DocumentBenchmarkConfig.STRATEGY = (strategy.equals("sequential") ? Strategy.SEQUENTIAL
		    : (strategy.equals("threadpool") ? Strategy.THREADPOOL : Strategy.FORKJOIN));
	}
    }

    private static void evaluateDocumentGeneratorProperties(Properties properties) {
	Integer numDocuments = PropertiesLoader.getIntegerProperty(properties, "numDocuments");
	if (numDocuments != null) {
	    DefaultDocumentGeneratorConfig.NUM_DOCUMENTS = numDocuments.intValue();
	}

	Integer randomSeed = PropertiesLoader.getIntegerProperty(properties, "randomSeed");
	if (randomSeed != null) {
	    DefaultDocumentGeneratorConfig.RANDOM_SEED = randomSeed.intValue();
	}

	Integer maxUsersPerDocument = PropertiesLoader.getIntegerProperty(properties,
		"maxUsersPerDocument");
	if (maxUsersPerDocument != null) {
	    DefaultDocumentGeneratorConfig.MAX_USERS_PER_DOCUMENT = maxUsersPerDocument.intValue();
	}

	Integer maxTagsPerDocument = PropertiesLoader.getIntegerProperty(properties,
		"maxTagsPerDocument");
	if (maxTagsPerDocument != null) {
	    DefaultDocumentGeneratorConfig.MAX_TAGS_PER_DOCUMENT = maxTagsPerDocument.intValue();
	}

	Boolean useUnbalanced = PropertiesLoader.getBooleanProperty(properties, "useUnbalanced");
	if (useUnbalanced != null) {
	    DefaultDocumentGeneratorConfig.USE_UNBALANCED = useUnbalanced.booleanValue();
	}

	Integer unbalancedIntervalLo = PropertiesLoader.getIntegerProperty(properties,
		"unbalancedIntervalLo");
	if (unbalancedIntervalLo != null) {
	    DefaultDocumentGeneratorConfig.UNBALANCED_INTERVAL_LO = unbalancedIntervalLo.intValue();
	}

	Integer unbalancedIntervalHi = PropertiesLoader.getIntegerProperty(properties,
		"unbalancedIntervalHi");
	if (unbalancedIntervalHi != null) {
	    DefaultDocumentGeneratorConfig.UNBALANCED_INTERVAL_HI = unbalancedIntervalHi.intValue();
	}

	Integer unbalancedFactor = PropertiesLoader.getIntegerProperty(properties,
		"unbalancedFactor");
	if (unbalancedFactor != null) {
	    DefaultDocumentGeneratorConfig.UNBALANCED_FACTOR = unbalancedFactor.intValue();
	}
    }
}
