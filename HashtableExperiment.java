/**
 * @author Tristin Watkins
 * CS321 Spring 2025
 * March 12 2025
 * //This file was written with help of Java online book, as well as AI use, However after AI use was given it was rewrote in my own style.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Random;

public class HashtableExperiment {

    public static void main(String[] args) throws IOException {
        processArgumentsAndRun(args);
    }

    private static void processArgumentsAndRun(String[] args) throws IOException {
        if (args.length < 2 || args.length > 3) {
            printUsage();
            System.exit(1);
        }

        int dataSource = Integer.parseInt(args[0]);
        double loadFactor = Double.parseDouble(args[1]);
        int debugLevel = (args.length == 3) ? Integer.parseInt(args[2]) : 0;

        runExperiment(dataSource, loadFactor, debugLevel);
    }

    private static void printUsage() {
        System.out.println("Usage: java HashtableExperiment <dataSource> <loadFactor> [<debugLevel>]");
        System.out.println("<dataSource>: 1 ==> random numbers, 2 ==> date values, 3 ==> word list");
        System.out.println("<loadFactor>: The ratio of objects to table size (alpha = n/m)");
        System.out.println("<debugLevel>: 0 ==> summary, 1 ==> save tables, 2 ==> debug output");
    }

    private static void runExperiment(int dataSource, double loadFactor, int debugLevel) throws IOException {
        int tableSize = TwinPrimeGenerator.generateTwinPrime(95500, 96000);
        System.out.println("HashtableExperiment: Found a twin prime table capacity: " + tableSize);

        int numElements = (int) Math.ceil(loadFactor * tableSize);
        HashObject[] data = generateData(dataSource, numElements);

        System.out.println("HashtableExperiment: Input: " + getDataSourceName(dataSource) + "  Loadfactor: " + String.format("%.2f", loadFactor));

        runHashingAndReport(new LinearProbing(tableSize), data, debugLevel, "Linear Probing", "linear-dump.txt", false);
        runHashingAndReport(new DoubleHashing(tableSize), data, debugLevel, "Double Hashing", "double-dump.txt", true);
    }

    private static HashObject[] generateData(int dataSource, int numElements) throws IOException {
        HashObject[] data = new HashObject[numElements];
        Random random = new Random();

        switch (dataSource) {
            case 1:
                for (int i = 0; i < numElements; i++) {
                    data[i] = new HashObject(random.nextInt());
                }
                break;
            case 2:
                long currentTime = new Date().getTime();
                for (int i = 0; i < numElements; i++) {
                    data[i] = new HashObject(new Date(currentTime));
                    currentTime += 1000;
                }
                break;
            case 3:
                try (BufferedReader reader = new BufferedReader(new FileReader("word-list.txt"))) {
                    String line;
                    int count = 0;
                    while ((line = reader.readLine()) != null && count < numElements) {
                        data[count++] = new HashObject(line);
                    }
                }
                break;
        }
        return data;
    }

    private static void runHashingAndReport(Hashtable table, HashObject[] data, int debugLevel, String probingType, String dumpFileName, boolean isDoubleHashing) throws IOException {
        System.out.println("\n        Using " + probingType);
    
        int duplicates = 0;
        long totalProbes = 0;
    
        for (HashObject hashObject : data) {
            int currentProbes = 0;
            int index = table.positiveMod(hashObject.getKey().hashCode(), table.tableSize);
    
            if (isDoubleHashing) {
                int stepSize = 1 + table.positiveMod(hashObject.getKey().hashCode(), table.tableSize - 2);
                while (table.table[index] != null && !table.table[index].getKey().equals(hashObject.getKey())) {
                    currentProbes++;
                    index = table.positiveMod(index + stepSize, table.tableSize);
                }
            } else {
                while (table.table[index] != null && !table.table[index].getKey().equals(hashObject.getKey())) {
                    currentProbes++;
                    index = table.positiveMod(index + 1, table.tableSize);
                }
            }
    
            if (table.search(hashObject.getKey()) != null) {
                duplicates++;  
                table.search(hashObject.getKey()).incrementFrequency(); 
            } else {
                table.insert(hashObject);  
                totalProbes += currentProbes;  
            }
    
            if (debugLevel == 2) {
                if (table.search(hashObject.getKey()).getFrequency() > 1) {
                    System.out.println("Duplicate key: " + hashObject.getKey().toString());
                } else {
                    System.out.println("Inserted key: " + hashObject.getKey().toString());
                }
            }
        }
    
        System.out.println("HashtableExperiment: size of hash table is " + data.length);
        System.out.println("        Inserted " + table.numElements + " elements, of which " + duplicates + " were duplicates");
        if ((data.length - duplicates) > 0) {
            System.out.println("        Avg. no. of probes = " + String.format("%.2f", (double) totalProbes / (data.length - duplicates)));
        } else {
            System.out.println("        Avg. no. of probes = NaN");
        }
    
        if (debugLevel >= 1) {
            dumpTableToFile(table, dumpFileName);
            System.out.println("HashtableExperiment: Saved dump of hash table");
        }
    }
    

    private static void dumpTableToFile(Hashtable table, String fileName) throws IOException {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            for (int i = 0; i < table.tableSize; i++) {
                if (table.table[i] != null) {
                    writer.println("table[" + i + "]: " + table.table[i].toString());
                }
            }
        }
    }

    private static String getDataSourceName(int dataSource) {
        switch (dataSource) {
            case 1:
                return "Random Numbers";
            case 2:
                return "Date Values";
            case 3:
                return "Word-List";
            default:
                return "Unknown";
        }
    }
}
