package gtf.math.algebra.fraction;

import gtf.math.algebra.Field;
import gtf.math.algebra.Ring;


/**
 * Field of formal fractions over a ring.
 *
 * <p>
 * This supports quotient constructions such as rational functions
 * over polynomial rings.
 * </p>
 *
 * @param <T> numerator and denominator type
 * @param <R> ring type
 *
 * @author gtf
 */
public class FractionField<T, R extends Ring<T>>
    implements Field<Fraction<T>> {

  private final R ring;

  public FractionField(R ring) {
    if (ring == null) {
      throw new NullPointerException("ring");
    }
    this.ring = ring;
  }

  public Fraction<T> fraction(T numerator, T denominator) {
    if (denominator.equals(ring.zero())) {
      throw new ArithmeticException("division by zero");
    }
    return new Fraction<T>(numerator, denominator);
  }

  @Override
  public Fraction<T> add(Fraction<T> left,
                         Fraction<T> right) {

    T numerator = ring.add(
        ring.mul(left.numerator(), right.denominator()),
        ring.mul(right.numerator(), left.denominator()));

    T denominator = ring.mul(
        left.denominator(),
        right.denominator());

    return fraction(numerator, denominator);
  }

  @Override
  public Fraction<T> neg(Fraction<T> arg) {
    return fraction(
        ring.neg(arg.numerator()),
        arg.denominator());
  }

  @Override
  public Fraction<T> mul(Fraction<T> left,
                         Fraction<T> right) {

    return fraction(
        ring.mul(left.numerator(), right.numerator()),
        ring.mul(left.denominator(), right.denominator()));
  }

  @Override
  public Fraction<T> inv(Fraction<T> arg) {
    return fraction(arg.denominator(), arg.numerator());
  }

  @Override
  public Fraction<T> zero() {
    return fraction(ring.zero(), ring.id());
  }

  @Override
  public Fraction<T> id() {
    return fraction(ring.id(), ring.id());
  }

  @Override
  public Ring<Fraction<T>> ring() {
    return this;
  }
}
