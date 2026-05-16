# Assignment 4 – Graph Traversal and Representation System

## A. Project Overview

### What is a Graph?

A **graph** is a data structure used to model relationships between objects. It consists of:

- **Vertices (nodes)** — individual entities (e.g. cities, users, web pages)
- **Edges (connections)** — relationships between pairs of vertices (e.g. roads, friendships, links)

Graphs can be **directed** (edges have a one-way direction) or **undirected** (edges go both ways). This project uses a **directed graph**.

### Breadth-First Search (BFS)

BFS explores a graph **level by level** from a starting vertex. It visits all neighbors of the start node before moving deeper. Think of it as spreading outward like ripples in water.

### Depth-First Search (DFS)

DFS explores a graph **as deep as possible** down one path before backtracking and trying another. Think of it as navigating a maze by always going forward until you hit a dead end, then retracing your steps.

---

## B. Class Descriptions

### `Vertex`

Represents a single node in the graph. Each vertex holds a unique integer `id` that identifies it. Methods include a constructor, a `getId()` getter, and `toString()`.

### `Edge`

Represents a directed connection between two vertices. Stores a `source` (starting vertex) and a `destination` (ending vertex). Includes a constructor, getters for both ends, and `toString()`.

### `Graph`

The core data structure. Implements the graph using an **adjacency list**.

**Adjacency List Representation:**

An adjacency list stores the graph as a `HashMap<Vertex, List<Vertex>>`. Each key is a vertex, and its value is the list of vertices it connects to directly.

```
Vertex 0 → [1, 2]
Vertex 1 → [2, 3]
Vertex 2 → [3, 4]
...
```

**Why adjacency list over adjacency matrix?**
- Space efficient: O(V + E) vs O(V²) for a matrix
- Faster iteration over neighbors: only real edges are stored
- Better for sparse graphs (most real-world graphs)

Key methods:
- `addVertex(Vertex v)` — registers a vertex in the graph
- `addEdge(int from, int to)` — adds a directed edge between two vertices by id
- `printGraph()` — prints the full adjacency list
- `bfs(int startId)` — runs BFS from a given vertex
- `dfs(int startId)` — runs DFS from a given vertex

### `Experiment`

Handles graph construction, traversal timing, and output reporting.

- `buildGraph(int n)` — creates a graph of `n` vertices with sequential, skip, and long-range edges
- `runTraversals(Graph g, String label)` — times BFS and DFS on a graph using `System.nanoTime()`
- `runMultipleTests()` — tests all three graph sizes automatically
- `printResults()` — prints a formatted summary table

---

## C. Algorithm Descriptions

### Breadth-First Search (BFS)

**Step-by-step:**
1. Create a visited set and a queue (FIFO).
2. Add the start vertex to the queue and mark it visited.
3. While the queue is not empty:
   - Dequeue the front vertex and record it.
   - For each unvisited neighbor, mark it visited and enqueue it.
4. The recorded order is the BFS traversal.

**Pseudocode:**
```
BFS(start):
  visited = {}
  queue = [start]
  visited.add(start)
  while queue not empty:
    v = queue.dequeue()
    process(v)
    for each neighbor u of v:
      if u not in visited:
        visited.add(u)
        queue.enqueue(u)
```

**Use cases:**
- Finding the **shortest path** between two nodes (unweighted graphs)
- Web crawlers exploring links level by level
- Social network analysis (finding friends-of-friends)
- GPS navigation (nearest locations first)

**Time complexity:** O(V + E) — every vertex is dequeued once and every edge is checked once.

**Space complexity:** O(V) — the queue and visited set hold at most V vertices.

---

### Depth-First Search (DFS)

**Step-by-step:**
1. Create a visited set and a stack (LIFO).
2. Push the start vertex onto the stack.
3. While the stack is not empty:
   - Pop the top vertex.
   - If not yet visited, mark it visited and record it.
   - Push all unvisited neighbors onto the stack.
4. The recorded order is the DFS traversal.

**Pseudocode:**
```
DFS(start):
  visited = {}
  stack = [start]
  while stack not empty:
    v = stack.pop()
    if v not in visited:
      visited.add(v)
      process(v)
      for each neighbor u of v:
        if u not in visited:
          stack.push(u)
```

**Use cases:**
- Detecting **cycles** in a graph
- Topological sorting (scheduling tasks with dependencies)
- Solving mazes and puzzles
- Finding connected components

**Time complexity:** O(V + E) — every vertex is popped once and every edge is examined once.

**Space complexity:** O(V) — the stack can grow to hold V vertices in the worst case (a linear chain).

---

## D. Experimental Results

### Setup

Graphs were built using a directed structure where each vertex `v` connects to:
- `v + 1` (sequential neighbor)
- `v + 2` (skip neighbor)
- `v + n/4` (long-range neighbor, avoids duplicates)

This creates a connected, varied graph that reveals differences in traversal behavior.

Timing was measured using `System.nanoTime()` immediately before and after each traversal call.

### Execution Time Comparison

| Graph Size        | BFS Time (ns) | DFS Time (ns) |
|-------------------|---------------|---------------|
| Small  (10 nodes) | ~165,964      | ~907,578      |
| Medium (30 nodes) | ~1,011,018    | ~489,855      |
| Large (100 nodes) | ~514,348      | ~533,007      |

> Note: nanosecond timings vary between runs due to JVM warm-up, garbage collection, and CPU scheduling. The values above are representative. Run the program multiple times to observe the natural variance.

### Observations and Patterns

**1. JVM warm-up effect:**
The first traversal in a run is almost always slower than later ones, regardless of algorithm. This is because the JVM compiles bytecode to native machine code lazily (JIT compilation). The small graph timings are most affected by this.

**2. Both algorithms are O(V + E):**
Neither BFS nor DFS consistently outperforms the other across all sizes. Their theoretical complexity is identical, and in practice the difference comes down to data-structure overhead — a queue (BFS) vs a stack (DFS) — and memory access patterns.

**3. Graph structure impacts traversal order significantly:**
- On a linear chain (small graph), both BFS and DFS produce the same order: 0, 1, 2, … 9.
- On the medium and large graphs with long-range edges, BFS visits vertices in "waves" (discovering nearby vertices before far ones), while DFS dives deep immediately.

**4. BFS traversal order (medium graph):**
```
0, 1, 2, 7, 3, 8, 4, 9, 14, 5, 10, 15, ...
```
BFS jumps to vertex 7 early because vertex 0 has a long-range edge to it (0 + 30/4 = 7).

**5. DFS traversal order (medium graph):**
```
0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, ...
```
DFS follows the sequential chain before jumping, producing a more linear order.

---

## E. Screenshots

> **How to generate screenshots:**
> 1. Compile: `javac src/*.java -d out/`
> 2. Run: `java -cp out/ Main`
> 3. Copy and screenshot the terminal output for each section.

**Expected output sections to screenshot:**

**Graph Structure Output** (small graph adjacency list):
```
Graph Adjacency List (10 vertices):
  0 -> 1 2
  1 -> 2 3
  2 -> 3 4
  3 -> 4 5
  4 -> 5 6
  5 -> 6 7
  6 -> 7 8
  7 -> 8 9
  8 -> 9
  9 -> (no edges)
```

**BFS Traversal Output:**
```
BFS from 0: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
```

**DFS Traversal Output:**
```
DFS from 0: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
```

**Performance Results Table:**
```
╔══════════════════════════╦══════════════════╦══════════════════╗
║ Graph Size               ║ BFS Time (ns)    ║ DFS Time (ns)    ║
╠══════════════════════════╬══════════════════╬══════════════════╣
║ Small  (10 vertices)     ║           165964 ║           907578 ║
║ Medium (30 vertices)     ║          1011018 ║           489855 ║
║ Large (100 vertices)     ║           514348 ║           533007 ║
╚══════════════════════════╩══════════════════╩══════════════════╝
```

---

## F. Reflection

Working through this assignment gave me a concrete understanding of how graph traversal algorithms behave in practice versus in theory. On paper, BFS and DFS both run in O(V + E) and seem almost interchangeable. But implementing them reveals real differences: BFS requires a queue and naturally spreads outward level by level, making it ideal when you care about distance or proximity. DFS uses a stack and plunges deep before backtracking, which makes it well-suited for problems like cycle detection or topological ordering where the full depth of a path matters.

The biggest challenge was understanding how the choice of data structure — a `HashMap` for the adjacency list — affects performance and correctness. An adjacency list is efficient for sparse graphs because it only stores edges that actually exist, unlike a matrix that allocates space for every possible pair. I also noticed how much the JVM's JIT compilation distorted early timing measurements, which showed me that microbenchmarking requires care — running multiple trials and discarding the first few is important for reliable results.

---

## Repository Structure

```
assignment3-graphs/
├── src/
│   ├── Vertex.java
│   ├── Edge.java
│   ├── Graph.java
│   ├── Experiment.java
│   └── Main.java
├── docs/
│   └── screenshots/
├── README.md
└── .gitignore
```

## How to Run

```bash
# Compile
javac src/*.java -d out/

# Run
java -cp out/ Main
```

Requires Java 8 or higher.
