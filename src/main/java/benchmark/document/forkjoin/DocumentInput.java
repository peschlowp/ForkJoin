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

import java.util.ArrayList;
import java.util.List;

import mapreduce.Input;
import mapreduce.MapReduceTask;
import mapreduce.Output;
import benchmark.document.DocumentBenchmarkConfig;
import document.Document;
import document.usertags.UserTagsBundle;
import document.usertags.UserTagsCalculator;

/**
 * Input entity for a document fork/join task. Each entity shares a document array with all other
 * entities and knows its associated section via the low and high index bounds. The array is shared
 * in order to save memory. This is possible because the array is only read during the job but never
 * written to.
 * 
 * This entity is computed directly when its associated document section is smaller than
 * {@link DocumentBenchmarkConfig#FORKJOIN_CUTOFF}. Direct computation is implemented by calling the
 * {@link UserTagsCalculator#compute(Document[], int, int)} method which contains the business
 * logic.
 * 
 * When this entity is not computed directly, it splits itself into two new entities, each of which
 * takes one half of the document section of this entity.
 * 
 * @author patrick.peschlow
 */
public class DocumentInput implements Input<UserTagsBundle> {

    private final Document[] documents;

    private final int lo;

    private final int hi;

    public DocumentInput(Document[] documents, int lo, int hi) {
	this.documents = documents;
	this.lo = lo;
	this.hi = hi;
    }

    @Override
    public boolean shouldBeComputedDirectly() {
	return hi - lo <= DocumentBenchmarkConfig.FORKJOIN_CUTOFF;
    }

    @Override
    public Output<UserTagsBundle> computeDirectly() {
	return new DocumentOutput(UserTagsCalculator.compute(documents, lo, hi));
    }

    @Override
    public List<MapReduceTask<UserTagsBundle>> split() {
	List<MapReduceTask<UserTagsBundle>> tasks = new ArrayList<>(2);
	int mid = (lo + hi) >>> 1;
	tasks.add(new MapReduceTask<UserTagsBundle>(new DocumentInput(documents, lo, mid)));
	tasks.add(new MapReduceTask<UserTagsBundle>(new DocumentInput(documents, mid, hi)));

	return tasks;
    }
}
