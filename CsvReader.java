import java.util.*;
import java.io.*;

// Parses lines from CSV file, splitting on commas
public class CsvReader {
    public static final String COMMA = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

    // Reads data from the provided file, converting each line into its own List split on commas.
    //      The returned value can be thought of as a 2d array, just with Lists instead!
    // Throws a FileNotFoundException
    //      If the provided file doesn't exist
    public static List<List<String>> read(String fileName) throws FileNotFoundException {
        List<List<String>> lines = new ArrayList<>();
        
        Scanner sc = new Scanner(new File(fileName));
        sc.nextLine();      // Skip the first row since it's just titles
        
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            lines.add(Arrays.asList(line.split(COMMA)));
        }
        return lines;
    }
}
