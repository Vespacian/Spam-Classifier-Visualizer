// GIVEN CLASS

// Reprsents a singular split within our dataset based some feature and threshold
public class Split {
    private double threshold;
    private String feature;

    // Construct a new split from the provided threshold and feature
    public Split(String feature, double threshold) {
        this.threshold = threshold;
        this.feature = feature;
    }

    // Returns the threshold for this split
    public double getThreshold() {
        return threshold;
    }

    // Returns the feature for this split. Importantly, if this is a complex feature
    //      containing two elements separated by Classifiable.SPLITTER, return the first part
    public String getFeature() {
        return feature.split(Classifiable.SPLITTER)[0];
    }

    // Evaluates the provided Classifiable object on this split, returning false if it falls
    //      below (<) this split, true if it falls above (>=) this split
    public boolean evaluate(Classifiable value) {
        return value.get(this.feature) < this.threshold;
    }

    // Returns a String representation of this split, with the feature on the first line preceeded
    //      by "Feature: " and the threshold on the second line preceeded by "Threshold: "
    public String toString() {
        return "Feature: " + this.feature + "\n" + "Threshold: " + this.threshold;
    }

    // Returns the value that falls exactly in the middle of the two provided doubles
    public static double midpoint(double one, double two) {
        return Math.min(one, two) + (Math.abs(one - two) / 2.0);
    }
}