/** Copyright Valkey GLIDE Project Contributors - SPDX Identifier: Apache-2.0 */
package glide.api.commands;

import glide.api.models.GlideString;
import glide.api.models.commands.client.ClientKillOptions;
import glide.api.models.commands.client.ClientPauseMode;
import glide.api.models.commands.client.ClientUnblockType;
import java.util.concurrent.CompletableFuture;

/**
 * Supports commands for the "Connection Management" group for a standalone client.
 *
 * @see <a href="https://valkey.io/commands/?group=connection">Connection Management Commands</a>
 */
public interface ConnectionManagementCommands {

    /**
     * Pings the server.
     *
     * @see <a href="https://valkey.io/commands/ping/">valkey.io</a> for details.
     * @return <code>String</code> with <code>"PONG"</code>.
     * @example
     *     <pre>{@code
     * String payload = client.ping().get();
     * assert payload.equals("PONG");
     * }</pre>
     */
    CompletableFuture<String> ping();

    /**
     * Pings the server.
     *
     * @see <a href="https://valkey.io/commands/ping/">valkey.io</a> for details.
     * @param message The server will respond with a copy of the message.
     * @return <code>String</code> with a copy of the argument <code>message</code>.
     * @example
     *     <pre>{@code
     * String payload = client.ping("GLIDE").get();
     * assert payload.equals("GLIDE");
     * }</pre>
     */
    CompletableFuture<String> ping(String message);

    /**
     * Pings the server.
     *
     * @see <a href="https://valkey.io/commands/ping/">valkey.io</a> for details.
     * @param message The server will respond with a copy of the message.
     * @return <code>GlideString</code> with a copy of the argument <code>message</code>.
     * @example
     *     <pre>{@code
     * GlideString payload = client.ping(gs("GLIDE")).get();
     * assert payload.equals(gs("GLIDE"));
     * }</pre>
     */
    CompletableFuture<GlideString> ping(GlideString message);

    /**
     * Gets the current connection id.
     *
     * @see <a href="https://valkey.io/commands/client-id/">valkey.io</a> for details.
     * @return The id of the client.
     * @example
     *     <pre>{@code
     * Long id = client.clientId().get();
     * assert id > 0;
     * }</pre>
     */
    CompletableFuture<Long> clientId();

    /**
     * Gets the name of the current connection.
     *
     * @see <a href="https://valkey.io/commands/client-getname/">valkey.io</a> for details.
     * @return The name of the client connection as a string if a name is set, or <code>null</code> if
     *     no name is assigned.
     * @example
     *     <pre>{@code
     * String clientName = client.clientGetName().get();
     * assert clientName != null;
     * }</pre>
     */
    CompletableFuture<String> clientGetName();

    /**
     * Echoes the provided <code>message</code> back.
     *
     * @see <a href="https://valkey.io/commands/echo/>valkey.io</a> for details.
     * @param message The message to be echoed back.
     * @return The provided <code>message</code>.
     * @example
     *     <pre>{@code
     * String payload = client.echo("GLIDE").get();
     * assert payload.equals("GLIDE");
     * }</pre>
     */
    CompletableFuture<String> echo(String message);

    /**
     * Echoes the provided <code>message</code> back.
     *
     * @see <a href="https://valkey.io/commands/echo/>valkey.io</a> for details.
     * @param message The message to be echoed back.
     * @return The provided <code>message</code>.
     * @example
     *     <pre>{@code
     * GlideString payload = client.echo(gs("GLIDE")).get();
     * assert payload.equals(gs("GLIDE"));
     * }</pre>
     */
    CompletableFuture<GlideString> echo(GlideString message);

    /**
     * Changes the currently selected database.
     *
     * <p><b>WARNING:</b> This command is <b>NOT RECOMMENDED</b> for production use. Upon
     * reconnection, the client will revert to the database_id specified in the client configuration
     * (default: 0), NOT the database selected via this command.
     *
     * <p><b>RECOMMENDED APPROACH:</b> Use the database_id parameter in client configuration instead:
     *
     * <p><b>RECOMMENDED EXAMPLE:</b>
     *
     * <pre>{@code
     * GlideClient client = GlideClient.createClient(
     *     GlideClientConfiguration.builder()
     *         .address(NodeAddress.builder().host("localhost").port(6379).build())
     *         .databaseId(5)  // Recommended: persists across reconnections
     *         .build()
     * ).get();
     * }</pre>
     *
     * @see <a href="https://valkey.io/commands/select/">valkey.io</a> for details.
     * @param index The index of the database to select.
     * @return A simple <code>OK</code> response.
     * @example
     *     <pre>{@code
     * String response = regularClient.select(0).get();
     * assert response.equals("OK");
     * }</pre>
     */
    CompletableFuture<String> select(long index);

    /**
     * Sets the name of the current connection.
     *
     * @see <a href="https://valkey.io/commands/client-setname/">valkey.io</a> for details.
     * @param connectionName The name to assign to the current connection.
     * @return <code>OK</code> on success.
     * @example
     *     <pre>{@code
     * String response = client.clientSetName("my-app").get();
     * assert response.equals("OK");
     * }</pre>
     */
    CompletableFuture<String> clientSetName(String connectionName);

    /**
     * Returns information about all client connections.
     *
     * @see <a href="https://valkey.io/commands/client-list/">valkey.io</a> for details.
     * @return A string containing client connection information.
     * @example
     *     <pre>{@code
     * String list = client.clientList().get();
     * assert list.contains("addr=");
     * }</pre>
     */
    CompletableFuture<String> clientList();

    /**
     * Returns information about the current client connection.
     *
     * @see <a href="https://valkey.io/commands/client-info/">valkey.io</a> for details.
     * @return A string containing current client connection information.
     * @example
     *     <pre>{@code
     * String info = client.clientInfo().get();
     * assert info.contains("id=");
     * }</pre>
     */
    CompletableFuture<String> clientInfo();

    /**
     * Kills a client connection by address.
     *
     * @see <a href="https://valkey.io/commands/client-kill/">valkey.io</a> for details.
     * @param ipPort The IP:PORT address of the client to kill.
     * @return The number of clients killed (0 or 1).
     * @example
     *     <pre>{@code
     * Long killed = client.clientKill("127.0.0.1:6379").get();
     * assert killed >= 0;
     * }</pre>
     */
    CompletableFuture<Long> clientKill(String ipPort);

    /**
     * Kills client connections matching the specified filters.
     *
     * @see <a href="https://valkey.io/commands/client-kill/">valkey.io</a> for details.
     * @param options Filters to match clients to kill.
     * @return The number of clients killed.
     * @example
     *     <pre>{@code
     * ClientKillOptions options = ClientKillOptions.builder()
     *     .type(ClientKillOptions.ClientType.NORMAL)
     *     .skipMe(true)
     *     .build();
     * Long killed = client.clientKill(options).get();
     * }</pre>
     */
    CompletableFuture<Long> clientKill(ClientKillOptions options);

    /**
     * Suspends all client connections for the specified timeout.
     *
     * @see <a href="https://valkey.io/commands/client-pause/">valkey.io</a> for details.
     * @param timeout The duration in milliseconds to pause clients.
     * @return <code>OK</code> on success.
     * @example
     *     <pre>{@code
     * String response = client.clientPause(100).get();
     * assert response.equals("OK");
     * }</pre>
     */
    CompletableFuture<String> clientPause(long timeout);

    /**
     * Suspends client connections for the specified timeout and mode.
     *
     * @see <a href="https://valkey.io/commands/client-pause/">valkey.io</a> for details.
     * @param timeout The duration in milliseconds to pause clients.
     * @param mode The pause mode ({@link ClientPauseMode#WRITE} or {@link ClientPauseMode#ALL}).
     * @return <code>OK</code> on success.
     * @example
     *     <pre>{@code
     * String response = client.clientPause(100, ClientPauseMode.WRITE).get();
     * assert response.equals("OK");
     * }</pre>
     */
    CompletableFuture<String> clientPause(long timeout, ClientPauseMode mode);

    /**
     * Resumes processing of clients that were paused by CLIENT PAUSE.
     *
     * @see <a href="https://valkey.io/commands/client-unpause/">valkey.io</a> for details.
     * @return <code>OK</code> on success.
     * @example
     *     <pre>{@code
     * String response = client.clientUnpause().get();
     * assert response.equals("OK");
     * }</pre>
     */
    CompletableFuture<String> clientUnpause();

    /**
     * Unblocks a client blocked by a blocking command.
     *
     * @see <a href="https://valkey.io/commands/client-unblock/">valkey.io</a> for details.
     * @param clientId The client ID to unblock.
     * @return 1 if the client was unblocked, 0 otherwise.
     * @example
     *     <pre>{@code
     * Long result = client.clientUnblock(123456).get();
     * }</pre>
     */
    CompletableFuture<Long> clientUnblock(long clientId);

    /**
     * Unblocks a client blocked by a blocking command with a specific error type.
     *
     * @see <a href="https://valkey.io/commands/client-unblock/">valkey.io</a> for details.
     * @param clientId The client ID to unblock.
     * @param type The unblock type ({@link ClientUnblockType#TIMEOUT} or {@link
     *     ClientUnblockType#ERROR}).
     * @return 1 if the client was unblocked, 0 otherwise.
     * @example
     *     <pre>{@code
     * Long result = client.clientUnblock(123456, ClientUnblockType.ERROR).get();
     * }</pre>
     */
    CompletableFuture<Long> clientUnblock(long clientId, ClientUnblockType type);
}
