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

/**
 * Simple message containing a text string and a time-to-live field. The time-to-live field may be
 * used to decide when this messages should be deleted.
 * 
 * @author patrick.peschlow
 */
public class Message {

    public static String createText(int senderId, int receiverId) {
	return senderId + " -> " + receiverId;
    }

    private final String text;

    private final int ttl;

    public Message(String text, int ttl) {
	this.text = text;
	this.ttl = ttl;
    }

    public String getText() {
	return text;
    }

    public int getTtl() {
	return ttl;
    }
}
