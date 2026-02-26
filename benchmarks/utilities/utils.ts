/**
 * Copyright Valkey GLIDE Project Contributors - SPDX Identifier: Apache-2.0
 */

import commandLineArgs from "command-line-args";
import {
    RedisClientType,
    RedisClusterType,
    createClient,
    createCluster,
} from "redis";

export const PORT = 6379;

export const SIZE_SET_KEYSPACE = 3000000; // 3 million
export const SIZE_GET_KEYSPACE = 3750000; // 3.75 million

export function getAddress(
    host: string,
    tls: boolean,
    port: number,
    username?: string | null,
    password?: string | null,
): string {
    const protocol = tls ? "rediss" : "redis";
    const auth =
        password != null
            ? `${username ?? "default"}:${encodeURIComponent(password)}@`
            : "";
    return `${protocol}://${auth}${host}:${port}`;
}

export function createRedisClient(
    host: string,
    isCluster: boolean,
    tls: boolean,
    port: number,
    password?: string | null,
    username?: string | null,
): RedisClusterType | RedisClientType {
    const socket = { host, port: port ?? PORT, tls };
    const auth =
        password != null
            ? { password, username: username ?? "default" }
            : {};
    return isCluster
        ? createCluster({
              rootNodes: [{ socket }],
              defaults: {
                  socket: { tls },
                  ...auth,
              },
              useReplicas: true,
          })
        : createClient({
              url: getAddress(host, tls, port, username, password),
              ...auth,
          });
}

const optionDefinitions = [
    {
        name: "resultsFile",
        type: String,
        defaultValue: "../results/node-results.json",
    },
    { name: "dataSize", type: String, defaultValue: "100" },
    {
        name: "concurrentTasks",
        type: String,
        multiple: true,
        defaultValue: ["1", "10", "100", "1000"],
    },
    { name: "clients", type: String, defaultValue: "all" },
    { name: "host", type: String, defaultValue: "localhost" },
    { name: "clientCount", type: String, multiple: true, defaultValue: ["1"] },
    { name: "tls", type: Boolean, defaultValue: false },
    { name: "no-tls", type: Boolean, defaultValue: false },
    { name: "minimal", type: Boolean, defaultValue: false },
    { name: "clusterModeEnabled", type: Boolean, defaultValue: false },
    { name: "port", type: Number, defaultValue: PORT },
    { name: "password", type: String, defaultValue: undefined },
    { name: "username", type: String, defaultValue: undefined },
];

export const receivedOptions = commandLineArgs(optionDefinitions);
if (receivedOptions["no-tls"]) {
    receivedOptions.tls = false;
}
if (
    receivedOptions.password === undefined &&
    typeof process !== "undefined" &&
    process.env.REDIS_PASSWORD
) {
    receivedOptions.password = process.env.REDIS_PASSWORD;
}

export function generateValue(size: number): string {
    return "0".repeat(size);
}

export function generateKeySet(): string {
    return (Math.floor(Math.random() * SIZE_SET_KEYSPACE) + 1).toString();
}

export function generateKeyGet(): string {
    const range = SIZE_GET_KEYSPACE - SIZE_SET_KEYSPACE;
    return Math.floor(Math.random() * range + SIZE_SET_KEYSPACE + 1).toString();
}
