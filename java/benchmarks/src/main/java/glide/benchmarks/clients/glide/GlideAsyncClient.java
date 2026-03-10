/** Copyright Valkey GLIDE Project Contributors - SPDX Identifier: Apache-2.0 */
package glide.benchmarks.clients.glide;

import static java.util.concurrent.TimeUnit.SECONDS;

import glide.api.BaseClient;
import glide.api.GlideClient;
import glide.api.GlideClusterClient;
import glide.api.models.configuration.GlideClientConfiguration;
import glide.api.models.configuration.GlideClusterClientConfiguration;
import glide.api.models.configuration.NodeAddress;
import glide.api.models.configuration.ServerCredentials;
import glide.benchmarks.clients.AsyncClient;
import glide.benchmarks.utils.ConnectionSettings;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/** A Glide client with async capabilities */
public class GlideAsyncClient implements AsyncClient<String> {
    private BaseClient glideClient;

    @Override
    public void connectToValkey(ConnectionSettings connectionSettings) {
        boolean hasCredentials =
                connectionSettings.password != null || connectionSettings.username != null;

        if (connectionSettings.clusterMode) {
            GlideClusterClientConfiguration.GlideClusterClientConfigurationBuilder<?, ?> builder =
                    GlideClusterClientConfiguration.builder()
                            .address(
                                    NodeAddress.builder()
                                            .host(connectionSettings.host)
                                            .port(connectionSettings.port)
                                            .build())
                            .useTLS(connectionSettings.useSsl);
            if (hasCredentials) {
                builder.credentials(
                        ServerCredentials.builder()
                                .username(connectionSettings.username)
                                .password(connectionSettings.password)
                                .build());
            }
            try {
                glideClient = GlideClusterClient.createClient(builder.build()).get(10, SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                throw new RuntimeException(e);
            }

        } else {
            GlideClientConfiguration.GlideClientConfigurationBuilder<?, ?> builder =
                    GlideClientConfiguration.builder()
                            .address(
                                    NodeAddress.builder()
                                            .host(connectionSettings.host)
                                            .port(connectionSettings.port)
                                            .build())
                            .useTLS(connectionSettings.useSsl);
            if (hasCredentials) {
                builder.credentials(
                        ServerCredentials.builder()
                                .username(connectionSettings.username)
                                .password(connectionSettings.password)
                                .build());
            }
            try {
                glideClient = GlideClient.createClient(builder.build()).get(10, SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public CompletableFuture<String> asyncSet(String key, String value) {
        return glideClient.set(key, value);
    }

    @Override
    public CompletableFuture<String> asyncGet(String key) {
        return glideClient.get(key);
    }

    @Override
    public void closeConnection() {
        try {
            glideClient.close();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getName() {
        return "glide";
    }
}
