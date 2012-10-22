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

package benchmark.actor.sequential;

import actor.ActorContext;
import benchmark.actor.ActorBenchmarkDef;
import benchmark.actor.ActorGenerator;

/**
 * Simple sequential execution of the actor benchmark. All actors are scheduled for execution in a
 * loop until the context indicates that the benchmark is finished.
 * 
 * @author patrick.peschlow
 */
public class SequentialActorBenchmark extends ActorBenchmarkDef {

    private SequentialDispatcher dispatcher;

    public SequentialActorBenchmark(ActorGenerator generator) {
	super(generator);
    }

    @Override
    public Object execute() {
	while (!context.isFinished()) {
	    dispatcher.scheduleAll();
	}
	return null;
    }

    @Override
    protected void initContext() {
	dispatcher = new SequentialDispatcher(actors);
	context = new ActorContext(dispatcher, actors, initialMessages);
    }
}
