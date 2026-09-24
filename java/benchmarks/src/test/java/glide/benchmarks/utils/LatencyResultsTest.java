/** Copyright Valkey GLIDE Project Contributors - SPDX Identifier: Apache-2.0 */
package glide.benchmarks.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class LatencyResultsTest {

    /**
     * Regression test: StandardDeviation.evaluate(values, mean) uses a two-pass formula that
     * subtracts a Sum(x - mean) correction term. Passing a mean in the wrong unit (previously: the
     * already ms-scaled, truncated avgLatency against a still-in-nanoseconds array) makes that
     * correction overshoot for near-uniform data, driving the result negative and turning
     * stdDeviation into NaN.
     */
    @Test
    void stdDeviation_isZeroNotNaN_forUniformLatencies() {
        double[] latencies = new double[20];
        for (int i = 0; i < latencies.length; i++) {
            latencies[i] = 2_100_000; // 2.1ms in nanoseconds, identical for every request
        }

        LatencyResults results = new LatencyResults(latencies);

        assertFalse(Double.isNaN(results.stdDeviation));
        assertEquals(0.0, results.stdDeviation);
        assertEquals(2.1, results.avgLatency);
    }

    @Test
    void empty_hasZeroedFieldsAndNoRequests() {
        assertEquals(0.0, LatencyResults.EMPTY.avgLatency);
        assertEquals(0.0, LatencyResults.EMPTY.stdDeviation);
        assertEquals(0, LatencyResults.EMPTY.totalRequests);
    }
}
