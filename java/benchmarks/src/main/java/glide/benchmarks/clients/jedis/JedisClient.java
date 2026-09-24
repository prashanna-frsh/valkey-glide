/** Copyright Valkey GLIDE Project Contributors - SPDX Identifier: Apache-2.0 */
package glide.benchmarks.clients.jedis;

import glide.benchmarks.clients.SyncClient;
import glide.benchmarks.utils.ConnectionSettings;
import java.util.Collections;
import java.util.Map;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

/** A Jedis client with sync capabilities. See: https://github.com/redis/jedis */
public class JedisClient implements SyncClient {
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
        return "jedis";
    }

    @Override
    public void connectToValkey(ConnectionSettings connectionSettings) {
        isClusterMode = connectionSettings.clusterMode;
        DefaultJedisClientConfig config =
                DefaultJedisClientConfig.builder()
                        .ssl(connectionSettings.useSsl)
                        .user(connectionSettings.username)
                        .password(connectionSettings.password)
                        .build();
        if (isClusterMode) {
            jedisCluster =
                    new JedisCluster(
                            Collections.singleton(
                                    new HostAndPort(connectionSettings.host, connectionSettings.port)),
                            config);
        } else {
            jedisStandalonePool =
                    new JedisPool(new HostAndPort(connectionSettings.host, connectionSettings.port), config);
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

    @Override
    public void hset(String key, Map<String, String> fieldValueMap) {
        if (isClusterMode) {
            jedisCluster.hset(key, fieldValueMap);
        } else {
            try (Jedis jedis = jedisStandalonePool.getResource()) {
                jedis.hset(key, fieldValueMap);
            }
        }
    }

    @Override
    public String hget(String key, String field) {
        if (isClusterMode) {
            return jedisCluster.hget(key, field);
        } else {
            try (Jedis jedis = jedisStandalonePool.getResource()) {
                return jedis.hget(key, field);
            }
        }
    }

    @Override
    public Map<String, String> hgetAll(String key) {
        if (isClusterMode) {
            return jedisCluster.hgetAll(key);
        } else {
            try (Jedis jedis = jedisStandalonePool.getResource()) {
                return jedis.hgetAll(key);
            }
        }
    }
}
