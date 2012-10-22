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

import java.util.concurrent.ForkJoinPool;

import mapreduce.MapReduce;
import benchmark.document.DocumentBenchmarkDef;
import document.DocumentGenerator;
import document.usertags.UserTagsBundle;

/**
 * Benchmark definition for the parallel document scenario using a {@link ForkJoinPool}. When the
 * benchmark is started, a single task is scheduled containing the whole array of input documents.
 * 
 * @author patrick.peschlow
 */
public class ForkJoinDocumentBenchmark extends DocumentBenchmarkDef {

    private final MapReduce<UserTagsBundle> mapReduce;

    public ForkJoinDocumentBenchmark(DocumentGenerator documentGenerator, int numThreads) {
	super(documentGenerator);
	mapReduce = new MapReduce<>(numThreads);
    }

    @Override
    public Object execute() {
	return mapReduce.execute(new DocumentInput(documents, 0, documents.length));
    }
}
