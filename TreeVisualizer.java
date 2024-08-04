import java.util.Scanner;

// import org.jgrapht.graph.DefaultEdge;
// import org.jgrapht.graph.SimpleGraph;

/**
 * This class builds a binary tree and adds a special visualize
 * feature that gives a visual representation of the tree created. 
 */
public class TreeVisualizer<T> extends NodeTree<String> {

    // empty constructor for initialization
    public TreeVisualizer() {}

    // builds tree with given tree for Spam Classifier
    // @param sc - format as described in SC specification
    // tags: feature, threshold, (n/a) label for each line
    public TreeVisualizer(Scanner sc) {
        if (!sc.hasNextLine()) {
            throw new IllegalStateException("blah blah blah No file to use - empty tree made blah blah blah");
        }
        overallRoot = buildTree(sc);
    }

    // completely assumes that the file is in the correct format
    private Node<String> buildTree(Scanner sc) {
        String line = sc.nextLine();
        // only two types of nodes possible: 
        if (line.startsWith("Feature:")) {
            String threshold = sc.nextLine();
            return new Node<String>(buildTree(sc), buildTree(sc), line, threshold);
        } else {
            return new Node<String>(line); // leaf
        }
    }

    // provides a visual representation of the tree constructed
    // @throws IllegalStateException if the tree is nonexistent
    public void visualize() {
        if (overallRoot == null) {
            throw new IllegalStateException("The tree doesn't exist, bad bad");
        }

        // TODO with library

    }

}
