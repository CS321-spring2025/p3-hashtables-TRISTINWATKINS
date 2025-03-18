/**
 * @author Tristin Watkins
 * CS321 Spring 2025
 * March 12 2025
 */
public class TwinPrimeGenerator {

    public static int generateTwinPrime(int min, int max) {
        for (int i = min; i <= max - 2; i++) {
            if (isPrime(i) && isPrime(i + 2)) {
                return i + 2;  
            }
        }
        return -1;  
    }
    

    private static boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        int twinPrime = generateTwinPrime(95500, 96000);
        System.out.println("Twin Prime: " + twinPrime);
    }
}