package gtf.math.geometry;

import gtf.math.algebra.Field;


/**
 * A coordinate tensor over a finite-dimensional vector space.
 *
 * <p>
 * This interface represents the components of a tensor relative to a
 * distinguished basis. It deliberately does not yet distinguish covariant
 * and contravariant indices. That distinction can be layered on later by a
 * richer tensor-space type.
 * </p>
 *
 * @author gtf
 *
 * @param <S> the scalar-field element type
 * @param <F> the scalar field
 */
public interface Tensor<S, F extends Field<S>> {

  /**
   * @return the scalar field
   */
  F ring();

  /**
   * @return the dimension of the underlying vector space
   */
  int dimension();

  /**
   * @return the tensor rank, or order
   */
  int rank();

  /**
   * Returns one tensor component.
   *
   * @param indices the component indices
   * @return the component value
   */
  S component(int... indices);

  /**
   * Adds another tensor of the same dimension and rank.
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
   * Contracts two tensor indices.
   *
   * <p>
   * Since this is a coordinate tensor without variance annotations, this is
   * the coordinate trace obtained by setting the two selected indices equal
   * and summing over that common index.
   * </p>
   *
   * @param index1 the first tensor index
   * @param index2 the second tensor index
   * @return the contracted tensor
   */
  Tensor<S, F> contract(int index1, int index2);
}
