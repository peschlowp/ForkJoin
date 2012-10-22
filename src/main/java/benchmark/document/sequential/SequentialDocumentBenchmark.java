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

package benchmark.document.sequential;

import benchmark.document.DocumentBenchmarkDef;
import document.DocumentGenerator;
import document.usertags.UserTagsCalculator;

/**
 * Simple sequential execution of the document benchmark. The whole computation is performed in a
 * single call.
 * 
 * @author patrick.peschlow
 */
public class SequentialDocumentBenchmark extends DocumentBenchmarkDef {

    public SequentialDocumentBenchmark(DocumentGenerator documentGenerator) {
	super(documentGenerator);
    }

    @Override
    public Object execute() {
	return UserTagsCalculator.compute(documents, 0, documents.length);
    }
}
