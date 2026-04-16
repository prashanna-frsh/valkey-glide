/** Copyright Valkey GLIDE Project Contributors - SPDX Identifier: Apache-2.0 */
package redis.clients.jedis;

import glide.api.models.Batch;
import glide.api.models.commands.SetOptions;
import glide.api.models.exceptions.RequestException;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.jedis.params.SetParams;

/**
 * Jedis-compatible pipeline backed by GLIDE's non-atomic {@link Batch} (Valkey pipelining).
 *
 * <p>Queue commands on this object, then call {@link #sync()} or {@link #close()} to execute the
 * batch. Read results with {@link Response#get()} after syncing.
 *
 * <pre>{@code
 * Pipeline p = jedis.pipelined();
 * Response<String> r1 = p.set("k", "v");
 * Response<String> r2 = p.get("k");
 * p.sync();
 * assert "OK".equals(r1.get());
 * assert "v".equals(r2.get());
 * }</pre>
 */
public class Pipeline implements Closeable {
    private final Jedis jedis;
    private Batch batch;
    private final List<Response<?>> pending = new ArrayList<>();

    Pipeline(Jedis jedis) {
        this.jedis = jedis;
        this.batch = new Batch(false);
    }

    private static Object normalizeResult(Object raw) {
        if (raw instanceof RequestException) {
            RequestException re = (RequestException) raw;
            return new JedisDataException(re.getMessage(), re);
        }
        return raw;
    }

    private static final Builder<Long> EXPIRE_REPLY_TO_LONG =
            new Builder<Long>() {
                @Override
                public Long build(Object data) {
                    if (data instanceof Boolean) {
                        return ((Boolean) data) ? 1L : 0L;
                    }
                    return BuilderFactory.LONG.build(data);
                }
            };

    /**
     * Sends all queued commands to the server and fills {@link Response} objects. Safe to call with
     * no queued commands (no-op).
     */
    public void sync() {
        jedis.assertUsable();
        if (pending.isEmpty()) {
            return;
        }
        executeBatch();
    }

    /**
     * Same as {@link #sync()}, then returns all response values in order (like Jedis {@code
     * syncAndReturnAll}).
     *
     * @return list of decoded results, same order as queued commands
     */
    public List<Object> syncAndReturnAll() {
        jedis.assertUsable();
        if (pending.isEmpty()) {
            return Collections.emptyList();
        }
        List<Response<?>> snapshot = new ArrayList<>(pending);
        executeBatch();
        List<Object> out = new ArrayList<>(snapshot.size());
        for (Response<?> r : snapshot) {
            out.add(r.get());
        }
        return out;
    }

    private void executeBatch() {
        try {
            Object[] results = jedis.getGlideClient().exec(batch, false).get();
            if (results == null) {
                throw new JedisException("Pipeline exec returned null");
            }
            int n = Math.min(pending.size(), results.length);
            for (int i = 0; i < n; i++) {
                pending.get(i).set(normalizeResult(results[i]));
            }
            if (results.length != pending.size()) {
                for (int i = n; i < pending.size(); i++) {
                    pending.get(i).set(new JedisDataException("Missing pipeline result at index " + i));
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new JedisConnectionException("Pipeline sync interrupted", e);
        } catch (ExecutionException e) {
            jedis.markBrokenIfPooledConnectionFailure(e);
            Throwable cause = e.getCause() != null ? e.getCause() : e;
            throw new JedisException("Pipeline sync failed", cause);
        } finally {
            pending.clear();
            batch = new Batch(false);
        }
    }

    /**
     * Queues SET.
     *
     * @return deferred response; call {@link #sync()} before {@link Response#get()}
     */
    public Response<String> set(String key, String value) {
        jedis.assertUsable();
        Response<String> r = new Response<>(BuilderFactory.STRING);
        pending.add(r);
        batch.set(key, value);
        return r;
    }

    /**
     * Queues SET with {@link SetParams}.
     *
     * @return deferred response; call {@link #sync()} before {@link Response#get()}
     */
    public Response<String> set(String key, String value, SetParams params) {
        jedis.assertUsable();
        Response<String> r = new Response<>(BuilderFactory.STRING);
        pending.add(r);
        SetOptions options = Jedis.convertSetParamsToSetOptions(params);
        batch.set(key, value, options);
        return r;
    }

    /** Queues SET (binary). Reply is typically {@code OK} (same as Jedis). */
    public Response<String> set(byte[] key, byte[] value) {
        jedis.assertUsable();
        Response<String> r = new Response<>(BuilderFactory.STRING);
        pending.add(r);
        batch.set(key, value);
        return r;
    }

    /** Queues SET with {@link SetParams} (binary). */
    public Response<String> set(byte[] key, byte[] value, SetParams params) {
        jedis.assertUsable();
        Response<String> r = new Response<>(BuilderFactory.STRING);
        pending.add(r);
        SetOptions options = Jedis.convertSetParamsToSetOptions(params);
        batch.set(key, value, options);
        return r;
    }

    /** Queues GET. */
    public Response<String> get(String key) {
        jedis.assertUsable();
        Response<String> r = new Response<>(BuilderFactory.STRING);
        pending.add(r);
        batch.get(key);
        return r;
    }

    /** Queues GET (binary key). */
    public Response<byte[]> get(byte[] key) {
        jedis.assertUsable();
        Response<byte[]> r = new Response<>(BuilderFactory.BYTE_ARRAY);
        pending.add(r);
        batch.get(key);
        return r;
    }

    /** Queues EXPIRE; response uses Jedis semantics (1/0). */
    public Response<Long> expire(String key, long seconds) {
        jedis.assertUsable();
        Response<Long> r = new Response<>(EXPIRE_REPLY_TO_LONG);
        pending.add(r);
        batch.expire(key, seconds);
        return r;
    }

    /** Queues EXPIRE (binary key). */
    public Response<Long> expire(byte[] key, long seconds) {
        jedis.assertUsable();
        Response<Long> r = new Response<>(EXPIRE_REPLY_TO_LONG);
        pending.add(r);
        batch.expire(key, seconds);
        return r;
    }

    /** Queues HGET. */
    public Response<String> hget(String key, String field) {
        jedis.assertUsable();
        Response<String> r = new Response<>(BuilderFactory.STRING);
        pending.add(r);
        batch.hget(key, field);
        return r;
    }

    /** Queues HGET (binary). */
    public Response<byte[]> hget(byte[] key, byte[] field) {
        jedis.assertUsable();
        Response<byte[]> r = new Response<>(BuilderFactory.BYTE_ARRAY);
        pending.add(r);
        batch.hget(key, field);
        return r;
    }

    @Override
    public void close() {
        sync();
    }
}
