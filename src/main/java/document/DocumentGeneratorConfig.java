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

import java.util.List;

/**
 * Data holder class that stores the configuration for the document generator.
 * 
 * @author patrick.peschlow
 */
public class DocumentGeneratorConfig {

    private final int numDocuments;

    private final List<String> users;

    private final List<String> tags;

    private final int maxUsersPerDocument;

    private final int maxTagsPerDocument;

    private final long seed;

    private final UnbalancedConfig unbalancedConfig;

    public DocumentGeneratorConfig(int numDocuments, List<String> users, List<String> tags,
	    int maxUsersPerDocument, int maxTagsPerDocument, long seed,
	    UnbalancedConfig unbalancedConfig) {
	this.numDocuments = numDocuments;
	this.users = users;
	this.tags = tags;
	this.maxUsersPerDocument = maxUsersPerDocument;
	this.maxTagsPerDocument = maxTagsPerDocument;
	this.seed = seed;
	this.unbalancedConfig = unbalancedConfig;
    }

    public int getNumDocuments() {
	return numDocuments;
    }

    public List<String> getUsers() {
	return users;
    }

    public List<String> getTags() {
	return tags;
    }

    public int getMaxUsersPerDocument() {
	return maxUsersPerDocument;
    }

    public int getMaxTagsPerDocument() {
	return maxTagsPerDocument;
    }

    public long getSeed() {
	return seed;
    }

    public UnbalancedConfig getUnbalancedConfig() {
	return unbalancedConfig;
    }
}
