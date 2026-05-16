package gtf.math.algebra.fraction;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import gtf.math.BigFraction;
import gtf.math.algebra.field.Q;
import gtf.math.algebra.polynomial.Polynomial;
import gtf.math.algebra.polynomial.PolynomialRing;


/**
 * Tests formal fractions of polynomials over Q.
 *
 * @author gtf
 */
public class PolynomialFractionFieldTest {

  private static final Q Q_FIELD = new Q();

  private static final PolynomialRing<BigFraction, Q> QX =
      new PolynomialRing<BigFraction, Q>(Q_FIELD);

  private static final FractionField<Polynomial<BigFraction>,
      PolynomialRing<BigFraction, Q>> QX_FRACTIONS =
          new FractionField<Polynomial<BigFraction>,
              PolynomialRing<BigFraction, Q>>(QX);

  @Test
  public void testAddPolynomialFractions() {
    Fraction<Polynomial<BigFraction>> left =
        fraction(poly(1), poly(1, 1));

    Fraction<Polynomial<BigFraction>> right =
        fraction(poly(1), poly(1, -1));

    Fraction<Polynomial<BigFraction>> expected =
        fraction(poly(2), poly(1, 0, -1));

    assertEquals(expected, QX_FRACTIONS.add(left, right));
  }

  @Test
  public void testMultiplyPolynomialFractions() {
    Fraction<Polynomial<BigFraction>> left =
        fraction(poly(1, 1), poly(1, -1));

    Fraction<Polynomial<BigFraction>> right =
        fraction(poly(1, -1), poly(1, 0, 1));

    Fraction<Polynomial<BigFraction>> expected =
        fraction(poly(1, 0, -1), poly(1, 0, 0, 0, -1));

    assertEquals(expected, QX_FRACTIONS.mul(left, right));
  }

  @Test
  public void testInversePolynomialFraction() {
    Fraction<Polynomial<BigFraction>> value =
        fraction(poly(1, 1), poly(1, -1));

    Fraction<Polynomial<BigFraction>> expected =
        fraction(poly(1, -1), poly(1, 1));

    assertEquals(expected, QX_FRACTIONS.inv(value));
  }

  @Test(expected = ArithmeticException.class)
  public void testRejectsZeroDenominator() {
    fraction(poly(1), QX.zero());
  }

  private Fraction<Polynomial<BigFraction>> fraction(
      Polynomial<BigFraction> numerator,
      Polynomial<BigFraction> denominator) {

    return QX_FRACTIONS.fraction(numerator, denominator);
  }

  private Polynomial<BigFraction> poly(long... coefficients) {
    BigFraction[] values = new BigFraction[coefficients.length];
    for (int i = 0; i < coefficients.length; i++) {
      values[i] = new BigFraction(coefficients[i]);
    }
    return QX.polynomial(values);
  }
}
