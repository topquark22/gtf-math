package gtf.math;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Computes Fibonacci numbers.
 * 
 * @author gtf
 */
public class Fibonacci {

  private static final int INIT_SIZE = 10000;

  private static Fibonacci instance = null;
  
  private final List<BigInteger> values; 
  
  /**
   * The index of the greatest valid value in the array
   */
  private int last;
  
  public static synchronized Fibonacci instance() {
    if (instance == null) {
      instance = new Fibonacci();
    }
    return instance;
  }
  
  private Fibonacci() {
    values = new ArrayList<BigInteger>(INIT_SIZE);
    values.add(BigInteger.ONE);
    values.add(BigInteger.ONE);
    last = 1;
  }
  
  public synchronized BigInteger get(int index) {
    if (index < 0) {
      return BigInteger.ZERO;
    } else if (index <= last) {
      // value already computed
      return values.get(index);
    } else {
      BigInteger next = get(index - 2).add(get(index - 1));
      values.add(next);
      last++;
      return next;
    }
  }
  
  /**
   * the G function
   * 
   * @param i
   * @return
   */
  public BigInteger G(int i) {
    return get(i - 1).gcd(get(i).subtract(BigInteger.ONE));
  }
  
  /**
   * Test my Fibonacci conjecture
   * 
   * @author gtf
   */
  public static class Test {

    private static final int SIZE = 10000;

    public static final int INTERVAL = 100;

    public static final int CERTAINTY = 5;

    public static void main(String[] args) {
      Fibonacci f = instance();
      for (int i = 2; i <= SIZE; i += 2) {
        BigInteger i2 = BigInteger.valueOf(i/2);
        if (i % INTERVAL == 0) {
          System.out.println("i=" + i);
        }
        //System.out.println("f(i)=" + f.get(i) + ", i/2=" + (i/2) + ": G(i)=" + f.G(i));
        // faster to test i2 first (it's smaller)
        if (i >= 10 && !i2.isProbablePrime(CERTAINTY)) {
          BigInteger g = f.G(i);
          // At this point (if conjecture holds) g is composite.
          // To do: look at the factors of g and of i/2.
          // Maybe it's something obvious, like i/2 divides g.
          if (g.isProbablePrime(CERTAINTY)) {
            // In order to have a counterexample, g must be prime and i2 must be composite.
            // A false positive can happen if:
            // *  g is composite but reported prime, and i2 is composite
            // A false negative can happen if:
            // *  g is prime, and i2 is composite but reported prime
            System.out.println("Conjecture fails at i=" + i + " if " + g + " is actually prime!");
          }
        }
      }
    }
  }
}
