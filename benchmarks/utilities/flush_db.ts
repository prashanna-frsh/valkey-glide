/**
 * Copyright Valkey GLIDE Project Contributors - SPDX Identifier: Apache-2.0
 */

import { RedisClientType, RedisClusterType } from "redis";
import { createRedisClient, receivedOptions } from "./utils";

async function flush_database(
    host: string,
    isCluster: boolean,
    tls: boolean,
    port: number,
    password?: string | null,
    username?: string | null,
) {
    if (isCluster) {
        const client = (await createRedisClient(
            host,
            isCluster,
            tls,
            port,
            password,
            username,
        )) as RedisClusterType;
        await Promise.all(
            client.masters.map((master) => {
                return flush_database(
                    master.host,
                    false,
                    tls,
                    master.port,
                    password,
                    username,
                );
            }),
        );
        await client.quit();
    } else {
        const client = (await createRedisClient(
            host,
            isCluster,
            tls,
            port,
            password,
            username,
        )) as RedisClientType;
        await client.connect();
        await client.flushAll();
        await client.quit();
    }
}

Promise.resolve()
    .then(async () => {
        console.log("Flushing " + receivedOptions.host);
        await flush_database(
            receivedOptions.host,
            receivedOptions.clusterModeEnabled,
            receivedOptions.tls,
            receivedOptions.port,
            receivedOptions.password,
            receivedOptions.username,
        );
    })
    .then(() => {
        process.exit(0);
    });
