/**
 * @author Tristin Watkins
 * CS321 Spring 2025
 * March 12 2025
 * //Help from TA on this file
 */
public class LinearProbing extends Hashtable {

    public LinearProbing(int tableSize) {
        super(tableSize);
    }

    public int getStepSize(int hashCode) {
        return 1;
    }
}