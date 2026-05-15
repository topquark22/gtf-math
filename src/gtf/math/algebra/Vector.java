package gtf.math.algebra;


/**
 * A coordinate vector over a field.
 *
 * <p>
 * This represents a vector as an indexed tuple of scalar coordinates. It is
 * distinct from {@code gtf.math.matrix.Vector}, which represents a vector in
 * the matrix package as a matrix-like object.
 * </p>
 *
 * @author gtf
 *
 * @param <S> the type of the scalar-field elements
 * @param <F> the scalar field
 */
public interface Vector<S, F extends Field<S>> {

  /**
   * @return the scalar field for this vector
   */
  F field();

  /**
   * @return the dimension of this vector
   */
  int dimension();

  /**
   * Returns one coordinate of this vector.
   *
   * @param index the coordinate index
   * @return the coordinate value
   */
  S get(int index);

  /**
   * Sets one coordinate of this vector.
   *
   * @param index the coordinate index
   * @param value the coordinate value
   */
  void set(int index, S value);

  /**
   * @return a copy of this vector
   */
  Vector<S, F> copy();
}
