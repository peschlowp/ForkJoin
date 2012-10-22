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

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import benchmark.actor.ActorBenchmarkConfig;

/**
 * Simple actor that receives messages and forwards each message to a randomly selected receiver.
 * 
 * @author patrick.peschlow
 */
public class Actor {

    public static final AtomicInteger COUNT_ALL = ActorBenchmarkConfig.DEBUG ? new AtomicInteger()
	    : null;

    private final AtomicBoolean active = ActorBenchmarkConfig.DEBUG ? new AtomicBoolean() : null;

    private final int id;

    private final int numActors;

    private final Random rng;

    private ActorContext context;

    public Actor(int id, int numActors, Random rng) {
	this.id = id;
	this.numActors = numActors;
	this.rng = rng;
    }

    public void setContext(ActorContext context) {
	this.context = context;
    }

    public int getId() {
	return id;
    }

    public void receive(Message message) {
	if (ActorBenchmarkConfig.DEBUG) {
	    debugReceiveEntered();
	}

	if (message == null) {
	    throw new IllegalArgumentException("Received null message!");
	}
	if (ActorBenchmarkConfig.VERBOSE) {
	    System.out.println("(" + id + ") Received message: " + message.getText());
	}

	if (ActorBenchmarkConfig.NUM_DUMMY_WORK_ITERATIONS > 0) {
	    dummyWork();
	}
	int receiverId = calcRandomReceiver();
	Message nextMessage = new Message(Message.createText(id, receiverId), message.getTtl() - 1);
	context.dispatch(receiverId, nextMessage);

	if (ActorBenchmarkConfig.DEBUG) {
	    debugReceiveFinished();
	}
    }

    private void dummyWork() {
	long sum = 0;
	for (int i = 0; i < ActorBenchmarkConfig.NUM_DUMMY_WORK_ITERATIONS; i++) {
	    sum += i;
	}
	if (sum == 0) {
	    System.out.println("Bingo!");
	}
    }

    private int calcRandomReceiver() {
	return rng.nextInt(numActors);
    }

    private void debugReceiveEntered() {
	boolean alreadyActive = active.getAndSet(true);
	if (alreadyActive) {
	    System.out.println("WARNING: Actors are scheduled multiple times at the same time!");
	    System.exit(-1);
	}
	COUNT_ALL.incrementAndGet();
    }

    private void debugReceiveFinished() {
	active.set(false);
    }
}
