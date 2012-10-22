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

package document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Default configuration for the document generator. Uses a set of random names for the possible
 * users of documents, and uses a list of buzzwords taken from Wikipedia to define the possible set
 * of tags for the documents.
 * 
 * @author patrick.peschlow
 */
public class DefaultDocumentGeneratorConfig extends DocumentGeneratorConfig {

    /** Number of documents used. */
    public static int NUM_DOCUMENTS = 3_000_000;

    /** Random seed for the scenario. */
    public static int RANDOM_SEED = 1234;

    /** Maximum number of users per document. */
    public static int MAX_USERS_PER_DOCUMENT = 3;

    /** Maximum number of tags per document. */
    public static int MAX_TAGS_PER_DOCUMENT = 3;

    /** Document ID where the unbalanced section starts. */
    public static int UNBALANCED_INTERVAL_LO = 500_000;

    /** Document ID where the unbalanced section ends. */
    public static int UNBALANCED_INTERVAL_HI = 1_000_000;

    /**
     * Factor that defines the maximum number of users and tags associated with the documents in the
     * unbalanced section.
     */
    public static int UNBALANCED_FACTOR = 4;

    /** Whether to use the unbalanced section at all. */
    public static boolean USE_UNBALANCED = false;

    public DefaultDocumentGeneratorConfig() {
	super(NUM_DOCUMENTS, loadTextFromClasspathResource("names.txt"),
		loadTextFromClasspathResource("buzzwords.txt"), MAX_USERS_PER_DOCUMENT,
		MAX_TAGS_PER_DOCUMENT, RANDOM_SEED, USE_UNBALANCED ? new UnbalancedConfig(
			UNBALANCED_INTERVAL_LO, UNBALANCED_INTERVAL_HI, UNBALANCED_FACTOR) : null);
    }

    private static List<String> loadTextFromClasspathResource(String filename) {
	InputStream is = DefaultDocumentGeneratorConfig.class.getClassLoader().getResourceAsStream(
		filename);
	if (is == null) {
	    throw new IllegalStateException("Could not find file " + filename + " on classpath!");
	}

	List<String> words = new ArrayList<>();
	try (Reader isr = new InputStreamReader(is, Charset.forName("UTF-8"));
		BufferedReader br = new BufferedReader(isr);) {
	    String line;
	    while ((line = br.readLine()) != null) {
		words.add(line);
	    }
	} catch (IOException e) {
	    throw new IllegalStateException("Could not read from file " + filename, e);
	}

	return words;
    }
}
