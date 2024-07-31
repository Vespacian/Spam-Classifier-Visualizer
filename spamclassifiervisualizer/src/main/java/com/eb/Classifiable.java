package spamclassifiervisualizer.src.main.java.com.eb;
// GIVEN INTERFACE

import java.util.*;

// This interface represents a piece of data that can be classified by some arbitrary
// machine learning model
public interface Classifiable {
    // Arbitrary character that feature from specific attribute, if necessary
    // e.g "wordPercent" + SPLITTER + "here"
    //      'wordPercent' is the feature, 'here' is the specific word
    public static final String SPLITTER = "~";

    // Returns the numeric value corresponding to the provided feature for this instance
    public double get(String feature);
    
    // Returns a Set of all valid features for this datatype
    public Set<String> getFeatures();
    
    // Returns a Split (feature and threshold) that segments this instance and provided 'other'
    // Throws an IllegalArgumentException
    //      If other isn't an instanceof this datatype
    public Split partition(Classifiable other);
}