import java.util.*;

/**
 * Graph class represents a graph using an adjacency list.
 * Supports directed edges, BFS traversal, and DFS traversal.
 *
 * Adjacency list structure:
 *   - A HashMap maps each Vertex to a List of its neighboring Vertices.
 *   - This gives O(1) average-case vertex lookup and O(V + E) space usage.
 */
public class Graph {

    // Maps each vertex to its list of adjacent (neighboring) vertices
    private Map<Vertex, List<Vertex>> adjacencyList;

    // Stores all vertices by their id for quick lookup
    private Map<Integer, Vertex> vertexMap;

    /**
     * Constructs an empty graph with no vertices or edges.
     */
    public Graph() {
        adjacencyList = new HashMap<>();
        vertexMap     = new HashMap<>();
    }

    /**
     * Adds a vertex to the graph.
     * If the vertex already exists, it is ignored.
     * @param v the Vertex to add
     */
    public void addVertex(Vertex v) {
        if (!adjacencyList.containsKey(v)) {
            adjacencyList.put(v, new ArrayList<>());
            vertexMap.put(v.getId(), v);
        }
    }

    /**
     * Adds a directed edge from vertex with id 'from' to vertex with id 'to'.
     * Both vertices must already exist in the graph.
     * @param from id of the source vertex
     * @param to   id of the destination vertex
     */
    public void addEdge(int from, int to) {
        Vertex source = vertexMap.get(from);
        Vertex dest   = vertexMap.get(to);

        if (source == null || dest == null) {
            System.out.println("Warning: vertex " + from + " or " + to + " not found.");
            return;
        }

        // Avoid duplicate edges
        if (!adjacencyList.get(source).contains(dest)) {
            adjacencyList.get(source).add(dest);
        }
    }

    /**
     * Returns the vertex object for the given id, or null if not found.
     * @param id the vertex id
     * @return the Vertex, or null
     */
    public Vertex getVertex(int id) {
        return vertexMap.get(id);
    }

    /**
     * Returns the number of vertices in the graph.
     * @return vertex count
     */
    public int vertexCount() {
        return adjacencyList.size();
    }

    /**
     * Prints the adjacency list representation of the graph.
     * Each line shows a vertex and all vertices it connects to.
     */
    public void printGraph() {
        System.out.println("Graph Adjacency List (" + adjacencyList.size() + " vertices):");
        // Sort by vertex id for consistent, readable output
        List<Vertex> sorted = new ArrayList<>(adjacencyList.keySet());
        sorted.sort(Comparator.comparingInt(Vertex::getId));

        for (Vertex v : sorted) {
            System.out.print("  " + v.getId() + " -> ");
            List<Vertex> neighbors = adjacencyList.get(v);
            if (neighbors.isEmpty()) {
                System.out.print("(no edges)");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Vertex neighbor : neighbors) {
                    sb.append(neighbor.getId()).append(" ");
                }
                System.out.print(sb.toString().trim());
            }
            System.out.println();
        }
    }

    // ─────────────────────────────────────────────────────────────
    //  BFS – Breadth-First Search
    // ─────────────────────────────────────────────────────────────

    /**
     * Performs Breadth-First Search starting from the vertex with the given id.
     *
     * BFS explores the graph level by level:
     *   1. Enqueue the start vertex and mark it visited.
     *   2. Dequeue the front vertex; process it.
     *   3. Enqueue all unvisited neighbors and mark them visited.
     *   4. Repeat until the queue is empty.
     *
     * Time complexity: O(V + E) — every vertex and edge is visited once.
     *
     * @param startId id of the starting vertex
     */
    public void bfs(int startId) {
        Vertex start = vertexMap.get(startId);
        if (start == null) {
            System.out.println("BFS: start vertex " + startId + " not found.");
            return;
        }

        Set<Vertex>   visited = new HashSet<>();
        Queue<Vertex> queue   = new LinkedList<>();
        List<Integer> order   = new ArrayList<>();

        // Step 1: initialise with the start vertex
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            // Step 2: dequeue and record
            Vertex current = queue.poll();
            order.add(current.getId());

            // Step 3: enqueue unvisited neighbors
            for (Vertex neighbor : adjacencyList.get(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        System.out.println("BFS from " + startId + ": " + order);
    }

    // ─────────────────────────────────────────────────────────────
    //  DFS – Depth-First Search
    // ─────────────────────────────────────────────────────────────

    /**
     * Performs Depth-First Search starting from the vertex with the given id.
     *
     * DFS explores as far along each branch as possible before backtracking:
     *   1. Push the start vertex onto a stack.
     *   2. Pop the top vertex; if not visited, mark it and process it.
     *   3. Push all unvisited neighbors onto the stack.
     *   4. Repeat until the stack is empty.
     *
     * Time complexity: O(V + E) — every vertex and edge is visited once.
     *
     * @param startId id of the starting vertex
     */
    public void dfs(int startId) {
        Vertex start = vertexMap.get(startId);
        if (start == null) {
            System.out.println("DFS: start vertex " + startId + " not found.");
            return;
        }

        Set<Vertex>   visited = new HashSet<>();
        Deque<Vertex> stack   = new ArrayDeque<>();
        List<Integer> order   = new ArrayList<>();

        // Step 1: initialise with the start vertex
        stack.push(start);

        while (!stack.isEmpty()) {
            // Step 2: pop and check
            Vertex current = stack.pop();

            if (!visited.contains(current)) {
                visited.add(current);
                order.add(current.getId());

                // Step 3: push neighbors (reversed so lower ids are explored first)
                List<Vertex> neighbors = new ArrayList<>(adjacencyList.get(current));
                Collections.reverse(neighbors);
                for (Vertex neighbor : neighbors) {
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }

        System.out.println("DFS from " + startId + ": " + order);
    }
}
