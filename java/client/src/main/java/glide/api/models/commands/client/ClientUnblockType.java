/** Copyright Valkey GLIDE Project Contributors - SPDX Identifier: Apache-2.0 */
package glide.api.models.commands.client;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Unblock type option for CLIENT UNBLOCK command.
 *
 * @see <a href="https://valkey.io/commands/client-unblock/">valkey.io</a>
 */
@RequiredArgsConstructor
@Getter
public enum ClientUnblockType {
    /** Unblock the client using a timeout error. */
    TIMEOUT("TIMEOUT"),
    /** Unblock the client using a generic error. */
    ERROR("ERROR");

    private final String valkeyApi;
}
