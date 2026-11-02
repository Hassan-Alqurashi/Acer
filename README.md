# # Acer 
A lightweight, multi-threaded in-memory key-value store built from scratch to explore TCP socket programming, concurrency control, and caching internals.

## Architecture Overview
- **Network Layer:** Custom TCP server handling concurrent client streams on port `6379`.
- **Storage Engine:** Thread-safe in-memory hash table with O(1) average lookup complexity.
- **Expiration Engine:** Lazy + periodic Time-To-Live (TTL) key eviction.

## Development Roadmap
- [x] Core TCP Socket Server & Client Handler
- [x] Basic `SET`, `GET`, and `DEL` command parsing
- [ ] Thread-safe concurrency control & TTL key expiration *(In Progress)*
- [ ] Append-Only File (AOF) disk persistence
- [ ] Benchmarking suite (Throughput & Latency tests)
