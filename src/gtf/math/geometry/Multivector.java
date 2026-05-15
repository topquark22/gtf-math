package gtf.math.geometry;

import gtf.math.algebra.Field;


/**
 * A multivector in a finite-dimensional Clifford algebra.
 *
 * <p>
 * Basis blades are indexed by bit mask. For example, in dimension 3:
 * </p>
 *
 * <pre>
 * 0  scalar
 * 1  e0
 * 2  e1
 * 3  e0 ^ e1
 * 4  e2
 * 5  e0 ^ e2
 * 6  e1 ^ e2
 * 7  e0 ^ e1 ^ e2
 * </pre>
 *
 * @author gtf
 *
 * @param <S> the type of the scalar-field elements
 * @param <F> the scalar field
 */
public interface Multivector<S, F extends Field<S>> {

  /**
   * @return the scalar field for this multivector
   */
  F field();

  /**
   * @return the dimension of the underlying vector space
   */
  int dimension();

  /**
   * @return the number of basis blades, equal to 2^dimension()
   */
  default int bladeCount() {
    return 1 << dimension();
  }

  /**
   * Returns the coefficient of one basis blade.
   *
   * @param blade the blade bit mask
   * @return the coefficient
   */
  S coefficient(int blade);

  /**
   * Sets the coefficient of one basis blade.
   *
   * @param blade the blade bit mask
   * @param value the coefficient value
   */
  void setCoefficient(int blade, S value);

  /**
   * @return a copy of this multivector
   */
  Multivector<S, F> copy();
}
