package gtf.math.algebra.fraction;


/**
 * Immutable formal fraction numerator / denominator.
 *
 * <p>
 * Arithmetic is supplied by {@link FractionField}, which knows the
 * arithmetic of the underlying ring. This class deliberately does not
 * extend {@link Number}; numerator and denominator may be polynomials,
 * matrices, or other algebraic objects.
 * </p>
 *
 * @param <T> numerator and denominator type
 *
 * @author gtf
 */
public final class Fraction<T> {

  private final T numerator;
  private final T denominator;

  Fraction(T numerator, T denominator) {
    if (numerator == null) {
      throw new NullPointerException("numerator");
    }
    if (denominator == null) {
      throw new NullPointerException("denominator");
    }
    this.numerator = numerator;
    this.denominator = denominator;
  }

  public T numerator() {
    return numerator;
  }

  public T denominator() {
    return denominator;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Fraction<?>)) {
      return false;
    }
    Fraction<?> other = (Fraction<?>) obj;
    return numerator.equals(other.numerator)
        && denominator.equals(other.denominator);
  }

  @Override
  public int hashCode() {
    return numerator.hashCode() ^ denominator.hashCode();
  }

  @Override
  public String toString() {
    return "(" + numerator + ")/(" + denominator + ")";
  }
}
