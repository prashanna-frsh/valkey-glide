/** Copyright Valkey GLIDE Project Contributors - SPDX Identifier: Apache-2.0 */
package glide.api.models.commands.client;

import glide.api.models.GlideString;
import glide.utils.ArgsBuilder;
import lombok.Builder;

/**
 * Optional arguments for CLIENT TRACKING command.
 *
 * @see <a href="https://valkey.io/commands/client-tracking/">valkey.io</a>
 */
@Builder
public final class ClientTrackingOptions {

    /** Enable redirection of tracking messages to another connection. */
    private final Boolean redirect;

    /** Client ID for redirection target. */
    private final Long clientId;

    /** Key prefixes to track. */
    private final String[] prefixes;

    /** Enable broadcasting mode for tracking. */
    private final Boolean bcast;

    /** Enable opt-in mode for caching. */
    private final Boolean optin;

    /** Enable opt-out mode for caching. */
    private final Boolean optout;

    /** Don't send invalidation messages to the client that performed the change. */
    private final Boolean noloop;

    /**
     * Converts options to Valkey API arguments.
     *
     * @return Array of arguments for CLIENT TRACKING command.
     */
    public GlideString[] toArgs() {
        ArgsBuilder builder = new ArgsBuilder();

        if (redirect != null && redirect) {
            builder.add("REDIRECT");
            if (clientId != null) {
                builder.add(Long.toString(clientId));
            }
        }

        if (prefixes != null && prefixes.length > 0) {
            for (String prefix : prefixes) {
                builder.add("PREFIX").add(prefix);
            }
        }

        if (bcast != null && bcast) {
            builder.add("BCAST");
        }

        if (optin != null && optin) {
            builder.add("OPTIN");
        }

        if (optout != null && optout) {
            builder.add("OPTOUT");
        }

        if (noloop != null && noloop) {
            builder.add("NOLOOP");
        }

        return builder.toArray();
    }
}
