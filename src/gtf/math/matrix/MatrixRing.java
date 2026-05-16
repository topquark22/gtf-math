package gtf.math.matrix;

import gtf.math.algebra.Ring;


/**
 * Ring of square matrices over a base ring.
 *
 * <p>
 * For a fixed size {@code n}, the set of {@code n x n} matrices over a ring
 * forms a ring under matrix addition and matrix multiplication. Rectangular
 * matrices do not form a ring under multiplication, so this class deliberately
 * represents only square matrices of one fixed size.
 * </p>
 *
 * @author gtf
 *
 * @param <T> the type of the matrix entries
 * @param <R> the base ring type
 */
public class MatrixRing<T, R extends Ring<T>>
    implements Ring<Matrix<T, R>> {

  private final R baseRing;
  private final int size;
  private final StorageFactory<T> storageFactory;

  /**
   * Creates a ring of dense {@code n x n} matrices over the given base ring.
   *
   * @param baseRing the base ring
   * @param size the matrix size
   * @return the matrix ring
   */
  public static <T, R extends Ring<T>> MatrixRing<T, R> dense(
      R baseRing,
      int size) {

    return new MatrixRing<T, R>(
        baseRing,
        size,
        new ArrayStorageFactory<T>());
  }

  /**
   * Creates a ring of sparse {@code n x n} matrices over the given base ring.
   *
   * @param baseRing the base ring
   * @param size the matrix size
   * @return the matrix ring
   */
  public static <T, R extends Ring<T>> MatrixRing<T, R> sparse(
      R baseRing,
      int size) {

    return new MatrixRing<T, R>(
        baseRing,
        size,
        new SparseStorageFactory<T>());
  }

  MatrixRing(
      R baseRing,
      int size,
      StorageFactory<T> storageFactory) {

    if (baseRing == null) {
      throw new NullPointerException("baseRing");
    }
    if (storageFactory == null) {
      throw new NullPointerException("storageFactory");
    }
    if (size < 0) {
      throw new IllegalArgumentException("size must be non-negative");
    }

    this.baseRing = baseRing;
    this.size = size;
    this.storageFactory = storageFactory;
  }

  /**
   * @return the base ring over which the matrix entries are defined
   */
  public R baseRing() {
    return baseRing;
  }

  /**
   * @return the size {@code n} for this ring of {@code n x n} matrices
   */
  public int size() {
    return size;
  }

  @Override
  public Matrix<T, R> zero() {
    return Matrix.create(baseRing, size, size, storageFactory);
  }

  @Override
  public Matrix<T, R> id() {
    Matrix<T, R> identity = zero();

    for (int index = 0; index < size; index++) {
      identity.setCell(index, index, baseRing.id());
    }

    return identity;
  }

  @Override
  public Matrix<T, R> add(Matrix<T, R> arg1, Matrix<T, R> arg2) {
    validate(arg1);
    validate(arg2);

    Matrix<T, R> result = zero();

    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        result.setCell(row, col,
            baseRing.add(arg1.getCell(row, col), arg2.getCell(row, col)));
      }
    }

    return result;
  }

  @Override
  public Matrix<T, R> neg(Matrix<T, R> arg) {
    validate(arg);

    Matrix<T, R> result = zero();

    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        result.setCell(row, col, baseRing.neg(arg.getCell(row, col)));
      }
    }

    return result;
  }

  @Override
  public Matrix<T, R> mul(Matrix<T, R> arg1, Matrix<T, R> arg2) {
    validate(arg1);
    validate(arg2);

    return arg1.multiply(arg2);
  }

  @Override
  public Ring<Matrix<T, R>> ring() {
    return this;
  }

  @Override
  public Matrix<T, R> divide(
      Matrix<T, R> numerator,
      Matrix<T, R> denominator) {

    validate(numerator);
    validate(denominator);

    return numerator.multiply(denominator.inverse());
  }

  private void validate(Matrix<T, R> matrix) {
    if (matrix == null) {
      throw new NullPointerException("matrix");
    }
    if (matrix.getRows() != size || matrix.getCols() != size) {
      throw new IllegalArgumentException("matrix has wrong size");
    }
  }
}
