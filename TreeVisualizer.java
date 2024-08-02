
// import org.jgrapht.Graph;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class TreeVisualizer<T> {
    public Node<T> overallRoot;

    


    // variable Node in Tree
    private class Node<S> {
        private List<S> data;
        private List<Node<S>> children;

        // constructors
        public Node(S... data) {
            this(new ArrayList<>(), data);
        }

        public Node(List<Node<S>> children, S... data) {
            this.children = (children == null)? new ArrayList<>() : children;
            this.data = new ArrayList<>();
            for (S val : data) {
                this.data.add(val);
            }
        }

        // getters
        public List<S> getData() {
            return data;
        }

        // prob to change later as I make the class
        public List<Node<S>> getChildren() {
            return children;
        }

        // toString
    }

}




    // public static void main(String[] args) {
    //     SimpleGraph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

    //     // Add some vertices
    //     graph.addVertex("A");
    //     graph.addVertex("B");
    //     graph.addVertex("C");

    //     // Add some edges
    //     graph.addEdge("A", "B");
    //     graph.addEdge("B", "C");
    //     graph.addEdge("C", "A");

    //     // Print the graph
    //     System.out.println("Graph: " + graph);
    // }