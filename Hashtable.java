import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * @author Tristin Watkins
 * CS321 Spring 2025
 * March 12 2025
 * //Help from TA on this file
 */


 public abstract class Hashtable {
    protected HashObject[] table;
    protected int tableSize = 0;
    protected int numElements;

    public Hashtable(int tableSize) {
        this.tableSize = tableSize;
        this.table = new HashObject[tableSize];
        this.numElements = 0;
    }

    public void insert(HashObject obj) {
        int index = positiveMod(obj.getKey().hashCode(), tableSize);
        int stepSize = getStepSize(obj.getKey().hashCode());
    
        while (table[index] != null) {
            obj.incrementProbCount();
            if (table[index].getKey().equals(obj.getKey())) {
                table[index].incrementFrequency();
                return; 
            }
            index = positiveMod(index + stepSize, tableSize);
        }
    
        table[index] = obj;
        numElements++;
    }
    
    
    // public void insert(HashObject obj) {
    //     int index = positiveMod(obj.getKey().hashCode(), tableSize);
    //     int stepSize = getStepSize(obj.getKey().hashCode());
        

    //     while (table[index] != null) {
    //         obj.incrementProbCount();
    //         if (table[index].getKey().equals(obj.getKey())) {
    //             table[index].incrementFrequency();
    //             return;
    //         }
    //         if (table[index] == null){
    //             table[index] = obj;
    //             numElements++;
    //             return;
    //         }
    //         index = positiveMod(index + stepSize, tableSize);
    //     }
    // }

    public abstract int getStepSize(int hashCode);

    

    public HashObject search(Object key) {
        int index = positiveMod(key.hashCode(), tableSize);
        int stepSize = getStepSize(key.hashCode());
        while (table[index] != null) {
            if (table[index].getKey().equals(key)) {
                return table[index];
            }
            index = positiveMod(index + stepSize, tableSize);
        }
        return null;
    }

    protected int positiveMod(int dividend, int divisor) {
        int quotient = dividend % divisor;
        if (quotient < 0) {
            quotient += divisor;
        }
        return quotient;
    }

    public void printSummary() {
        System.out.println("Hashtable: size of hash table is " + numElements);
    }

    public void dumpToFile(String fileName) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(fileName);
            for (int i = 0; i < table.length; i++) {
                if (table[i] != null) {
                    out.println("table[" + i + "]: " + table[i].toString());
                }
            }
            out.close();
        }
        
    }
