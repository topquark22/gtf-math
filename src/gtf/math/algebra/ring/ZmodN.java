package gtf.math.algebra.ring;

import java.math.BigInteger;

import gtf.math.algebra.Ring;


/**
 * The ring of integers mod n.
 * 
 * @author gtf
 */
public class ZmodN implements Ring<BigInteger> {

  private final BigInteger modulus;
  
  public ZmodN(BigInteger order) {
    if (order.compareTo(BigInteger.ONE) < 0) {
      throw new IllegalArgumentException("order must be >= 1");
    }
    modulus = order;
  }

  //@Override
  public BigInteger mul(BigInteger arg1, BigInteger arg2) {
    // TODO efficient modular multiplication using bit shift
    return arg1.multiply(arg2).mod(modulus);
  }

  //@Override
  public BigInteger id() {
    return BigInteger.ONE;
  }

  //@Override
  public BigInteger zero() {
    return BigInteger.ZERO;
  }
  
  //@Override
  public BigInteger add(BigInteger arg1, BigInteger arg2) {
    return arg1.add(arg2).mod(modulus);
  }

  //@Override
  public BigInteger neg(BigInteger arg) {
    return arg.negate().mod(modulus);
  }

  /*
   * (non-Javadoc)
   * @see gtf.math.algebra.Module#ring()
   */
  //@Override
  public Ring<BigInteger> ring() {
    return this;
  }
}
