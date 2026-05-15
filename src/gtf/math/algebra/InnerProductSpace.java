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
   * <p>
   * The default implementation computes the standard coordinate-wise inner
   * product relative to the distinguished basis.
   * </p>
   *
   * @param v1 the first vector
   * @param v2 the second vector
   * @return the inner product
   */
  default S innerProduct(V v1, V v2) {
    F field = ring();
    S sum = field.zero();

    for (int i = 0; i < dimension(); i++) {
      sum = field.add(sum,
          field.mul(coordinate(v1, i), coordinate(v2, i)));
    }

    return sum;
  }
}
