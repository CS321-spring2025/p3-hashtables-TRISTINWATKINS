/**
 * @author Tristin Watkins
 * CS321 Spring 2025
 * March 12 2025
 */


public abstract class Hashtable {
    protected HashObject[] table;
    protected int tableSize;
    protected int numElements;

    public Hashtable(int tableSize) {
        this.tableSize = tableSize;
        this.table = new HashObject[tableSize];
        this.numElements = 0;
    }

    public abstract void insert(HashObject obj);

    public abstract HashObject search(Object key);

    protected int positiveMod(int dividend, int divisor) {
        int quotient = dividend % divisor;
        if (quotient < 0) quotient += divisor;
        return quotient;
    }

    public double getLoadFactor() {
        return (double) numElements / tableSize;
    }

    public void printSummary() {
        System.out.println("Size of the hash table: " + tableSize);
        System.out.println("Number of elements inserted: " + numElements);
    }

    public void dumpToFile(String fileName) {
        try (java.io.PrintWriter out = new java.io.PrintWriter(fileName)) {
            for (int i = 0; i < table.length; i++) {
                if (table[i] != null) {
                    out.println("table[" + i + "]: " + table[i].toString());
                }
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
