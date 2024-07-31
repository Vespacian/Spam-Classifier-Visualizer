package com.eb;

// import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class GraphVisualizer {

    public static void main(String[] args) {
        SimpleGraph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        // Add some vertices
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");

        // Add some edges
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");

        // Print the graph
        System.out.println("Graph: " + graph);
    }

    private static class ClassificationNode {
        public Split split;
        public String label;
        public Classifiable data;
        public ClassificationNode left;
        public ClassificationNode right;

        public ClassificationNode(Split split, ClassificationNode left, ClassificationNode right) {
            this.split = split;
            this.left = left;
            this.right = right;
        }

        public ClassificationNode(String label) {
            this(null, label);
        }

        public ClassificationNode(Classifiable data, String label) {
            this.data = data;
            this.label = label;
        }

        public boolean evaluate(Classifiable value) {
            return split.evaluate(value);
        }
    }

}