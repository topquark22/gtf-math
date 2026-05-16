package gtf.math.geometry;

import gtf.math.Permutation;
import gtf.math.algebra.Field;


/**
 * A coordinate tensor over a finite-dimensional vector space.
 *
 * @author gtf
 *
 * @param <S> the scalar-field element type
 * @param <F> the scalar field
 */
public interface Tensor<S, F extends Field<S>> {

  /**
   * @return the finite-dimensional vector space over which this tensor is
   *         defined
   */
  FiniteDimensionalVectorSpace<?, S, F> vectorSpace();

  /**
   * @return the scalar field
   */
  default F field() {
    return vectorSpace().ring();
  }

  /**
   * @return the dimension of the underlying vector space
   */
  default int dimension() {
    return vectorSpace().dimension();
  }

  /**
   * @return the total tensor rank
   */
  int rank();

  /**
   * @return the number of contravariant indices
   */
  int contravariantRank();

  /**
   * @return the number of covariant indices
   */
  int covariantRank();

  /**
   * Returns the variance of one tensor index.
   *
   * @param index the tensor index
   * @return the index variance
   */
  TensorVariance variance(int index);

  /**
   * Returns one tensor component.
   *
   * @param indices the component indices
   * @return the component value
   */
  S component(int... indices);

  /**
   * Adds another tensor of the same dimension, rank, and variance.
   *
   * @param arg the tensor to add
   * @return the sum
   */
  Tensor<S, F> add(Tensor<S, F> arg);

  /**
   * @return the additive inverse of this tensor
   */
  Tensor<S, F> neg();

  /**
   * Multiplies this tensor by a scalar.
   *
   * @param scalar the scalar
   * @return the scalar multiple
   */
  Tensor<S, F> mul(S scalar);

  /**
   * Computes the tensor product of this tensor with another tensor.
   *
   * @param arg the right-hand tensor
   * @return the tensor product
   */
  Tensor<S, F> tensorProduct(Tensor<S, F> arg);

  /**
   * Permutes the tensor indices.
   *
   * <p>
   * The permutation describes where each old tensor index moves in the result.
   * If {@code permutation.image(i) == j}, then old index {@code i} becomes
   * result index {@code j}.
   * </p>
   *
   * @param permutation the index permutation
   * @return the permuted tensor
   */
  Tensor<S, F> permute(Permutation permutation);

  /**
   * Contracts two tensor indices.
   *
   * <p>
   * Ordinary contraction is only defined between one contravariant index and
   * one covariant index.
   * </p>
   *
   * @param index1 the first tensor index
   * @param index2 the second tensor index
   * @return the contracted tensor
   */
  Tensor<S, F> contract(int index1, int index2);
}
