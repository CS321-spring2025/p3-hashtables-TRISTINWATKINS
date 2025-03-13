/**
 * @author Tristin Watkins
 * CS321 Spring 2025
 * March 12 2025
 */

public class DoubleHashing extends Hashtable {

    public DoubleHashing(int tableSize) {
        super(tableSize);
    }

    @Override
    public void insert(HashObject obj) {
        int index = positiveMod(obj.getKey().hashCode(), tableSize);
        int probeCount = 0;
        int stepSize = 1 + positiveMod(obj.getKey().hashCode(), tableSize - 2);

        while (table[index] != null) {
            probeCount++;
            if (table[index].getKey().equals(obj.getKey())) {
                table[index].incrementFrequency(); 
                return;
            }
            index = positiveMod(index + stepSize, tableSize); 
        }

        table[index] = obj;
        table[index].setProbeCount(probeCount);
        numElements++;
    }

    @Override
    public HashObject search(Object key) {
        int index = positiveMod(key.hashCode(), tableSize);
        int stepSize = 1 + positiveMod(key.hashCode(), tableSize - 2);

        while (table[index] != null) {
            if (table[index].getKey().equals(key)) {
                return table[index];
            }
            index = positiveMod(index + stepSize, tableSize); 
        }
        return null;
    }

    public boolean isDuplicate(HashObject obj) {
        int index = positiveMod(obj.hashCode(), table.length);
        int step = 1 + positiveMod(obj.hashCode(), table.length - 2);

        while (table[index] != null) {
            if (table[index].equals(obj)) {
                return true;  
            }
            index = positiveMod(index + step, table.length);
        }
        return false;  
    }

    public void printDebug() {
        System.out.println("Debugging Double Hashing Table...");
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                System.out.println("Index " + i + ": " + table[i].toString());
            }
        }
    }
}
