package gtf.math.algebra;


/**
 * A finite-dimensional inner-product space over a field.
 *
 * <p>
 * This interface extends a finite-dimensional vector space with a specified
 * inner product.
 * </p>
 *
 * @author gtf
 *
 * @param <V> the type of the vector-space elements
 * @param <S> the type of the scalar-field elements
 * @param <F> the scalar field
 */
public interface InnerProductSpace<V, S, F extends Field<S>>
    extends FiniteDimensionalVectorSpace<V, S, F> {

  /**
   * Computes the inner product of two vectors.
   *
   * @param v1 the first vector
   * @param v2 the second vector
   * @return the inner product
   */
  S innerProduct(V v1, V v2);
}
