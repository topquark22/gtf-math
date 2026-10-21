package gtf.math.geometry.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gtf.math.algebra.Field;
import gtf.math.geometry.FiniteDimensionalVectorSpace;
import gtf.math.geometry.Tensor;
import gtf.math.geometry.TensorVariance;


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
  private final List<TensorVariance> variances;
  private final int contravariantRank;
  private final int covariantRank;

  /**
   * Creates a tensor over a finite-dimensional vector space.
   *
   * @param vectorSpace the vector space
   * @param variances the tensor-index variances
   */
  protected AbstractTensor(
      FiniteDimensionalVectorSpace<?, S, F> vectorSpace,
      List<TensorVariance> variances) {
    if (vectorSpace == null) {
      throw new NullPointerException("vectorSpace");
    }
    if (variances == null) {
      throw new NullPointerException("variances");
    }

    int contravariant = 0;
    int covariant = 0;

    for (TensorVariance variance : variances) {
      if (variance == null) {
        throw new NullPointerException("variance");
      }
      if (variance == TensorVariance.CONTRAVARIANT) {
        contravariant++;
      } else {
        covariant++;
      }
    }

    this.vectorSpace = vectorSpace;
    this.variances = Collections.unmodifiableList(
        new ArrayList<TensorVariance>(variances));
    this.contravariantRank = contravariant;
    this.covariantRank = covariant;
  }

  @Override
  public final FiniteDimensionalVectorSpace<?, S, F> vectorSpace() {
    return vectorSpace;
  }

  @Override
  public final int rank() {
    return variances.size();
  }

  @Override
  public final int contravariantRank() {
    return contravariantRank;
  }

  @Override
  public final int covariantRank() {
    return covariantRank;
  }

  @Override
  public final TensorVariance variance(int index) {
    if (index < 0 || index >= rank()) {
      throw new ArrayIndexOutOfBoundsException(index);
    }

    return variances.get(index);
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
   * Constructs a tensor with the same vector space, rank, and variance as
   * this tensor.
   *
   * @param components flattened row-major component values
   * @return the resulting tensor
   */
  protected abstract Tensor<S, F> create(List<S> components);

  /**
   * Returns the tensor-index variances.
   *
   * @return the tensor-index variances
   */
  protected final List<TensorVariance> variances() {
    return variances;
  }

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

    for (int i = 0; i < rank(); i++) {
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

    int[] indices = new int[rank()];

    for (int i = rank() - 1; i >= 0; i--) {
      indices[i] = offset % dimension();
      offset /= dimension();
    }

    return indices;
  }

  /**
   * Checks that another tensor has the same vector space, rank, and variance.
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
    for (int i = 0; i < rank(); i++) {
      if (arg.variance(i) != variance(i)) {
        throw new IllegalArgumentException("variance mismatch");
      }
    }
  }

  private S componentAt(Tensor<S, F> tensor, int offset) {
    return tensor.component(unflatten(offset));
  }
}
