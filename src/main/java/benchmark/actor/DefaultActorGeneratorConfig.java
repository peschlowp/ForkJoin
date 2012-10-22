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
 * Defines default values for the actor generator configuration.
 * 
 * @author patrick.peschlow
 */
public class DefaultActorGeneratorConfig extends ActorGeneratorConfig {

    /** Number of actors in the system. */
    public static int NUM_ACTORS = 1000;

    /** Number of initial messages. */
    public static int NUM_INITIAL_MESSAGES = 1000;

    /** Time-to-live for each message (how often it gets forwarded before being discarded). */
    public static int MESSAGE_TTL = 10_000;

    /** Random seed for the scenario. */
    public static int RANDOM_SEED = 1234;

    public DefaultActorGeneratorConfig() {
	super(NUM_ACTORS, NUM_INITIAL_MESSAGES, MESSAGE_TTL, RANDOM_SEED);
    }
}
