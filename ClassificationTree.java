// import java.util.*;
// import java.io.*;

// public class ClassificationTree extends Classifier {
//     // TODO: Implement these!
    
//     public ClassificationTree(List<Classifiable> data, List<String> labels) {
//         throw new UnsupportedOperationException("Not yet implemented!");
//     }

//     public ClassificationTree(Scanner sc) {
//         throw new UnsupportedOperationException("Not yet implemented!");
//     }
    
//     public boolean canClassify(Classifiable input) {
//         throw new UnsupportedOperationException("Not yet implemented!");
//     }

//     public String classify(Classifiable input) {
//         throw new UnsupportedOperationException("Not yet implemented!");
//     }

//     public void save(PrintStream ps) {
//         throw new UnsupportedOperationException("Not yet implemented!");
//     }

//     public static class ClassificationNode {
//         // TODO: Design this!
//     }
// }


import java.util.*;
import java.io.*;

public class ClassificationTree extends Classifier {
    private ClassificationNode overallRoot;

    public ClassificationTree(List<Classifiable> list, List<String> results) {
        if (list.size() != results.size() || list.size() == 0) {
            throw new IllegalArgumentException();
        }
        
        for (int i = 0; i < list.size(); i++) {
            overallRoot = train(overallRoot, list.get(i), results.get(i));
        }
    }

    private ClassificationNode train(ClassificationNode root, Classifiable curr, String result) {
        if (root == null) {
            root = new ClassificationNode(curr, result);
        } else if (root.left == null && root.right == null) {
            if (!root.label.equals(result)) {
                // Need to partition
                Split s = curr.partition(root.data);
                if (s.evaluate(curr)) {
                    // curr belongs on the left
                    root = new ClassificationNode(s, new ClassificationNode(curr, result), root);
                } else {
                    // curr belongs on the right
                    root =  new ClassificationNode(s, root, new ClassificationNode(curr, result));
                }
            }
        } else if (root.evaluate(curr)) {
            root.left = train(root.left, curr, result);
        } else {
            root.right = train(root.right, curr, result);
        }
        return root;
    }

    public ClassificationTree(Scanner sc) {
        overallRoot = load(sc);
        if (overallRoot == null) {
            throw new IllegalStateException();
        }
    }

    private ClassificationNode load(Scanner sc) {
        String line = sc.nextLine();
        if (!line.contains("Feature: ")) {
            // Leaf node
            return new ClassificationNode(line);
        } else {
            double threshold = Double.parseDouble(
                        sc.nextLine().substring("Threshold: ".length()));
            return new ClassificationNode(
                        new Split(line.substring("Feature: ".length()), threshold),
                        load(sc), load(sc));
        }
    }
    
    public boolean canClassify(Classifiable input) {
        return canClassify(input, overallRoot);
    }

    private boolean canClassify(Classifiable input, ClassificationNode root) {
        if (root != null && root.left != null && root.right != null) {
            return input.getFeatures().contains(root.split.getFeature()) &&
                   canClassify(input, root.left) &&
                   canClassify(input, root.right);
        }
        return true;
    }

    public String classify(Classifiable input) {
        return classify(input, overallRoot);
    }

    private String classify(Classifiable input, ClassificationNode root) {
        if (root.left == null && root.right == null) {
            return root.label;
        } else {
            return root.evaluate(input) ? classify(input, root.left) :
                                          classify(input, root.right);
        }
    }

    public void save(PrintStream ps) {
        save(overallRoot, ps);
    }

    private void save(ClassificationNode root, PrintStream ps) {
        if (root.left == null && root.right == null) {
            ps.println(root.label);
        } else {
            ps.println(root.split);
            save(root.left, ps);
            save(root.right, ps);
        }
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