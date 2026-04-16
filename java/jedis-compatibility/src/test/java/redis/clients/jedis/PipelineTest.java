/** Copyright Valkey GLIDE Project Contributors - SPDX Identifier: Apache-2.0 */
package redis.clients.jedis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import glide.api.GlideClient;
import glide.api.models.Batch;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.params.SetParams;

class PipelineTest {

    @Test
    void pipelinedSyncFillsResponses() throws Exception {
        GlideClient glideClient = mock(GlideClient.class);
        when(glideClient.exec(any(Batch.class), eq(false)))
                .thenReturn(
                        CompletableFuture.completedFuture(new Object[] {"OK", "value", 1L, "OK", "fieldVal"}));

        Jedis jedis = new Jedis(glideClient, DefaultJedisClientConfig.builder().build());

        Pipeline p = jedis.pipelined();
        Response<String> rSet = p.set("k", "v");
        Response<String> rGet = p.get("k");
        Response<Long> rExp = p.expire("k", 60);
        Response<String> rSetParams = p.set("k2", "v2", SetParams.setParams().ex(10));
        Response<String> rHget = p.hget("h", "f");
        p.sync();

        assertEquals("OK", rSet.get());
        assertEquals("value", rGet.get());
        assertEquals(1L, rExp.get());
        assertEquals("OK", rSetParams.get());
        assertEquals("fieldVal", rHget.get());
    }

    @Test
    void syncAndReturnAllMatchesOrder() throws Exception {
        GlideClient glideClient = mock(GlideClient.class);
        when(glideClient.exec(any(Batch.class), eq(false)))
                .thenReturn(CompletableFuture.completedFuture(new Object[] {"OK", "x"}));

        Jedis jedis = new Jedis(glideClient, DefaultJedisClientConfig.builder().build());
        Pipeline p = jedis.pipelined();
        p.set("a", "b");
        p.get("a");
        List<Object> all = p.syncAndReturnAll();
        assertEquals(Arrays.asList("OK", "x"), all);
    }
}
