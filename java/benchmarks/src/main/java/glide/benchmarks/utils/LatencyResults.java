/** Copyright Valkey GLIDE Project Contributors - SPDX Identifier: Apache-2.0 */
package glide.benchmarks.utils;

import java.util.Arrays;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;

/** Raw timing results in nanoseconds */
public class LatencyResults {
    // measurements are done in nano-seconds, but latencies should be converted to milliseconds
    static final double NANO_TO_MILLI = 1e-6;

    public static final LatencyResults EMPTY = new LatencyResults();

    public final double avgLatency;
    public final double p50Latency;
    public final double p90Latency;
    public final double p99Latency;
    public final double stdDeviation;
    public final int totalRequests;

    /** An all-zero result, used when an action wasn't part of the run's workload. */
    private LatencyResults() {
        avgLatency = 0;
        p50Latency = 0;
        p90Latency = 0;
        p99Latency = 0;
        stdDeviation = 0;
        totalRequests = 0;
    }

    private double TruncateDecimal(double number, int digits) {
        int stepper = (int) Math.pow((double) 10, (double) digits);
        return Math.floor(number * stepper) / stepper;
    }

    public LatencyResults(double[] latencies) {
        // Commons Math's two-pass Variance/StandardDeviation formula subtracts a
        // Sum(x - mean) correction term; if `mean` doesn't match the array's actual
        // (nanosecond-scale) mean, that correction can overshoot and drive the result
        // negative, so sqrt() returns NaN. The mean passed to StandardDeviation must
        // therefore be computed in the same (nanosecond) units as `latencies`, not the
        // already-truncated, millisecond-scaled avgLatency below.
        double meanNanos = Arrays.stream(latencies).sum() / latencies.length;
        avgLatency = TruncateDecimal(NANO_TO_MILLI * meanNanos, 3);
        p50Latency = TruncateDecimal((NANO_TO_MILLI * new Percentile().evaluate(latencies, 50)), 3);
        p90Latency = TruncateDecimal((NANO_TO_MILLI * new Percentile().evaluate(latencies, 90)), 3);
        p99Latency = TruncateDecimal((NANO_TO_MILLI * new Percentile().evaluate(latencies, 99)), 3);
        stdDeviation =
                TruncateDecimal(
                        (NANO_TO_MILLI * new StandardDeviation().evaluate(latencies, meanNanos)), 3);
        totalRequests = latencies.length;
    }
}
