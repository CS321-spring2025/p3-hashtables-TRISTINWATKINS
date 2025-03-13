// This file is not done yet, This is just the start, I still have debugging anf testing to run to get this file working, just wanted to start it.
/**
 * @author Tristin Watkins
 * CS321 Spring 2025
 * March 12 2025
 */


import java.io.*;
import java.util.*;

public class HashtableExperiment {

    public static void main(String[] args) {
        if (args.length < 2) {
            printUsage();
            return;
        }

        String dataSource = args[0];
        double loadFactor = Double.parseDouble(args[1]);
        int debugLevel = args.length > 2 ? Integer.parseInt(args[2]) : 0;

        List<HashObject> data = loadData(dataSource);

        int m = 10000; 
        int n = (int) Math.ceil(loadFactor * m);

        runExperiment(data, n, m, loadFactor, debugLevel);
    }

    private static void printUsage() {
        System.out.println("Usage: java HashtableExperiment <dataSource> <loadFactor> [<debugLevel>]");
        System.out.println("<dataSource>: 1 ==> random numbers");
        System.out.println("<dataSource>: 2 ==> date value as a long");
        System.out.println("<dataSource>: 3 ==> word list");
        System.out.println("<loadFactor>: The ratio of objects to table size, denoted by alpha = n/m");
        System.out.println("<debugLevel>: 0 ==> print summary of experiment");
        System.out.println("<debugLevel>: 1 ==> save hash tables to files");
        System.out.println("<debugLevel>: 2 ==> print detailed debug output");
    }

    private static List<HashObject> loadData(String dataSource) {
        List<HashObject> data = new ArrayList<>();
        switch (dataSource) {
            case "1":
                data = generateRandomNumbers();
                break;
            case "2":
                data = generateDateObjects();
                break;
            case "3":
                data = generateWordsFromFile();
                break;
            default:
                System.out.println("Invalid data source!");
                System.exit(1);
        }
        return data;
    }

    private static List<HashObject> generateRandomNumbers() {
        List<HashObject> data = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 10000; i++) {
            data.add(new HashObject(rand.nextInt()));
        }
        return data;
    }

    private static List<HashObject> generateDateObjects() {
        List<HashObject> data = new ArrayList<>();
        long current = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            data.add(new HashObject(new Date(current)));
            current += 1000;
        }
        return data;
    }

    private static List<HashObject> generateWordsFromFile() {
        List<HashObject> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("word-list.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(new HashObject(line));
            }
        } catch (IOException e) {
            System.out.println("Error reading word list file.");
            e.printStackTrace();
        }
        return data;
    }

    private static void runExperiment(List<HashObject> data, int n, int m, double loadFactor, int debugLevel) {
        LinearProbing linearTable = new LinearProbing(m);
        DoubleHashing doubleHashingTable = new DoubleHashing(m);

        int totalProbesLinear = 0;
        int totalProbesDouble = 0;
        int insertionsLinear = 0;
        int insertionsDouble = 0;

        for (HashObject obj : data) {
            if (insertionsLinear < n) {
                totalProbesLinear += linearTable.insert(obj);
                if (!linearTable.isDuplicate(obj)) insertionsLinear++;
            }
            if (insertionsDouble < n) {
                totalProbesDouble += doubleHashingTable.insert(obj);
                if (!doubleHashingTable.isDuplicate(obj)) insertionsDouble++;
            }
        }

        double avgProbesLinear = insertionsLinear == 0 ? 0 : (double) totalProbesLinear / insertionsLinear;
        double avgProbesDouble = insertionsDouble == 0 ? 0 : (double) totalProbesDouble / insertionsDouble;

        switch (debugLevel) {
            case 0:
                printSummary(avgProbesLinear, avgProbesDouble);
                break;
            case 1:
                printSummary(avgProbesLinear, avgProbesDouble);
                linearTable.dumpToFile("linear-dump.txt");
                doubleHashingTable.dumpToFile("double-dump.txt");
                break;
            case 2:
                linearTable.printDebug();
                doubleHashingTable.printDebug();
                break;
            default:
                printSummary(avgProbesLinear, avgProbesDouble);
                break;
        }
    }

    private static void printSummary(double avgProbesLinear, double avgProbesDouble) {
        System.out.println("Experiment Summary:");
        System.out.println("Linear Probing Average Probes: " + avgProbesLinear);
        System.out.println("Double Hashing Average Probes: " + avgProbesDouble);
    }

    protected int positiveMod(int dividend, int divisor) {
        int quotient = dividend % divisor;
        if (quotient < 0) {
            quotient += divisor;
        }
        return quotient;
    }
}
