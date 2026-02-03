/** Copyright Valkey GLIDE Project Contributors - SPDX Identifier: Apache-2.0 */
package glide.api.models.commands.client;

import glide.api.models.GlideString;
import glide.utils.ArgsBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Optional arguments for CLIENT KILL command.
 *
 * @see <a href="https://valkey.io/commands/client-kill/">valkey.io</a>
 */
@Builder
public final class ClientKillOptions {

    /** Client ID filter. */
    private final String id;

    /** Client address (IP:PORT) filter. */
    private final String addr;

    /** Client type filter. */
    private final ClientType type;

    /** Username filter. */
    private final String user;

    /** Whether to skip killing the current connection. */
    private final Boolean skipMe;

    /** Maximum age filter in seconds. */
    private final Long maxAge;

    /** Client type for filtering. */
    @RequiredArgsConstructor
    @Getter
    public enum ClientType {
        /** Normal client connection. */
        NORMAL("normal"),
        /** Master replication connection. */
        MASTER("master"),
        /** Replica replication connection. */
        REPLICA("replica"),
        /** Pub/Sub client connection. */
        PUBSUB("pubsub");

        private final String valkeyApi;
    }

    /**
     * Converts options to Valkey API arguments.
     *
     * @return Array of arguments for CLIENT KILL command.
     */
    public GlideString[] toArgs() {
        ArgsBuilder builder = new ArgsBuilder();

        if (id != null) {
            builder.add("ID").add(id);
        }
        if (addr != null) {
            builder.add("ADDR").add(addr);
        }
        if (type != null) {
            builder.add("TYPE").add(type.getValkeyApi());
        }
        if (user != null) {
            builder.add("USER").add(user);
        }
        if (skipMe != null) {
            builder.add("SKIPME").add(skipMe ? "YES" : "NO");
        }
        if (maxAge != null) {
            builder.add("MAXAGE").add(Long.toString(maxAge));
        }

        return builder.toArray();
    }
}
