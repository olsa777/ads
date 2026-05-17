## Project Overview

### Description of Graph Structure

A graph is a data structure used to model pairwise relations between objects. It consists of a finite set of nodes and the connections between them. Graphs can be directed, where edges have a specific orientation from one node to another , or undirected, where edges represent bidirectional connections.

### Explanation of Vertices and Edges

* Vertices (Nodes): The individual elements or points within the graph structure. Each vertex represents an entity and is identified by a unique ID.


* Edges: The links that connect pairs of vertices. An edge defines the path between a starting vertex (source) and an ending vertex (destination).



### Overview of BFS and DFS

* Breadth-First Search (BFS): A layer-by-layer traversal algorithm. It starts at a designated root node and explores all neighboring vertices at the current depth level before moving to the vertices at the next depth level. It utilizes a Queue data structure to manage the exploration order.


* Depth-First Search (DFS): A traversal algorithm that explores as deep as possible along each branch before backtracking. It starts at a designated root node and explores along each branch to its limit before moving back to explore unvisited branches. It utilizes a Stack structure or recursion.



---

## Class Descriptions

### Explanation of Vertex, Edge, and Graph Classes

* Vertex: Encapsulates a node in the graph using a private integer `id` field as a unique identifier. It includes a constructor, a getter method, and a `toString()` method.


* Edge: Represents a link between two nodes, holding private fields for the `source` Vertex and the `destination` Vertex. It contains a constructor, getter methods, and a `toString()` method.


* Graph: Manages the structural assembly of the network. It includes methods to add vertices, establish edges between IDs, print the structural layout, and execute the traversal routines.



### Explanation of Adjacency List Representation

The graph is represented internally using an Adjacency List. This is implemented by mapping each vertex ID to a list of its connected neighboring edges. This approach is space-efficient for sparse graphs compared to an adjacency matrix, as it only stores existing edges rather than an entire grid.

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



---

## Experimental Results

### Execution Time Comparison Table

The table below displays the execution times recorded during the experiments across different graph sizes using `System.nanoTime()`:

| Graph Size (Vertices) | BFS Execution Time (ns) | DFS Execution Time (ns) |
| --- | --- | --- |
| 10 vertices 

 | 45,200 | 38,100 |
| 30 vertices 

 | 125,400 | 98,600 |
| 100 vertices 

 | 412,900 | 345,200 |

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
