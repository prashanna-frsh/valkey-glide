/** Copyright Valkey GLIDE Project Contributors - SPDX Identifier: Apache-2.0 */
package glide.benchmarks.clients.jediscompat;

import glide.benchmarks.clients.SyncClient;
import glide.benchmarks.utils.ConnectionSettings;
import java.util.Collections;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

/**
 * A Jedis-compatibility client with sync capabilities. Uses the jedis-compatibility layer which
 * provides Jedis API backed by GLIDE core. See: java/jedis-compatibility/
 */
public class JedisCompatClient implements SyncClient {
    boolean isClusterMode;
    private JedisPool jedisStandalonePool;
    private JedisCluster jedisCluster;

    @Override
    public void closeConnection() {
        if (jedisCluster != null) {
            jedisCluster.close();
        }
        if (jedisStandalonePool != null) {
            jedisStandalonePool.close();
        }
    }

    @Override
    public String getName() {
        return "jedis-compat";
    }

    @Override
    public void connectToValkey(ConnectionSettings connectionSettings) {
        isClusterMode = connectionSettings.clusterMode;
        if (isClusterMode) {
            DefaultJedisClientConfig.Builder configBuilder =
                    DefaultJedisClientConfig.builder().ssl(connectionSettings.useSsl);

            if (connectionSettings.username != null && connectionSettings.password != null) {
                configBuilder.user(connectionSettings.username).password(connectionSettings.password);
            }

            jedisCluster =
                    new JedisCluster(
                            Collections.singleton(
                                    new HostAndPort(connectionSettings.host, connectionSettings.port)),
                            configBuilder.build());
        } else {
            DefaultJedisClientConfig.Builder configBuilder =
                    DefaultJedisClientConfig.builder().ssl(connectionSettings.useSsl);

            if (connectionSettings.username != null && connectionSettings.password != null) {
                configBuilder.user(connectionSettings.username).password(connectionSettings.password);
            }

            jedisStandalonePool =
                    new JedisPool(
                            new HostAndPort(connectionSettings.host, connectionSettings.port),
                            configBuilder.build());
        }
    }

    @Override
    public void set(String key, String value) {
        if (isClusterMode) {
            jedisCluster.set(key, value);
        } else {
            try (Jedis jedis = jedisStandalonePool.getResource()) {
                jedis.set(key, value);
            }
        }
    }

    @Override
    public String get(String key) {
        if (isClusterMode) {
            return jedisCluster.get(key);
        } else {
            try (Jedis jedis = jedisStandalonePool.getResource()) {
                return jedis.get(key);
            }
        }
    }
}
