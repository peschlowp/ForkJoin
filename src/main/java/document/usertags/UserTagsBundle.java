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

import java.util.Map;
import java.util.Set;

/**
 * Simple data holder class for maps of user names to tag sets.
 */
public class UserTagsBundle {

    private final Map<String, Set<String>> userToTags;

    public UserTagsBundle(Map<String, Set<String>> userToTags) {
	this.userToTags = userToTags;
    }

    public Map<String, Set<String>> getUserToTags() {
	return userToTags;
    }
}
