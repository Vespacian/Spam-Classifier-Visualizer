import java.util.*;
import java.io.*;

public class ClassificationTree extends Classifier {
    // TODO: Implement these!
    private ClassificationNode overallRoot;
    
    // throws IAE
    public ClassificationTree(List<Classifiable> data, List<String> results) {
        if (data.size() == 0 || results.size() == 0 || data.size() != results.size()) {
            throw new IllegalArgumentException("input lists not good nono");
        }
        
        for (int i = 0; i < data.size(); i++) {
            overallRoot = trainTree(data.get(i), results.get(i), overallRoot);
        }
    }

    private ClassificationNode trainTree(Classifiable data, String label, ClassificationNode node) {
        if (node == null) {
            return new ClassificationNode(label, data);
        } else if (node.label != null && node.label != label) {
            ClassificationNode left = new ClassificationNode(label, data);
            ClassificationNode right = new ClassificationNode(node.label, node.dataPoint);
            
            Split split = node.dataPoint.partition(data);
            if (split.evaluate(data)) {
                ClassificationNode temp = left;
                left = right;
                right = temp;
            }
            return new ClassificationNode(node.dataPoint.partition(data), left, right);
        } else {
            return (node.split.evaluate(data)) ? trainTree(data, label, node.right) : trainTree(data, label, node.left);
        }  
    }

    // throws ISE
    public ClassificationTree(Scanner sc) {
        if (!sc.hasNextLine()) {
            throw new IllegalStateException("blah blah blah No file to use - empty tree made blah blah blah");
        }
        overallRoot = buildTree(sc);
    }

    // completely assumes that the file is in the correct format
    private ClassificationNode buildTree(Scanner sc) {
        String line = sc.nextLine();
        // only two types of nodes possible: 
        if (line.startsWith("Feature:")) {
            sc.next(); // get rid of "Threshold: "
            double threshold = sc.nextDouble();
            return new ClassificationNode(new Split(line, threshold), buildTree(sc), buildTree(sc));
        } else {
            return new ClassificationNode(line); // base case
        }
    }
    
    public boolean canClassify(Classifiable input) {
        Set<String> features = input.getFeatures();
        return canClassify(features, overallRoot);
    }

    private boolean canClassify(Set<String> features, ClassificationNode node) {
        if (node.label == null) {
            return features.contains(node.split.getFeature()) 
                && canClassify(features, node.left) 
                && canClassify(features, node.right);
        }
        return true;
    }

    // throws IAE
    public String classify(Classifiable input) {
        if (!canClassify(input)) {
            throw new IllegalArgumentException("Unable to classify the given input. The square block doesn't fit in the triangle hole :(");
        }
        return classify(input, overallRoot);
    }

    private String classify(Classifiable input, ClassificationNode node) {
        if (node.label != null) {
            return node.label;
        } else {
            return (node.split.evaluate(input)) ? classify(input, node.right) : classify(input, node.left);
        }        
    }

    public void save(PrintStream ps) {
        save(ps, overallRoot);
    }

    private void save(PrintStream ps, ClassificationNode node) {
        if (node.label != null) {
            ps.println(node.label);
        } else {
            ps.println(node.split.toString());
            save(ps, node.left);
            save(ps, node.right);
        }
    }

    public static class ClassificationNode {
        // data
        public String label;
        public Split split; // ig it's also possible to not use this datatype and have all the fields in Split here instead - I think this should be a deduction
        public Classifiable dataPoint;
        // references
        private ClassificationNode left; // deduct on morphing
        private ClassificationNode right;

        // constructors
        // label node guarantees that it is a leaf node - will use label != null to show that we're currently at leaf node
        public ClassificationNode(String label) {
            this (null, null, null);
            this.label = label;
        }

        public ClassificationNode(String label, Classifiable dataPoint) {
            this (label);
            this.dataPoint = dataPoint;
        }

        public ClassificationNode(Split split) {
            this (split, null, null);
        }

        public ClassificationNode(Split split, ClassificationNode left, ClassificationNode right) {
            this.split = split;
            this.left = left;
            this.right = right;
        }

    }
}
