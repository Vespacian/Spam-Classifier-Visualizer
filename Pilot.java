
// java libraries
import javax.swing.JFrame;
import java.util.Hashtable;
import java.util.Map;

// Datastructures
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;

// Visualization
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxPoint;


public class Pilot extends JFrame {

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

        // Customize the graph appearance
        customizeGraphStyles(graphAdapter);

        mxGraphComponent graphComponent = new mxGraphComponent(graphAdapter);
        graphComponent.getGraph().getView().setTranslate(new mxPoint(50, 50));
        getContentPane().add(graphComponent);

        // Layout the graph as a tree
        mxCompactTreeLayout layout = new mxCompactTreeLayout(graphAdapter);
        layout.setHorizontal(false);  // Set to false for a vertical tree layout, true for horizontal
        layout.setNodeDistance(30);   // Set the distance between nodes
        layout.setLevelDistance(50);  // Set the distance between levels
        layout.execute(graphAdapter.getDefaultParent());
    }

    private void customizeGraphStyles(mxGraph graph) {
        // Define the styles for nodes and edges
        mxStylesheet stylesheet = graph.getStylesheet();
        Map<String, Object> nodeStyle = new Hashtable<>();
        
        // node styling
        nodeStyle.put(mxConstants.STYLE_SHAPE, mxConstants.ARROW_OVAL);
        nodeStyle.put(mxConstants.STYLE_FONTCOLOR, "#000000");
        nodeStyle.put(mxConstants.STYLE_FILLCOLOR, "#87CEEB");
        nodeStyle.put(mxConstants.STYLE_STROKECOLOR, "#000000");
        nodeStyle.put(mxConstants.STYLE_FONTSIZE, 16);

        // node spacing
        nodeStyle.put(mxConstants.STYLE_AUTOSIZE, true);
        nodeStyle.put(mxConstants.STYLE_SPACING, 10);

        // node moveable
        nodeStyle.put(mxConstants.STYLE_MOVABLE, true);
        nodeStyle.put(mxConstants.STYLE_RESIZABLE, false);
        nodeStyle.put(mxConstants.STYLE_EDITABLE, false);


        // nodeStyle.put(mxConstants.STYLE_WHITE_SPACE, "wrap"); // Enable text wrapping
        // nodeStyle.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
        // nodeStyle.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);


        stylesheet.putCellStyle("NODE", nodeStyle);

        Map<String, Object> edgeStyle = new Hashtable<>();
        edgeStyle.put(mxConstants.STYLE_STROKECOLOR, "#000000");
        edgeStyle.put(mxConstants.STYLE_NOLABEL, true);
        stylesheet.putCellStyle("EDGE", edgeStyle);

        // Apply styles to all vertices and edges
        Object[] cells = graph.getChildCells(graph.getDefaultParent(), true, true);
        for (Object cell : cells) {
            if (graph.getModel().isVertex(cell)) {
                graph.setCellStyle("NODE", new Object[]{cell});
            } else if (graph.getModel().isEdge(cell)) {
                graph.setCellStyle("EDGE", new Object[]{cell});
            }
        }
    }
}




