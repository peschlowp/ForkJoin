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

package document;

/**
 * Data holder class for the configuration of an unbalanced amount of users and tags among the
 * documents.
 * 
 * @author patrick.peschlow
 */
public class UnbalancedConfig {

    private final int unbalancedIntervalLo;

    private final int unbalancedIntervalHi;

    private final int factor;

    public UnbalancedConfig(int unbalancedIntervalLo, int unbalancedIntervalHi, int factor) {
	this.unbalancedIntervalLo = unbalancedIntervalLo;
	this.unbalancedIntervalHi = unbalancedIntervalHi;
	this.factor = factor;
    }

    public int getUnbalancedIntervalLo() {
	return unbalancedIntervalLo;
    }

    public int getUnbalancedIntervalHi() {
	return unbalancedIntervalHi;
    }

    public int getFactor() {
	return factor;
    }
}
