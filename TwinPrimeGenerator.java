/**
 * @author Tristin Watkins
 * CS321 Spring 2025
 * March 12 2025
 */
public class TwinPrimeGenerator {
    public static int generateTwinPrime(int min, int max) {
        for (int i = min; i <= max; i++) {
            if (isPrime(i) && isPrime(i - 2)) {
                return i;
            }
        }
        return -1; 
    }
    private static boolean isPrime(int num) {
        if (num <= 1) return false;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }
}
