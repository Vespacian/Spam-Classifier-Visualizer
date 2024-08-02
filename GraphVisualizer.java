
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

}