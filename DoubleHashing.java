/**
 * @author Tristin Watkins
 * CS321 Spring 2025
 * March 12 2025
 * //Help from TA on this file
 */
public class DoubleHashing extends Hashtable {

    public DoubleHashing(int tableSize) {
        super(tableSize);
    }

    public int getStepSize(int hashCode) {
        return 1 + positiveMod(hashCode, tableSize - 2); 
    }
        
}