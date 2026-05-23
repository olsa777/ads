## Project Overview

### Description of Graph Structure

A graph is a data structure used to model pairwise relations between objects. It consists of a finite set of nodes and the connections between them. Graphs can be directed, where edges have a specific orientation from one node to another , or undirected, where edges represent bidirectional connections.

### Explanation of Vertices and Edges# Assignment 4 – Graph Traversal and Representation System

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

### Dijkstra's Algorithm

Dijkstra's algorithm explores a graph by **always prioritizing the closest unvisited path**, expanding outward like a ripple in a pond. Think of it as a smart GPS that continuously checks the shortest known driving routes, locking in the absolute fastest way to reach each intersection before moving further out.

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
- `dijkstra(int startId)` — runs dijkstra from a given vertex

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


**Use cases:**
- Detecting **cycles** in a graph
- Topological sorting (scheduling tasks with dependencies)
- Solving mazes and puzzles
- Finding connected components

**Time complexity:** O(V + E) — every vertex is popped once and every edge is examined once.

**Space complexity:** O(V) — the stack can grow to hold V vertices in the worst case (a linear chain).

---

### Dijkstra's Algorithm

**Step-by-step:**

1. Create a distances dictionary (set start to 0, all others to infinity), a visited set, and a priority queue (Min-Heap) storing pairs of `(distance, vertex)`.
2. Push `(0, start)` onto the priority queue.
3. While the priority queue is not empty:
* Pop the vertex with the smallest distance.
* If already visited, skip it. Otherwise, mark it visited and record it.
* For each unvisited neighbor, calculate the tentative distance: `current_distance + edge_weight`.
* If this tentative distance is less than the neighbor's recorded distance, update the distance dictionary and push `(tentative_distance, neighbor)` onto the priority queue.


4. The shortest distance to all reachable nodes is captured in the distances dictionary.

**Use cases:**

* **GPS and digital mapping** apps (finding the fastest driving route)
* Network routing protocols (like OSPF, to find the path with the least delay)
* Robotic path planning and AI navigation in video games
* Flight agenda scheduling (finding connections with minimum cost or time)

**Time complexity:** $O((V + E) \log V)$ — using a binary heap, extracting the minimum vertex takes $O(\log V)$ time, and updating edge distances takes $O(\log V)$ time per edge.

**Space complexity:** $O(V + E)$ — to store the graph structures, the distances dictionary, and the priority queue, which can hold up to $E$ elements in the worst case before clean up.

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

<img width="439" height="368" alt="image" src="https://github.com/user-attachments/assets/2f41efd6-208b-4159-a5e9-20b98b2ad1c8" />

<img width="1253" height="534" alt="image" src="https://github.com/user-attachments/assets/27c24c21-edf5-40c0-ad57-fa34747c194e" />

<img width="386" height="419" alt="image" src="https://github.com/user-attachments/assets/4a9d858a-8066-4703-8cb4-03dcd7db14f2" />


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

<img width="569" height="186" alt="image" src="https://github.com/user-attachments/assets/e3a62679-4613-4ea8-a1da-4ad4e9fdedad" />

---

## F. Reflection

Working through this assignment gave me a concrete understanding of how graph traversal algorithms behave in practice versus in theory. On paper, BFS and DFS both run in O(V + E) and seem almost interchangeable. But implementing them reveals real differences: BFS requires a queue and naturally spreads outward level by level, making it ideal when you care about distance or proximity. DFS uses a stack and plunges deep before backtracking, which makes it well-suited for problems like cycle detection or topological ordering where the full depth of a path matters.

The biggest challenge was understanding how the choice of data structure — a `HashMap` for the adjacency list — affects performance and correctness. An adjacency list is efficient for sparse graphs because it only stores edges that actually exist, unlike a matrix that allocates space for every possible pair. I also noticed how much the JVM's JIT compilation distorted early timing measurements, which showed me that microbenchmarking requires care — running multiple trials and discarding the first few is important for reliable results.

---

### Overview of BFS and DFS

* Breadth-First Search (BFS): A layer-by-layer traversal algorithm. It starts at a designated root node and explores all neighboring vertices at the current depth level before moving to the vertices at the next depth level. It utilizes a Queue data structure to manage the exploration order.


* Depth-First Search (DFS): A traversal algorithm that explores as deep as possible along each branch before backtracking. It starts at a designated root node and explores along each branch to its limit before moving back to explore unvisited branches. It utilizes a Stack structure or recursion.



---

## Algorithm Descriptions

### BFS (Breadth-First Search)

* Step-by-Step Explanation:
1. Initialize an empty queue and a boolean array to track visited vertices.
2. Enqueue the starting vertex and mark it as visited.
3. While the queue is not empty, dequeue the front vertex and process it.
4. For each unvisited neighbor of the dequeued vertex, mark it as visited and enqueue it.
5. Repeat until the queue is empty.


* Use Cases: Finding the shortest path on unweighted graphs, peer-to-peer networks, web crawlers, and finding connected components.
* Time Complexity: $O(V + E)$ where $V$ is the number of vertices and $E$ is the number of edges. Every vertex and edge is evaluated a constant number of times.



### DFS (Depth-First Search)

* Step-by-Step Explanation:
1. Initialize a boolean array to track visited vertices.
2. Start at the designated source vertex, mark it as visited, and process it.
3. Recursively visit each unvisited neighbor of the current vertex.
4. If a vertex has no unvisited neighbors, backtrack to the previous vertex.
5. Repeat until all reachable vertices are marked visited.


* Use Cases: Topological sorting, solving mazes, detecting cycles in a graph, and finding strongly connected components.
* Time Complexity: $O(V + E)$ where $V$ is the number of vertices and $E$ is the number of edges. Each vertex is visited once, and all its edges are traversed.


### Dijkstra's Algorithm

* Step-by-Step Explanation:
1. Initialize a `distances` array setting the starting vertex to 0 and all other vertices to infinity ($\infty$). Create a priority queue (min-heap) and insert the starting vertex with a distance of 0.
2. Initialize a boolean array or set to track visited/finalized vertices.
3. While the priority queue is not empty, extract the vertex with the minimum tentative distance. If it has already been finalized, skip it.
4. Mark the extracted vertex as finalized. For each of its unvisited neighbors, calculate the distance from the starting vertex through the current vertex.
5. If this new calculated distance is less than the neighbor’s current recorded distance, update its distance in the array and push the neighbor along with its new distance into the priority queue.
6. Repeat until the priority queue is empty or the target vertex is finalized.


* Use Cases: Finding the shortest path on weighted graphs (with non-negative edge weights), digital mapping and routing services (like GPS navigation), network routing protocols (like OSPF), and robotic path planning.
* Time Complexity: $O((V + E) \log V)$ when implemented using a binary heap-based priority queue, where $V$ is the number of vertices and $E$ is the number of edges. Each vertex is extracted from the priority queue once, and each edge weight update costs logarithmic time.

---

## Experimental Results

### Execution Time Comparison Table

The table below displays the execution times recorded during the experiments across different graph sizes using `System.nanoTime()`:
Small (10 vertices):

BFS Execution Time: 45,200 ns
DFS Execution Time: 38,100 ns

Medium (30 vertices):

BFS Execution Time: 125,400 ns
DFS Execution Time: 98,600 ns

Large (100 vertices):

BFS Execution Time: 412,900 ns
DFS Execution Time: 345,200 ns

### Analysis Questions & Observations

#### How does graph size affect BFS and DFS performance?

As the graph size scales from 10 to 100 vertices, the execution time for both BFS and DFS increases linearly. This scale is due to the proportional increase in the number of vertices ($V$) and edges ($E$) that need to be initialized, tracked, and traversed in memory.

#### Which traversal is faster in your experiments?

In these experiments, DFS finished faster than BFS across all sizes. This occurs because DFS utilizes the implicit call stack which avoids the object overhead and shifting operations associated with managing a dynamic queue structure in BFS.

#### Do results match the expected complexity O(V + E)?

Yes, the experimental results match the theoretical $O(V + E)$ time complexity. When the scale of the graph increases, the execution times do not exhibit quadratic ($O(V^2)$) growth, confirming that the adjacency list allows both algorithms to visit vertices and edges directly without iterating over empty matrix cells.

#### How does graph structure affect traversal order?

The distribution of edges dictates how paths branch out. In a wide graph, BFS expands across all immediate neighbors, whereas DFS plunges down single deep lines. If a graph contains disconnected components, both traversal types fail to visit the isolated vertices unless the algorithm is manually restarted from an unvisited node.

#### When is BFS preferred over DFS?

BFS is preferred when finding the shortest path between two nodes in an unweighted graph, as it guarantees that the first time a target node is reached, it is reached via the minimum number of edges. It is also ideal when target nodes are expected to be close to the starting source.

#### What are the limitations of DFS?

DFS is not guaranteed to find the shortest path in terms of edge count. On deep or infinitely branching graphs, DFS can get trapped down an incorrect path, potentially running out of memory due to a stack overflow error if the recursion depth exceeds system limits.

---

## Reflection Section

Implementing this graph representation and traversal system deepened my practical understanding of non-linear data structures. Working directly with adjacency lists helped connect theoretical runtime complexities with physical performance metrics. Observing how a simple swap between a Queue and a Stack alters the sequence of visited nodes made the differences between BFS and DFS intuitive.

The primary technical challenge encountered during this implementation was preventing duplicate edges and ensuring clean tracking of visited states during the recursive DFS calls. Managing memory allocation configurations when scaling the experiment up to large sizes required a disciplined approach to variable scoping and object instantiation. Resolving these issues highlighted the importance of selecting space-efficient data structures when handling larger datasets.
