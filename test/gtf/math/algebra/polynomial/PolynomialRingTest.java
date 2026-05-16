package gtf.math.algebra.polynomial;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import gtf.math.BigFraction;
import gtf.math.algebra.field.Q;


/**
 * @author gtf
 */
public class PolynomialRingTest {

  private static final Q FIELD = new Q();
  private static final PolynomialRing<BigFraction, Q> RING =
      new PolynomialRing<BigFraction, Q>(FIELD);

  @Test
  public void testAdd() {
    Polynomial<BigFraction> left = polynomial(1, 2, 3);
    Polynomial<BigFraction> right = polynomial(5, 7);

    assertEquals(polynomial(6, 9, 3),
        RING.add(left, right));
  }

  @Test
  public void testMultiply() {
    Polynomial<BigFraction> left = polynomial(1, 1);
    Polynomial<BigFraction> right = polynomial(1, -1);

    assertEquals(polynomial(1, 0, -1),
        RING.mul(left, right));
  }

  @Test
  public void testDivideAndRemainder() {
    Polynomial<BigFraction> numerator =
        polynomial(-1, 0, 1);

    Polynomial<BigFraction> denominator =
        polynomial(-1, 1);

    Polynomial<BigFraction>[] result =
        RING.divideAndRemainder(numerator, denominator);

    assertEquals(polynomial(1, 1), result[0]);
    assertEquals(RING.zero(), result[1]);
  }

  private Polynomial<BigFraction> polynomial(long... coefficients) {
    BigFraction[] values = new BigFraction[coefficients.length];
    for (int i = 0; i < coefficients.length; i++) {
      values[i] = new BigFraction(coefficients[i]);
    }
    return RING.polynomial(values);
  }
}
