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

/**
 * Data holder class for the actor generator configuration.
 * 
 * @author patrick.peschlow
 */
public class ActorGeneratorConfig {

    private final int numActors;

    private final int numInitialMessages;

    private final int messageTtl;

    private final long seed;

    public ActorGeneratorConfig(int numActors, int numInitialMessages, int messageTtl, long seed) {
	this.numActors = numActors;
	this.numInitialMessages = numInitialMessages;
	this.messageTtl = messageTtl;
	this.seed = seed;
    }

    public int getNumActors() {
	return numActors;
    }

    public int getInitialMessageRatio() {
	return numInitialMessages;
    }

    public int getMessageTtl() {
	return messageTtl;
    }

    public long getSeed() {
	return seed;
    }
}
