import java.util.*;
public class Graph {
    private Map<Vertex, List<Vertex>> adjacencyList;
    private Map<Integer, Vertex> vertexMap;
    public Graph() {
        adjacencyList = new HashMap<>();
        vertexMap     = new HashMap<>();
    }
    public void addVertex(Vertex v) {
        if (!adjacencyList.containsKey(v)) {
            adjacencyList.put(v, new ArrayList<>());
            vertexMap.put(v.getId(), v);
        }
    }
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
    public Vertex getVertex(int id) {
        return vertexMap.get(id);
    }
    public int vertexCount() {
        return adjacencyList.size();
    }
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
    public void bfs(int startId) {
        Vertex start = vertexMap.get(startId);
        if (start == null) {
            System.out.println("BFS: start vertex " + startId + " not found.");
            return;
        }

        Set<Vertex>   visited = new HashSet<>();
        Queue<Vertex> queue   = new LinkedList<>();
        List<Integer> order   = new ArrayList<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Vertex current = queue.poll();
            order.add(current.getId());
            for (Vertex neighbor : adjacencyList.get(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        System.out.println("BFS from " + startId + ": " + order);
    }
    public void dfs(int startId) {
        Vertex start = vertexMap.get(startId);
        if (start == null) {
            System.out.println("DFS: start vertex " + startId + " not found.");
            return;
        }

        Set<Vertex>   visited = new HashSet<>();
        Deque<Vertex> stack   = new ArrayDeque<>();
        List<Integer> order   = new ArrayList<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            Vertex current = stack.pop();

            if (!visited.contains(current)) {
                visited.add(current);
                order.add(current.getId());
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
