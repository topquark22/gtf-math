package gtf.math;

/**
 *  Integer math utility functions.
 *
 *@author     gtf
 */
public final class IMath {

  /**
   * Prevent instantiation.
   */
  private IMath() {}

  /**
   * Compute and return the signum of a long value.
   *
   *@param n  a long
   */
  public static int signum(long n) {
    return (n > 0 ? 1 : (n < 0 ? -1 : 0));
  }

  /**
   * Compute the Greatest Common Divisor of two long values
   * using Euclid's algorithm.
   *
   *@param  m  arg1
   *@param  n  arg2
   */
  public static long gcd(long m, long n) {
    m = Math.abs(m);
    n = Math.abs(n);
    if (m < n) {
      return gcd(n, m);
    }
    long r = m % n;
    if (r == 0) {
      return n;
    } else {
      return gcd(n, r);
    }
  }

  /**
   * Compute the Least Common Multiple of two long values.
   *
   *@param  m  arg1
   *@param  n  arg2
   *@return    The LCM
   */
  public static long lcm(long m, long n) {
    m = Math.abs(m);
    n = Math.abs(n);
    return (m / gcd(m, n)) * n;
  }
}
