/** Copyright Valkey GLIDE Project Contributors - SPDX Identifier: Apache-2.0 */
package glide.benchmarks.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class JsonWriterTest {

    private static LatencyResults results(double value, int totalRequests) {
        double[] latencies = new double[totalRequests];
        for (int i = 0; i < totalRequests; i++) {
            latencies[i] = value * 1e6; // ms -> ns, matching LatencyResults' NANO_TO_MILLI scaling
        }
        return new LatencyResults(latencies);
    }

    private static JsonWriter.Measurements readSingleRecord(java.nio.file.Path path)
            throws IOException {
        Gson gson = new Gson();
        String json = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        TypeToken<Collection<JsonWriter.Measurements>> collectionType =
                new TypeToken<Collection<JsonWriter.Measurements>>() {};
        Collection<JsonWriter.Measurements> recordings = gson.fromJson(json, collectionType.getType());
        return recordings.iterator().next();
    }

    @Test
    void writingHashWorkload_zeroFillsStringFieldsAndSetsWorkload(@TempDir java.nio.file.Path tempDir)
            throws IOException {
        Map<ChosenAction, LatencyResults> calculated = new HashMap<>();
        calculated.put(ChosenAction.HSET, results(1.5, 10));
        calculated.put(ChosenAction.HGET, results(0.75, 20));
        calculated.put(ChosenAction.HGETALL, results(2.5, 5));

        java.nio.file.Path resultsFile = tempDir.resolve("results.json");
        JsonWriter.Write(
                calculated, resultsFile.toString(), false, 100, "glide", 1, 10, 5000.0, "hash");

        JsonWriter.Measurements record = readSingleRecord(resultsFile);

        assertEquals("hash", record.workload);
        assertEquals(0.0, record.get_existing_average_latency);
        assertEquals(0.0, record.set_average_latency);
        assertEquals(1.5, record.hset_average_latency);
        assertEquals(0.75, record.hget_average_latency);
        assertEquals(2.5, record.hgetall_average_latency);
    }

    @Test
    void writingStringWorkload_zeroFillsHashFields(@TempDir java.nio.file.Path tempDir)
            throws IOException {
        Map<ChosenAction, LatencyResults> calculated = new HashMap<>();
        calculated.put(ChosenAction.GET_EXISTING, results(0.7, 10));
        calculated.put(ChosenAction.GET_NON_EXISTING, results(0.6, 10));
        calculated.put(ChosenAction.SET, results(0.9, 10));

        java.nio.file.Path resultsFile = tempDir.resolve("results.json");
        JsonWriter.Write(
                calculated, resultsFile.toString(), false, 100, "glide", 1, 10, 5000.0, "string");

        JsonWriter.Measurements record = readSingleRecord(resultsFile);

        assertEquals("string", record.workload);
        assertEquals(0.7, record.get_existing_average_latency);
        assertEquals(0.0, record.hset_average_latency);
        assertEquals(0.0, record.hget_average_latency);
        assertEquals(0.0, record.hgetall_average_latency);
    }
}
