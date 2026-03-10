/** Copyright Valkey GLIDE Project Contributors - SPDX Identifier: Apache-2.0 */
package glide.benchmarks.clients.jedis;

import glide.benchmarks.clients.SyncClient;
import glide.benchmarks.utils.ConnectionSettings;
import java.util.Collections;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPooled;

/** A GLIDE jedis-compatibility client using the JedisPooled API */
public class JedisCompatClient implements SyncClient {
    private boolean isClusterMode;
    private JedisPooled jedisPooled;
    private JedisCluster jedisCluster;

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
            jedisPooled = new JedisPooled(new HostAndPort(connectionSettings.host, connectionSettings.port), config);
        }
    }

    @Override
    public void set(String key, String value) {
        if (isClusterMode) {
            jedisCluster.set(key, value);
        } else {
            jedisPooled.set(key, value);
        }
    }

    @Override
    public String get(String key) {
        if (isClusterMode) {
            return jedisCluster.get(key);
        } else {
            return jedisPooled.get(key);
        }
    }

    @Override
    public void closeConnection() {
        if (jedisCluster != null) {
            jedisCluster.close();
        }
        if (jedisPooled != null) {
            jedisPooled.close();
        }
    }

    @Override
    public String getName() {
        return "glide_jedis_compat";
    }
}
