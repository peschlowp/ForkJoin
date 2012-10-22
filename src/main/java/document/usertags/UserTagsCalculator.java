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

package document.usertags;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import document.Document;

/**
 * Business logic for the computation of user tag sets. Provides a method to compute the user tags
 * sets across a set of documents ({@link #compute(Document[], int, int)}) and to combine (reduce)
 * several such user tag bundles into one ( {@link #reduce(UserTagsBundle, UserTagsBundle)}).
 * 
 * @author patrick.peschlow
 */
public class UserTagsCalculator {

    /** Whether to lowercase all tags in order to eliminate duplicates. */
    private static final boolean LOWERCASE_TAGS = true;

    public static UserTagsBundle compute(Document[] documents, int lo, int hi) {
	Map<String, Set<String>> map = new HashMap<>();
	for (int i = lo; i < hi; i++) {
	    Document document = documents[i];
	    for (String user : document.getUsers()) {
		addTagsToSet(getUserTags(map, user), document.getTags());
	    }
	}

	return new UserTagsBundle(map);
    }

    private static void addTagsToSet(Set<String> userTags, List<String> tags) {
	if (LOWERCASE_TAGS) {
	    for (String tag : tags) {
		userTags.add(tag.toLowerCase());
	    }
	} else {
	    userTags.addAll(tags);
	}
    }

    private static Set<String> getUserTags(Map<String, Set<String>> map, String user) {
	Set<String> userTags = map.get(user);
	if (userTags == null) {
	    userTags = new HashSet<>();
	    map.put(user, userTags);
	}

	return userTags;
    }

    public static UserTagsBundle reduce(UserTagsBundle bundle1, UserTagsBundle bundle2) {
	Map<String, Set<String>> map1 = bundle1.getUserToTags();
	Map<String, Set<String>> map2 = bundle2.getUserToTags();
	for (Entry<String, Set<String>> entry : map2.entrySet()) {
	    String user = entry.getKey();
	    Set<String> map1UserTags = map1.get(user);
	    Set<String> map2UserTags = entry.getValue();
	    if (map1UserTags == null) {
		map1.put(user, map2UserTags);
	    } else {
		map1UserTags.addAll(map2UserTags);
	    }
	}

	return bundle1;
    }
}
