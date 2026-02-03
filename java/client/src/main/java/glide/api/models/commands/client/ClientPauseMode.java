/** Copyright Valkey GLIDE Project Contributors - SPDX Identifier: Apache-2.0 */
package glide.api.models.commands.client;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Mode option for CLIENT PAUSE command.
 *
 * @see <a href="https://valkey.io/commands/client-pause/">valkey.io</a>
 */
@RequiredArgsConstructor
@Getter
public enum ClientPauseMode {
    /** Pause only write commands. */
    WRITE("WRITE"),
    /** Pause all commands. */
    ALL("ALL");

    private final String valkeyApi;
}
