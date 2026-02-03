/** Copyright Valkey GLIDE Project Contributors - SPDX Identifier: Apache-2.0 */
package glide.api.commands;

import glide.api.models.ClusterValue;
import glide.api.models.GlideString;
import glide.api.models.commands.client.ClientKillOptions;
import glide.api.models.commands.client.ClientPauseMode;
import glide.api.models.commands.client.ClientUnblockType;
import glide.api.models.configuration.RequestRoutingConfiguration.Route;
import java.util.concurrent.CompletableFuture;

/**
 * Supports commands for the "Connection Management" group for a cluster client.
 *
 * @see <a href="https://valkey.io/commands/?group=connection">Connection Management Commands</a>
 */
public interface ConnectionManagementClusterCommands {

    /**
     * Pings the server.<br>
     * The command will be routed to all primary nodes.
     *
     * @see <a href="https://valkey.io/commands/ping/">valkey.io</a> for details.
     * @return <code>String</code> with <code>"PONG"</code>.
     * @example
     *     <pre>{@code
     * String payload = clusterClient.ping().get();
     * assert payload.equals("PONG");
     * }</pre>
     */
    CompletableFuture<String> ping();

    /**
     * Pings the server.<br>
     * The command will be routed to all primary nodes.
     *
     * @see <a href="https://valkey.io/commands/ping/">valkey.io</a> for details.
     * @param message The server will respond with a copy of the message.
     * @return <code>String</code> with a copy of the argument <code>message</code>.
     * @example
     *     <pre>{@code
     * String payload = clusterClient.ping("GLIDE").get();
     * assert payload.equals("GLIDE");
     * }</pre>
     */
    CompletableFuture<String> ping(String message);

    /**
     * Pings the server.<br>
     * The command will be routed to all primary nodes.
     *
     * @see <a href="https://valkey.io/commands/ping/">valkey.io</a> for details.
     * @param message The server will respond with a copy of the message.
     * @return <code>GlideString</code> with a copy of the argument <code>message</code>.
     * @example
     *     <pre>{@code
     * GlideString payload = clusterClient.ping(gs("GLIDE")).get();
     * assert payload.equals(gs("GLIDE"));
     * }</pre>
     */
    CompletableFuture<GlideString> ping(GlideString message);

    /**
     * Pings the server.
     *
     * @see <a href="https://valkey.io/commands/ping/">valkey.io</a> for details.
     * @param route Specifies the routing configuration for the command. The client will route the
     *     command to the nodes defined by <code>route</code>.
     * @return <code>String</code> with <code>"PONG"</code>.
     * @example
     *     <pre>{@code
     * String payload = clusterClient.ping(ALL_NODES).get();
     * assert payload.equals("PONG");
     * }</pre>
     */
    CompletableFuture<String> ping(Route route);

    /**
     * Pings the server.
     *
     * @see <a href="https://valkey.io/commands/ping/">valkey.io</a> for details.
     * @param message The ping argument that will be returned.
     * @param route Specifies the routing configuration for the command. The client will route the
     *     command to the nodes defined by <code>route</code>.
     * @return <code>String</code> with a copy of the argument <code>message</code>.
     * @example
     *     <pre>{@code
     * String payload = clusterClient.ping("GLIDE", RANDOM).get();
     * assert payload.equals("GLIDE");
     * }</pre>
     */
    CompletableFuture<String> ping(String message, Route route);

    /**
     * Pings the server.
     *
     * @see <a href="https://valkey.io/commands/ping/">valkey.io</a> for details.
     * @param message The ping argument that will be returned.
     * @param route Specifies the routing configuration for the command. The client will route the
     *     command to the nodes defined by <code>route</code>.
     * @return <code>GlideString</code> with a copy of the argument <code>message</code>.
     * @example
     *     <pre>{@code
     * GlideString payload = clusterClient.ping(gs("GLIDE"), RANDOM).get();
     * assert payload.equals(gs("GLIDE"));
     * }</pre>
     */
    CompletableFuture<GlideString> ping(GlideString message, Route route);

    /**
     * Gets the current connection id.<br>
     * The command will be routed to a random node.
     *
     * @see <a href="https://valkey.io/commands/client-id/">valkey.io</a> for details.
     * @return The id of the client.
     * @example
     *     <pre>{@code
     * long id = client.clientId().get();
     * assert id > 0
     * }</pre>
     */
    CompletableFuture<Long> clientId();

    /**
     * Gets the current connection id.
     *
     * @see <a href="https://valkey.io/commands/client-id/">valkey.io</a> for details.
     * @param route Specifies the routing configuration for the command. The client will route the
     *     command to the nodes defined by <code>route</code>.
     * @return A {@link ClusterValue} which holds a single value if single node route is used or a
     *     dictionary where each address is the key and its corresponding node response is the value.
     *     The value is the id of the client on that node.
     * @example
     *     <pre>{@code
     * long id = client.clientId(new SlotIdRoute(...)).get().getSingleValue();
     * assert id > 0;
     *
     * Map<String, Long> idPerNode = client.clientId(ALL_NODES).get().getMultiValue();
     * assert idPerNode.get("node1.example.com:6379") > 0;
     * </pre>
     */
    CompletableFuture<ClusterValue<Long>> clientId(Route route);

    /**
     * Gets the name of the current connection.<br>
     * The command will be routed a random node.
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
     * Gets the name of the current connection.
     *
     * @see <a href="https://valkey.io/commands/client-getname/">valkey.io</a> for details.
     * @param route Specifies the routing configuration for the command. The client will route the
     *     command to the nodes defined by <code>route</code>.
     * @return A {@link ClusterValue} which holds a single value if single node route is used or a
     *     dictionary where each address is the key and its corresponding node response is the value.
     *     The value is the name of the client connection as a string if a name is set, or null if no
     *     name is assigned.
     * @example
     *     <pre>{@code
     * String clientName = client.clientGetName(new SlotIdRoute(...)).get().getSingleValue();
     * assert clientName != null;
     *
     * Map<String, String> clientNamePerNode = client.clientGetName(ALL_NODES).get().getMultiValue();
     * assert clientNamePerNode.get("node1.example.com:6379") != null;
     * }</pre>
     */
    CompletableFuture<ClusterValue<String>> clientGetName(Route route);

    /**
     * Echoes the provided <code>message</code> back.<br>
     * The command will be routed a random node.
     *
     * @see <a href="https://valkey.io/commands/echo/">valkey.io</a> for details.
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
     * Echoes the provided <code>message</code> back.<br>
     * The command will be routed a random node.
     *
     * @see <a href="https://valkey.io/commands/echo/">valkey.io</a> for details.
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
     * Echoes the provided <code>message</code> back.
     *
     * @see <a href="https://valkey.io/commands/echo/">valkey.io</a> for details.
     * @param message The message to be echoed back.
     * @param route Specifies the routing configuration for the command. The client will route the
     *     command to the nodes defined by <code>route</code>.
     * @return The provided <code>message</code>.
     * @example
     *     <pre>{@code
     * // Command sent to a single random node via RANDOM route, expecting a SingleValue result.
     * String message = client.echo("GLIDE", RANDOM).get().getSingleValue();
     * assert message.equals("GLIDE");
     *
     * // Command sent to all nodes via ALL_NODES route, expecting a MultiValue result.
     * Map<String, String> msgForAllNodes = client.echo("GLIDE", ALL_NODES).get().getMultiValue();
     * for(var msgPerNode : msgForAllNodes.entrySet()) {
     *     assert msgPerNode.equals("GLIDE");
     * }
     * }</pre>
     */
    CompletableFuture<ClusterValue<String>> echo(String message, Route route);

    /**
     * Echoes the provided <code>message</code> back.
     *
     * @see <a href="https://valkey.io/commands/echo/">valkey.io</a> for details.
     * @param message The message to be echoed back.
     * @param route Specifies the routing configuration for the command. The client will route the
     *     command to the nodes defined by <code>route</code>.
     * @return The provided <code>message</code>.
     * @example
     *     <pre>{@code
     * // Command sent to a single random node via RANDOM route, expecting a SingleValue result.
     * GlideString message = client.echo(gs("GLIDE"), RANDOM).get().getSingleValue();
     * assert message.equals(gs("GLIDE"));
     *
     * // Command sent to all nodes via ALL_NODES route, expecting a MultiValue result.
     * Map<String, GlideString> msgForAllNodes = client.echo(gs("GLIDE"), ALL_NODES).get().getMultiValue();
     * for(var msgPerNode : msgForAllNodes.entrySet()) {
     *     assert msgPerNode.equals(gs("GLIDE"));
     * }
     * }</pre>
     */
    CompletableFuture<ClusterValue<GlideString>> echo(GlideString message, Route route);

    /**
     * Changes the currently selected database.
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
     * Sets the name of the current connection.<br>
     * The command will be routed to all nodes.
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
     * Sets the name of the current connection.
     *
     * @see <a href="https://valkey.io/commands/client-setname/">valkey.io</a> for details.
     * @param connectionName The name to assign to the current connection.
     * @param route Specifies the routing configuration for the command. The client will route the
     *     command to the nodes defined by <code>route</code>.
     * @return <code>OK</code> on success.
     * @example
     *     <pre>{@code
     * String response = client.clientSetName("my-app", RANDOM).get();
     * assert response.equals("OK");
     * }</pre>
     */
    CompletableFuture<String> clientSetName(String connectionName, Route route);

    /**
     * Returns information about all client connections.<br>
     * The command will be routed to a random node.
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
     * Returns information about all client connections.
     *
     * @see <a href="https://valkey.io/commands/client-list/">valkey.io</a> for details.
     * @param route Specifies the routing configuration for the command. The client will route the
     *     command to the nodes defined by <code>route</code>.
     * @return A {@link ClusterValue} containing client connection information.
     * @example
     *     <pre>{@code
     * ClusterValue<String> result = client.clientList(ALL_NODES).get();
     * for (String nodeList : result.getMultiValue().values()) {
     *     assert nodeList.contains("addr=");
     * }
     * }</pre>
     */
    CompletableFuture<ClusterValue<String>> clientList(Route route);

    /**
     * Returns information about the current client connection.<br>
     * The command will be routed to a random node.
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
     * Returns information about the current client connection.
     *
     * @see <a href="https://valkey.io/commands/client-info/">valkey.io</a> for details.
     * @param route Specifies the routing configuration for the command. The client will route the
     *     command to the nodes defined by <code>route</code>.
     * @return A {@link ClusterValue} containing current client connection information.
     * @example
     *     <pre>{@code
     * ClusterValue<String> result = client.clientInfo(ALL_NODES).get();
     * for (String nodeInfo : result.getMultiValue().values()) {
     *     assert nodeInfo.contains("id=");
     * }
     * }</pre>
     */
    CompletableFuture<ClusterValue<String>> clientInfo(Route route);

    /**
     * Kills a client connection by address.<br>
     * The command will be routed to all nodes.
     *
     * @see <a href="https://valkey.io/commands/client-kill/">valkey.io</a> for details.
     * @param ipPort The IP:PORT address of the client to kill.
     * @return The number of clients killed.
     * @example
     *     <pre>{@code
     * Long killed = client.clientKill("127.0.0.1:6379").get();
     * assert killed >= 0;
     * }</pre>
     */
    CompletableFuture<Long> clientKill(String ipPort);

    /**
     * Kills a client connection by address.
     *
     * @see <a href="https://valkey.io/commands/client-kill/">valkey.io</a> for details.
     * @param ipPort The IP:PORT address of the client to kill.
     * @param route Specifies the routing configuration for the command. The client will route the
     *     command to the nodes defined by <code>route</code>.
     * @return The number of clients killed.
     * @example
     *     <pre>{@code
     * Long killed = client.clientKill("127.0.0.1:6379", RANDOM).get();
     * assert killed >= 0;
     * }</pre>
     */
    CompletableFuture<Long> clientKill(String ipPort, Route route);

    /**
     * Kills client connections matching the specified filters.<br>
     * The command will be routed to all nodes.
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
     * Kills client connections matching the specified filters.
     *
     * @see <a href="https://valkey.io/commands/client-kill/">valkey.io</a> for details.
     * @param options Filters to match clients to kill.
     * @param route Specifies the routing configuration for the command. The client will route the
     *     command to the nodes defined by <code>route</code>.
     * @return The number of clients killed.
     * @example
     *     <pre>{@code
     * ClientKillOptions options = ClientKillOptions.builder()
     *     .type(ClientKillOptions.ClientType.NORMAL)
     *     .build();
     * Long killed = client.clientKill(options, ALL_NODES).get();
     * }</pre>
     */
    CompletableFuture<Long> clientKill(ClientKillOptions options, Route route);

    /**
     * Suspends all client connections for the specified timeout.<br>
     * The command will be routed to all nodes.
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
     * Suspends all client connections for the specified timeout.
     *
     * @see <a href="https://valkey.io/commands/client-pause/">valkey.io</a> for details.
     * @param timeout The duration in milliseconds to pause clients.
     * @param route Specifies the routing configuration for the command. The client will route the
     *     command to the nodes defined by <code>route</code>.
     * @return <code>OK</code> on success.
     * @example
     *     <pre>{@code
     * String response = client.clientPause(100, ALL_NODES).get();
     * assert response.equals("OK");
     * }</pre>
     */
    CompletableFuture<String> clientPause(long timeout, Route route);

    /**
     * Suspends client connections for the specified timeout and mode.<br>
     * The command will be routed to all nodes.
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
     * Suspends client connections for the specified timeout and mode.
     *
     * @see <a href="https://valkey.io/commands/client-pause/">valkey.io</a> for details.
     * @param timeout The duration in milliseconds to pause clients.
     * @param mode The pause mode ({@link ClientPauseMode#WRITE} or {@link ClientPauseMode#ALL}).
     * @param route Specifies the routing configuration for the command. The client will route the
     *     command to the nodes defined by <code>route</code>.
     * @return <code>OK</code> on success.
     * @example
     *     <pre>{@code
     * String response = client.clientPause(100, ClientPauseMode.WRITE, ALL_NODES).get();
     * assert response.equals("OK");
     * }</pre>
     */
    CompletableFuture<String> clientPause(long timeout, ClientPauseMode mode, Route route);

    /**
     * Resumes processing of clients that were paused by CLIENT PAUSE.<br>
     * The command will be routed to all nodes.
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
     * Resumes processing of clients that were paused by CLIENT PAUSE.
     *
     * @see <a href="https://valkey.io/commands/client-unpause/">valkey.io</a> for details.
     * @param route Specifies the routing configuration for the command. The client will route the
     *     command to the nodes defined by <code>route</code>.
     * @return <code>OK</code> on success.
     * @example
     *     <pre>{@code
     * String response = client.clientUnpause(ALL_NODES).get();
     * assert response.equals("OK");
     * }</pre>
     */
    CompletableFuture<String> clientUnpause(Route route);

    /**
     * Unblocks a client blocked by a blocking command.<br>
     * The command will be routed to all nodes.
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
     * Unblocks a client blocked by a blocking command.
     *
     * @see <a href="https://valkey.io/commands/client-unblock/">valkey.io</a> for details.
     * @param clientId The client ID to unblock.
     * @param route Specifies the routing configuration for the command. The client will route the
     *     command to the nodes defined by <code>route</code>.
     * @return 1 if the client was unblocked, 0 otherwise.
     * @example
     *     <pre>{@code
     * Long result = client.clientUnblock(123456, RANDOM).get();
     * }</pre>
     */
    CompletableFuture<Long> clientUnblock(long clientId, Route route);

    /**
     * Unblocks a client blocked by a blocking command with a specific error type.<br>
     * The command will be routed to all nodes.
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

    /**
     * Unblocks a client blocked by a blocking command with a specific error type.
     *
     * @see <a href="https://valkey.io/commands/client-unblock/">valkey.io</a> for details.
     * @param clientId The client ID to unblock.
     * @param type The unblock type ({@link ClientUnblockType#TIMEOUT} or {@link
     *     ClientUnblockType#ERROR}).
     * @param route Specifies the routing configuration for the command. The client will route the
     *     command to the nodes defined by <code>route</code>.
     * @return 1 if the client was unblocked, 0 otherwise.
     * @example
     *     <pre>{@code
     * Long result = client.clientUnblock(123456, ClientUnblockType.ERROR, RANDOM).get();
     * }</pre>
     */
    CompletableFuture<Long> clientUnblock(long clientId, ClientUnblockType type, Route route);
}
