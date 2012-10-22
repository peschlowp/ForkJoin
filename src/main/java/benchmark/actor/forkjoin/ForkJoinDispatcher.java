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

package benchmark.actor.forkjoin;

import java.util.concurrent.ForkJoinPool;

import actor.Actor;
import benchmark.actor.parallel.AbstractDispatcher;

/**
 * Dispatcher for the fork/join actor benchmark. The dispatcher maintains an internal
 * {@link ForkJoinPool} and schedules a new {@link ActorForkJoinTask} when a scheduling request for
 * an actor is made.
 * 
 * @author patrick.peschlow
 */
public class ForkJoinDispatcher extends AbstractDispatcher {

    private final ForkJoinPool pool;

    public ForkJoinDispatcher(Actor[] actors, ForkJoinPool pool) {
	super(actors);
	this.pool = pool;
    }

    @Override
    protected void schedule(int actorId) {
	pool.execute(new ActorForkJoinTask(this, mailboxes[actorId]));
    }
}
