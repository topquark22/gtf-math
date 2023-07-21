package gtf.math.algebra.field;

import gtf.math.algebra.Field;
import gtf.math.algebra.Ring;
import gtf.math.BigFraction;


/**
 * Ring of rational numbers.
 * 
 * @author gtf
 */
public class Q implements Field<BigFraction> {

  //@Override
  public BigFraction mul(BigFraction arg1, BigFraction arg2) {
    return arg1.multiply(arg2);
  }

  //@Override
  public BigFraction id() {
    return BigFraction.ONE;
  }

  //@Override
  public BigFraction zero() {
    return BigFraction.ZERO;
  }

  //@Override
  public BigFraction add(BigFraction arg1, BigFraction arg2) {
    return arg1.add(arg2);
  }

  //@Override
  public BigFraction neg(BigFraction arg) {
    return arg.negate();
  }
  
  //@Override
  public BigFraction inv(BigFraction arg) {
    return arg.reciprocal();
  }

  //@Override
  public Ring<BigFraction> ring() {
    return this;
  }
}
