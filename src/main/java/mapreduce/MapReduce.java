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

package mapreduce;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * A this wrapper around a ForkJoinPool that enables the caller to retrieve the result of a
 * Map/Reduce computation in a type-safe fashion.
 * 
 * @author patrick.peschlow
 * 
 * @param <T>
 *            the type of the result computed by the job
 */
public class MapReduce<T> {

    private final ForkJoinPool pool;

    public MapReduce(int numThreads) {
	this.pool = new ForkJoinPool(numThreads);
    }

    public T execute(Input<T> input) {
	ForkJoinTask<Output<T>> task = new MapReduceTask<T>(input);

	Output<T> output = pool.invoke(task);

	return output.getResult();
    }
}
