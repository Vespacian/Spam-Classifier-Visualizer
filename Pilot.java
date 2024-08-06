import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

// import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.swing.mxGraphComponent;

import javax.swing.JFrame;

import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;

public class Pilot extends JFrame{

    public static void main(String[] args) {
        Pilot frame = new Pilot();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    public Pilot() {
    // Create a binary tree graph
        Graph<String, DefaultEdge> binaryTree = new DefaultDirectedGraph<>(DefaultEdge.class);

        // Add vertices (nodes)
        binaryTree.addVertex("Root");
        binaryTree.addVertex("LeftChild");
        binaryTree.addVertex("RightChild");
        binaryTree.addVertex("LeftLeftChild");
        binaryTree.addVertex("LeftRightChild");
        binaryTree.addVertex("RightLeftChild");
        binaryTree.addVertex("RightRightChild");

        // Add edges to form a binary tree
        binaryTree.addEdge("Root", "LeftChild");
        binaryTree.addEdge("Root", "RightChild");
        binaryTree.addEdge("LeftChild", "LeftLeftChild");
        binaryTree.addEdge("LeftChild", "LeftRightChild");
        binaryTree.addEdge("RightChild", "RightLeftChild");
        binaryTree.addEdge("RightChild", "RightRightChild");

        // Create a visualization using JGraphX
        JGraphXAdapter<String, DefaultEdge> graphAdapter = new JGraphXAdapter<>(binaryTree);
        mxGraphComponent graphComponent = new mxGraphComponent(graphAdapter);
        getContentPane().add(graphComponent);

        // Layout the graph as a tree
        mxCompactTreeLayout layout = new mxCompactTreeLayout(graphAdapter);
        layout.setHorizontal(false);  // Set to false for a vertical tree layout, true for horizontal
        layout.execute(graphAdapter.getDefaultParent());
    }
}