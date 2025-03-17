/**
 * @author Tristin Watkins
 * CS321 Spring 2025
 * March 12 2025
 */
public class LinearProbing extends Hashtable {

    public LinearProbing(int tableSize) {
        super(tableSize);
    }

    @Override
    public void insert(HashObject obj) {
        int index = positiveMod(obj.getKey().hashCode(), tableSize);
        int probeCount = 0;

        while (table[index] != null) {
            probeCount++;
            if (table[index].getKey().equals(obj.getKey())) {
                table[index].incrementFrequency();
                return;
            }
            index = positiveMod(index + 1, tableSize);
        }

        table[index] = obj;
        table[index].setProbeCount(probeCount);
        numElements++;
    }

    @Override
    public HashObject search(Object key) {
        int index = positiveMod(key.hashCode(), tableSize);
        while (table[index] != null) {
            if (table[index].getKey().equals(key)) {
                return table[index];
            }
            index = positiveMod(index + 1, tableSize);
        }
        return null;
    }
}