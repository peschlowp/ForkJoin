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

package benchmark.actor.parallel;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import actor.Actor;
import actor.Message;

/**
 * Mailbox implementation for the parallel actor scenarios. A mailbox contains a thread-safe queue
 * for its messages. Also, the mailbox offers a thread-safe way of setting its scheduling state.
 * 
 * @author patrick.peschlow
 */
public class Mailbox {

    private final Actor actor;

    private final Queue<Message> messages = new ConcurrentLinkedQueue<>();

    private final AtomicBoolean isScheduled = new AtomicBoolean();

    public Mailbox(Actor actor) {
	this.actor = actor;
    }

    public Actor getActor() {
	return actor;
    }

    public void addMessage(Message message) {
	messages.add(message);
    }

    public Message pollMessage() {
	return messages.poll();
    }

    public boolean isEmpty() {
	return messages.isEmpty();
    }

    public boolean setScheduled(boolean scheduled) {
	return isScheduled.getAndSet(scheduled);
    }
}
