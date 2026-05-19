import java.util.*;
public class Experiment {
    private List<String[]> results = new ArrayList<>();
    public void runTraversals(Graph g, String label) {
        System.out.println("\n--- " + label + " ---");
        long bfsStart = System.nanoTime();
        g.bfs(0);
        long bfsEnd  = System.nanoTime();
        long bfsTime = bfsEnd - bfsStart;
        long dfsStart = System.nanoTime();
        g.dfs(0);
        long dfsEnd  = System.nanoTime();
        long dfsTime = dfsEnd - dfsStart;

        System.out.println("BFS time: " + bfsTime + " ns");
        System.out.println("DFS time: " + dfsTime + " ns");

        results.add(new String[]{label, String.valueOf(bfsTime), String.valueOf(dfsTime)});
    }
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
    public Graph buildGraph(int n) {
        Graph g = new Graph();
        for (int i = 0; i < n; i++) {
            g.addVertex(new Vertex(i));
        }
        for (int i = 0; i < n; i++) {
            if (i + 1 < n)     g.addEdge(i, i + 1);      // sequential neighbor
            if (i + 2 < n)     g.addEdge(i, i + 2);      // skip neighbor
            if (i + n / 4 < n) g.addEdge(i, i + n / 4);  // long-range neighbor
        }

        return g;
    }

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
