package gtf.math.algebra.field;

import java.math.BigInteger;

import gtf.math.MillerRabin32;
import gtf.math.algebra.Field;
import gtf.math.algebra.ring.ZmodN;


/**
 * The field of integers mod p.
 * 
 * @author gtf
 */
public class ZmodP extends ZmodN implements Field<BigInteger> {

  /**
   * Certainty for argument checking purposes only. For moduli too large
   * for the deterministic 32-bit Miller-Rabin test, this is used as a
   * fallback sanity check.
   */
  private static final int PRIME_CERTAINTY = 20;
  
  private final BigInteger modulus;
  
  public ZmodP(BigInteger order) {
    super(order);
    if (!isPrime(order)) {
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

  private static boolean isPrime(BigInteger n) {
    if (n.bitLength() < Integer.SIZE) {
      return MillerRabin32.miller_rabin_32(n.intValue());
    }
    return n.isProbablePrime(PRIME_CERTAINTY);
  }
}
