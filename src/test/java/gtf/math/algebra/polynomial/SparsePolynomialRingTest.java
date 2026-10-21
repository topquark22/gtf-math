package gtf.math.algebra.polynomial;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import gtf.math.BigFraction;
import gtf.math.algebra.field.Q;


/**
 * @author gtf
 */
public class SparsePolynomialRingTest {

  private static final Q FIELD = new Q();
  private static final PolynomialRing<BigFraction, Q> RING =
      new PolynomialRing<BigFraction, Q>(
          FIELD,
          new SparsePolynomialStorageFactory<BigFraction>());

  @Test
  public void testSparseAdd() {
    Polynomial<BigFraction> left = polynomial(0, 0, 3, 0, 0, 7);
    Polynomial<BigFraction> right = polynomial(0, 5, 0, 0, -2);

    assertEquals(polynomial(0, 5, 3, 0, -2, 7),
        RING.add(left, right));
  }

  @Test
  public void testSparseMultiplyWithLargeGap() {
    Polynomial<BigFraction> left = polynomial(0, 0, 1);
    Polynomial<BigFraction> right = polynomial(0, 0, 0, 0, 0, 1);

    assertEquals(polynomial(0, 0, 0, 0, 0, 0, 0, 1),
        RING.mul(left, right));
  }

  @Test
  public void testSparseDivideAndRemainder() {
    Polynomial<BigFraction> numerator =
        polynomial(-1, 0, 0, 0, 1);

    Polynomial<BigFraction> denominator =
        polynomial(-1, 0, 1);

    Polynomial<BigFraction>[] result =
        RING.divideAndRemainder(numerator, denominator);

    assertEquals(polynomial(1, 0, 1), result[0]);
    assertEquals(RING.zero(), result[1]);
  }

  @Test
  public void testSparseTrailingZerosAreIgnored() {
    assertEquals(polynomial(1, 0, 2),
        polynomial(1, 0, 2, 0, 0, 0));
  }

  private Polynomial<BigFraction> polynomial(long... coefficients) {
    BigFraction[] values = new BigFraction[coefficients.length];
    for (int i = 0; i < coefficients.length; i++) {
      values[i] = new BigFraction(coefficients[i]);
    }
    return RING.polynomial(values);
  }
}
