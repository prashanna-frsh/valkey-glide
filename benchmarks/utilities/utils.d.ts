/**
 * Copyright Valkey GLIDE Project Contributors - SPDX Identifier: Apache-2.0
 */
import commandLineArgs from "command-line-args";
import { RedisClientType, RedisClusterType } from "redis";
export declare const PORT = 6379;
export declare const SIZE_SET_KEYSPACE = 3000000;
export declare const SIZE_GET_KEYSPACE = 3750000;
export declare function getAddress(host: string, tls: boolean, port: number, username?: string | null, password?: string | null): string;
export declare function createRedisClient(host: string, isCluster: boolean, tls: boolean, port: number, password?: string | null, username?: string | null): RedisClusterType | RedisClientType;
export declare const receivedOptions: commandLineArgs.CommandLineOptions;
export declare function generateValue(size: number): string;
export declare function generateKeySet(): string;
export declare function generateKeyGet(): string;
