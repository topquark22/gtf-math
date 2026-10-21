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

    T leadingCoefficient = denominator.leadingCoefficient();
    denominator = ring.monic(denominator);
    numerator = ring.scalarMultiply(
        ring.field().inv(leadingCoefficient),
        numerator);

    return new Fraction<Polynomial<T>>(numerator, denominator);
  }

  @Override
  public Fraction<Polynomial<T>> add(Fraction<Polynomial<T>> left,
                                     Fraction<Polynomial<T>> right) {

    Polynomial<T> numerator = ring.add(
        ring.mul(left.numerator(), right.denominator()),
        ring.mul(right.numerator(), left.denominator()));

    Polynomial<T> denominator = ring.mul(
        left.denominator(),
        right.denominator());

    return fraction(numerator, denominator);
  }

  @Override
  public Fraction<Polynomial<T>> mul(Fraction<Polynomial<T>> left,
                                     Fraction<Polynomial<T>> right) {

    return fraction(
        ring.mul(left.numerator(), right.numerator()),
        ring.mul(left.denominator(), right.denominator()));
  }

  @Override
  public Fraction<Polynomial<T>> neg(Fraction<Polynomial<T>> arg) {
    return fraction(
        ring.neg(arg.numerator()),
        arg.denominator());
  }

  @Override
  public Fraction<Polynomial<T>> inv(Fraction<Polynomial<T>> arg) {
    return fraction(arg.denominator(), arg.numerator());
  }
}
