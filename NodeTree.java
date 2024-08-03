import java.util.ArrayList;
import java.util.List;

/*
 * Allows for Binary Tree representation. Building tree functionality. 
 */
public class NodeTree<T> {
    public Node<T> overallRoot;

    // adds a node to the tree
    // @param node - to add
    // @return true if action successful, false otherwise
    public boolean add(Node<T> node) {
        if (overallRoot == null) {
            overallRoot = node;
            return true;
        }
        return false;
    }

    // remove
    // search
    // contains

    // returns a string representing every node in the tree
    // in preorder
    public String toString() {
        return print(overallRoot);
    }

    // builds string representation of the tree in preorder
    // @param root - curr Node
    // @return str rep
    private String print(Node<T> root) {
        if (root == null) {
            return "";
        } else {
            return root.toString() + "\n" + print(root.left) + "\n" + print(root.right);
        }
    }


    // variable Node in Tree - meant to be versitile to use for
    // any binary tree assignment in CSE 123
    // ex: in Spam Classifier, can add multiple String lines for data like
    // "Feature: ___" and "Threshold: ___" or just "Label: ___"
    public class Node<S> {
        private List<S> data;
        public Node<S> left;
        public Node<S> right;

        // leaf node
        // @param varargs data in node
        public Node(@SuppressWarnings("unchecked") S... data) {
            this(null, null, data);
        }

        // intermediary node
        // @param left - left child
        // @param right - right child
        // @param varargs data in node
        public Node(Node<S> left, Node<S> right, @SuppressWarnings("unchecked") S... data) {
            this.left = left;
            this.right = right;
            this.data = new ArrayList<>();
            for (S val : data) {
                this.data.add(val);
            }
        }

        // gives the raw List with all the data in the node
        public List<S> getData() {
            return data;
        }

        // Returns a string rep of all the data in the node 
        // separated by a space in between
        public String toString() {
            String str = "";
            for (S val : data) str += val + " ";
            return str.trim();
        }
    }
}
