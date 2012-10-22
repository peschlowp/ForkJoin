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

package actor;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Semaphore;

import benchmark.actor.ActorBenchmarkConfig;

/**
 * Execution context for actors which supports the scheduling of messages between actors. Also
 * contains helper logic for the benchmark, by providing a way to schedule initial messages and to
 * check if the benchmark has finished.
 * 
 * @author patrick.peschlow
 */
public class ActorContext {

    private final Dispatcher dispatcher;

    private final Semaphore countFinishedMessages;

    public ActorContext(Dispatcher dispatcher, Actor[] actors,
	    Map<Integer, List<Message>> initialMessages) {
	this.dispatcher = dispatcher;
	for (int i = 0; i < actors.length; i++) {
	    actors[i].setContext(this);
	}
	int numInitialMessages = 0;
	for (Entry<Integer, List<Message>> entry : initialMessages.entrySet()) {
	    List<Message> actorMessages = entry.getValue();
	    numInitialMessages += actorMessages.size();
	    addInitialMessages(entry.getKey(), actorMessages);
	}
	countFinishedMessages = new Semaphore(1 - numInitialMessages);
    }

    public void dispatch(int receiverId, Message message) {
	if (message.getTtl() == 0) {
	    countFinishedMessages.release();
	    if (ActorBenchmarkConfig.VERBOSE) {
		System.out.println("countFinishedMessages released, available permits: "
			+ countFinishedMessages.availablePermits());
	    }
	} else {
	    dispatcher.dispatch(receiverId, message);
	}
    }

    public void addInitialMessages(int receiverId, List<Message> messages) {
	for (Message message : messages) {
	    dispatcher.addInitialMessage(receiverId, message);
	}
    }

    public boolean isFinished() {
	return countFinishedMessages.tryAcquire();
    }

    public void waitForFinished() {
	try {
	    countFinishedMessages.acquire();
	} catch (InterruptedException e) {
	    // Ignore.
	}
    }
}
