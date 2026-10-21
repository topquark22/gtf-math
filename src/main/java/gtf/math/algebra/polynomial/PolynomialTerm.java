package gtf.math.algebra.polynomial;


/**
 * A nonzero term of a polynomial.
 *
 * @param <T> coefficient type
 *
 * @author gtf
 */
public final class PolynomialTerm<T> {

  private final int degree;
  private final T coefficient;

  public PolynomialTerm(int degree, T coefficient) {
    if (degree < 0) {
      throw new IllegalArgumentException("degree must be non-negative");
    }
    if (coefficient == null) {
      throw new NullPointerException("coefficient");
    }
    this.degree = degree;
    this.coefficient = coefficient;
  }

  public int degree() {
    return degree;
  }

  public T coefficient() {
    return coefficient;
  }
}
