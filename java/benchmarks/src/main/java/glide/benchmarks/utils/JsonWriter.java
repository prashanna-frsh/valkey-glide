/** Copyright Valkey GLIDE Project Contributors - SPDX Identifier: Apache-2.0 */
package glide.benchmarks.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class JsonWriter {

    private static byte[] readAllBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[8192];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }

    private static LatencyResults getOrEmpty(
            Map<ChosenAction, LatencyResults> calculatedResults, ChosenAction action) {
        return calculatedResults.getOrDefault(action, LatencyResults.EMPTY);
    }

    public static void Write(
            Map<ChosenAction, LatencyResults> calculatedResults,
            String resultsFile,
            boolean isCluster,
            int dataSize,
            String client,
            int clientCount,
            int numOfTasks,
            double tps,
            String workload) {

        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            Collection<Measurements> recordings = new ArrayList<>();

            Path path = Paths.get(resultsFile);
            if (Files.exists(path)) {
                TypeToken<Collection<Measurements>> collectionType =
                        new TypeToken<Collection<Measurements>>() {};
                byte[] fileBytes = readAllBytes(Files.newInputStream(path));
                String json = new String(fileBytes);
                recordings = gson.fromJson(json, collectionType.getType());
            }

            LatencyResults getExisting = getOrEmpty(calculatedResults, ChosenAction.GET_EXISTING);
            LatencyResults getNonExisting = getOrEmpty(calculatedResults, ChosenAction.GET_NON_EXISTING);
            LatencyResults set = getOrEmpty(calculatedResults, ChosenAction.SET);
            LatencyResults hset = getOrEmpty(calculatedResults, ChosenAction.HSET);
            LatencyResults hget = getOrEmpty(calculatedResults, ChosenAction.HGET);
            LatencyResults hgetall = getOrEmpty(calculatedResults, ChosenAction.HGETALL);

            Measurements data =
                    new Measurements(
                            client,
                            clientCount,
                            dataSize,
                            isCluster,
                            numOfTasks,
                            workload,
                            getExisting.avgLatency,
                            getExisting.p50Latency,
                            getExisting.p90Latency,
                            getExisting.p99Latency,
                            getExisting.stdDeviation,
                            getNonExisting.avgLatency,
                            getNonExisting.p50Latency,
                            getNonExisting.p90Latency,
                            getNonExisting.p99Latency,
                            getNonExisting.stdDeviation,
                            set.avgLatency,
                            set.p50Latency,
                            set.p90Latency,
                            set.p99Latency,
                            set.stdDeviation,
                            hset.avgLatency,
                            hset.p50Latency,
                            hset.p90Latency,
                            hset.p99Latency,
                            hset.stdDeviation,
                            hget.avgLatency,
                            hget.p50Latency,
                            hget.p90Latency,
                            hget.p99Latency,
                            hget.stdDeviation,
                            hgetall.avgLatency,
                            hgetall.p50Latency,
                            hgetall.p90Latency,
                            hgetall.p99Latency,
                            hgetall.stdDeviation,
                            tps);

            recordings.add(data);

            Files.write(path, gson.toJson(recordings).getBytes());
        } catch (IOException e) {
            System.out.printf(
                    "Failed to write measurement results into a file '%s': %s%n",
                    resultsFile, e.getMessage());
            e.printStackTrace();
        }
    }

    public static class Measurements {
        public Measurements(
                String client,
                int client_count,
                int data_size,
                boolean is_cluster,
                int num_of_tasks,
                String workload,
                double get_existing_average_latency,
                double get_existing_p50_latency,
                double get_existing_p90_latency,
                double get_existing_p99_latency,
                double get_existing_std_dev,
                double get_non_existing_average_latency,
                double get_non_existing_p50_latency,
                double get_non_existing_p90_latency,
                double get_non_existing_p99_latency,
                double get_non_existing_std_dev,
                double set_average_latency,
                double set_p50_latency,
                double set_p90_latency,
                double set_p99_latency,
                double set_std_dev,
                double hset_average_latency,
                double hset_p50_latency,
                double hset_p90_latency,
                double hset_p99_latency,
                double hset_std_dev,
                double hget_average_latency,
                double hget_p50_latency,
                double hget_p90_latency,
                double hget_p99_latency,
                double hget_std_dev,
                double hgetall_average_latency,
                double hgetall_p50_latency,
                double hgetall_p90_latency,
                double hgetall_p99_latency,
                double hgetall_std_dev,
                double tps) {
            this.client = client;
            this.client_count = client_count;
            this.data_size = data_size;
            this.is_cluster = is_cluster;
            this.num_of_tasks = num_of_tasks;
            this.workload = workload;
            this.get_existing_average_latency = get_existing_average_latency;
            this.get_existing_p50_latency = get_existing_p50_latency;
            this.get_existing_p90_latency = get_existing_p90_latency;
            this.get_existing_p99_latency = get_existing_p99_latency;
            this.get_existing_std_dev = get_existing_std_dev;
            this.get_non_existing_average_latency = get_non_existing_average_latency;
            this.get_non_existing_p50_latency = get_non_existing_p50_latency;
            this.get_non_existing_p90_latency = get_non_existing_p90_latency;
            this.get_non_existing_p99_latency = get_non_existing_p99_latency;
            this.get_non_existing_std_dev = get_non_existing_std_dev;
            this.set_average_latency = set_average_latency;
            this.set_p50_latency = set_p50_latency;
            this.set_p90_latency = set_p90_latency;
            this.set_p99_latency = set_p99_latency;
            this.set_std_dev = set_std_dev;
            this.hset_average_latency = hset_average_latency;
            this.hset_p50_latency = hset_p50_latency;
            this.hset_p90_latency = hset_p90_latency;
            this.hset_p99_latency = hset_p99_latency;
            this.hset_std_dev = hset_std_dev;
            this.hget_average_latency = hget_average_latency;
            this.hget_p50_latency = hget_p50_latency;
            this.hget_p90_latency = hget_p90_latency;
            this.hget_p99_latency = hget_p99_latency;
            this.hget_std_dev = hget_std_dev;
            this.hgetall_average_latency = hgetall_average_latency;
            this.hgetall_p50_latency = hgetall_p50_latency;
            this.hgetall_p90_latency = hgetall_p90_latency;
            this.hgetall_p99_latency = hgetall_p99_latency;
            this.hgetall_std_dev = hgetall_std_dev;
            this.tps = tps;
        }

        public String client;
        public int client_count;
        public int data_size;
        public boolean is_cluster;
        public int num_of_tasks;
        public String workload;
        public double get_existing_average_latency;
        public double get_existing_p50_latency;
        public double get_existing_p90_latency;
        public double get_existing_p99_latency;
        public double get_existing_std_dev;
        public double get_non_existing_average_latency;
        public double get_non_existing_p50_latency;
        public double get_non_existing_p90_latency;
        public double get_non_existing_p99_latency;
        public double get_non_existing_std_dev;
        public double set_average_latency;
        public double set_p50_latency;
        public double set_p90_latency;
        public double set_p99_latency;
        public double set_std_dev;
        public double hset_average_latency;
        public double hset_p50_latency;
        public double hset_p90_latency;
        public double hset_p99_latency;
        public double hset_std_dev;
        public double hget_average_latency;
        public double hget_p50_latency;
        public double hget_p90_latency;
        public double hget_p99_latency;
        public double hget_std_dev;
        public double hgetall_average_latency;
        public double hgetall_p50_latency;
        public double hgetall_p90_latency;
        public double hgetall_p99_latency;
        public double hgetall_std_dev;
        public double tps;
    }
}
