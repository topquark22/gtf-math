package gtf.math.geometry.impl;

import java.util.ArrayList;
import java.util.List;

import gtf.math.algebra.Field;
import gtf.math.algebra.FiniteDimensionalVectorSpace;
import gtf.math.geometry.Tensor;


/**
 * Partial implementation of a coordinate tensor.
 *
 * <p>
 * This class implements the operations that depend only on coordinate access:
 * addition, additive inverse, and scalar multiplication. Concrete subclasses
 * provide storage and construction of tensors from component arrays.
 * </p>
 *
 * @author gtf
 *
 * @param <S> the scalar-field element type
 * @param <F> the scalar field
 */
public abstract class AbstractTensor<S, F extends Field<S>>
    implements Tensor<S, F> {

  private final FiniteDimensionalVectorSpace<?, S, F> vectorSpace;
  private final int rank;

  /**
   * Creates a tensor over a finite-dimensional vector space.
   *
   * @param vectorSpace the vector space
   * @param rank the tensor rank
   */
  protected AbstractTensor(
      FiniteDimensionalVectorSpace<?, S, F> vectorSpace,
      int rank) {
    if (vectorSpace == null) {
      throw new NullPointerException("vectorSpace");
    }
    if (rank < 0) {
      throw new IllegalArgumentException("rank must be non-negative");
    }

    this.vectorSpace = vectorSpace;
    this.rank = rank;
  }

  @Override
  public final FiniteDimensionalVectorSpace<?, S, F> vectorSpace() {
    return vectorSpace;
  }

  @Override
  public final int rank() {
    return rank;
  }

  @Override
  public final Tensor<S, F> add(Tensor<S, F> arg) {
    validateCompatible(arg);

    List<S> components = new ArrayList<S>(size());

    for (int offset = 0; offset < size(); offset++) {
      components.add(
          field().add(componentAt(offset), componentAt(arg, offset)));
    }

    return create(components);
  }

  @Override
  public final Tensor<S, F> neg() {
    List<S> components = new ArrayList<S>(size());

    for (int offset = 0; offset < size(); offset++) {
      components.add(field().neg(componentAt(offset)));
    }

    return create(components);
  }

  @Override
  public Tensor<S, F> mul(S scalar) {
    List<S> components = new ArrayList<S>(size());

    for (int offset = 0; offset < size(); offset++) {
      components.add(field().mul(scalar, componentAt(offset)));
    }

    return create(components);
  }

  /**
   * Constructs a tensor with the same vector space and rank as this tensor.
   *
   * @param components flattened row-major component values
   * @return the resulting tensor
   */
  protected abstract Tensor<S, F> create(List<S> components);

  /**
   * Returns one component by flattened row-major offset.
   *
   * @param offset the component offset
   * @return the component value
   */
  protected final S componentAt(int offset) {
    return component(unflatten(offset));
  }

  /**
   * @return the total number of components
   */
  protected final int size() {
    int result = 1;

    for (int i = 0; i < rank; i++) {
      result *= dimension();
    }

    return result;
  }

  /**
   * Converts a flattened offset to row-major tensor indices.
   *
   * @param offset the flattened offset
   * @return the corresponding tensor indices
   */
  protected final int[] unflatten(int offset) {
    if (offset < 0 || offset >= size()) {
      throw new ArrayIndexOutOfBoundsException(offset);
    }

    int[] indices = new int[rank];

    for (int i = rank - 1; i >= 0; i--) {
      indices[i] = offset % dimension();
      offset /= dimension();
    }

    return indices;
  }

  /**
   * Checks that another tensor has the same vector space and rank.
   *
   * @param arg the tensor to check
   */
  protected final void validateCompatible(Tensor<S, F> arg) {
    if (arg == null) {
      throw new NullPointerException("arg");
    }
    if (arg.vectorSpace() != vectorSpace()) {
      throw new IllegalArgumentException("vector space mismatch");
    }
    if (arg.rank() != rank()) {
      throw new IllegalArgumentException("rank mismatch");
    }
  }

  private S componentAt(Tensor<S, F> tensor, int offset) {
    return tensor.component(unflatten(offset));
  }
}
