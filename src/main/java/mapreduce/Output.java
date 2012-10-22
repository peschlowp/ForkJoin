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

/**
 * Interface for Map/Reduce output entities. An output entity is produced by the map phase of the
 * job and will be subject to the reduce phase of the job where it will be combined (reduced) with
 * other output entities. Eventually, the final output entity remaining will provide the overall
 * result of the job.
 * 
 * @author patrick.peschlow
 * 
 * @param <T>
 *            the type of the result computed by the job
 */
public interface Output<T> {
    /**
     * Reduces this output entity with another output entity.
     * 
     * @param other
     *            the entity to reduce with this entity
     * @return an entity representing the combined result of the two reduced entities
     */
    Output<T> reduce(Output<T> other);

    /**
     * Provides the actual result associated with this output entity.
     * 
     * @return the actual result of the computation
     */
    T getResult();
}
