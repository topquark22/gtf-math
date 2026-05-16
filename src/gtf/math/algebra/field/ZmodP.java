package gtf.math.algebra.field;

import java.math.BigInteger;

import gtf.math.algebra.Field;
import gtf.math.algebra.ring.ZmodN;


/**
 * The field of integers mod p.
 * 
 * @author gtf
 */
public class ZmodP extends ZmodN implements Field<BigInteger> {

  /**
   * Certainty for argument checking purposes only.
   */
  private static final int PRIME_CERTAINTY = 100;
  
  public ZmodP(BigInteger order) {
    super(order);
    if (!order.isProbablePrime(PRIME_CERTAINTY)) {
      throw new IllegalArgumentException("order must be prime");
    }
  }
  
  @Override
  public BigInteger inv(BigInteger arg) {
    BigInteger value = arg.mod(modulus());
    if (value.signum() == 0) {
      throw new ArithmeticException("zero has no multiplicative inverse");
    }
    return value.modInverse(modulus());
  }
}
