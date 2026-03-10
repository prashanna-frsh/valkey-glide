# Valkey Java Client Benchmark Summary Report

Generated from `java/benchmarks/output.json`. Compares **Jedis**, **Jedis-Compat**, **Valkey GLIDE**, and **Lettuce** across data sizes (100 B, 4000 B) and concurrency levels (1, 10, 100, 1000 tasks). Standalone mode, 1 client.

---

## Executive summary

- **Throughput (TPS):** GLIDE leads at high concurrency (100 and 1000 tasks), with ~5–6× higher TPS than sync clients (Jedis, Jedis-Compat) and ~2× Lettuce. At low concurrency (1–10 tasks), results are similar across clients.
- **Latency:** GLIDE and Lettuce (async) keep average and P99 latency low under load; Jedis and Jedis-Compat show much higher P99 at 100+ tasks.
- **Best per scenario:** See table below; GLIDE is best in 6 of 8 scenarios (all high-concurrency cases).

---

## 1. Throughput (TPS) by scenario

| Data size | Concurrent tasks | Jedis | Jedis-Compat | GLIDE | Lettuce | Best |
|-----------|------------------|-------|--------------|-------|---------|------|
| 100 | 1 | 1225 | 1490 | 1232 | 1121 | jedis-compat |
| 100 | 10 | 10576 | 10961 | 12049 | 10541 | glide |
| 100 | 100 | 9559 | 11188 | 54132 | 20801 | glide |
| 100 | 1000 | 9683 | 9770 | 50626 | 28602 | glide |
| 4000 | 1 | 1441 | 1440 | 1259 | 1247 | jedis |
| 4000 | 10 | 10655 | 11138 | 12268 | 11925 | glide |
| 4000 | 100 | 8215 | 10944 | 62556 | 22003 | glide |
| 4000 | 1000 | 10523 | 10607 | 50921 | 25887 | glide |

## 2. Average latency (ms) by operation

| Data size | Tasks | Client | GET (existing) | GET (non-existing) | SET | TPS |
|-----------|-------|--------|----------------|---------------------|-----|-----|
| 100 | 1 | jedis | 0.702 | 0.695 | 1.17 | 1225 |
| 100 | 1 | jedis-compat | 0.666 | 0.660 | 0.686 | 1490 |
| 100 | 1 | glide | 0.826 | 0.769 | 0.784 | 1232 |
| 100 | 1 | lettuce | 0.895 | 0.867 | 0.895 | 1121 |
| 100 | 10 | jedis | 0.927 | 0.923 | 0.930 | 10576 |
| 100 | 10 | jedis-compat | 0.850 | 1.09 | 0.893 | 10961 |
| 100 | 10 | glide | 0.818 | 0.812 | 0.821 | 12049 |
| 100 | 10 | lettuce | 0.936 | 0.964 | 0.926 | 10541 |
| 100 | 100 | jedis | 7.98 | 9.37 | 8.97 | 9559 |
| 100 | 100 | jedis-compat | 7.42 | 9.08 | 8.85 | 11188 |
| 100 | 100 | glide | 1.20 | 1.18 | 1.17 | 54132 |
| 100 | 100 | lettuce | 4.11 | 4.12 | 4.22 | 20801 |
| 100 | 1000 | jedis | 21.0 | 19.4 | 26.4 | 9683 |
| 100 | 1000 | jedis-compat | 23.1 | 22.8 | 23.1 | 9770 |
| 100 | 1000 | glide | 1.23 | 1.23 | 1.21 | 50626 |
| 100 | 1000 | lettuce | 4.56 | 4.46 | 4.37 | 28602 |
| 4000 | 1 | jedis | 0.688 | 0.679 | 0.704 | 1441 |
| 4000 | 1 | jedis-compat | 0.692 | 0.682 | 0.705 | 1440 |
| 4000 | 1 | glide | 0.793 | 0.774 | 0.809 | 1259 |
| 4000 | 1 | lettuce | 0.792 | 0.777 | 0.847 | 1247 |
| 4000 | 10 | jedis | 0.901 | 0.976 | 0.929 | 10655 |
| 4000 | 10 | jedis-compat | 0.823 | 1.03 | 0.987 | 11138 |
| 4000 | 10 | glide | 0.809 | 0.787 | 0.815 | 12268 |
| 4000 | 10 | lettuce | 0.835 | 0.815 | 0.838 | 11925 |
| 4000 | 100 | jedis | 8.87 | 7.09 | 9.27 | 8215 |
| 4000 | 100 | jedis-compat | 8.21 | 8.92 | 9.72 | 10944 |
| 4000 | 100 | glide | 1.49 | 1.49 | 1.48 | 62556 |
| 4000 | 100 | lettuce | 4.33 | 4.44 | 4.55 | 22003 |
| 4000 | 1000 | jedis | 38.0 | 38.6 | 34.8 | 10523 |
| 4000 | 1000 | jedis-compat | 38.1 | 35.9 | 40.2 | 10607 |
| 4000 | 1000 | glide | 2.60 | 2.67 | 2.64 | 50921 |
| 4000 | 1000 | lettuce | 11.8 | 11.7 | 11.4 | 25887 |

## 3. P99 latency (ms) by operation

| Data size | Tasks | Client | GET (existing) | GET (non-existing) | SET |
|-----------|-------|--------|----------------|---------------------|-----|
| 100 | 1 | jedis | 0.900 | 0.846 | 0.934 |
| 100 | 1 | jedis-compat | 0.741 | 0.709 | 0.870 |
| 100 | 1 | glide | 0.988 | 0.966 | 1.18 |
| 100 | 1 | lettuce | 1.47 | 1.39 | 1.43 |
| 100 | 10 | jedis | 3.49 | 2.86 | 3.28 |
| 100 | 10 | jedis-compat | 6.36 | 8.13 | 6.75 |
| 100 | 10 | glide | 1.05 | 1.04 | 1.03 |
| 100 | 10 | lettuce | 1.31 | 3.82 | 3.56 |
| 100 | 100 | jedis | 79.2 | 74.5 | 74.4 |
| 100 | 100 | jedis-compat | 82.4 | 78.8 | 79.3 |
| 100 | 100 | glide | 1.55 | 1.54 | 1.54 |
| 100 | 100 | lettuce | 14.2 | 14.2 | 14.2 |
| 100 | 1000 | jedis | 87.6 | 85.4 | 91.4 |
| 100 | 1000 | jedis-compat | 86.8 | 87.1 | 88.0 |
| 100 | 1000 | glide | 1.80 | 1.78 | 1.77 |
| 100 | 1000 | lettuce | 7.45 | 7.47 | 8.08 |
| 4000 | 1 | jedis | 0.802 | 0.795 | 0.821 |
| 4000 | 1 | jedis-compat | 0.829 | 0.866 | 0.904 |
| 4000 | 1 | glide | 0.962 | 0.825 | 0.881 |
| 4000 | 1 | lettuce | 0.976 | 0.986 | 1.30 |
| 4000 | 10 | jedis | 3.75 | 3.71 | 2.75 |
| 4000 | 10 | jedis-compat | 4.11 | 9.72 | 9.97 |
| 4000 | 10 | glide | 1.03 | 0.933 | 1.01 |
| 4000 | 10 | lettuce | 1.42 | 1.02 | 1.15 |
| 4000 | 100 | jedis | 80.6 | 81.8 | 90.3 |
| 4000 | 100 | jedis-compat | 88.6 | 89.5 | 90.6 |
| 4000 | 100 | glide | 2.33 | 2.34 | 2.33 |
| 4000 | 100 | lettuce | 9.35 | 9.42 | 9.35 |
| 4000 | 1000 | jedis | 91.8 | 91.1 | 93.3 |
| 4000 | 1000 | jedis-compat | 91.5 | 88.8 | 90.1 |
| 4000 | 1000 | glide | 4.19 | 5.26 | 4.13 |
| 4000 | 1000 | lettuce | 18.2 | 18.1 | 18.2 |

## 4. Per-scenario comparison (TPS and latency ms)

### Data size = 100 B, concurrent tasks = 1

| Client | TPS | GET avg | SET avg | GET p99 | SET p99 |
|--------|-----|---------|---------|---------|--------|
| jedis | 1225 | 0.702 | 1.17 | 0.900 | 0.934 |
| jedis-compat | 1490 | 0.666 | 0.686 | 0.741 | 0.870 |
| glide | 1232 | 0.826 | 0.784 | 0.988 | 1.18 |
| lettuce | 1121 | 0.895 | 0.895 | 1.47 | 1.43 |

### Data size = 100 B, concurrent tasks = 10

| Client | TPS | GET avg | SET avg | GET p99 | SET p99 |
|--------|-----|---------|---------|---------|--------|
| jedis | 10576 | 0.927 | 0.930 | 3.49 | 3.28 |
| jedis-compat | 10961 | 0.850 | 0.893 | 6.36 | 6.75 |
| glide | 12049 | 0.818 | 0.821 | 1.05 | 1.03 |
| lettuce | 10541 | 0.936 | 0.926 | 1.31 | 3.56 |

### Data size = 100 B, concurrent tasks = 100

| Client | TPS | GET avg | SET avg | GET p99 | SET p99 |
|--------|-----|---------|---------|---------|--------|
| jedis | 9559 | 7.98 | 8.97 | 79.2 | 74.4 |
| jedis-compat | 11188 | 7.42 | 8.85 | 82.4 | 79.3 |
| glide | 54132 | 1.20 | 1.17 | 1.55 | 1.54 |
| lettuce | 20801 | 4.11 | 4.22 | 14.2 | 14.2 |

### Data size = 100 B, concurrent tasks = 1000

| Client | TPS | GET avg | SET avg | GET p99 | SET p99 |
|--------|-----|---------|---------|---------|--------|
| jedis | 9683 | 21.0 | 26.4 | 87.6 | 91.4 |
| jedis-compat | 9770 | 23.1 | 23.1 | 86.8 | 88.0 |
| glide | 50626 | 1.23 | 1.21 | 1.80 | 1.77 |
| lettuce | 28602 | 4.56 | 4.37 | 7.45 | 8.08 |

### Data size = 4000 B, concurrent tasks = 1

| Client | TPS | GET avg | SET avg | GET p99 | SET p99 |
|--------|-----|---------|---------|---------|--------|
| jedis | 1441 | 0.688 | 0.704 | 0.802 | 0.821 |
| jedis-compat | 1440 | 0.692 | 0.705 | 0.829 | 0.904 |
| glide | 1259 | 0.793 | 0.809 | 0.962 | 0.881 |
| lettuce | 1247 | 0.792 | 0.847 | 0.976 | 1.30 |

### Data size = 4000 B, concurrent tasks = 10

| Client | TPS | GET avg | SET avg | GET p99 | SET p99 |
|--------|-----|---------|---------|---------|--------|
| jedis | 10655 | 0.901 | 0.929 | 3.75 | 2.75 |
| jedis-compat | 11138 | 0.823 | 0.987 | 4.11 | 9.97 |
| glide | 12268 | 0.809 | 0.815 | 1.03 | 1.01 |
| lettuce | 11925 | 0.835 | 0.838 | 1.42 | 1.15 |

### Data size = 4000 B, concurrent tasks = 100

| Client | TPS | GET avg | SET avg | GET p99 | SET p99 |
|--------|-----|---------|---------|---------|--------|
| jedis | 8215 | 8.87 | 9.27 | 80.6 | 90.3 |
| jedis-compat | 10944 | 8.21 | 9.72 | 88.6 | 90.6 |
| glide | 62556 | 1.49 | 1.48 | 2.33 | 2.33 |
| lettuce | 22003 | 4.33 | 4.55 | 9.35 | 9.35 |

### Data size = 4000 B, concurrent tasks = 1000

| Client | TPS | GET avg | SET avg | GET p99 | SET p99 |
|--------|-----|---------|---------|---------|--------|
| jedis | 10523 | 38.0 | 34.8 | 91.8 | 93.3 |
| jedis-compat | 10607 | 38.1 | 40.2 | 91.5 | 90.1 |
| glide | 50921 | 2.60 | 2.64 | 4.19 | 4.13 |
| lettuce | 25887 | 11.8 | 11.4 | 18.2 | 18.2 |

---

*Latency units: milliseconds. TPS = transactions per second. Source: `output.json`.*