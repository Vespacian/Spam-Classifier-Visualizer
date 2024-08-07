
// java libraries
import java.util.Scanner;
import javax.swing.JFrame;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.awt.Dimension;

// Datastructures
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;

// Visualization
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxRectangle;


/**
 * This class builds a binary tree and adds a special visualize
 * feature that gives a visual representation of the tree created. 
 */
public class TreeVisualizer extends JFrame{
    private Node overallRoot;
    private Map<String, String> idToData;

    // initializes the JFrame board
    public TreeVisualizer() {
        idToData = new HashMap<>();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500); // increase default frame size if needed
        setResizable(true);
        setVisible(false);
    }

    // builds tree with given tree for Spam Classifier
    // @param sc - format as described in SC specification
    // tags: feature, threshold, label for each line
    public TreeVisualizer(Scanner sc) {
        this();
        if (!sc.hasNextLine()) {
            throw new IllegalStateException("blah blah blah No file to use - empty tree made blah blah blah");
        }
        overallRoot = buildTree(sc);
    }

    private int idCounter = 0;
    // fills up the tree with nodes to be used to create the visual
    // completely assumes that the file is in the correct format
    private Node buildTree(Scanner sc) {
        String line = sc.nextLine();
        idCounter++;
        String strCounter = "" + idCounter;
        // only two types of nodes possible: 
        if (line.startsWith("Feature:")) {
            String threshold = sc.nextLine();
            idToData.put(strCounter, line + "\n" + threshold);
            return new Node(strCounter, line + "\n" + threshold, buildTree(sc), buildTree(sc));
        } else {
            idToData.put(strCounter, line);
            return new Node(strCounter, line); // leaf
        }
    }

    // provides a visual representation of the tree constructed
    // @throws IllegalStateException if the tree is nonexistent
    public void visualize() {
        if (overallRoot == null) {
            throw new IllegalStateException("The tree doesn't exist. Make sure to input properly formatted tree file");
        }

        // adding nodes and edges to the graph
        Graph<String, DefaultEdge> tree = new DefaultDirectedGraph<>(DefaultEdge.class);
        tree.addVertex(overallRoot.getId());
        buildGraph(tree, overallRoot);

        displayGraph(tree);
    }

    // adds vertices and edges to the graph
    private void buildGraph(Graph<String, DefaultEdge> tree, Node root) {
        if (root != null) {
            // if available, add left and right nodes + edges
            if (root.left != null) {
                tree.addVertex(root.left.getId());
                tree.addEdge(root.getId(), root.left.getId());
            }
            if (root.right != null) {
                tree.addVertex(root.right.getId());
                tree.addEdge(root.getId(), root.right.getId());
            }            

            // traverse left and right
            buildGraph(tree, root.left);
            buildGraph(tree, root.right);
        }
    }

    // generates and displays the graph in the JFrame
    private void displayGraph(Graph<String, DefaultEdge> tree) {
        // creating visualization
        JGraphXAdapter<String, DefaultEdge> adapter = new JGraphXAdapter<>(tree) {
            @Override
            public String convertValueToString(Object val) {
                if (val instanceof mxCell) {
                    Object cellVal = ((mxCell) val).getValue();
                    String key = cellVal.toString();
                    return idToData.get(key);
                }
                return super.convertValueToString(val);
            }
        };

        applyStyles(adapter, styles(adapter));

        // creating layout of graph to make it look like tree
        mxCompactTreeLayout layout = new mxCompactTreeLayout(adapter);
        layout.setHorizontal(false);
        layout.setNodeDistance(20);
        layout.setLevelDistance(35);
        layout.execute(adapter.getDefaultParent());

        // positioning graph
        int padding = 50;
        mxGraphComponent component = new mxGraphComponent(adapter);
        component.getGraph().getView().setTranslate(new mxPoint(padding, padding)); // initial position

        mxRectangle graphSize = adapter.getGraphBounds(); // adding padding
        int newWidth = (int) graphSize.getWidth() + padding*2;
        int newHeight = (int) graphSize.getHeight() + padding*2;
        Dimension paddedSize = new Dimension(
            (newWidth > 800)? 800 : newWidth, 
            (newHeight > 800)? 800 : newHeight
        );
        component.setPreferredSize(paddedSize);
        getContentPane().add(component);

        // resize frame
        pack();

        // show the tree
        setVisible(true);
    }

    // applies the NODE and EDGE styles for every node and edge in the 
    // given graph
    private void applyStyles(mxGraph graph, mxStylesheet stylesheet) {
        Object[] cells = graph.getChildCells(graph.getDefaultParent(), true, true);
        for (Object cell : cells) {
            if (graph.getModel().isVertex(cell)) {
                if (graph.convertValueToString(cell).startsWith("Feature:")) {
                    graph.setCellStyle("INTERMEDIARY_NODE", new Object[] {cell});
                } else {
                    graph.setCellStyle("LEAF_NODE", new Object[] {cell});
                }
                
            } else if (graph.getModel().isEdge(cell)) {
                graph.setCellStyle("EDGE", new Object[] {cell});
            }
        }
    }

    // generates styles for the nodes and edges of the given graph
    // returns the generated mxStyleSheet
    private mxStylesheet styles(mxGraph graph) {
        mxStylesheet stylesheet = graph.getStylesheet();
        Map<String, Object> intermediaryNodeStyles = new Hashtable<>();
        Map<String, Object> leafNodeStyles = new Hashtable<>();
    
        // Intermediary node styling
        intermediaryNodeStyles.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        intermediaryNodeStyles.put(mxConstants.STYLE_FONTCOLOR, "#000000");
        intermediaryNodeStyles.put(mxConstants.STYLE_FILLCOLOR, "#c9daf8");
        intermediaryNodeStyles.put(mxConstants.STYLE_STROKECOLOR, "#000000");
        intermediaryNodeStyles.put(mxConstants.STYLE_FONTSIZE, 12);
        intermediaryNodeStyles.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD);
        intermediaryNodeStyles.put(mxConstants.STYLE_FONTFAMILY, "Arial");

        // fluid movements
        intermediaryNodeStyles.put(mxConstants.STYLE_MOVABLE, true);
        intermediaryNodeStyles.put(mxConstants.STYLE_EDITABLE, false);
        intermediaryNodeStyles.put(mxConstants.STYLE_AUTOSIZE, true);
        intermediaryNodeStyles.put(mxConstants.STYLE_RESIZABLE, true);

        // align labels in the middle
        intermediaryNodeStyles.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        intermediaryNodeStyles.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_CENTER);
        intermediaryNodeStyles.put(mxConstants.STYLE_LABEL_POSITION, mxConstants.ALIGN_CENTER);
        intermediaryNodeStyles.put(mxConstants.STYLE_VERTICAL_LABEL_POSITION, mxConstants.ALIGN_CENTER);
    

        // Leaf node styling
        leafNodeStyles.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        leafNodeStyles.put(mxConstants.STYLE_FONTCOLOR, "#000000");
        leafNodeStyles.put(mxConstants.STYLE_FILLCOLOR, "#d9ead3");
        leafNodeStyles.put(mxConstants.STYLE_STROKECOLOR, "#000000");
        leafNodeStyles.put(mxConstants.STYLE_FONTSIZE, 12);
        leafNodeStyles.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD);
        leafNodeStyles.put(mxConstants.STYLE_FONTFAMILY, "Arial");

        // fluid movements
        leafNodeStyles.put(mxConstants.STYLE_MOVABLE, true);
        leafNodeStyles.put(mxConstants.STYLE_EDITABLE, false);
        leafNodeStyles.put(mxConstants.STYLE_AUTOSIZE, true);
        leafNodeStyles.put(mxConstants.STYLE_RESIZABLE, true);

        // align labels in the middle
        leafNodeStyles.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        leafNodeStyles.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_CENTER);
        leafNodeStyles.put(mxConstants.STYLE_LABEL_POSITION, mxConstants.ALIGN_CENTER);
        leafNodeStyles.put(mxConstants.STYLE_VERTICAL_LABEL_POSITION, mxConstants.ALIGN_CENTER);
    
        // Add node styles to sheet
        stylesheet.putCellStyle("INTERMEDIARY_NODE", intermediaryNodeStyles);
        stylesheet.putCellStyle("LEAF_NODE", leafNodeStyles);

        // edge styling
        Map<String, Object> edgeStyles = new Hashtable<>();

        // edgeStyles.put(mxConstants.STYLE_SHAPE, mxConstants.ARROW_CLASSIC);
        edgeStyles.put(mxConstants.STYLE_STROKECOLOR, "#000000");
        edgeStyles.put(mxConstants.STYLE_NOLABEL, true);

        // add edge styles to sheet
        stylesheet.putCellStyle("EDGE", edgeStyles);

        return stylesheet;
    }

    // builds an example tree and displays it in JFrame to show visualize works
    public void testVisualize() {
        Graph<String, DefaultEdge> tree = new DefaultDirectedGraph<>(DefaultEdge.class);

        // Add vertices (nodes)
        tree.addVertex("Root");
        tree.addVertex("LeftChild");
        tree.addVertex("RightChild");
        tree.addVertex("LeftLeftChild");
        tree.addVertex("LeftRightChild");
        tree.addVertex("RightLeftChild");
        tree.addVertex("RightRightChild");

        // Add edges to form a binary tree
        tree.addEdge("Root", "LeftChild");
        tree.addEdge("Root", "RightChild");
        tree.addEdge("LeftChild", "LeftLeftChild");
        tree.addEdge("LeftChild", "LeftRightChild");
        tree.addEdge("RightChild", "RightLeftChild");
        tree.addEdge("RightChild", "RightRightChild");

        displayGraph(tree);
    }

    // represents a singular node in the Tree
    // the primary purpose behind the inner Node class is to
    // use this structure to translate easily into visualization
    private class Node {
        // unique identification of nodes in order to create edges
        private String id;
        private String data;
        public Node left;
        public Node right;

        public Node(String id, String data) {
            this(id, data, null, null);
        }

        public Node(String id, String data, Node left, Node right) {
            this.id = id;
            this.data = data;
            this.left = left;
            this.right = right;
        }

        @SuppressWarnings("unused")
        public String getData() {
            return this.data;
        }

        public String getId() {
            return this.id;
        }
    }

}
