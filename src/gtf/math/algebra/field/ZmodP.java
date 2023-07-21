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
   * Certainty for argument checking purposes only. If caller passes
   * an order that is not prime, there is a 1 in a million chance
   * that this won't be detected, which is extremely unlikely
   * to cause a problem, considering that it is only a sanity
   * check in the first place.
   */
  private static final int PRIME_CERTAINTY = 20;
  
  private final BigInteger modulus;
  
  public ZmodP(BigInteger order) {
    super(order);
    if (!order.isProbablePrime(PRIME_CERTAINTY)) {
      throw new IllegalArgumentException("order must be prime");
    }
    modulus = order;
  }
  
  public BigInteger inv(BigInteger arg) {
    return arg.modInverse(modulus);
  }

  public BigInteger mul(BigInteger arg1, BigInteger arg2) {
    // TODO efficient modular multiplication using bit shift
    return arg1.multiply(arg2).mod(modulus);
  }

  public BigInteger id() {
    return BigInteger.ONE;
  }

  public BigInteger zero() {
    return BigInteger.ZERO;
  }

  public BigInteger add(BigInteger arg1, BigInteger arg2) {
    return arg1.add(arg2).mod(modulus);
  }

  public BigInteger neg(BigInteger arg) {
    return arg.negate().mod(modulus);
  }
}
