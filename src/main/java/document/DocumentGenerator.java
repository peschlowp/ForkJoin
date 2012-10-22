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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Document generator that creates documents according to the specified configuration. All
 * randomness is based on the seed provided, so that the exact same data can be computed whenever
 * desired.
 * 
 * @author patrick.peschlow
 */
public class DocumentGenerator {

    private final int numDocuments;

    private final int maxUsersPerDocument;

    private final int maxTagsPerDocument;

    private final String[] allUsers;

    private final String[] allTags;

    private final long seed;

    private final UnbalancedConfig unbalancedConfig;

    private final Random random;

    public DocumentGenerator(DocumentGeneratorConfig config) {
	numDocuments = config.getNumDocuments();
	maxUsersPerDocument = config.getMaxUsersPerDocument();
	maxTagsPerDocument = config.getMaxTagsPerDocument();
	allUsers = new String[config.getUsers().size()];
	config.getUsers().toArray(allUsers);
	allTags = new String[config.getTags().size()];
	config.getTags().toArray(allTags);
	seed = config.getSeed();
	unbalancedConfig = config.getUnbalancedConfig();
	random = new Random(seed);
    }

    public Document[] generateDocuments() {
	System.out.println("Generating an array of " + numDocuments + " documents with seed "
		+ seed);

	Document[] documents = new Document[numDocuments];
	for (int i = 0; i < numDocuments; i++) {
	    Set<String> documentUsers = generateDocumentUsers(i);
	    List<String> documentTags = generateDocumentTags(i);
	    documents[i] = new Document(documentUsers, documentTags);
	}

	return documents;
    }

    private Set<String> generateDocumentUsers(int i) {
	Set<String> documentUsers = new HashSet<>();
	int numUsers = random.nextInt(maxUsersPerDocument) + 1;
	if (numUsers == 0) {
	    return new HashSet<>();
	}

	if (isUnbalancedDocument(i)) {
	    numUsers = Math.min(numUsers * unbalancedConfig.getFactor(), allUsers.length);
	}
	shuffle(allUsers);
	for (int j = 0; j < numUsers; j++) {
	    documentUsers.add(allUsers[j]);
	}
	return documentUsers;
    }

    private List<String> generateDocumentTags(int i) {
	List<String> documentTags = new ArrayList<>();
	int numTags = random.nextInt(maxTagsPerDocument) + 1;
	if (numTags == 0) {
	    return new ArrayList<>();
	}

	if (isUnbalancedDocument(i)) {
	    numTags = Math.min(numTags * unbalancedConfig.getFactor(), allTags.length);
	}
	shuffle(allTags);
	for (int j = 0; j < numTags; j++) {
	    documentTags.add(allTags[j]);
	}
	return documentTags;
    }

    private boolean isUnbalancedDocument(int i) {
	return unbalancedConfig != null && i >= unbalancedConfig.getUnbalancedIntervalLo()
		&& i < unbalancedConfig.getUnbalancedIntervalHi();
    }

    private void shuffle(String[] array) {
	for (int i = 0; i < array.length; i++) {
	    int dst = random.nextInt(array.length);
	    String tmp = array[i];
	    array[i] = array[dst];
	    array[dst] = tmp;
	}
    }
}
