import java.util.Date;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HashtableExperiment {

    public static void main(String[] args) {
        if (args.length < 2 || args.length > 3) {
            System.out.println("Usage: java HashtableExperiment <dataSource> <loadFactor> [<debugLevel>]");
            System.exit(1);
        }

        int dataSource = Integer.parseInt(args[0]);
        double loadFactor = Double.parseDouble(args[1]);
        int debugLevel = (args.length == 3) ? Integer.parseInt(args[2]) : 0;

        int tableSize = TwinPrimeGenerator.generateTwinPrime(95500, 96000);
        System.out.println("HashtableExperiment: Found a twin prime table capacity: " + tableSize);

        int numElements = (int) Math.ceil(loadFactor * tableSize);

        System.out.println("HashtableExperiment: Input: " + getDataSourceName(dataSource) + "  Loadfactor: " + loadFactor);

        runExperiment(dataSource, numElements, tableSize, debugLevel);
    }

    private static void runExperiment(int dataSource, int numElements, int tableSize, int debugLevel) {
        Random random = new Random();
        HashObject[] data = new HashObject[numElements];

        // Generate data
        if (dataSource == 1) { // Random numbers
            for (int i = 0; i < numElements; i++) {
                data[i] = new HashObject(random.nextInt());
            }
        } else if (dataSource == 2) { // Date values
            long current = new Date().getTime();
            for (int i = 0; i < numElements; i++) {
                data[i] = new HashObject(new Date(current));
                current += 1000;
            }
        } else if (dataSource == 3) { // Word list
            try (BufferedReader reader = new BufferedReader(new FileReader("word-list.txt"))) {
                String line;
                int count = 0;
                while ((line = reader.readLine()) != null && count < numElements) {
                    data[count++] = new HashObject(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        // Run Linear Probing
        System.out.println("\n        Using Linear Probing");
        LinearProbing linearTable = new LinearProbing(tableSize);
        runHashing(linearTable, data, debugLevel);
        if (debugLevel >= 1){
            linearTable.dumpToFile("linear-dump.txt");
            System.out.println("HashtableExperiment: Saved dump of hash table");
        }

        // Run Double Hashing
        System.out.println("\n        Using Double Hashing");
        DoubleHashing doubleTable = new DoubleHashing(tableSize);
        runHashing(doubleTable, data, debugLevel);
        if (debugLevel >= 1){
            doubleTable.dumpToFile("double-dump.txt");
            System.out.println("HashtableExperiment: Saved dump of hash table");
        }
    }

    private static void runHashing(Hashtable table, HashObject[] data, int debugLevel) {
        int duplicates = 0;
        long totalProbes = 0;

        for (HashObject obj : data) {
            int initialSize = table.numElements;
            table.insert(obj);
            if(initialSize == table.numElements){
                duplicates++;
            }else{
                totalProbes += obj.getProbeCount();
            }

            if (debugLevel == 2) {
                if (table.search(obj.getKey()).getFrequency() > 1) {
                    System.out.println("Duplicate key: " + obj.getKey().toString());
                } else {
                    System.out.println("Inserted key: " + obj.getKey().toString());
                }
            }
        }

        table.printSummary();
        System.out.println("        Inserted " + data.length + " elements, of which " + duplicates + " were duplicates");
        System.out.println("        Avg. no. of probes = " + String.format("%.2f", (double) totalProbes / (data.length - duplicates)));
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