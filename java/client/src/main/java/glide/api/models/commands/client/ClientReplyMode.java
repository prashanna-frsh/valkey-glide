/** Copyright Valkey GLIDE Project Contributors - SPDX Identifier: Apache-2.0 */
package glide.api.models.commands.client;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Reply mode option for CLIENT REPLY command.
 *
 * @see <a href="https://valkey.io/commands/client-reply/">valkey.io</a>
 */
@RequiredArgsConstructor
@Getter
public enum ClientReplyMode {
    /** The server will return replies to commands. */
    ON("ON"),
    /** The server will not return replies to commands. */
    OFF("OFF"),
    /** The server will skip the reply for the immediately following command. */
    SKIP("SKIP");

    private final String valkeyApi;
}
