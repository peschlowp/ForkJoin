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

import actor.Actor;
import actor.Dispatcher;
import actor.Message;

/**
 * Base dispatcher implementation for the parallel actor benchmarks. The dispatcher maintains the
 * set of mailboxes of all actors and scheduled new messages. When dispatching a message, the
 * dispatcher checks the mailbox state in order to schedule the actor for execution only if it isn't
 * already scheduled.
 * 
 * @author patrick.peschlow
 */
public abstract class AbstractDispatcher implements Dispatcher {

    protected final Mailbox[] mailboxes;

    protected final boolean[] hasInitialMessage;

    public AbstractDispatcher(Actor[] actors) {
	mailboxes = new Mailbox[actors.length];
	for (int actorId = 0; actorId < mailboxes.length; actorId++) {
	    mailboxes[actorId] = new Mailbox(actors[actorId]);
	}
	hasInitialMessage = new boolean[actors.length];
    }

    @Override
    public void dispatch(int receiverId, Message message) {
	mailboxes[receiverId].addMessage(message);
	scheduleUnlessAlreadyScheduled(receiverId);
    }

    @Override
    public void addInitialMessage(int receiverId, Message message) {
	hasInitialMessage[receiverId] = true;
	mailboxes[receiverId].addMessage(message);
    }

    public void scheduleInitially() {
	for (int actorId = 0; actorId < mailboxes.length; actorId++) {
	    if (hasInitialMessage[actorId]) {
		scheduleUnlessAlreadyScheduled(actorId);
	    }
	}
    }

    public void scheduleUnlessAlreadyScheduled(int actorId) {
	boolean wasScheduled = mailboxes[actorId].setScheduled(true);
	if (!wasScheduled) {
	    schedule(actorId);
	}
    }

    protected abstract void schedule(int actorId);
}
