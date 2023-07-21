package gtf.math.algebra.group;

import java.math.BigInteger;

import gtf.math.algebra.Group;


/**
 * Cyclic group of order n, written multiplicatively.
 * Implemented additively using BigInteger.
 * 
 * @author gtf
 */
public final class CyclicGroup implements Group<BigInteger> {

  private final BigInteger modulus;
  
  public CyclicGroup(BigInteger order) {
    if (order.compareTo(BigInteger.ONE) < 0) {
      throw new IllegalArgumentException("order must be >= 1");
    }
    modulus = order;
  }
  
  public BigInteger inv(BigInteger arg) {
    return arg.negate().mod(modulus);
  }

  public BigInteger mul(BigInteger arg1, BigInteger arg2) {
    return arg1.add(arg2).mod(modulus);
  }

  public BigInteger id() {
    return BigInteger.ZERO;
  }
}
