package gtf.math.geometry.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gtf.math.algebra.Field;
import gtf.math.algebra.FiniteDimensionalVectorSpace;
import gtf.math.geometry.Tensor;


/**
 * Dense immutable tensor implementation backed by a row-major component list.
 *
 * <p>
 * The components are stored in row-major order. For a rank-3 tensor, for
 * example, the component {@code T(i, j, k)} is stored at offset
 * {@code (i * n + j) * n + k}, where {@code n} is the dimension of the
 * underlying vector space.
 * </p>
 *
 * @author gtf
 *
 * @param <S> the scalar-field element type
 * @param <F> the scalar field
 */
public final class ArrayTensor<S, F extends Field<S>>
    extends AbstractTensor<S, F> {

  private final List<S> components;

  /**
   * Creates a dense tensor from a flattened row-major component list.
   *
   * @param vectorSpace the vector space
   * @param rank the tensor rank
   * @param components flattened row-major component values
   */
  public ArrayTensor(
      FiniteDimensionalVectorSpace<?, S, F> vectorSpace,
      int rank,
      List<S> components) {
    super(vectorSpace, rank);

    if (components == null) {
      throw new NullPointerException("components");
    }
    if (components.size() != size()) {
      throw new IllegalArgumentException("wrong number of components");
    }

    this.components = Collections.unmodifiableList(
        new ArrayList<S>(components));
  }

  @Override
  public S component(int... indices) {
    return components.get(flatten(indices));
  }

  @Override
  public Tensor<S, F> tensorProduct(Tensor<S, F> arg) {
    validateTensorProductArgument(arg);

    List<S> result = new ArrayList<S>(size() * size(arg.rank()));

    for (int offset1 = 0; offset1 < size(); offset1++) {
      S component1 = components.get(offset1);

      for (int offset2 = 0; offset2 < size(arg.rank()); offset2++) {
        result.add(field().mul(component1, componentAt(arg, offset2)));
      }
    }

    return new ArrayTensor<S, F>(vectorSpace(), rank() + arg.rank(), result);
  }

  @Override
  public Tensor<S, F> contract(int index1, int index2) {
    validateContractionIndices(index1, index2);

    int resultRank = rank() - 2;
    List<S> result = new ArrayList<S>(size(resultRank));

    for (int offset = 0; offset < size(resultRank); offset++) {
      int[] resultIndices = unflatten(resultRank, offset);
      S sum = field().zero();

      for (int k = 0; k < dimension(); k++) {
        int[] indices = contractedIndices(resultIndices, index1, index2, k);
        sum = field().add(sum, component(indices));
      }

      result.add(sum);
    }

    return new ArrayTensor<S, F>(vectorSpace(), resultRank, result);
  }

  @Override
  protected Tensor<S, F> create(List<S> components) {
    return new ArrayTensor<S, F>(vectorSpace(), rank(), components);
  }

  private int flatten(int... indices) {
    if (indices == null) {
      throw new NullPointerException("indices");
    }
    if (indices.length != rank()) {
      throw new IllegalArgumentException("wrong number of indices");
    }

    int offset = 0;

    for (int i = 0; i < indices.length; i++) {
      if (indices[i] < 0 || indices[i] >= dimension()) {
        throw new ArrayIndexOutOfBoundsException(indices[i]);
      }

      offset = offset * dimension() + indices[i];
    }

    return offset;
  }

  private int size(int rank) {
    int result = 1;

    for (int i = 0; i < rank; i++) {
      result *= dimension();
    }

    return result;
  }

  private int[] unflatten(int rank, int offset) {
    int size = size(rank);

    if (offset < 0 || offset >= size) {
      throw new ArrayIndexOutOfBoundsException(offset);
    }

    int[] indices = new int[rank];

    for (int i = rank - 1; i >= 0; i--) {
      indices[i] = offset % dimension();
      offset /= dimension();
    }

    return indices;
  }

  private S componentAt(Tensor<S, F> tensor, int offset) {
    return tensor.component(unflatten(tensor.rank(), offset));
  }

  private void validateTensorProductArgument(Tensor<S, F> arg) {
    if (arg == null) {
      throw new NullPointerException("arg");
    }
    if (arg.vectorSpace() != vectorSpace()) {
      throw new IllegalArgumentException("vector space mismatch");
    }
  }

  private void validateContractionIndices(int index1, int index2) {
    if (rank() < 2) {
      throw new UnsupportedOperationException(
          "contraction requires rank at least 2");
    }
    if (index1 < 0 || index1 >= rank()) {
      throw new ArrayIndexOutOfBoundsException(index1);
    }
    if (index2 < 0 || index2 >= rank()) {
      throw new ArrayIndexOutOfBoundsException(index2);
    }
    if (index1 == index2) {
      throw new IllegalArgumentException("indices must be distinct");
    }
  }

  private int[] contractedIndices(
      int[] resultIndices,
      int index1,
      int index2,
      int contractedIndex) {
    int[] indices = new int[rank()];
    int resultIndex = 0;

    for (int i = 0; i < rank(); i++) {
      if (i == index1 || i == index2) {
        indices[i] = contractedIndex;
      } else {
        indices[i] = resultIndices[resultIndex];
        resultIndex++;
      }
    }

    return indices;
  }
}
