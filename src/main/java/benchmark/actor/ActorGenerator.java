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

package benchmark.actor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import actor.Actor;
import actor.Message;

/**
 * Generates a number of actors based on the specified configuration. Uses a global seed to be able
 * to construct the same scenario again when needed.
 * 
 * @author patrick.peschlow
 */
public class ActorGenerator {

    private final int numActors;

    private final int numInitialMessages;

    private final int messageTtl;

    private final long seed;

    public ActorGenerator(ActorGeneratorConfig config) {
	numActors = config.getNumActors();
	numInitialMessages = config.getInitialMessageRatio();
	messageTtl = config.getMessageTtl();
	seed = config.getSeed();
    }

    public Actor[] generateActors() {
	Random random = new Random(seed);
	Actor[] actors = new Actor[numActors];
	for (int i = 0; i < numActors; i++) {
	    actors[i] = new Actor(i, numActors, new Random(random.nextLong()));
	}
	return actors;
    }

    public Map<Integer, List<Message>> generateInitialMessages() {
	Map<Integer, List<Message>> messages = new HashMap<>();
	int actorId = 0;
	for (int i = 0; i < numInitialMessages; i++) {
	    List<Message> actorMessages = getActorMessageList(messages, actorId);
	    actorMessages.add(new Message(Message.createText(-1, actorId), messageTtl));
	    if (ActorBenchmarkConfig.VERBOSE) {
		System.out.println("Initial message " + i + " scheduled at actor " + actorId);
	    }
	    if (++actorId == numActors) {
		actorId = 0;
	    }
	}
	return messages;
    }

    private List<Message> getActorMessageList(Map<Integer, List<Message>> messages, int actorId) {
	List<Message> actorMessages = messages.get(actorId);
	if (actorMessages == null) {
	    actorMessages = new ArrayList<>();
	    messages.put(actorId, actorMessages);
	}
	return actorMessages;
    }
}
