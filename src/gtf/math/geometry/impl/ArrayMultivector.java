package gtf.math.geometry.impl;

import gtf.math.algebra.Field;
import gtf.math.geometry.Multivector;


/**
 * Array-backed implementation of a multivector.
 *
 * <p>
 * Basis blades are indexed by bit mask. The array length is therefore
 * {@code 2^dimension}.
 * </p>
 *
 * @author gtf
 *
 * @param <S> the type of the scalar-field elements
 * @param <F> the scalar field
 */
public class ArrayMultivector<S, F extends Field<S>> implements Multivector<S, F> {

  private final F field;
  private final int dimension;
  private final Object[] coefficients;

  public ArrayMultivector(F field, int dimension) {
    if (field == null) {
      throw new NullPointerException("field");
    }
    if (dimension < 0) {
      throw new IllegalArgumentException("dimension must be non-negative");
    }
    if (dimension >= Integer.SIZE - 1) {
      throw new IllegalArgumentException("dimension too large");
    }
    this.field = field;
    this.dimension = dimension;
    this.coefficients = new Object[1 << dimension];
    for (int i = 0; i < coefficients.length; i++) {
      coefficients[i] = field.zero();
    }
  }

  public ArrayMultivector(Multivector<S, F> source) {
    if (source == null) {
      throw new NullPointerException("source");
    }
    this.field = source.field();
    this.dimension = source.dimension();
    this.coefficients = new Object[source.bladeCount()];
    for (int i = 0; i < coefficients.length; i++) {
      coefficients[i] = source.coefficient(i);
    }
  }

  @Override
  public F field() {
    return field;
  }

  @Override
  public int dimension() {
    return dimension;
  }

  @Override
  public S coefficient(int blade) {
    validateBlade(blade);
    @SuppressWarnings("unchecked")
    S result = (S) coefficients[blade];
    return result;
  }

  @Override
  public void setCoefficient(int blade, S value) {
    validateBlade(blade);
    coefficients[blade] = value;
  }

  @Override
  public Multivector<S, F> copy() {
    return new ArrayMultivector<S, F>(this);
  }

  private void validateBlade(int blade) {
    if (blade < 0 || blade >= coefficients.length) {
      throw new ArrayIndexOutOfBoundsException(blade);
    }
  }
}
