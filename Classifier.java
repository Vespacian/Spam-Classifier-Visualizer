import java.io.*;
import java.util.*;

// This abstract class represents a Classifier, capable of assigning a label to a
// Classifiable input using probability
public abstract class Classifier {
    // Returns whether or not the classifier is able to classify datapoints that match that
    //      of the provided 'input'
    public abstract boolean canClassify(Classifiable input);

    // Classifies the provided 'input', returning the associated learned label
    public abstract String classify(Classifiable input);

    // Saves this classifier to the provided PrintStream 'ps'
    public abstract void save(PrintStream ps);

    // Calculates the accuracy of this model on provided Lists of testing 'data' and
    //      corresponding 'labels'. The label for a datapoint at index 'i' within 'data' should be
    //      found at the same 'i' within 'labels'.
    // Throws IllegalArgumentException
    //      If the the number of datapoints doesn't match the number of provided labels
    //      If any of the datapoints aren't able to be classified by this classifier
    // Returns a map storing the classification accuracy for each of the encountered labels when
    //      classifying
    public Map<String, Double> calculateAccuracy(List<Classifiable> data, List<String> labels) {
        // Check to make sure the lists have the same size (each datapoint has an expected label)
        if (data.size() != labels.size()) {
            throw new IllegalArgumentException(
                    String.format("Length of provided data [%d] doesn't match provided labels [%d]",
                                  data.size(), labels.size()));
        }
        
        // Create our total and correct maps for average calculation
        Map<String, Integer> labelToTotal = new HashMap<>();
        Map<String, Double> labelToCorrect = new HashMap<>();
        labelToTotal.put("Overall", 0);
        labelToCorrect.put("Overall", 0.0);
        
        for (int i = 0; i < data.size(); i++) {
            // Check to make sure we can classify this datapoint
            if (!canClassify(data.get(i))) {
               throw new IllegalArgumentException();
            }

            String result = classify(data.get(i));
            String label = labels.get(i);

            // Increment totals depending on resultant label
            labelToTotal.put(label, labelToTotal.getOrDefault(label, 0) + 1);
            labelToTotal.put("Overall", labelToTotal.get("Overall") + 1);
            if (result.equals(label)) {
                labelToCorrect.put(result, labelToCorrect.getOrDefault(result, 0.0) + 1);
                labelToCorrect.put("Overall", labelToCorrect.get("Overall") + 1);
            }
        }

        // Turn totals into accuracy percentage
        for (String label : labelToCorrect.keySet()) {
            labelToCorrect.put(label, labelToCorrect.get(label) / labelToTotal.get(label));
        }
        return labelToCorrect;
    }
}
