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

package benchmark.document.forkjoin;

import mapreduce.Output;
import document.usertags.UserTagsBundle;
import document.usertags.UserTagsCalculator;

/**
 * Output entity for a document fork/join task. The entity carries a {@link UserTagsBundle} which
 * contains tag sets for users. When this entity is to be reduced with another entity, it calls the
 * {@link UserTagsCalculator#reduce(UserTagsBundle, UserTagsBundle)} method which contains the
 * business logic. The result of the reduce operation is stored in this entity.
 * 
 * @author patrick.peschlow
 */
public class DocumentOutput implements Output<UserTagsBundle> {

    private final UserTagsBundle userTagBundle;

    public DocumentOutput(UserTagsBundle userTagBundle) {
	this.userTagBundle = userTagBundle;
    }

    @Override
    public UserTagsBundle getResult() {
	return userTagBundle;
    }

    @Override
    public Output<UserTagsBundle> reduce(Output<UserTagsBundle> other) {
	UserTagsCalculator.reduce(userTagBundle, other.getResult());
	return this;
    }
}
