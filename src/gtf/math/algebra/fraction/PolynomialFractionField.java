package gtf.math.algebra.fraction;

import gtf.math.algebra.Field;
import gtf.math.algebra.polynomial.Polynomial;
import gtf.math.algebra.polynomial.PolynomialRing;


/**
 * Field of reduced rational functions over F[x].
 *
 * @param <T> coefficient type
 * @param <F> coefficient field type
 *
 * @author gtf
 */
public class PolynomialFractionField<T,
    F extends Field<T>>
    extends FractionField<Polynomial<T>, PolynomialRing<T, F>> {

  private final PolynomialRing<T, F> ring;

  public PolynomialFractionField(PolynomialRing<T, F> ring) {
    super(ring);
    this.ring = ring;
  }

  @Override
  public Fraction<Polynomial<T>> fraction(
      Polynomial<T> numerator,
      Polynomial<T> denominator) {

    if (denominator.isZero()) {
      throw new ArithmeticException("division by zero");
    }

    Polynomial<T> gcd = ring.gcd(numerator, denominator);

    numerator = ring.divide(numerator, gcd);
    denominator = ring.divide(denominator, gcd);

    denominator = ring.monic(denominator);

    numerator = ring.scalarMultiply(
        denominator.leadingCoefficient(),
        numerator);

    return new Fraction<Polynomial<T>>(numerator, denominator);
  }
}
