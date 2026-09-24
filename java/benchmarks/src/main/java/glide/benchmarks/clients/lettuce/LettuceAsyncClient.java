/** Copyright Valkey GLIDE Project Contributors - SPDX Identifier: Apache-2.0 */
package glide.benchmarks.clients.lettuce;

import glide.benchmarks.clients.AsyncClient;
import glide.benchmarks.utils.ConnectionSettings;
import io.lettuce.core.AbstractRedisClient;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulConnection;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisHashAsyncCommands;
import io.lettuce.core.api.async.RedisStringAsyncCommands;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import java.time.Duration;
import java.util.Map;

/** A Lettuce client with async capabilities see: https://lettuce.io/ */
public class LettuceAsyncClient implements AsyncClient<String> {
    static final int ASYNC_OPERATION_TIMEOUT_SEC = 1;

    private AbstractRedisClient client;
    private RedisStringAsyncCommands<String, String> asyncCommands;
    private RedisHashAsyncCommands<String, String> hashCommands;
    private StatefulConnection<String, String> connection;

    @Override
    public void connectToValkey(ConnectionSettings connectionSettings) {
        RedisURI.Builder uriBuilder =
                RedisURI.builder()
                        .withHost(connectionSettings.host)
                        .withPort(connectionSettings.port)
                        .withSsl(connectionSettings.useSsl);
        if (connectionSettings.username != null) {
            uriBuilder.withAuthentication(
                    connectionSettings.username,
                    connectionSettings.password != null
                            ? connectionSettings.password.toCharArray()
                            : new char[0]);
        } else if (connectionSettings.password != null) {
            uriBuilder.withPassword(connectionSettings.password.toCharArray());
        }
        RedisURI uri = uriBuilder.build();
        Object commands;
        if (!connectionSettings.clusterMode) {
            client = RedisClient.create(uri);
            connection = ((RedisClient) client).connect();
            commands = ((StatefulRedisConnection<String, String>) connection).async();
        } else {
            client = RedisClusterClient.create(uri);
            connection = ((RedisClusterClient) client).connect();
            commands = ((StatefulRedisClusterConnection<String, String>) connection).async();
        }
        asyncCommands = (RedisStringAsyncCommands<String, String>) commands;
        hashCommands = (RedisHashAsyncCommands<String, String>) commands;
        connection.setTimeout(Duration.ofSeconds(ASYNC_OPERATION_TIMEOUT_SEC));
    }

    @Override
    public RedisFuture<String> asyncSet(String key, String value) {
        return asyncCommands.set(key, value);
    }

    @Override
    public RedisFuture<String> asyncGet(String key) {
        return asyncCommands.get(key);
    }

    @Override
    public RedisFuture<Long> asyncHset(String key, Map<String, String> fieldValueMap) {
        return hashCommands.hset(key, fieldValueMap);
    }

    @Override
    public RedisFuture<String> asyncHget(String key, String field) {
        return hashCommands.hget(key, field);
    }

    @Override
    public RedisFuture<Map<String, String>> asyncHgetAll(String key) {
        return hashCommands.hgetall(key);
    }

    @Override
    public void closeConnection() {
        connection.close();
        client.shutdown();
    }

    @Override
    public String getName() {
        return "lettuce";
    }
}
