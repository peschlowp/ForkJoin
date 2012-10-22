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

/**
 * Interface for Map/Reduce input entities. An input entity can be split into sub-entities that will
 * eventually be subject to the map phase of the Map/Reduce job.
 * 
 * @author patrick.peschlow
 * 
 * @param <T>
 *            the type of the result computed by the job
 */
public interface Input<T> {
    /**
     * Determines whether this entity should be computed directly or split further into smaller
     * entities.
     * 
     * @return {@code true} if this entity should be computed directly, {@code false} otherwise
     */
    boolean shouldBeComputedDirectly();

    /**
     * Performs the computation for this entity directly (i.e., the map phase of the job) and
     * returns the output object carrying the result for this entity.
     * 
     * @return the output object returned by the computation
     */
    Output<T> computeDirectly();

    /**
     * Splits this entity into a number of MapReduceTasks which can then be submitted to a
     * ForkJoinPool.
     * 
     * @return a list of MapReduceTasks representing subtasks of this task
     */
    List<MapReduceTask<T>> split();
}
