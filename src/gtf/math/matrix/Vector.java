package gtf.math.matrix;

import gtf.math.algebra.Ring;

/**
 * Convenience wrapper around an n x 1 matrix.
 *
 * @author gtf
 *
 * @param <T> the element type
 * @param <R> the ring type
 */
public final class Vector<T, R extends Ring<T>> {

  private final Matrix<T, R> matrix;

  private Vector(Matrix<T, R> matrix) {
    if (matrix == null) {
      throw new NullPointerException("matrix");
    }
    if (matrix.getCols() != 1) {
      throw new IllegalArgumentException(
          "vector matrix must have exactly one column");
    }
    this.matrix = matrix;
  }

  /**
   * Creates a dense vector.
   *
   * @param ring the base ring
   * @param size the vector dimension
   * @return the vector
   */
  public static <T, R extends Ring<T>> Vector<T, R> dense(
      R ring,
      int size) {

    return new Vector<T, R>(
        Matrix.dense(ring, size, 1));
  }

  /**
   * Creates a sparse vector.
   *
   * @param ring the base ring
   * @param size the vector dimension
   * @return the vector
   */
  public static <T, R extends Ring<T>> Vector<T, R> sparse(
      R ring,
      int size) {

    return new Vector<T, R>(
        Matrix.sparse(ring, size, 1));
  }

  /**
   * Wraps an existing n x 1 matrix as a vector.
   *
   * @param matrix the matrix
   * @return the vector wrapper
   */
  public static <T, R extends Ring<T>> Vector<T, R> of(
      Matrix<T, R> matrix) {

    return new Vector<T, R>(matrix);
  }

  public R ring() {
    return matrix.getRing();
  }

  public int size() {
    return matrix.getRows();
  }

  public T get(int index) {
    return matrix.getCell(index, 0);
  }

  public void set(int index, T value) {
    matrix.setCell(index, 0, value);
  }

  /**
   * Computes the dot product using matrix multiplication.
   *
   * @param arg the other vector
   * @return the dot product
   */
  public T dot(Vector<T, R> arg) {
    if (arg == null) {
      throw new NullPointerException("arg");
    }
    if (size() != arg.size()) {
      throw new IllegalArgumentException(
          "vector dimensions must match");
    }

    return matrix.transpose()
        .multiply(arg.matrix)
        .getCell(0, 0);
  }

  /**
   * Returns the underlying n x 1 matrix.
   *
   * @return the matrix representation
   */
  public Matrix<T, R> matrix() {
    return matrix;
  }
}
