/**
 * Main entry point for the Graph Traversal and Representation System.
 *
 * Demonstrates:
 *   1. Building and printing a small graph (10 vertices)
 *   2. Running BFS and DFS on the small graph (to show traversal order)
 *   3. Running performance experiments across small, medium, and large graphs
 *   4. Printing a summary results table
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("  Graph Traversal and Representation System");

        // ── Part 1: Small graph demo – structure + traversal order ──────────
        System.out.println("\n[1] Small Graph – Structure & Traversal Order");

        Experiment exp = new Experiment();
        Graph smallGraph = exp.buildGraph(10);

        System.out.println();
        smallGraph.printGraph();

        System.out.println("\nTraversal order (starting from vertex 0):");
        smallGraph.bfs(0);
        smallGraph.dfs(0);

        // ── Part 2: Performance experiments on all graph sizes ───────────────
        System.out.println("\n[2] Performance Experiments");
        System.out.println("Testing BFS and DFS execution time on graphs of increasing size.");

        exp.runMultipleTests();

        // ── Part 3: Summary results table ────────────────────────────────────
        System.out.println("\n[3] Results Summary");
        exp.printResults();

        System.out.println("\nDone.");
    }
}
