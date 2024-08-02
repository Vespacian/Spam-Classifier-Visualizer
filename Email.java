import java.util.*;

// This class represents an Email storing information about the percentage of words found within
public class Email implements Classifiable {
    public static final Set<String> FEATURES = Set.of("wordPercent");
    
    private Map<String, Integer> words;
    private double totalWords;

    // Constructs a new Email from the provided content String
    public Email(String content) {
        this.words = new HashMap<>();
        parseContent(content);
    }

    // Helper method - parses the content from the provided content String,
    //      populating the word -> count map and counting the total words/tokens
    private void parseContent(String content) {
        Scanner sc = new Scanner(content);
        while (sc.hasNext()) {
            String word = sc.next();
            if (!words.containsKey(word)) {
                words.put(word, 0);
            }
            words.put(word, words.get(word) + 1);
            totalWords++;
        }
    }

    // Returns the Set of all features present within an email that can be used in classification
    public Set<String> getFeatures() {
        return FEATURES;
    }

    // Returns the stored numeric value for the provided feature
    // Throws IllegalArgumentException
    //      If provided feature is invalid, not contained within getFeatures()
    public double get(String feature) {
        String[] splitted = feature.split(Classifiable.SPLITTER);

        if (!FEATURES.contains(splitted[0])) {
            throw new IllegalArgumentException(
                    String.format("Invalid feature [%s], not within possible features [%s]",
                                  feature, FEATURES.toString()));
        }
        
        if (splitted[0].equals("wordPercent")) {
            // Feature 1: wordPercent
            return getWordPercentage(splitted[1]);
        }
        return 0.0; // Mandatory return statement - should never reach
    }

    // Helper method - calculates and returns the percentage occurance of the given word
    private double getWordPercentage(String word) {
        return totalWords == 0 ? 0.0 : this.words.getOrDefault(word, 0) / totalWords;
    }

    // Returns a Split representing the midpoint in difference between this and the provided
    //      provided other classifiable object
    // Throws IllegalArgumentException
    //      If other isn't an instance of the Email class
    public Split partition(Classifiable other) {
        if (!(other instanceof Email)) {
            throw new IllegalArgumentException("Provided 'other' not instance of Email.java");
        }

        Email otherEmail = (Email) other;

        // Find word with the largest percentage difference between this and other
        Set<String> allWords = new HashSet<>(this.words.keySet());
        allWords.addAll(otherEmail.words.keySet());

        String bestWord = null;
        double highestDiff = 0;
        for (String word : allWords) {
            double diff = this.getWordPercentage(word) - otherEmail.getWordPercentage(word);
            diff = Math.abs(diff);
            if (diff > highestDiff) {
                bestWord = word;
                highestDiff = diff;
            }
        }

        // Calculate halfway between the two points
        double halfway = Split.midpoint(this.getWordPercentage(bestWord),
                                        otherEmail.getWordPercentage(bestWord));
        return new Split("wordPercent" + Classifiable.SPLITTER + bestWord, halfway);
    }

    // Creates and returns a Classifiable object from the provided row of email data
    public static Classifiable toClassifiable(List<String> row) {
        return new Email(row.get(1));
    }
}
