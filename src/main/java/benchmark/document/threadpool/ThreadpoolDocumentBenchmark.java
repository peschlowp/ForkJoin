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

package benchmark.document.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import benchmark.document.DocumentBenchmarkDef;
import document.DocumentGenerator;
import document.usertags.UserTagsBundle;
import document.usertags.UserTagsCalculator;

/**
 * Benchmark definition for the parallel document scenario using a {@link ThreadPoolExecutor}. When
 * the benchmark is started, the input documents array is split into {@link #numThreads} equally
 * sized chunks each of which is submitted as a task to the pool. Once the pool has finished
 * computation of these tasks, the results are combined in a final step by calling the
 * {@link UserTagsCalculator#reduce(UserTagsBundle, UserTagsBundle)} method.
 * 
 * @author patrick.peschlow
 */
public class ThreadpoolDocumentBenchmark extends DocumentBenchmarkDef {

    private final int numThreads;

    private final ExecutorService threadPool;

    public ThreadpoolDocumentBenchmark(DocumentGenerator documentGenerator, int numThreads) {
	super(documentGenerator);
	this.numThreads = numThreads;
	this.threadPool = Executors.newFixedThreadPool(numThreads);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object execute() {
	Callable<UserTagsBundle>[] tasks = new Callable[numThreads];
	int chunkSize = documents.length / tasks.length;
	int startIndex = 0;
	for (int i = 0; i < tasks.length; i++) {
	    tasks[i] = new DocumentCallable(documents, startIndex, startIndex + chunkSize);
	    startIndex += chunkSize;
	}

	Future<UserTagsBundle>[] results = new Future[tasks.length];
	for (int i = 0; i < tasks.length; i++) {
	    results[i] = threadPool.submit(tasks[i]);
	}

	return combineResults(results);
    }

    private UserTagsBundle combineResults(Future<UserTagsBundle>[] results) {
	UserTagsBundle result = null;
	for (int i = 0; i < results.length; i++) {
	    try {
		if (result == null) {
		    result = results[i].get();
		} else {
		    UserTagsCalculator.reduce(result, results[i].get());
		}
	    } catch (InterruptedException | ExecutionException e) {
		System.out
			.println("WARNING: Something went wrong while collecting result. Exception: "
				+ e);
	    }
	}
	return result;
    }

    @Override
    public void afterAll() {
	threadPool.shutdown();
    }
}
