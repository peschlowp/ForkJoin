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

import java.util.ArrayDeque;

import actor.Actor;
import actor.Dispatcher;
import actor.Message;
import benchmark.actor.ActorBenchmarkConfig;

/**
 * Dispatcher implementation for the sequential actor benchmark. Maintains the actors and their
 * associated mainboxes. If requested, the dispatcher is able to schedule all actors that have a
 * non-empty mailbox for execution.
 * 
 * @author patrick.peschlow
 * 
 */
public class SequentialDispatcher implements Dispatcher {

    private final Actor[] actors;

    private final ArrayDeque<Message>[] mailboxes;

    @SuppressWarnings("unchecked")
    public SequentialDispatcher(Actor[] actors) {
	this.actors = actors;
	mailboxes = new ArrayDeque[actors.length];
	for (int i = 0; i < mailboxes.length; i++) {
	    mailboxes[i] = new ArrayDeque<>();
	}
    }

    @Override
    public void dispatch(int receiverId, Message message) {
	mailboxes[receiverId].add(message);
    }

    @Override
    public void addInitialMessage(int receiverId, Message message) {
	mailboxes[receiverId].add(message);
    }

    public void scheduleAll() {
	for (int i = 0; i < actors.length; i++) {
	    ArrayDeque<Message> mailbox = mailboxes[i];
	    if (!mailbox.isEmpty()) {
		int counter = 0;
		Message message;
		while (counter++ < ActorBenchmarkConfig.MAX_CONSUME_BURST
			&& (message = mailbox.poll()) != null) {
		    actors[i].receive(message);
		}
	    }
	}
    }
}
