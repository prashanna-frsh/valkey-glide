# Jedis Compatibility Commands

This document lists the commands supported by the Jedis compatibility layer provided by Valkey GLIDE. It includes method signatures and brief descriptions.

| Valkey Command | Java Method Signature | Description | Valkey Version | Binary Support |
| :------------- | :-------------------- | :---------- | :------------- | :------------- |
| SET | `String set(String key, String value)` | Set the string value of a key. | 1.0.0 | Yes (byte[]) |
| SET | `String set(String key, String value, SetParams params)` | Set the string value of a key with options. | 1.0.0 | Yes (byte[], SetParams) |
| GET | `String get(String key)` | Get the string value of a key. | 1.0.0 | Yes (byte[]) |
| PING | `String ping()` | Test if the server is alive. | 1.0.0 | Yes (byte[]) |
| PING | `String ping(String message)` | Test if the server is alive and echo back a message. | 2.8.0 | Yes (byte[]) |
| SELECT | `String select(int index)` | Select a database. (Stubbed in compatibility layer) | N/A | N/A |
| AUTH | `String auth(String password)` | Authenticate with the server. (Stubbed in compatibility layer) | N/A | N/A |
| AUTH | `String auth(String user, String password)` | Authenticate with username and password. (Stubbed in compatibility layer) | N/A | N/A |
| ACL.LIST | `List<String> aclList()` | Return the list of ACL rules. | N/A | No |
| ACL.GETUSER | `AccessControlUser aclGetUser(String name)` | Return the ACL rules defined for the given user. | N/A | No |
| ACL.SETUSER | `String aclSetUser(String name, String... rules)` | Create or modify an ACL user with the given rules. | N/A | No |
| ACL.DELUSER | `long aclDelUser(String... usernames)` | Delete the specified ACL users. | N/A | No |
| ACL.CAT | `List<String> aclCat()` | Return the list of ACL categories. | N/A | No |
| ACL.CAT | `List<String> aclCat(String category)` | Return the list of commands in the given ACL category. | N/A | No |
| ACL.GENPASS | `String aclGenPass()` | Generate a random password for ACL users (default bit length). | N/A | No |
| ACL.GENPASS | `String aclGenPass(int bits)` | Generate a random password with specified bits for ACL users. | N/A | No |
| ACL.LOG | `List<AccessControlLogEntry> aclLog()` | Return recent ACL security events. | N/A | No |
| ACL.LOG | `List<AccessControlLogEntry> aclLog(int count)` | Return the specified number of recent ACL security events. | N/A | No |
| ACL.LOG.RESET | `String aclLogReset()` | Clear the ACL security events log. | N/A | No |
| ACL.WHOAMI | `String aclWhoAmI()` | Return the username the current connection is authenticated as. | N/A | No |
| ACL.USERS | `List<String> aclUsers()` | Return the list of ACL usernames. | N/A | No |
| ACL.SAVE | `String aclSave()` | Save the current ACL rules to the configured ACL file. | N/A | No |
| ACL.LOAD | `String aclLoad()` | Reload ACL rules from the configured ACL file. | N/A | No |
| ACL.DRYRUN | `String aclDryRun(String username, String command, String... args)` | Simulate execution of a command by a user without executing it. | N/A | No |
| DEL | `long del(String key)` | Delete one or more keys. | 1.0.0 | Yes (byte[]) |
| DEL | `long del(String... keys)` | Delete one or more keys. | 1.0.0 | Yes (byte[][]) |
| KEYS | `Set<String> keys(String pattern)` | Find all keys matching the given pattern. | 1.0.0 | Yes (byte[]) |
| MSET | `String mset(String... keysvalues)` | Set multiple key-value pairs. | 1.0.0 | Yes (byte[][]) |
| MSET | `String mset(Map<String, String> keyValueMap)` | Set multiple key-value pairs from a map. | 1.0.0 | Yes (Map<byte[], byte[]>) |
| MGET | `List<String> mget(String... keys)` | Get multiple values. | 1.0.0 | Yes (byte[][]) |
| SETNX | `long setnx(String key, String value)` | Set key to value if key does not exist. | 1.0.0 | Yes (byte[]) |
| SETEX | `String setex(String key, long seconds, String value)` | Set key to value with expiration in seconds. | 1.0.0 | Yes (byte[]) |
| PSETEX | `String psetex(String key, long milliseconds, String value)` | Set key to value with expiration in milliseconds. | 1.0.0 | Yes (byte[]) |
| GETSET (Deprecated) | `String getSet(String key, String value)` | Get old value and set new value. | 1.0.0 | Yes (byte[]) |
| SETGET | `String setGet(String key, String value)` | Set new value and return old value. | 6.2.0 | Yes (byte[]) |
| SETGET | `String setGet(String key, String value, SetParams params)` | Set new value with options and return old value. | 6.2.0 | Yes (byte[], SetParams) |
| GETDEL | `String getDel(String key)` | Get value and delete key. | 7.2.0 | Yes (byte[]) |
| GETEX | `String getEx(String key, GetExParams params)` | Get the value of a key and optionally set its expiration. | 6.2.0 | Yes (byte[]) |
| APPEND | `long append(String key, String value)` | Append a value to the end of the string. | 2.0.0 | Yes (byte[]) |
| STRLEN | `long strlen(String key)` | Get the length of the string value. | 1.0.0 | Yes (byte[]) |
| INCR | `long incr(String key)` | Increment the integer value of key by 1. | 1.0.0 | Yes (byte[]) |
| INCRBY | `long incrBy(String key, long increment)` | Increment the integer value of key by amount. | 1.0.0 | Yes (byte[]) |
| INCRBYFLOAT | `double incrByFloat(String key, double increment)` | Increment the float value of key by amount. | 1.0.0 | Yes (byte[]) |
| DECR | `long decr(String key)` | Decrement the integer value of key by 1. | 1.0.0 | Yes (byte[]) |
| DECRBY | `long decrBy(String key, long decrement)` | Decrement the integer value of key by amount. | 1.0.0 | Yes (byte[]) |
| UNLINK | `long unlink(String... keys)` | Asynchronously delete one or more keys. | 4.0.0 | Yes (byte[][]) |
| EXISTS | `long exists(String... keys)` | Check if one or more keys exist. | 1.0.0 | Yes (byte[]) / Yes (byte[][]) |
| keyExists | `boolean keyExists(String key)` | Check if a key exists (alias). | N/A | No |
| TYPE | `String type(String key)` | Get the type of a key. | 1.0.0 | Yes (byte[]) |
| EXPIRE | `long expire(String key, long seconds)` | Set expiration time in seconds. | 1.0.0 | Yes (byte[]) |
| EXPIRE | `long expire(String key, long seconds, ExpiryOption expiryOption)` | Set expiration time in seconds with condition. | 7.0.0 | Yes (byte[]) |
| EXPIREAT | `long expireAt(String key, long unixTime)` | Set the expiration time of a key as a Unix timestamp (seconds). | 1.0.0 | Yes (byte[]) |
| EXPIREAT | `long expireAt(String key, long unixTime, ExpiryOption expiryOption)` | Set expiration time at a specific timestamp with condition. | 7.0.0 | Yes (byte[]) |
| PEXPIRE | `long pexpire(String key, long milliseconds)` | Set expiration time in milliseconds. | 1.0.0 | Yes (byte[]) |
| PEXPIRE | `long pexpire(String key, long milliseconds, ExpiryOption expiryOption)` | Set expiration time in milliseconds with condition. | 7.0.0 | Yes (byte[]) |
| EXPIRETIME | `long expireTime(String key)` | Get the expiration timestamp of a key in seconds. | 2.0.0 | Yes (byte[]) |
| PEXPIRETIME | `long pexpireTime(String key)` | Get the expiration timestamp of a key in milliseconds. | 1.0.0 | Yes (byte[]) |
| TTL | `long ttl(String key)` | Get the time to live of a key in seconds. | 1.0.0 | Yes (byte[]) |
| PTTL | `long pttl(String key)` | Get the time to live of a key in milliseconds. | 1.0.0 | Yes (byte[]) |
| PERSIST | `long persist(String key)` | Remove the expiration from a key. | 1.0.0 | Yes (byte[]) |
| SORT | `List<String> sort(String key)` | Sort the elements in a list, set, or sorted set. | 1.0.0 | No |
| SORT | `List<String> sort(String key, String... sortingParameters)` | Sort the elements with options. | 1.0.0 | No |
| DUMP | `byte[] dump(String key)` | Serialize a key's value. | 2.0.0 | Yes (byte[]) |
| RESTORE | `String restore(String key, long ttl, byte[] serializedValue)` | Deserialize a value and store it at a key. | 2.0.0 | No |
| MIGRATE | `String migrate(String host, int port, String key, int destinationDb, int timeout)` | Move a key to another Valkey instance. | 3.0.2 | No |
| MOVE | `long move(String key, int dbIndex)` | Move a key to another database. | 1.0.0 | Yes (byte[]) |
| SCAN | `ScanResult<String> scan(String cursor, ScanParams params)` | Iterate over keys with scan parameters. | 2.8.0 | No |
| SCAN | `ScanResult<byte[]> scan(byte[] cursor, ScanParams params)` | Iterate over keys with scan parameters (binary). | 2.8.0 | Yes |
| SCAN | `ScanResult<byte[]> scan(byte[] cursor)` | Iterate over keys. | 2.8.0 | Yes |
| SCAN | `ScanResult<String> scan(String cursor)` | Iterate over keys. | 2.8.0 | No |
| SCAN | `ScanResult<String> scan(String cursor, ScanParams params, String type)` | Iterate over keys with scan parameters and type filter. | 2.8.0 | No |
| SCAN | `ScanResult<byte[]> scan(byte[] cursor, ScanParams params, byte[] type)` | Iterate over keys with scan parameters and type filter (binary). | 2.8.0 | Yes |
| TOUCH | `long touch(String... keys)` | Update the last access time of keys. | 3.2.0 | Yes (byte[][]) |
| COPY | `boolean copy(String srcKey, String dstKey, boolean replace)` | Copy a key to another key. | 6.2.0 | Yes (byte[]) |
| COPY | `boolean copy(String srcKey, String dstKey, int db, boolean replace)` | Copy a key to another key in a different database. | 6.2.0 | Yes (byte[]) |
| SETBIT | `boolean setbit(String key, long offset, boolean value)` | Sets or clears the bit at offset. | 2.2.0 | Yes (byte[]) |
| GETBIT | `boolean getbit(String key, long offset)` | Returns the bit value at offset. | 2.2.0 | Yes (byte[]) |
| BITCOUNT | `long bitcount(String key)` | Count the number of set bits in a string. | 2.6.0 | Yes (byte[]) |
| BITCOUNT | `long bitcount(String key, long start, long end)` | Count set bits in a string within a byte range. | 2.6.0 | Yes (byte[]) |
| BITCOUNT | `long bitcount(String key, long start, long end, BitCountOption option)` | Count set bits with option. | 2.6.0 | Yes (byte[]) |
| BITPOS | `long bitpos(String key, boolean value)` | Return the position of the first bit set to 1 or 0. | 2.2.0 | Yes (byte[]) |
| BITPOS | `long bitpos(String key, boolean value, BitPosParams params)` | Return the position with parameters. | 2.2.0 | Yes (byte[]) |
| BITOP | `long bitop(BitOP op, String destKey, String... srcKeys)` | Perform bitwise operations between strings. | 2.2.0 | Yes (byte[]) |
| BITFIELD | `List<Long> bitfield(String key, String... arguments)` | Perform multiple bitfield operations. | 3.2.0 | Yes (byte[]) |
| BITFIELD_RO | `List<Long> bitfieldReadonly(String key, String... arguments)` | Perform read-only bitfield operations. | 3.2.0 | Yes (byte[]) |
| PFADD | `long pfadd(String key, String... elements)` | Adds elements to the HyperLogLog. | 2.8.9 | Yes (byte[]) |
| PFCOUNT | `long pfcount(String key)` | Estimates the cardinality of a HyperLogLog. | 2.8.9 | Yes (byte[]) |
| PFCOUNT | `long pfcount(String... keys)` | Estimates the cardinality of multiple HyperLogLogs. | 2.8.9 | Yes (byte[][]) |
| PFMERGE | `String pfmerge(String destKey, String... sourceKeys)` | Merges multiple HyperLogLog values. | 2.8.9 | Yes (byte[]) |
| SENDCOMMAND | `Object sendCommand(ProtocolCommand cmd, byte[]... args)` | Sends a Valkey command with byte array arguments. | N/A | Yes |
| SENDCOMMAND | `Object sendCommand(ProtocolCommand cmd, String... args)` | Sends a Valkey command with string arguments. | N/A | No |
| SENDCOMMAND | `Object sendCommand(ProtocolCommand cmd)` | Sends a Valkey command without arguments. | N/A | No |
| HSET | `long hset(String key, String field, String value)` | Sets the specified field in the hash to value. | 1.0.0 | Yes (byte[]) |
| HSET | `long hset(String key, Map<String, String> hash)` | Sets the specified fields to their respective values in the hash. | 1.0.0 | Yes (Map<byte[], byte[]>) |
| HGET | `String hget(String key, String field)` | Returns the value associated with field in the hash. | 1.0.0 | Yes (byte[]) |
| HMSET | `String hmset(String key, Map<String, String> hash)` | Sets the specified fields to their respective values in the hash. (Deprecated, use HSET with map) | 1.0.0 | Yes (Map<byte[], byte[]>) |
| HMGET | `List<String> hmget(String key, String... fields)` | Returns the values associated with the specified fields in the hash. | 1.0.0 | Yes (byte[][]) |
| HGETALL | `Map<String, String> hgetAll(String key)` | Returns all fields and values of the hash. | 1.0.0 | Yes (byte[]) |
| HDEL | `long hdel(String key, String... fields)` | Removes the specified fields from the hash. | 1.0.0 | Yes (byte[][]) |
| HEXISTS | `boolean hexists(String key, String field)` | Returns if field is an existing field in the hash. | 1.0.0 | Yes (byte[]) |
| HLEN | `long hlen(String key)` | Returns the number of fields contained in the hash. | 2.0.0 | Yes (byte[]) |
| HKEYS | `Set<String> hkeys(String key)` | Returns all field names in the hash. | 2.0.0 | Yes (byte[]) |
| HVALS | `List<String> hvals(String key)` | Returns all values in the hash. | 2.0.0 | Yes (byte[]) |
| HINCRBY | `long hincrBy(String key, String field, long value)` | Increments the number stored at field in the hash. | 1.0.0 | Yes (byte[]) |
| HINCRBYFLOAT | `double hincrByFloat(String key, String field, double value)` | Increments the score of a field in a hash by the specified increment. | 2.6.0 | Yes (byte[]) |
| HSETNX | `long hsetnx(String key, String field, String value)` | Sets field in hash to value, only if field does not yet exist. | 1.0.0 | Yes (byte[]) |
| Hstrlen | `long hstrlen(String key, String field)` | Returns the string length of the value associated with field. | 3.2.0 | Yes (byte[]) |
| HRANDFIELD | `String hrandfield(String key)` | Returns a random field from the hash. | 6.2.0 | Yes (byte[]) |
| HRANDFIELD | `List<String> hrandfield(String key, long count)` | Returns an array of random fields from the hash. | 6.2.0 | Yes (byte[]) |
| HRANDFIELD.VALUES | `List<Map.Entry<String, String>> hrandfieldWithValues(String key, long count)` | Returns an array of random field-value pairs from the hash. | 6.2.0 | Yes (Map.Entry<byte[], byte[]>) |
| HSETEX | `long hsetex(String key, HSetExParams params, String field, String value)` | Sets field in hash to value with expiration and existence conditions. | 7.9.0+ | No |
| HSETEX | `long hsetex(String key, HSetExParams params, Map<String, String> hash)` | Sets fields to values in hash with expiration and existence conditions. | 7.9.0+ | No |
| HGETEX | `List<String> hgetex(String key, HGetExParams params, String... fields)` | Retrieves values associated with fields and optionally sets their expiration. | 7.9.0+ | No |
| HGETDEL | `List<String> hgetdel(String key, String... fields)` | Retrieves values and then deletes fields from hash. | 7.9.0+ | No |
| HSCAN | `ScanResult<Map.Entry<String, String>> hscan(String key, String cursor)` | Iterates fields of Hash types and their associated values. | 2.8.0 | Yes (Map.Entry<byte[], byte[]>) |
| HSCAN | `ScanResult<Map.Entry<String, String>> hscan(String key, String cursor, ScanParams params)` | Iterates fields with scan options. | 2.8.0 | Yes (Map.Entry<byte[], byte[]>) |
| HSCAN.NOVALUES | `ScanResult<String> hscanNoValues(String key, String cursor)` | Iterates fields of Hash types without their values. | 5.0.0 | Yes (byte[]) |
| HSCAN.NOVALUES | `ScanResult<String> hscanNoValues(String key, String cursor, ScanParams params)` | Iterates fields without values with scan options. | 5.0.0 | Yes (byte[]) |
| HEXPIRE | `List<Long> hexpire(String key, long seconds, String... fields)` | Sets expiry for hash field using relative time (seconds). | 7.4.0+ | No |
| HEXPIRE | `List<Long> hexpire(String key, long seconds, ExpiryOption condition, String... fields)` | Sets expiry with condition. | 7.4.0+ | No |
| HPEXPIRE | `List<Long> hpexpire(String key, long milliseconds, String... fields)` | Sets expiry using relative time (milliseconds). | 7.4.0+ | No |
| HPEXPIRE | `List<Long> hpexpire(String key, long milliseconds, ExpiryOption condition, String... fields)` | Sets expiry with condition. | 7.4.0+ | No |
| HEXPIREAT | `List<Long> hexpireAt(String key, long unixTimeSeconds, String... fields)` | Sets expiry using absolute Unix timestamp (seconds). | 7.4.0+ | No |
| HEXPIREAT | `List<Long> hexpireAt(String key, long unixTimeSeconds, ExpiryOption condition, String... fields)` | Sets expiry with condition. | 7.4.0+ | No |
| HPEXPIREAT | `List<Long> hpexpireAt(String key, long unixTimeMillis, String... fields)` | Sets expiry using absolute Unix timestamp (milliseconds). | 7.4.0+ | No |
| HPEXPIREAT | `List<Long> hpexpireAt(String key, long unixTimeMillis, ExpiryOption condition, String... fields)` | Sets expiry with condition. | 7.4.0+ | No |
| HEXPIRETIME | `List<Long> hexpireTime(String key, String... fields)` | Gets expiration time in seconds. | 7.4.0+ | No |
| HPEXPIRETIME | `List<Long> hpexpireTime(String key, String... fields)` | Gets expiration time in milliseconds. | 7.4.0+ | No |
| HTTL | `List<Long> httl(String key, String... fields)` | Gets TTL in seconds. | 7.4.0+ | No |
| HPTTL | `List<Long> hpttl(String key, String... fields)` | Gets TTL in milliseconds. | 7.4.0+ | No |
| HPERSIST | `List<Long> hpersist(String key, String... fields)` | Removes expiration time. | 7.4.0+ | No |
| HSETEX | `long hsetex(byte[] key, HSetExParams params, byte[] field, byte[] value)` | Sets field in hash to value with expiration and existence conditions (binary). | 7.9.0+ | Yes |
| HSETEX | `long hsetex(byte[] key, HSetExParams params, Map<byte[], byte[]> hash)` | Sets fields to values in hash with expiration and existence conditions (binary). | 7.9.0+ | Yes |
| HGETEX | `List<byte[]> hgetex(byte[] key, HGetExParams params, byte[]... fields)` | Retrieves values and optionally sets their expiration (binary). | 7.9.0+ | Yes |
| HGETDEL | `List<byte[]> hgetdel(byte[] key, byte[]... fields)` | Retrieves values and then deletes fields (binary). | 7.9.0+ | Yes |
| HEXPIRE | `List<Long> hexpire(byte[] key, long seconds, byte[]... fields)` | Sets expiry using relative time (seconds) (binary). | 7.4.0+ | Yes |
| HEXPIRE | `List<Long> hexpire(byte[] key, long seconds, ExpiryOption condition, byte[]... fields)` | Sets expiry with condition (binary). | 7.4.0+ | Yes |
| HPEXPIRE | `List<Long> hpexpire(byte[] key, long milliseconds, byte[]... fields)` | Sets expiry using relative time (milliseconds) (binary). | 7.4.0+ | Yes |
| HPEXPIRE | `List<Long> hpexpire(byte[] key, long milliseconds, ExpiryOption condition, byte[]... fields)` | Sets expiry with condition (binary). | 7.4.0+ | Yes |
| HEXPIREAT | `List<Long> hexpireAt(byte[] key, long unixTimeSeconds, byte[]... fields)` | Sets expiry using absolute Unix timestamp (seconds) (binary). | 7.4.0+ | Yes |
| HEXPIREAT | `List<Long> hexpireAt(byte[] key, long unixTimeSeconds, ExpiryOption condition, byte[]... fields)` | Sets expiry with condition (binary). | 7.4.0+ | Yes |
| HPEXPIREAT | `List<Long> hpexpireAt(byte[] key, long unixTimeMillis, byte[]... fields)` | Sets expiry using absolute Unix timestamp (milliseconds) (binary). | 7.4.0+ | Yes |
| HPEXPIREAT | `List<Long> hpexpireAt(byte[] key, long unixTimeMillis, ExpiryOption condition, byte[]... fields)` | Sets expiry with condition (binary). | 7.4.0+ | Yes |
| HEXPIRETIME | `List<Long> hexpireTime(byte[] key, byte[]... fields)` | Gets expiration time in seconds (binary). | 7.4.0+ | Yes |
| HPEXPIRETIME | `List<Long> hpexpireTime(byte[] key, byte[]... fields)` | Gets expiration time in milliseconds (binary). | 7.4.0+ | Yes |
| HTTL | `List<Long> httl(byte[] key, byte[]... fields)` | Gets TTL in seconds (binary). | 7.4.0+ | Yes |
| HPTTL | `List<Long> hpttl(byte[] key, byte[]... fields)` | Gets TTL in milliseconds (binary). | 7.4.0+ | Yes |
| HPERSIST | `List<Long> hpersist(byte[] key, byte[]... fields)` | Removes expiration time (binary). | 7.4.0+ | Yes |
| ZADD | `long zadd(byte[] key, double score, byte[] member)` | Adds member to sorted set, or updates score (binary). | 1.2.0 | Yes |
| ZADD | `long zadd(byte[] key, Map<byte[], Double> scoreMembers)` | Adds multiple members to sorted set (binary). | 1.2.0 | Yes |
| ZADD | `long zadd(byte[] key, double score, byte[] member, ZAddParams params)` | Adds member with options (binary). | 3.0.2 | Yes |
| ZADD | `long zadd(byte[] key, Map<byte[], Double> scoreMembers, ZAddParams params)` | Adds multiple members with options (binary). | 3.0.2 | Yes |
| ZINCRBY | `double zaddIncr(byte[] key, double increment, byte[] member)` | Increments score of member in sorted set (binary). | 1.2.0 | Yes |
| ZINCRBY | `Double zincrby(byte[] key, double increment, byte[] member, ZIncrByParams params)` | Increments score with options (binary). | 3.0.2 | Yes |
| ZREM | `long zrem(byte[] key, byte[]... members)` | Removes members from sorted set (binary). | 1.2.0 | Yes |
| ZCARD | `long zcard(byte[] key)` | Gets the number of members in a sorted set (binary). | 1.2.0 | Yes |
| ZMPOPMIN | `KeyValue<byte[], List<Tuple>> zmpop(SortedSetOption option, byte[]... keys)` | Pops minimum score members from first non-empty sorted set (binary). | 7.0.0 | Yes |
| ZMPOPMIN | `KeyValue<byte[], List<Tuple>> zmpop(SortedSetOption option, int count, byte[]... keys)` | Pops `count` minimum score members (binary). | 7.0.0 | Yes |
| BZPOPMIN | `KeyValue<byte[], Tuple> bzpopmin(double timeout, byte[]... keys)` | Blocking pop minimum score member (binary). | 5.0.0 | Yes |
| ZMPOPMAX | `KeyValue<byte[], List<Tuple>> zmpop(SortedSetOption option, int count, byte[]... keys)` | Pops maximum score members (binary). | 7.0.0 | Yes |
| BZPOPMAX | `KeyValue<byte[], Tuple> bzpopmax(double timeout, byte[]... keys)` | Blocking pop maximum score member (binary). | 5.0.0 | Yes |
| ZSCORE | `Double zscore(byte[] key, byte[] member)` | Gets the score of a member in a sorted set (binary). | 1.2.0 | Yes |
| ZMSCORE | `List<Double> zmscore(byte[] key, byte[]... members)` | Gets scores of multiple members (binary). | 6.2.0 | Yes |
| ZRANGE | `List<byte[]> zrange(byte[] key, long start, long stop)` | Gets elements by rank (binary). | 1.2.0 | Yes |
| ZRANGE | `List<byte[]> zrange(byte[] key, ZRangeParams zRangeParams)` | Gets elements with advanced range query options (binary). | N/A | Yes |
| ZREVRANGE | `List<byte[]> zrevrange(byte[] key, long start, long stop)` | Gets elements by rank in reverse order (binary). | 1.2.0 | Yes |
| ZRANK | `Long zrank(byte[] key, byte[] member)` | Gets rank of a member (low to high) (binary). | 2.0.0 | Yes |
| ZREVRANK | `Long zrevrank(byte[] key, byte[] member)` | Gets rank of a member (high to low) (binary). | 2.0.0 | Yes |
| ZCOUNT | `long zcount(byte[] key, double min, double max)` | Gets count of members within score range (binary). | 2.0.0 | Yes |
| ZCOUNT | `long zcount(byte[] key, byte[] min, byte[] max)` | Gets count with byte array score bounds (binary). | 2.0.0 | Yes |
| ZINCRBY | `double zincrby(byte[] key, double increment, byte[] member)` | Increments score of member (binary). | 1.2.0 | Yes |
| ZINCRBY | `Double zincrby(byte[] key, double increment, byte[] member, ZIncrByParams params)` | Increments score with options (binary). | 3.0.2 | Yes |
| ZPOPMIN | `Tuple zpopmin(byte[] key)` | Removes and returns member with lowest score (binary). | 5.0.0 | Yes |
| ZPOPMIN | `List<Tuple> zpopmin(byte[] key, int count)` | Removes and returns `count` lowest score members (binary). | 5.0.0 | Yes |
| ZPOPMAX | `Tuple zpopmax(byte[] key)` | Removes and returns member with highest score (binary). | 5.0.0 | Yes |
| ZPOPMAX | `List<Tuple> zpopmax(byte[] key, int count)` | Removes and returns `count` highest score members (binary). | 5.0.0 | Yes |
| ZUNIONSTORE | `long zunionstore(byte[] dstkey, byte[]... sets)` | Computes union of sorted sets and stores result (binary). | 2.0.0 | Yes |
| ZUNIONSTORE | `long zunionstore(byte[] dstkey, ZParams params, byte[]... sets)` | Computes union with weights/aggregation (binary). | 2.0.0 | Yes |
| ZINTERSTORE | `long zinterstore(byte[] dstkey, byte[]... sets)` | Computes intersection of sorted sets and stores result (binary). | 2.0.0 | Yes |
| ZINTERSTORE | `long zinterstore(byte[] dstkey, ZParams params, byte[]... sets)` | Computes intersection with weights/aggregation (binary). | 2.0.0 | Yes |
| ZREMRANGEBYRANK | `long zremrangebyrank(byte[] key, long start, long stop)` | Removes members by rank range (binary). | 2.0.0 | Yes |
| ZREMRANGEBYSCORE | `long zremrangebyscore(byte[] key, double min, double max)` | Removes members by score range (binary). | 1.2.0 | Yes |
| ZREMRANGEBYSCORE | `long zremrangebyscore(byte[] key, byte[] min, byte[] max)` | Removes members by score range with byte array bounds (binary). | 1.2.0 | Yes |
| ZSCAN | `ScanResult<Tuple> zscan(byte[] key, byte[] cursor)` | Iterates members and scores (binary). | 2.8.0 | Yes |
| ZSCAN | `ScanResult<Tuple> zscan(byte[] key, byte[] cursor, ScanParams params)` | Iterates members with scan options (binary). | 2.8.0 | Yes |
| ZRANGESTORE | `long zrangestore(byte[] destination, byte[] source, long start, long stop)` | Stores range from source sorted set to destination (binary). | 6.2.0 | Yes |
| ZRANGESTORE | `long zrangestore(byte[] dest, byte[] src, ZRangeParams zRangeParams)` | Stores range with advanced options (binary). | 6.2.0 | Yes |
| ZRANKWITHSCORE | `KeyValue<Long, Double> zrankWithScore(byte[] key, byte[] member)` | Gets rank and score (low to high) (binary). | 7.2.0 | Yes |
| ZREVRANKWITHSCORE | `KeyValue<Long, Double> zrevrankWithScore(byte[] key, byte[] member)` | Gets rank and score (high to low) (binary). | 7.2.0 | Yes |
| ZLEXCOUNT | `long zlexcount(byte[] key, byte[] min, byte[] max)` | Counts members within lexicographical range (binary). | 2.8.9 | Yes |
| SADD | `long sadd(byte[] key, byte[]... members)` | Adds members to set (binary). | 1.0.0 | Yes |
| SREM | `long srem(byte[] key, byte[]... members)` | Removes members from set (binary). | 1.0.0 | Yes |
| SCARD | `long scard(byte[] key)` | Gets the number of elements in set (binary). | 1.0.0 | Yes |
| SISMEMBER | `boolean sismember(byte[] key, byte[] member)` | Checks if element is a member of set (binary). | 1.0.0 | Yes |
| SMISMEMBER | `List<Boolean> smismember(byte[] key, byte[]... members)` | Checks if multiple elements are members of set (binary). | 6.2.0 | Yes |
| SMEMBERS | `Set<byte[]> smembers(byte[] key)` | Gets all members of set (binary). | 1.0.0 | Yes |
| SRANDMEMBER | `byte[] srandmember(byte[] key)` | Returns a random element from set (binary). | 1.0.0 | Yes |
| SRANDMEMBER | `List<byte[]> srandmember(byte[] key, int count)` | Returns multiple random elements from set (binary). | 1.0.0 | Yes |
| SPOP | `byte[] spop(byte[] key)` | Removes and returns a random element from set (binary). | 1.0.0 | Yes |
| SPOP | `List<byte[]> spop(byte[] key, int count)` | Removes and returns multiple random elements from set (binary). | 5.0.0 | Yes |
| SMOVE | `boolean smove(byte[] srckey, byte[] dstkey, byte[] member)` | Moves member from source set to destination set (binary). | 1.0.0 | Yes |
| SUNION | `Set<byte[]> sunion(byte[]... keys)` | Returns elements in the union of sets (binary). | 1.0.0 | Yes |
| SINTER | `Set<byte[]> sinter(byte[]... keys)` | Returns elements in the intersection of sets (binary). | 1.0.0 | Yes |
| SDIFF | `Set<byte[]> sdiff(byte[]... keys)` | Returns elements in the difference of sets (binary). | 1.0.0 | Yes |
| SUNIONSTORE | `long sunionstore(byte[] dstkey, byte[]... keys)` | Computes union of sets and stores result (binary). | 1.0.0 | Yes |
| SINTERSTORE | `long sinterstore(byte[] dstkey, byte[]... keys)` | Computes intersection of sets and stores result (binary). | 1.0.0 | Yes |
| SDIFFSTORE | `long sdiffstore(byte[] dstkey, byte[]... keys)` | Computes difference of sets and stores result (binary). | 1.0.0 | Yes |
| XADD | `String xadd(byte[] key, Map<byte[], byte[]> body)` | Adds entry to stream (binary). | 5.0.0 | Yes |
| XADD | `String xadd(byte[] key, Map<byte[], byte[]> body, XAddParams params)` | Adds entry to stream with options (binary). | 5.0.0 | Yes |
| XRANGE | `List<StreamEntry> xrange(byte[] key, StreamEntryID start, StreamEntryID end)` | Reads entries from stream by range (binary). | 5.0.0 | Yes |
| XRANGE | `List<StreamEntry> xrange(byte[] key, StreamEntryID start, StreamEntryID end, int count)` | Reads entries with count (binary). | 5.0.0 | Yes |
| XREVRANGE | `List<StreamEntry> xrevrange(byte[] key, StreamEntryID start, StreamEntryID end)` | Reads entries in reverse range (binary). | 5.0.0 | Yes |
| XREVRANGE | `List<StreamEntry> xrevrange(byte[] key, StreamEntryID start, StreamEntryID end, int count)` | Reads entries in reverse range with count (binary). | 5.0.0 | Yes |
| XREAD | `Map<byte[], List<StreamEntry>> xread(int count, long block, Map<byte[], StreamEntryID> streams)` | Reads from multiple streams, blocking (binary). | 5.0.0 | Yes |
| XREADGROUP | `Map<byte[], List<StreamEntry>> xreadgroup(String groupname, String consumername, Map<byte[], StreamEntryID> streams, int count, long block)` | Reads from multiple streams using consumer group, blocking (binary). | 5.0.0 | Yes |
| XREADGROUP | `Map<byte[], List<StreamEntry>> xreadgroup(String groupname, String consumername, Map<byte[], StreamEntryID> streams, XReadGroupParams params)` | Reads from multiple streams using consumer group with options (binary). | 5.0.0 | Yes |
| XGROUP CREATE | `String xgroupCreate(byte[] key, byte[] groupname, StreamEntryID id, boolean mkstream)` | Creates a consumer group (binary). | 5.0.0 | No |
| XGROUP DESTROY | `long xgroupDestroy(byte[] key, byte[] groupname)` | Destroys a consumer group (binary). | 5.0.0 | No |
| XGROUP SETID | `String xgroupSetid(byte[] key, byte[] groupname, StreamEntryID id)` | Sets the last delivered ID for a consumer group (binary). | 5.0.0 | No |
| XPENDING | `PendingSummary xpendingSummary(byte[] key, byte[] groupname)` | Gets pending message summary (binary). | 5.0.0 | No |
| XPENDING | `List<PendingMessage> xpending(byte[] key, byte[] groupname, Range from, Range to, int count)` | Gets details of pending messages (binary). | 5.0.0 | No |
| XCLAIM | `List<StreamEntry> xclaim(byte[] key, byte[] groupname, byte[] consumername, long minIdleTime, StreamEntryID[] ids)` | Claims messages (binary). | 5.0.0 | No |
| XCLAIM | `List<StreamEntry> xclaim(byte[] key, byte[] groupname, byte[] consumername, long minIdleTime, long minIdleTimeMillis, StreamEntryID[] ids)` | Claims messages with millisecond idle time (binary). | 5.0.0 | No |
| XCLAIM | `List<StreamEntry> xclaim(byte[] key, byte[] groupname, byte[] consumername, long minIdleTime, StreamEntryID[] ids, XClaimParams params)` | Claims messages with options (binary). | 5.0.0 | No |
| XAUTOCLAIM | `AutoClaimResult autoClaim(byte[] key, byte[] groupname, byte[] minId, long minIdleTime, StreamEntryID[] ids)` | Automatically claims messages (binary). | 6.2.0 | No |
| XAUTOCLAIM | `AutoClaimResult autoClaim(byte[] key, byte[] groupname, byte[] minId, long minIdleTime, long minIdleTimeMillis, StreamEntryID[] ids)` | Auto claims with millisecond idle time (binary). | 6.2.0 | No |
| XAUTOCLAIM | `AutoClaimResult autoClaim(byte[] key, byte[] groupname, byte[] minId, long minIdleTime, StreamEntryID[] ids, XAutoClaimParams params)` | Auto claims with options (binary). | 6.2.0 | No |
| XLEN | `long xlen(byte[] key)` | Gets the length of the stream (binary). | 5.0.0 | Yes |
| XDEL | `long xdel(byte[] key, byte[]... ids)` | Deletes entries from stream (binary). | 5.0.0 | Yes |
| XTRIM | `String xtrim(byte[] key, TrimMode mode, long offset)` | Trims stream to specified length (binary). | 5.0.0 | Yes |
| XTRIM | `String xtrim(byte[] key, TrimMode mode, long offset, boolean minid)` | Trims stream with minid option (binary). | 5.0.0 | Yes |
| XTRIM | `String xtrim(byte[] key, TrimMode mode, Range range)` | Trims stream with range (binary). | 5.0.0 | Yes |
| XGROUP CREATE | `String xgroupCreate(String key, String groupname, StreamEntryID id, boolean mkstream)` | Creates a consumer group. | 5.0.0 | No |
| XGROUP DESTROY | `long xgroupDestroy(String key, String groupname)` | Destroys a consumer group. | 5.0.0 | No |
| XGROUP SETID | `String xgroupSetid(String key, String groupname, StreamEntryID id)` | Sets the last delivered ID for a consumer group. | 5.0.0 | No |
| XPENDING | `PendingSummary xpendingSummary(String key, String groupname)` | Gets pending message summary for a consumer group. | 5.0.0 | No |
| XPENDING | `List<PendingMessage> xpending(String key, String groupname, Range from, Range to, int count)` | Gets details of pending messages. | 5.0.0 | No |
| XCLAIM | `List<StreamEntry> xclaim(String key, String groupname, String consumername, long minIdleTime, StreamEntryID[] ids)` | Claims messages belonging to other consumers. | 5.0.0 | No |
| XCLAIM | `List<StreamEntry> xclaim(String key, String groupname, String consumername, long minIdleTime, long minIdleTimeMillis, StreamEntryID[] ids)` | Claims messages with millisecond idle time. | 5.0.0 | No |
| XCLAIM | `List<StreamEntry> xclaim(String key, String groupname, String consumername, long minIdleTime, StreamEntryID[] ids, XClaimParams params)` | Claims messages with options. | 5.0.0 | No |
| XAUTOCLAIM | `AutoClaimResult autoClaim(String key, String groupname, String minId, long minIdleTime, StreamEntryID[] ids)` | Automatically claims messages. | 6.2.0 | No |
| XAUTOCLAIM | `AutoClaimResult autoClaim(String key, String groupname, String minId, long minIdleTime, long minIdleTimeMillis, StreamEntryID[] ids)` | Auto claims with millisecond idle time. | 6.2.0 | No |
| XAUTOCLAIM | `AutoClaimResult autoClaim(String key, String groupname, String minId, long minIdleTime, StreamEntryID[] ids, XAutoClaimParams params)` | Auto claims with options. | 6.2.0 | No |
| JSON.SET | `String jsonSet(String key, String path, String json)` | Sets a JSON value at a path. | 7.2.0+ | No |
| JSON.SET | `String jsonSet(String key, String path, String json, ExpiryOption expiryOption, long expireSeconds)` | Sets JSON value with expiration. | 7.2.0+ | No |
| JSON.GET | `String jsonGet(String key, String... paths)` | Retrieves a JSON value at a path. | 7.2.0+ | No |
| JSON.DEL | `long jsonDel(String key, String... paths)` | Deletes a JSON value at a path. | 7.2.0+ | No |
| JSON.TYPE | `String jsonType(String key, String path)` | Gets the type of a JSON value. | 7.2.0+ | No |
| JSON.STRLEN | `long jsonStrlen(String key, String path)` | Gets the string length of a JSON value. | 7.2.0+ | No |
| JSON.NUMMULTBY | `String jsonMulf(String key, String path, double value)` | Multiplies a JSON number by a value. | 7.2.0+ | No |
| JSON.NUMINCRBY | `String jsonIncr(String key, String path, long value)` | Increments a JSON number by an integer. | 7.2.0+ | No |
| JSON.NUMINCRBY | `String jsonIncr(String key, String path, double value)` | Increments a JSON number by a float. | 7.2.0+ | No |
| JSON.ARRLEN | `long jsonArrLen(String key, String path)` | Gets the length of a JSON array. | 7.2.0+ | No |
| JSON.ARRAPPEND | `long jsonArrAppend(String key, String path, String... values)` | Appends values to a JSON array. | 7.2.0+ | No |
| JSON.ARRINSERT | `long jsonArrInsert(String key, String path, long index, String... values)` | Inserts values into a JSON array at an index. | 7.2.0+ | No |
| JSON.ARRPOP | `String jsonArrPop(String key, String path, long index)` | Removes and returns an element from a JSON array at an index. | 7.2.0+ | No |
| JSON.ARRTRIM | `long jsonArrTrim(String key, String path, long start, long stop)` | Trims a JSON array to a specified range. | 7.2.0+ | No |
| JSON.ARRTRIM | `long jsonArrTrim(String key, String path, long start, long stop, boolean exclusive)` | Trims with exclusive bounds. | 7.2.0+ | No |
| JSON.OBJKEYS | `List<String> jsonObjKeys(String key, String path)` | Gets keys of a JSON object. | 7.2.0+ | No |
| JSON.OBJLEN | `long jsonObjLen(String key, String path)` | Gets the number of keys in a JSON object. | 7.2.0+ | No |
| JSON.DEBUG | `String jsonDebug(String key, String subCommand, String path)` | Debug operations on JSON values. | 7.2.0+ | No |
| JSON.DEBUG | `String jsonDebug(String key, String subCommand)` | Debug operations on root JSON value. | 7.2.0+ | No |
| JSON.SET | `String jsonSet(byte[] key, byte[] path, byte[] json)` | Sets a JSON value at a path (binary). | 7.2.0+ | Yes |
| JSON.SET | `String jsonSet(byte[] key, byte[] path, byte[] json, ExpiryOption expiryOption, long expireSeconds)` | Sets JSON value with expiration (binary). | 7.2.0+ | Yes |
| JSON.GET | `byte[] jsonGet(byte[] key, String... paths)` | Retrieves a JSON value at a path (binary). | 7.2.0+ | Yes |
| JSON.DEL | `long jsonDel(byte[] key, String... paths)` | Deletes a JSON value at a path (binary). | 7.2.0+ | Yes |
| JSON.TYPE | `String jsonType(byte[] key, String path)` | Gets the type of a JSON value (binary). | 7.2.0+ | Yes |
| JSON.STRLEN | `long jsonStrlen(byte[] key, String path)` | Gets the string length of a JSON value (binary). | 7.2.0+ | Yes |
| JSON.NUMMULTBY | `String jsonMulf(byte[] key, byte[] path, double value)` | Multiplies a JSON number by a value (binary). | 7.2.0+ | Yes |
| JSON.NUMINCRBY | `String jsonIncr(byte[] key, byte[] path, long value)` | Increments a JSON number by an integer (binary). | 7.2.0+ | Yes |
| JSON.NUMINCRBY | `String jsonIncr(byte[] key, byte[] path, double value)` | Increments a JSON number by a float (binary). | 7.2.0+ | Yes |
| JSON.ARRLEN | `long jsonArrLen(byte[] key, String path)` | Gets the length of a JSON array (binary). | 7.2.0+ | Yes |
| JSON.ARRAPPEND | `long jsonArrAppend(byte[] key, byte[] path, byte[]... values)` | Appends values to a JSON array (binary). | 7.2.0+ | Yes |
| JSON.ARRINSERT | `long jsonArrInsert(byte[] key, byte[] path, long index, byte[]... values)` | Inserts values into a JSON array at an index (binary). | 7.2.0+ | Yes |
| JSON.ARRPOP | `byte[] jsonArrPop(byte[] key, byte[] path, long index)` | Removes and returns an element from a JSON array at an index (binary). | 7.2.0+ | Yes |
| JSON.ARRTRIM | `long jsonArrTrim(byte[] key, byte[] path, long start, long stop)` | Trims a JSON array to a specified range (binary). | 7.2.0+ | Yes |
| JSON.ARRTRIM | `long jsonArrTrim(byte[] key, byte[] path, long start, long stop, boolean exclusive)` | Trims with exclusive bounds (binary). | 7.2.0+ | Yes |
| JSON.OBJKEYS | `List<byte[]> jsonObjKeys(byte[] key, byte[] path)` | Gets keys of a JSON object (binary). | 7.2.0+ | Yes |
| JSON.OBJLEN | `long jsonObjLen(byte[] key, byte[] path)` | Gets the number of keys in a JSON object (binary). | 7.2.0+ | Yes |
| JSON.DEBUG | `String jsonDebug(byte[] key, String subCommand, byte[] path)` | Debug operations on JSON values (binary). | 7.2.0+ | Yes |
| JSON.DEBUG | `String jsonDebug(byte[] key, String subCommand)` | Debug operations on root JSON value (binary). | 7.2.0+ | Yes |

**Note:** Some commands are stubbed or have limited functionality in this compatibility layer due to differences between Jedis and Valkey GLIDE's internal mechanisms. For optimal performance and access to all features, consider migrating to native Valkey GLIDE APIs.
DN_COMMANDS.md