package gtf.math.algebra.ring;

import java.math.BigInteger;

import gtf.math.algebra.Ring;


/**
 * The ring of integers.
 * 
 * @author gtf
 */
public class Z implements Ring<BigInteger> {

  public BigInteger mul(BigInteger arg1, BigInteger arg2) {
    return arg1.multiply(arg2);
  }

  public BigInteger id() {
    return BigInteger.ONE;
  }

  public BigInteger zero() {
    return BigInteger.ZERO;
  }

  public BigInteger add(BigInteger arg1, BigInteger arg2) {
    return arg1.add(arg2);
  }

  public BigInteger neg(BigInteger arg) {
    return arg.negate();
  }

  /**
   * This ring is a module over itself.
   */
  //@Override
  public Ring<BigInteger> ring() {
    return this;
  }
}
