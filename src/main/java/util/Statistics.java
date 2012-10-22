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

package util;

import java.util.List;

/**
 * Computes basic statistics (sample mean and standard deviation) for a number of observations.
 * 
 * @author patrick.peschlow
 */
public class Statistics {

    public static double computeSampleMean(List<Long> observations) {
	long sum = 0;
	for (Long observation : observations) {
	    sum += observation;
	}
	return sum / (double) observations.size();
    }

    public static double computeStdDev(List<Long> observations, double mean) {
	double variance = computeSampleVariance(observations, mean);
	return Math.sqrt(variance);
    }

    private static double computeSampleVariance(List<Long> observations, double mean) {
	if (observations.size() == 1) {
	    return 0;
	}
	double sum = 0;
	for (Long observation : observations) {
	    double diff = mean - observation;
	    sum += diff * diff;
	}
	return sum / (observations.size() - 1);
    }
}
