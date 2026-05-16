import java.util.*;

/**
 * Experiment class handles graph construction, traversal execution,
 * performance timing, and results reporting.
 *
 * It tests BFS and DFS on three graph sizes:
 *   - Small:  10 vertices
 *   - Medium: 30 vertices
 *   - Large:  100 vertices
 */
public class Experiment {

    // Stores timing results: label -> [bfsNanos, dfsNanos]
    private List<String[]> results = new ArrayList<>();

    /**
     * Runs BFS and DFS on a given graph, measures execution time in nanoseconds,
     * and stores the results internally.
     *
     * @param g     the graph to traverse
     * @param label a label for this experiment (e.g. "Small (10 vertices)")
     */
    public void runTraversals(Graph g, String label) {
        System.out.println("\n--- " + label + " ---");

        // ── BFS timing ──────────────────────────────────────────
        long bfsStart = System.nanoTime();
        g.bfs(0);
        long bfsEnd  = System.nanoTime();
        long bfsTime = bfsEnd - bfsStart;

        // ── DFS timing ──────────────────────────────────────────
        long dfsStart = System.nanoTime();
        g.dfs(0);
        long dfsEnd  = System.nanoTime();
        long dfsTime = dfsEnd - dfsStart;

        System.out.println("BFS time: " + bfsTime + " ns");
        System.out.println("DFS time: " + dfsTime + " ns");

        results.add(new String[]{label, String.valueOf(bfsTime), String.valueOf(dfsTime)});
    }

    /**
     * Builds and tests graphs of three sizes: 10, 30, and 100 vertices.
     * Edges are added in a pattern that creates a densely connected graph
     * representative of real-world networks (each vertex connects to its
     * next few neighbors and a distant one, forming a small-world topology).
     */
    public void runMultipleTests() {
        int[] sizes = {10, 30, 100};
        String[] labels = {
            "Small  (10 vertices)",
            "Medium (30 vertices)",
            "Large (100 vertices)"
        };

        for (int i = 0; i < sizes.length; i++) {
            int   n = sizes[i];
            Graph g = buildGraph(n);
            runTraversals(g, labels[i]);
        }
    }

    /**
     * Builds a directed graph with n vertices.
     * Each vertex v gets edges to:
     *   - v+1 (next neighbor, if it exists)
     *   - v+2 (skip neighbor, if it exists)
     *   - v + n/4 (long-range neighbor, if it exists)
     *
     * This creates a connected graph with a mix of short and long paths,
     * making traversal differences between BFS and DFS more visible.
     *
     * @param n number of vertices
     * @return the constructed Graph
     */
    public Graph buildGraph(int n) {
        Graph g = new Graph();

        // Add all vertices first
        for (int i = 0; i < n; i++) {
            g.addVertex(new Vertex(i));
        }

        // Add edges to form a connected, varied structure
        for (int i = 0; i < n; i++) {
            if (i + 1 < n)     g.addEdge(i, i + 1);      // sequential neighbor
            if (i + 2 < n)     g.addEdge(i, i + 2);      // skip neighbor
            if (i + n / 4 < n) g.addEdge(i, i + n / 4);  // long-range neighbor
        }

        return g;
    }

    /**
     * Prints a formatted summary table of all recorded traversal times.
     */
    public void printResults() {
        System.out.println("\n╔══════════════════════════╦══════════════════╦══════════════════╗");
        System.out.println("║ Graph Size               ║ BFS Time (ns)    ║ DFS Time (ns)    ║");
        System.out.println("╠══════════════════════════╬══════════════════╬══════════════════╣");
        for (String[] row : results) {
            System.out.printf("║ %-24s ║ %16s ║ %16s ║%n", row[0], row[1], row[2]);
        }
        System.out.println("╚══════════════════════════╩══════════════════╩══════════════════╝");
    }
}
