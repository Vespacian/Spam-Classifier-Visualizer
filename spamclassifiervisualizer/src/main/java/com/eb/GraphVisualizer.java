package spamclassifiervisualizer.src.main.java.com.eb;


public class GraphVisualizer {

    public static void main(String[] args) {
        System.out.println("Hello world");
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