# Jedis Compatibility Layer

This sub-module provides a Jedis-compatible API layer for Valkey GLIDE, allowing existing Jedis applications to migrate to GLIDE with minimal code changes.

## Architecture

The Jedis compatibility layer is implemented as a separate Gradle sub-module that:

- **Depends on**: The main `client` module containing GLIDE core functionality
- **Provides**: Jedis-compatible classes and interfaces
- **Enables**: Drop-in replacement for Jedis in existing applications

## Key Components

### Core Classes
- `Jedis` - Main client class compatible with Jedis API
- `JedisCluster` - Cluster client compatible with Jedis cluster API
- `UnifiedJedis` - Unified interface for both standalone and cluster operations
- `JedisPool` / `JedisPooled` - Connection pooling implementations

### Configuration
- `JedisClientConfig` - Client configuration interface
- `DefaultJedisClientConfig` - Default configuration implementation
- `ConfigurationMapper` - Maps Jedis config to GLIDE config
- `ClusterConfigurationMapper` - Maps Jedis cluster config to GLIDE cluster config

### Protocol Support
- `Protocol` - Redis protocol constants and commands
- `RedisProtocol` - Protocol version handling
- Various parameter classes for command options

## Usage

### Gradle Dependency

```gradle
dependencies {
    implementation group: 'io.valkey', name: 'valkey-glide-jedis-compatibility', version: '2.1.0', classifier: 'osx-aarch_64'
}
```

### Maven Dependency

```xml
<dependency>
    <groupId>io.valkey</groupId>
    <artifactId>valkey-glide-jedis-compatibility</artifactId>
    <version>2.1.0</version>
    <classifier>osx-aarch_64</classifier>
</dependency>
```

### Basic Example

```java
import redis.clients.jedis.Jedis;

// Drop-in replacement for Jedis
try (Jedis jedis = new Jedis("localhost", 6379)) {
    jedis.set("key", "value");
    String value = jedis.get("key");
    System.out.println(value); // prints: value
}
```

### Connection and Client Commands

The compatibility layer supports client connection management and inspection commands:

```java
import redis.clients.jedis.Jedis;

try (Jedis jedis = new Jedis("localhost", 6379)) {
    // Echo a message
    String echoed = jedis.echo("Hello, Valkey!");
    System.out.println(echoed); // prints: Hello, Valkey!
    
    // Get the client connection ID
    long clientId = jedis.clientId();
    System.out.println("Client ID: " + clientId);
    
    // Get the client connection name (if set)
    String clientName = jedis.clientGetname();
    System.out.println("Client name: " + clientName);
}
```

### Custom Commands

Execute any Valkey command, including module commands or new commands not yet in the API:

```java
import redis.clients.jedis.Jedis;

try (Jedis jedis = new Jedis("localhost", 6379)) {
    // Execute a custom command
    Object result = jedis.customCommand("SET", "mykey", "myvalue");
    System.out.println(result); // prints: OK
    
    // Execute a module command (e.g., RedisJSON)
    Object jsonResult = jedis.customCommand("JSON.SET", "user:1", "$", "{\"name\":\"John\"}");
    
    // Read the value back
    Object value = jedis.customCommand("GET", "mykey");
    System.out.println(value); // prints: myvalue
}
```

### Transaction Commands

The compatibility layer provides transaction support with GLIDE's Batch API:

```java
import redis.clients.jedis.Jedis;
import glide.api.models.Batch;

try (Jedis jedis = new Jedis("localhost", 6379)) {
    // Watch keys for optimistic locking
    jedis.watch("counter");
    
    // Get current value
    String currentValue = jedis.get("counter");
    int newValue = (currentValue != null ? Integer.parseInt(currentValue) : 0) + 1;
    
    // Execute transaction using GLIDE's Batch API
    Batch transaction = new Batch(true)  // true = atomic (transactional)
        .set("counter", String.valueOf(newValue))
        .get("counter");
    
    Object[] results = jedis.getGlideClient().exec(transaction, true).get();
    System.out.println("Transaction result: " + results[1]);
    
    // Unwatch all keys
    jedis.unwatch();
}
```

**Note**: The traditional Jedis transaction pattern (`multi()`, `exec()`, `discard()`) is not supported in the GLIDE compatibility layer. These methods will throw `UnsupportedOperationException`. Use GLIDE's `Batch` API instead for transactional operations.

### Scripting Commands

The compatibility layer supports Lua scripting and Valkey Functions:

#### Lua Scripts

```java
import redis.clients.jedis.Jedis;
import java.util.Collections;
import java.util.List;

try (Jedis jedis = new Jedis("localhost", 6379)) {
    // Execute Lua script directly
    Object result = jedis.eval("return 'Hello'");
    
    // Execute with keys and arguments
    jedis.set("mykey", "myvalue");
    Object value = jedis.eval(
        "return redis.call('GET', KEYS[1])", 
        1, 
        "mykey"
    );
    
    // Load script and execute by SHA
    String sha1 = jedis.scriptLoad("return ARGV[1]");
    Object result2 = jedis.evalsha(sha1, 0, "test");
    
    // Check if scripts exist
    List<Boolean> exists = jedis.scriptExists(sha1);
    System.out.println("Script exists: " + exists.get(0));
}
```

#### Valkey Functions (7.0+)

```java
import redis.clients.jedis.Jedis;
import java.util.Collections;
import java.util.List;

try (Jedis jedis = new Jedis("localhost", 6379)) {
    // Load a function library
    String lib = "#!lua name=mylib\n" +
                 "redis.register_function('myfunc', " +
                 "function(keys, args) return args[1] end)";
    String libName = jedis.functionLoad(lib);
    
    // Call the function
    Object result = jedis.fcall(
        "myfunc",
        Collections.emptyList(),
        Collections.singletonList("42")
    );
    System.out.println("Result: " + result); // prints: 42
    
    // List loaded functions
    List<Object> functions = jedis.functionList();
    
    // Clean up
    jedis.functionDelete("mylib");
}
```

#### Script Debugging

```java
import redis.clients.jedis.Jedis;

try (Jedis jedis = new Jedis("localhost", 6379)) {
    // Enable script debugging
    jedis.scriptDebug("YES");
    
    // Get script help/information
    List<String> scriptHelp = jedis.scriptShow();
    scriptHelp.forEach(System.out::println);
    
    // Disable script debugging
    jedis.scriptDebug("NO");
}
```

#### Implementation Notes

The scripting commands are implemented using a combination of:
- **Type-safe GLIDE APIs** for most operations (e.g., `scriptExists`, `scriptFlush`, `evalReadOnly`, `fcall`, `functionLoad`)
- **`customCommand`** for operations not yet exposed in GLIDE's type-safe API:
  - `SCRIPT LOAD` - Required to explicitly load scripts to the server cache
  - `EVALSHA` (non-readonly) - Standard EVALSHA that allows write operations

This hybrid approach ensures full Jedis API compatibility while leveraging GLIDE's type-safe APIs wherever available.

## Migration Guide

See the [compatibility layer migration guide](./compatibility-layer-migration-guide.md) for detailed migration instructions.

## Build Commands

```bash
# Compile the compatibility layer
./gradlew :jedis-compatibility:compileJava

# Run tests
./gradlew :jedis-compatibility:test

# Build JAR
./gradlew :jedis-compatibility:jar

# Publish to local repository
./gradlew :jedis-compatibility:publishToMavenLocal
```

## Module Dependencies

```
jedis-compatibility
├── client (GLIDE core client)
│   ├── protobuf-java
│   ├── netty-handler
│   └── native libraries (Rust FFI)
└── commons-pool2 (connection pooling)
```
