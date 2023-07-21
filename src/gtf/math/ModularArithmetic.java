package gtf.math;

/**
 * Utility class for operations on integers mod n.
 * 
 * @author gtf
 * 
 */
public final class ModularArithmetic {

  private ModularArithmetic() {
  }

  /**
   * Return the remainder of integer division. The result will have the same
   * sign as the divisor b.
   * 
   * Necessitated by the sign conventions of the Java operator %, which doesn't
   * do the right thing for negative numbers.
   * 
   * @param a
   *          The dividend
   * @param b
   *          The divisor
   * @return the remainder, in the range 0..a. The result will have the same
   *         sign as the divisor b.
   * @throws ArithmeticException
   *           if b == 0
   */
  public static int remainder(int a, int b) {
    int result = a % b;
    if ((result < 0 && b > 0) || (result > 0 && b < 0)) {
      result += b;
    }
    return result;
  }

  /**
   * Take the exponent of a 32-bit integer to some power
   * modulo some modulus. Uses bit operations to avoid
   * overflow.
   * 
   * @param base the integer to exponentiate
   * @param power the power
   * @param modulus the modulus
   * @return (base^power)%modulus
   */
  public static int exp32(int base, int power, int modulus) {
    long result = 1;
    for (int i = 31; i >= 0; i--) {
      result = (result * result) % modulus;
      if ((power & (1 << i)) != 0) {
        result = (result * base) % modulus;
      }
    }
    return (int) result; // Will not truncate since modulus is an int
  }
}
