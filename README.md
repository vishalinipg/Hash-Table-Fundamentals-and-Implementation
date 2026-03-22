# Hash Table Fundamentals and Implementation

This repository contains real-world problems designed to understand **Hash Tables from fundamentals to advanced real-time systems**.

Each problem includes:

* Scenario
* Problem Statement
* Concepts Covered
* Use Cases
* Expected Output

---

# Week 1

---

## Problem 1: Social Media Username Availability Checker

### Scenario

You're building a registration system for a social media platform with 10 million users. Users frequently check if usernames are available before registering.

### Problem Statement

Design a system to check username availability in real-time. The system should:

* Check if a username exists in O(1) time
* Handle 1000 concurrent username checks per second
* Suggest similar available usernames if the requested one is taken
* Track popularity of attempted usernames

### Concepts Covered

* Hash table basics (key-value mapping)
* O(1) lookup performance
* Collision handling
* Frequency counting

### Use Cases

* Twitter / Instagram registration
* Gaming platform username selection
* Email address availability checking

### Output

```
checkAvailability("john_doe") → false
checkAvailability("jane_smith") → true
suggestAlternatives("john_doe") → [john_doe1, john_doe2, john.doe]
getMostAttempted() → admin (3 attempts)
```

---

## Problem 2: E-commerce Flash Sale Inventory Manager

### Scenario

During a flash sale, 50,000 customers simultaneously try to purchase limited stock items (only 100 units available). You need to prevent overselling while maintaining high performance.

### Problem Statement

Implement an inventory management system that:

* Tracks product stock levels in real-time
* Processes purchase requests in O(1) time
* Handles concurrent requests safely
* Maintains a waiting list when stock runs out
* Provides instant stock availability checks

### Concepts Covered

* Hash table for instant stock lookup
* Collision resolution (multiple users buying same product)
* Load factor management during high traffic
* Performance benchmarking under load

### Use Cases

* Amazon Prime Day deals
* Concert ticket booking systems
* Limited edition product launches

### Output

```
checkStock("IPHONE15_256GB") → 100 units available
purchaseItem("IPHONE15_256GB", userId=12345) → Success, 99 units remaining
purchaseItem("IPHONE15_256GB", userId=67890) → Success, 98 units remaining
purchaseItem("IPHONE15_256GB", userId=99999) → Added to waiting list, position #1
```

---

## Problem 3: DNS Cache with TTL (Time To Live)

### Scenario

Build a DNS resolver cache that stores domain-to-IP mappings to reduce lookup times from 100ms to <1ms. Cache entries should expire after a specified TTL.

### Problem Statement

Create a DNS caching system that:

* Stores domain name → IP address mappings
* Implements TTL-based expiration (entries expire after X seconds)
* Automatically removes expired entries
* Handles cache misses by querying upstream DNS
* Reports cache hit/miss ratios
* Implements LRU eviction when cache is full

### Concepts Covered

* Hash table implementation with custom Entry class
* Chaining for collision resolution
* Time-based operations
* Performance metrics

### Use Cases

* Browser DNS caching
* CDN edge server DNS resolution
* Corporate network DNS servers

### Output

```
resolve("google.com") → Cache MISS → Query upstream → 172.217.14.209 (TTL: 3s)
resolve("google.com") → Cache HIT → 172.217.14.209 (retrieved in 0.01 ms)
Cache EXPIRED → Query upstream
resolve("google.com") → Cache MISS → Query upstream → 172.217.14.216 (TTL: 3s)
Hit Rate: 33.33%, Avg Lookup Time: 2.50 ms
```

---

## Problem 4: Plagiarism Detection System

### Scenario

A university needs to check student submissions against a database of 100,000 previous essays to detect plagiarism. Simple string matching is too slow.

### Problem Statement

Build a plagiarism detector that:

* Breaks documents into n-grams (sequences of n words)
* Stores n-grams in a hash table with document references
* Finds matching n-grams between documents
* Calculates similarity percentage
* Identifies the most similar documents in O(n) time

### Concepts Covered

* String hashing techniques
* Frequency counting with hash maps
* Good hash function properties
* Performance benchmarking (hash vs linear search)

### Use Cases

* Academic plagiarism detection (Turnitin)
* Code similarity detection (MOSS)
* Document deduplication systems

### Output

```
analyzeDocument("essay_123.txt")
Extracted 10 n-grams
Found 7 matching n-grams with "essay_092.txt"
Similarity: 70.0% (PLAGIARISM DETECTED)
Found 7 matching n-grams with "essay_089.txt"
Similarity: 70.0% (PLAGIARISM DETECTED)
```

---

## Problem 5: Real-Time Analytics Dashboard for Website Traffic

### Scenario

A news website gets 1 million page views per hour. The marketing team needs real-time analytics showing top pages, traffic sources, and user locations.

### Problem Statement

Implement a streaming analytics system that:

* Processes incoming page view events in real-time
* Maintains top 10 most visited pages
* Tracks unique visitors per page
* Counts visits by traffic source (Google, Facebook, Direct, etc.)
* Updates dashboard every 5 seconds with zero lag

### Concepts Covered

* Frequency counting applications
* Multiple hash tables for different dimensions
* Load factor and resizing under high throughput
* Time/space complexity optimization

### Use Cases

* Google Analytics real-time dashboard
* Twitter trending topics
* E-commerce product popularity tracking

### Output

```
DASHBOARD

Top Pages:
1. /article/breaking-news - 3 views (2 unique)
2. /sports/championship - 3 views (3 unique)
3. /tech/ai-update - 1 views (1 unique)

Traffic Sources:
Google: 42.9%
Facebook: 28.6%
Direct: 28.6%
```

---

# Week 2

---

## Problem 6: Distributed Rate Limiter for API Gateway

### Scenario

Your API gateway handles requests from 100,000 clients. Each client is allowed 1000 requests per hour. You need to enforce this limit efficiently.

### Problem Statement

Build a token bucket rate limiter that:

* Tracks request counts per client (by API key or IP)
* Allows burst traffic up to limit
* Resets counters every hour
* Responds within 1ms for rate limit checks
* Handles distributed deployment (multiple servers)
* Provides clear error messages when limit exceeded

### Concepts Covered

* Hash table for client tracking
* Time-based operations
* Collision handling (multiple clients)
* Performance under concurrent access

### Use Cases

* AWS API Gateway rate limiting
* GitHub API rate limits
* Stripe payment API throttling

### Output

```
checkRateLimit(clientId="abc123") → Allowed (999 requests remaining)
checkRateLimit(clientId="abc123") → Allowed (998 requests remaining)
checkRateLimit(clientId="abc123") → Allowed (997 requests remaining)
getRateLimitStatus("abc123") → {used: 3, limit: 1000, reset: 1774169701279}
```

---

## Problem 7: Autocomplete System for Search Engine

### Scenario

Build a Google-like autocomplete that suggests queries as users type, based on 10 million previous search queries and their popularity.

### Problem Statement

Create an autocomplete system that:

* Stores search queries with frequency counts
* Returns top 10 suggestions for any prefix in <50ms
* Updates frequencies based on new searches
* Handles typos and suggests corrections
* Optimizes for memory (10M queries × avg 30 characters)

### Concepts Covered

* Hash table for query frequency storage
* String hashing techniques
* Performance benchmarking (prefix search)
* Space complexity optimization

### Use Cases

* Google search autocomplete
* Amazon product search suggestions
* IDE code completion

### Output

```
search("jav") →
1. "a tutorial" (3 searches)
2. "a script" (1 searches)
3. "a 21 features" (1 searches)
4. "a download" (1 searches)

updateFrequency("21 features") → Frequency: 1 → 2 → 4 (trending)
```

---

## Problem 8: Parking Lot Management with Open Addressing

### Scenario

A smart parking lot with 500 spots needs to track which vehicles are parked where, handle collisions when multiple vehicles arrive simultaneously, and optimize spot allocation.

### Problem Statement

Implement a parking system using open addressing that:

* Assigns parking spots based on license plate hash
* Uses linear probing when preferred spot is occupied
* Tracks entry/exit times for billing
* Finds nearest available spot to entrance
* Generates parking statistics (avg occupancy, peak hours)

### Concepts Covered

* Open addressing (linear/quadratic probing)
* Collision resolution strategies
* Custom hash functions
* Load factor management

### Use Cases

* Airport parking systems
* Mall parking management
* Street parking apps (ParkMobile)

### Output

```
parkVehicle("ABC-1234") → Assigned spot #127 Spot #127 (0 probes)
parkVehicle("ABC-1235") → Assigned spot #127... occupied... Spot #128 (1 probe)
parkVehicle("XYZ-9999") → Assigned spot #127... occupied...... occupied... Spot #129 (2 probes)
exitVehicle("ABC-1234") → Spot #127 freed, Duration: 0h 1m, Fee: $0.10
getStatistics() → Occupancy: 0.4%, Avg Probes: 1.0, Peak Hour: 2-3 PM
```

---

## Problem 9: Two-Sum Problem Variants for Financial Transactions

### Scenario

A payment processing company needs to detect fraudulent transaction pairs that sum to specific amounts (money laundering), find complementary trades, and identify duplicate payments.

### Problem Statement

Given millions of daily transactions, implement:

* Classic Two-Sum: Find pairs that sum to target amount
* Two-Sum with time window: Pairs within 1 hour
* K-Sum: Find K transactions that sum to target
* Duplicate detection: Same amount, same merchant, different accounts
* All under 100ms response time

### Concepts Covered

* Hash table for complement lookup
* O(1) lookup performance
* Multiple hash tables for different checks
* Time complexity analysis

### Use Cases

* Fraud detection systems
* Tax evasion detection
* Cryptocurrency transaction analysis

### Output

```
transactions = [
{id:1, amount:500, merchant:"Store A", time:"10:00"},
{id:2, amount:300, merchant:"Store B", time:"10:15"},
{id:3, amount:200, merchant:"Store C", time:"10:30"}
]

findTwoSum(target=500) → [(id:2, id:3)] // 300 + 200
detectDuplicates() → [{amount:500, merchant:"Store A", accounts:[acc1, acc2]}]
findKSum(k=3, target=1000) → [(id:1, id:2, id:3)] // 500+300+200
```

---

## Problem 10: Multi-Level Cache System with Hash Tables

### Scenario

Design a cache hierarchy for a video streaming service (like Netflix) with L1 (memory), L2 (SSD), and L3 (database) levels. Optimize for 10M concurrent users.

### Problem Statement

Build a multi-level caching system that:

* L1 Cache: 10,000 most popular videos (in-memory HashMap)
* L2 Cache: 100,000 frequently accessed videos (SSD-backed)
* L3: Database (slow, all videos)
* Implements LRU eviction at each level
* Promotes videos between levels based on access patterns
* Tracks cache hit ratios for each level
* Handles cache invalidation when content updates

### Concepts Covered

* Multiple hash tables with different purposes
* Resizing/rehashing strategies
* Performance benchmarking across levels
* Load factor optimization for each tier

### Use Cases

* Netflix video streaming cache
* CDN content delivery
* Database query result caching

### Output

```
getVideo("video_123")
→ L1 Cache MISS (0.5ms)
→ L2 Cache HIT (5ms)
→ Promoted to L1
→ Total: 5.5ms 

getVideo("video_123") [second request]
→ L1 Cache HIT (0.5ms)

getVideo("video_999")
→ L1 Cache MISS (0.5ms)
→ L2 Cache MISS
→ L3 Database HIT (150ms)
→ Added to L2 (access count: 1)

getStatistics() →
L1: Hit Rate 85%, Avg Time: 0.5ms
L2: Hit Rate 12%, Avg Time: 5ms
L3: Hit Rate 3%, Avg Time: 150ms
Overall: Hit Rate 97%, Avg Time: 2.3ms
```
