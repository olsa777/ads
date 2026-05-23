public class Main {

    public static void main(String[] args) {

        System.out.println("==============================================");
        System.out.println("  Graph Traversal and Representation System");
        System.out.println("==============================================");

        Experiment exp = new Experiment();
        Graph smallGraph = exp.buildGraph(10);

        System.out.println("\n[1] Small Graph Structure");
        System.out.println();
        smallGraph.printGraph();

        System.out.println("\nTraversal order from vertex 0:");
        smallGraph.bfs(0);
        smallGraph.dfs(0);

        System.out.println("\n[2] Performance Experiments");
        exp.runMultipleTests();

        System.out.println("\n[3] Results Summary");
        exp.printResults();

        // Bonus: Dijkstra on a small weighted graph
        System.out.println("\n[4] Dijkstra Shortest Path");
        Graph weighted = new Graph();
        for (int i = 0; i < 6; i++) weighted.addVertex(new Vertex(i));

        weighted.addEdge(0, 1, 4);
        weighted.addEdge(0, 2, 1);
        weighted.addEdge(2, 1, 2);
        weighted.addEdge(1, 3, 1);
        weighted.addEdge(2, 3, 5);
        weighted.addEdge(3, 4, 3);
        weighted.addEdge(4, 5, 2);

        System.out.println("\nWeighted graph structure:");
        weighted.printGraph();
        System.out.println();
        weighted.dijkstra(0);

        System.out.println("\nDone.");
    }
}
