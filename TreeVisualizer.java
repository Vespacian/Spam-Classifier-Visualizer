import java.util.Scanner;

// import org.jgrapht.graph.DefaultEdge;
// import org.jgrapht.graph.SimpleGraph;

/**
 * This class will allow for regular binary tree functions such as
 * adding and removing nodes in any way preference. Also adds a special visualize
 * feature that gives a visual representation of the tree created. 
 */
public class TreeVisualizer<T> extends NodeTree<T> {

    // empty constructor for initializing
    public TreeVisualizer() {}

    // builds tree with given tree for Spam Classifier
    // @param sc - format as described in SC specification
    // tags: feature, threshold, (n/a) label for each line
    public TreeVisualizer(Scanner sc) {
        // 
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
