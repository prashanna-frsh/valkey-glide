/** Copyright Valkey GLIDE Project Contributors - SPDX Identifier: Apache-2.0 */
package glide.benchmarks.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import glide.benchmarks.clients.AsyncClient;
import glide.benchmarks.clients.SyncClient;
import glide.benchmarks.utils.Benchmarking.Operation;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.junit.jupiter.api.Test;

public class BenchmarkingTest {

    /** Records every call made against it instead of talking to a real server. */
    private static class RecordingClient implements SyncClient, AsyncClient<String> {
        Map<String, String> lastHsetFields;
        String lastHgetField;
        int hsetCalls = 0;
        int hgetCalls = 0;
        int hgetAllCalls = 0;
        int getCalls = 0;
        int setCalls = 0;

        @Override
        public void connectToValkey(glide.benchmarks.utils.ConnectionSettings connectionSettings) {}

        @Override
        public String getName() {
            return "recording-client";
        }

        @Override
        public void set(String key, String value) {
            setCalls++;
        }

        @Override
        public String get(String key) {
            getCalls++;
            return "value";
        }

        @Override
        public void hset(String key, Map<String, String> fieldValueMap) {
            hsetCalls++;
            lastHsetFields = fieldValueMap;
        }

        @Override
        public String hget(String key, String field) {
            hgetCalls++;
            lastHgetField = field;
            return "value";
        }

        @Override
        public Map<String, String> hgetAll(String key) {
            hgetAllCalls++;
            return new HashMap<>();
        }

        @Override
        public Future<String> asyncSet(String key, String value) {
            setCalls++;
            return CompletableFuture.completedFuture("OK");
        }

        @Override
        public Future<String> asyncGet(String key) {
            getCalls++;
            return CompletableFuture.completedFuture("value");
        }

        @Override
        public Future<Long> asyncHset(String key, Map<String, String> fieldValueMap) {
            hsetCalls++;
            lastHsetFields = fieldValueMap;
            return CompletableFuture.completedFuture((long) fieldValueMap.size());
        }

        @Override
        public Future<String> asyncHget(String key, String field) {
            hgetCalls++;
            lastHgetField = field;
            return CompletableFuture.completedFuture("value");
        }

        @Override
        public Future<Map<String, String>> asyncHgetAll(String key) {
            hgetAllCalls++;
            return CompletableFuture.completedFuture(new HashMap<>());
        }
    }

    @Test
    void generateHashKey_isWithinFormKeyspace() {
        for (int i = 0; i < 1000; i++) {
            String key = Benchmarking.generateHashKey();
            assertTrue(key.startsWith("form:"));
            double id = Double.parseDouble(key.substring("form:".length()));
            assertTrue(id >= 1 && id <= Benchmarking.SIZE_HASH_KEYSPACE);
        }
    }

    @Test
    void generateHashField_isWithinFieldCount() {
        for (int i = 0; i < 1000; i++) {
            String field = Benchmarking.generateHashField();
            assertTrue(field.startsWith("field"));
            int index = Integer.parseInt(field.substring("field".length()));
            assertTrue(index >= 0 && index < Benchmarking.HASH_FIELD_COUNT);
        }
    }

    @Test
    void getHashActionMap_containsExactlyHashActions() {
        Map<ChosenAction, Operation> actions = Benchmarking.getHashActionMap(100, false);
        assertEquals(3, actions.size());
        assertTrue(actions.containsKey(ChosenAction.HSET));
        assertTrue(actions.containsKey(ChosenAction.HGET));
        assertTrue(actions.containsKey(ChosenAction.HGETALL));
    }

    @Test
    void getActionMap_containsExactlyStringActions() {
        Map<ChosenAction, Operation> actions = Benchmarking.getActionMap(100, false);
        assertEquals(3, actions.size());
        assertTrue(actions.containsKey(ChosenAction.GET_EXISTING));
        assertTrue(actions.containsKey(ChosenAction.GET_NON_EXISTING));
        assertTrue(actions.containsKey(ChosenAction.SET));
    }

    @Test
    void hashActionMap_hsetWritesAllFormFieldsSized()
            throws InterruptedException, ExecutionException {
        RecordingClient client = new RecordingClient();
        Map<ChosenAction, Operation> actions = Benchmarking.getHashActionMap(7, false);

        actions.get(ChosenAction.HSET).go(client);

        assertEquals(1, client.hsetCalls);
        assertEquals(Benchmarking.HASH_FIELD_COUNT, client.lastHsetFields.size());
        for (String value : client.lastHsetFields.values()) {
            assertEquals(7, value.length());
        }
    }

    @Test
    void hashActionMap_hgetReadsASingleField() throws InterruptedException, ExecutionException {
        RecordingClient client = new RecordingClient();
        Map<ChosenAction, Operation> actions = Benchmarking.getHashActionMap(100, false);

        actions.get(ChosenAction.HGET).go(client);

        assertEquals(1, client.hgetCalls);
        assertTrue(client.lastHgetField.startsWith("field"));
    }

    @Test
    void hashActionMap_hgetAllReadsTheWholeForm() throws InterruptedException, ExecutionException {
        RecordingClient client = new RecordingClient();
        Map<ChosenAction, Operation> actions = Benchmarking.getHashActionMap(100, false);

        actions.get(ChosenAction.HGETALL).go(client);

        assertEquals(1, client.hgetAllCalls);
    }

    @Test
    void hashActionMap_asyncPathDelegatesToAsyncClient()
            throws InterruptedException, ExecutionException {
        RecordingClient client = new RecordingClient();
        Map<ChosenAction, Operation> actions = Benchmarking.getHashActionMap(100, true);

        actions.get(ChosenAction.HSET).go(client);
        actions.get(ChosenAction.HGET).go(client);
        actions.get(ChosenAction.HGETALL).go(client);

        assertEquals(1, client.hsetCalls);
        assertEquals(1, client.hgetCalls);
        assertEquals(1, client.hgetAllCalls);
    }

    @Test
    void measurePerformance_recordsTheSelectedHashAction()
            throws InterruptedException, ExecutionException {
        RecordingClient client = new RecordingClient();
        Map<ChosenAction, Operation> actions = Benchmarking.getHashActionMap(100, false);

        org.apache.commons.lang3.tuple.Pair<ChosenAction, Long> result =
                Benchmarking.measurePerformance(client, actions, () -> ChosenAction.HGETALL);

        assertEquals(ChosenAction.HGETALL, result.getLeft());
        assertTrue(result.getRight() >= 0);
        assertEquals(1, client.hgetAllCalls);
    }
}
