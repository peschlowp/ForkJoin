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

import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * A ForkJoinTask to be used for generic Map/Reduce computations. Needs to be initialized with an
 * input entity and provides an output entity upon successful execution.
 * 
 * @author patrick.peschlow
 * 
 * @param <T>
 *            the type of the result computed by the job
 */
public class MapReduceTask<T> extends RecursiveTask<Output<T>> {

    private static final long serialVersionUID = 1L;

    private final Input<T> input;

    public MapReduceTask(Input<T> input) {
	this.input = input;
    }

    @Override
    protected Output<T> compute() {
	if (input.shouldBeComputedDirectly()) {
	    return input.computeDirectly();
	}
	List<MapReduceTask<T>> subTasks = input.split();

	for (int i = 1; i < subTasks.size(); i++) {
	    subTasks.get(i).fork();
	}

	Output<T> result = subTasks.get(0).compute();
	for (int i = 1; i < subTasks.size(); i++) {
	    result = result.reduce(subTasks.get(i).join());
	}

	return result;
    }
}
