package gtf.math.algebra.polynomial;


/**
 * Represents the storage backing for polynomial coefficients.
 *
 * @param <T> coefficient type
 *
 * @author gtf
 */
interface PolynomialStorageModel<T> {

  /**
   * Returns the coefficient of x^degree.
   *
   * @param degree the degree
   * @return the coefficient
   */
  T coefficient(int degree);

  /**
   * Returns the largest degree having a stored nonzero coefficient,
   * or -1 for the zero polynomial.
   *
   * @return the degree
   */
  int degree();

  /**
   * Returns the next degree after the given one having a stored
   * nonzero coefficient.
   *
   * @param degree the current degree
   * @return the next stored degree, or -1 if none exists
   */
  int nextDegree(int degree);

  /**
   * Returns an iterator over nonzero polynomial terms.
   *
   * @return the iterator
   */
  PolynomialTermIterator<T> iterator();
}
