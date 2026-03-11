# Jedis vs Glide Jedis Compatibility — Benchmark Report

## Setup

| Component | Version |
|-----------|---------|
| **Jedis** | 7.0.0 |
| **Valkey GLIDE** (glide_jedis_compat backend) | Project build (see `defaultReleaseVersion` in `java/build.gradle`; e.g. `255.255.255` for dev) |
| **glide_jedis_compat** | Valkey GLIDE Java client + `valkey-glide-jedis-compatibility` layer |
| **Server** | Not recorded in result files |
| **Topology** | Standalone (`is_cluster: false`), single client (`client_count: 1`) |

**Data sources:** Averages below are over **3 runs**: `result1.json`, `result2.json`, `result3.json`.

**Scenario matrix:** `data_size` ∈ {100, 4000} bytes × `num_of_tasks` ∈ {1, 10, 100, 1000}.  
Latency and TPS are from GET (existing key), GET (non-existing key), and SET operations.

---

## Summary of Results

- **When Jedis is faster (3-run mean)**
  - **10 tasks:** Jedis has lower average at 4000 B; lower p90 at 100 B; lower p99 at both payload sizes.
  - **100 tasks, 100 B:** Jedis has lower average, p90, and p99.
  - **1000 tasks:** Jedis has lower average at both payload sizes; lower p90 at 100 B; lower p99 at 100 B. At 4000 B, compat has lower p90 and p99.

- **When glide_jedis_compat is faster (3-run mean)**
  - **1 task:** compat wins on average and p90 for both payload sizes; wins on p99 at 100 B (Jedis had a high p99 outlier in one run at 100 B).
  - **1 task, 4000 B:** compat wins on average and p90; Jedis has slightly lower p99.
  - **10 tasks, 100 B:** compat has lower average; Jedis has lower p90 and p99.
  - **10 tasks, 4000 B:** compat has lower p90; Jedis has lower average and p99.
  - **100 tasks, 4000 B:** compat wins on p90 and p99; Jedis has lower average.
  - **1000 tasks, 4000 B:** compat wins on p90 and p99; Jedis has lower average.

- **Takeaways**
  - **Low concurrency (1 task):** glide_jedis_compat wins on average and p90; at 100 B compat also wins p99 (one run had a high Jedis GET-miss tail).
  - **Medium concurrency (10 tasks):** Mixed; Jedis often better on p99 and sometimes average; compat better on p90 at 4000 B.
  - **High concurrency (100–1000 tasks):** At **4000 B**, compat has better p90 and p99 (fewer connections / multiplexing helps tail latency). Jedis often has lower average at high load.

---

## Latency Comparison Tables (3-run average)

Composite latency = mean of GET-existing, GET–non-existing, and SET latencies for that metric (all in ms).  
**% Δ** = `(glide_jedis_compat − jedis) / jedis × 100`. Negative = glide_jedis_compat faster.

### Average latency (ms)

| data_size | num_of_tasks | jedis (avg) | glide_jedis_compat (avg) | % Δ (compat vs jedis) |
|-----------|----------------|-------------|---------------------------|------------------------|
| 100 | 1 | 0.822 | 0.690 | **−16.1%** |
| 4000 | 1 | 0.705 | 0.681 | **−3.4%** |
| 100 | 10 | 0.969 | 0.924 | **−4.6%** |
| 4000 | 10 | 0.935 | 0.984 | +5.3% |
| 100 | 100 | 8.495 | 8.815 | +3.8% |
| 4000 | 100 | 8.783 | 10.795 | +22.9% |
| 100 | 1000 | 19.767 | 24.516 | +24.0% |
| 4000 | 1000 | 37.879 | 39.623 | +4.6% |

### P90 latency (ms) — high percentile proxy (p95 not measured)

| data_size | num_of_tasks | jedis (p90) | glide_jedis_compat (p90) | % Δ (compat vs jedis) |
|-----------|----------------|-------------|---------------------------|------------------------|
| 100 | 1 | 0.743 | 0.699 | **−6.0%** |
| 4000 | 1 | 0.717 | 0.688 | **−4.0%** |
| 100 | 10 | 1.374 | 1.466 | +6.7% |
| 4000 | 10 | 1.417 | 1.322 | **−6.7%** |
| 100 | 100 | 29.109 | 32.989 | +13.3% |
| 4000 | 100 | 32.708 | 27.583 | **−15.7%** |
| 100 | 1000 | 57.252 | 66.947 | +16.9% |
| 4000 | 1000 | 78.118 | 71.379 | **−8.6%** |

### P99 latency (ms)

| data_size | num_of_tasks | jedis (p99) | glide_jedis_compat (p99) | % Δ (compat vs jedis) |
|-----------|----------------|-------------|---------------------------|------------------------|
| 100 | 1 | 6.224 | 1.055 | **−83.1%** |
| 4000 | 1 | 0.835 | 0.846 | +1.3% |
| 100 | 10 | 3.720 | 4.190 | +12.6% |
| 4000 | 10 | 3.229 | 3.667 | +13.6% |
| 100 | 100 | 73.329 | 80.473 | +9.7% |
| 4000 | 100 | 79.135 | 70.664 | **−10.7%** |
| 100 | 1000 | 82.320 | 87.350 | +6.1% |
| 4000 | 1000 | 94.563 | 90.549 | **−4.2%** |

*Note: Jedis p99 at 100 B / 1 task is elevated by a high GET-miss tail in one run (result3); the −83.1% reflects that outlier.*

---

## Summary Table — Who Has Lower Latency per Scenario (3-run mean)

| data_size | num_of_tasks | Lower average | Lower p90 | Lower p99 |
|-----------|----------------|---------------|-----------|-----------|
| 100 | 1 | glide_jedis_compat | glide_jedis_compat | glide_jedis_compat |
| 4000 | 1 | glide_jedis_compat | glide_jedis_compat | jedis |
| 100 | 10 | glide_jedis_compat | jedis | jedis |
| 4000 | 10 | jedis | glide_jedis_compat | jedis |
| 100 | 100 | jedis | jedis | jedis |
| 4000 | 100 | jedis | glide_jedis_compat | glide_jedis_compat |
| 100 | 1000 | jedis | jedis | jedis |
| 4000 | 1000 | jedis | glide_jedis_compat | glide_jedis_compat |

---

*Report generated from `result1.json`, `result2.json`, and `result3.json` (3-run averages). P95 was not collected; p90 is used as a high-percentile proxy.*
