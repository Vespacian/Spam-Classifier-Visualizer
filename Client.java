import java.util.*;
import java.util.function.*;
import java.io.*;

// Client class for interaction with Classifiers
public class Client {
    // TODO: Change all 4 of these if choosing new data type!
    public static final String TRAIN_FILE = "data/emails/train.csv"; 
    public static final String TEST_FILE = "data/emails/test.csv";      
    public static final int LABEL_INDEX = 0; 
    // Very strange thing we're doing here - we're storing a method as a variable!
    // You should not do this in your homework as it's a forbidden feature, but if you're
    // interested in how this works, look up 'functional programming'!                
    public static final Function<List<String>, Classifiable> CONVERTER = Email::toClassifiable;
    // Change this variable to turn the visualizer on and off! 
    public static final boolean DEBUG = true;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner console = new Scanner(System.in);

        if (DEBUG) {
            System.out.println("What tree file do you want visualized? Enter the file path");
            System.out.print("> ");
            Scanner file = new Scanner(new File(console.next()));
            TreeVisualizer vis = new TreeVisualizer(file);
            vis.visualize();
        }

        System.out.println("Welcome to the CSE 123 Classifier! " +
                           "To begin, enter your desired mode of operation:");
        System.out.println();
        Classifier c = createModel(console);

        System.out.println();
        System.out.println("What would you like to do with your model?");
        int choice = -1;
        do {
            System.out.println();
            System.out.println("1) Test with an input file");
            System.out.println("2) Get testing accuracy");
            System.out.println("3) Save to a file");
            System.out.println("4) Visualize tree with .txt");
            System.out.println("5) Quit");
            System.out.print("Enter your choice here: ");

            choice = console.nextInt();
            while (choice != 1 && choice != 2 && choice != 3 && choice != 4 && choice != 5) {
                System.out.print("Please enter a valid option from above: ");
                choice = console.nextInt();
            }

            if (choice == 1) {
                System.out.print("Please enter the file you'd like to test: ");
                evalModel(c, console.next());
            } else if (choice == 2) {
                testModel(c, TEST_FILE);
            } else if (choice == 3) {
                System.out.print("Please enter the file name you'd like to save to: ");
                c.save(new PrintStream(console.next()));
            } else if (choice == 4) {
                System.out.println("What tree file do you want visualized? Enter the file path");
                System.out.print("> ");
                Scanner file = new Scanner(new File(console.next()));
                TreeVisualizer vis = new TreeVisualizer(file);
                vis.visualize();
            }
        } while (choice != 5);
    }

    // Creates a classifier from a client provided information by either:
    //      Loading a previously created model file or
    //      Training a model from a provided dataset
    // Requires a Scanner connected to the console to retrieve user input
    // Throws a FileNotFoundException
    //      If one of the client provided files doesn't exist
    private static Classifier createModel(Scanner console) throws FileNotFoundException {
        System.out.println("1) Train classification model");
        System.out.println("2) Load model from file");
        System.out.print("Enter your choice here: ");

        int choice = console.nextInt();
        while (choice != 1 && choice != 2) {
            System.out.print("Please enter a valid option from above: ");
            choice = console.nextInt();
        }

        if (choice == 1) {
            DataLoader loader = new DataLoader(TRAIN_FILE, LABEL_INDEX, CONVERTER);
            return new ClassificationTree(loader.getData(), loader.getLabels());
        } else {
            System.out.print("Please enter the path to the file you'd like to load: ");
            Scanner input = new Scanner(new File(console.next()));
            return new ClassificationTree(input);
        }
    }

    // Uses the given Classifier to predict labels for the datapoints within the given testing file,
    //      printing out the results
    // Throws a FileNotFoundException
    //      If the provided testing dataset file doesn't exist
    private static void evalModel(Classifier c, String fileName) throws FileNotFoundException {
        DataLoader loader = new DataLoader(fileName, LABEL_INDEX, CONVERTER);
        List<String> results = new ArrayList<>();
        for (Classifiable data : loader.getData()) {
            results.add(c.classify(data));
        }
        System.out.println("Results: " + results);
    }

    // Tests the given Classifier on the datapoints within the given testing file, printing out the
    //      accuracies for labels encountered during testing
    // Throws a FileNotFoundException
    //      If the provided testing dataset file doesn't exist
    private static void testModel(Classifier c, String fileName) throws FileNotFoundException {
        DataLoader loader = new DataLoader(fileName, LABEL_INDEX, CONVERTER);
        Map<String, Double> labelToAccuracy = c.calculateAccuracy(loader.getData(),
                                                                  loader.getLabels());
        for (String label : labelToAccuracy.keySet()) {
            System.out.println(label + ": " + labelToAccuracy.get(label));
        }
    }
}
