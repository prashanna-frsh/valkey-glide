# Benchmarks

[`install_and_test.sh`](./install_and_test.sh) is the benchmark script we use to check performance. Run `install_and_test.sh -h` to get the full list of available flags.

**Node.js:** The `benchmarks/utilities` scripts (fill_db, flush) require **Node.js 14 or newer** because the `redis` npm package uses optional chaining. Use `node -v` to check; upgrade with nvm or your system package manager if needed. If your server uses a non-default port, pass `--port <port>` (e.g. `npm run flush -- --host <host> --no-tls --port 30160`). On managed Redis (e.g. ElastiCache) where FLUSHALL is disabled, run the script with `-no-flush` to skip flush/fill (e.g. `./install_and_test.sh -no-flush -minimal`).

The results of the benchmark runs will be written into .csv files in the `./results` folder.

If while running benchmarks your redis-server is killed every time the program runs the 4000 data-size benchmark, it might be because you don't have enough available storage on your machine.
To solve this issue, you have two options -

1. Allocate more storage to your'e machine. for me the case was allocating from 500 gb to 1000 gb.
2. Go to benchmarks/install_and_test.sh and change the "dataSize="100 4000"" to a data-size that your machine can handle. try for example dataSize="100 1000".
