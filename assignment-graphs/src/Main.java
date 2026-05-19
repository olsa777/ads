public class Main {

    public static void main(String[] args) {

        System.out.println("  Graph Traversal and Representation System");
        System.out.println("\n[1] Small Graph – Structure & Traversal Order");
        Experiment exp = new Experiment();
        Graph smallGraph = exp.buildGraph(10);
        System.out.println();
        smallGraph.printGraph();
        System.out.println("\nTraversal order (starting from vertex 0):");
        smallGraph.bfs(0);
        smallGraph.dfs(0);
        System.out.println("\n[2] Performance Experiments");
        System.out.println("Testing BFS and DFS execution time on graphs of increasing size.");
        exp.runMultipleTests();
        System.out.println("\n[3] Results Summary");
        exp.printResults();
        System.out.println("\nDone.");
    }
}
