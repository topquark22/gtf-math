package gtf.math.algebra;


/**
 * A finite-dimensional vector space over a field.
 *
 * <p>
 * This interface adds the concept of dimension and a distinguished basis to
 * the general notion of a vector space. The choice of basis is part of the
 * structure exposed by this interface.
 * </p>
 *
 * @author gtf
 *
 * @param <V> the type of the vector-space elements
 * @param <S> the type of the scalar-field elements
 * @param <F> the scalar field
 */
public interface FiniteDimensionalVectorSpace<V, S, F extends Field<S>>
    extends VectorSpace<V, S, F> {

  /**
   * @return the dimension of this vector space
   */
  int dimension();

  /**
   * Returns one vector from the distinguished basis of this vector space.
   *
   * @param index the basis-vector index
   * @return the basis vector at the given index
   */
  V basisVector(int index);
}
