/** Copyright Valkey GLIDE Project Contributors - SPDX Identifier: Apache-2.0 */
package glide.benchmarks.clients;

import java.util.Map;

/** A Valkey client with sync capabilities */
public interface SyncClient extends Client {
    void set(String key, String value);

    String get(String key);

    void hset(String key, Map<String, String> fieldValueMap);

    String hget(String key, String field);

    Map<String, String> hgetAll(String key);
}
