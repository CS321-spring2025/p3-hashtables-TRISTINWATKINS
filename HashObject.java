/**
 * @author Tristin Watkins
 * CS321 Spring 2025
 * March 12 2025
 */
public class HashObject {
    private Object key;
    private int frequency;
    private int probeCount;

    public HashObject(Object key) {
        this.key = key;
        this.frequency = 1;
        this.probeCount = 0;
    }

    public Object getKey() {
        return key;
    }

    public int getFrequency() {
        return frequency;
    }

    public void incrementFrequency() {
        frequency++;
    }

    public void setProbeCount(int probeCount) {
        this.probeCount = probeCount;
    }

    public int getProbeCount() {
        return probeCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        HashObject other = (HashObject) obj;
        return key.equals(other.key);
    }

    @Override
    public String toString() {
        return key.toString() + " " + frequency + " " + probeCount;
    }
}
