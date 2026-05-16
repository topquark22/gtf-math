package gtf.math.matrix;

import gtf.math.algebra.Ring;


/**
 * A matrix over an abstract ring. This means it supports addition,
 * multiplication, row-reduction and determinant calculations.
 *
 * @author gtf
 *
 * @param <T> the type of the matrix/ring elements
 * @param <R> the type of the ring
 */
public interface Matrix<T, R extends Ring<T>> {

  /**
   * Creates a dense matrix backed by array storage.
   *
   * @param ring the ring over which the matrix entries are defined
   * @param rows the number of rows
   * @param cols the number of columns
   * @return a dense matrix
   */
  static <T, R extends Ring<T>> Matrix<T, R> dense(
      R ring,
      int rows,
      int cols) {

    return new MatrixImpl<T, R>(
        ring,
        rows,
        cols,
        new ArrayStorageFactory<T>());
  }

  /**
   * Creates a sparse matrix backed by sparse storage.
   *
   * @param ring the ring over which the matrix entries are defined
   * @param rows the number of rows
   * @param cols the number of columns
   * @return a sparse matrix
   */
  static <T, R extends Ring<T>> Matrix<T, R> sparse(
      R ring,
      int rows,
      int cols) {

    return new SparseMatrix<T, R>(ring, rows, cols);
  }

  /**
   * Creates a matrix with an explicitly supplied storage factory.
   *
   * <p>
   * This is mainly useful for tests and specialized storage strategies.
   * Most client code should use {@link #dense(Ring, int, int)} or
   * {@link #sparse(Ring, int, int)}.
   * </p>
   *
   * @param ring the ring over which the matrix entries are defined
   * @param rows the number of rows
   * @param cols the number of columns
   * @param storageFactory the storage factory
   * @return a matrix
   */
  static <T, R extends Ring<T>> Matrix<T, R> create(
      R ring,
      int rows,
      int cols,
      StorageFactory<T> storageFactory) {

    return new MatrixImpl<T, R>(ring, rows, cols, storageFactory);
  }

  R getRing();

  int getRows();

  int getCols();

  T getCell(int row, int col);

  void setCell(int row, int col, T value);

  /**
   * Adds a matrix of the same dimensions.
   *
   * @param arg the matrix to add
   * @return the sum
   */
  Matrix<T, R> add(Matrix<T, R> arg);

  /**
   * Computes the transpose of this matrix.
   *
   * @return the transpose
   */
  Matrix<T, R> transpose();

  /**
   * Returns a submatrix.
   *
   * @param rowStart the starting row
   * @param numRows the number of rows
   * @param colStart the starting column
   * @param numCols the number of columns
   * @return the submatrix
   */
  Matrix<T, R> submatrix(
      int rowStart,
      int numRows,
      int colStart,
      int numCols);

  /**
   * Catenates another matrix on the right side of this matrix.
   *
   * @param matrix the matrix to catenate
   * @return the catenated matrix
   */
  Matrix<T, R> catenate(Matrix<T, R> matrix);

  /**
   * Returns a copy of the given matrix with one row deleted.
   *
   * @param matrix the source matrix
   * @param row the row to delete
   * @return the resulting matrix
   */
  Matrix<T, R> deleteRow(Matrix<T, R> matrix, int row);

  /**
   * Returns a copy of the given matrix with one column deleted.
   *
   * @param matrix the source matrix
   * @param col the column to delete
   * @return the resulting matrix
   */
  Matrix<T, R> deleteCol(Matrix<T, R> matrix, int col);

  /**
   * Multiplies the entire matrix by a scalar.
   *
   * @param scalar the scalar to multiply by
   */
  void scalarMultiply(T scalar);

  /**
   * Multiplies one row of the matrix by a scalar.
   *
   * @param row the row number
   * @param scalar the scalar to multiply by
   */
  void scalarMultiplyRow(int row, T scalar);

  /**
   * Adds a scalar multiple of one row to another.
   *
   * @param sourceRow the source row number
   * @param destRow the destination row number
   * @param scalar the scalar multiple
   */
  void addScalarMultiple(int sourceRow, int destRow, T scalar);

  /**
   * Swaps two rows of the matrix.
   *
   * @param row1 the first row number
   * @param row2 the second row number
   */
  void swapRows(int row1, int row2);

  /**
   * @return true if the matrix is square
   */
  default boolean isSquare() {
    return getRows() == getCols();
  }

  /**
   * @return true if the matrix is the identity matrix
   */
  default boolean isIdentity() {
    if (!isSquare()) {
      return false;
    }

    R ring = getRing();

    for (int row = 0; row < getRows(); row++) {
      for (int col = 0; col < getCols(); col++) {
        T value = getCell(row, col);

        if (row == col) {
          if (!ring.id().equals(value)) {
            return false;
          }
        } else {
          if (!ring.zero().equals(value)) {
            return false;
          }
        }
      }
    }

    return true;
  }

  /**
   * Computes the trace of the matrix.
   *
   * @return the sum of the diagonal entries
   * @throws UnsupportedOperationException if the matrix is not square
   */
  T trace();

  /**
   * Multiplies two matrices.
   *
   * @param arg the right-hand factor
   * @return the product
   */
  Matrix<T, R> multiply(Matrix<T, R> arg);

  /**
   * Converts the matrix to reduced row-echelon form in-place,
   * restricting the reduction to the first specified number of
   * columns of the matrix.
   *
   * @param workingColumns the number of columns to work on
   * @return the reciprocal of the aggregate scalar factor by which the rows
   *     of the matrix have been multiplied, or R.zero() if the matrix is not
   *     of full row-rank relative to the working columns
   */
  T rowReduce(int workingColumns);

  /**
   * Computes the determinant of the given matrix.
   *
   * @return the determinant
   * @throws UnsupportedOperationException if the given matrix is not square
   */
  T determinant();

  /**
   * Computes the inverse of the matrix. The matrix must be square and
   * invertible.
   *
   * @return the inverse matrix
   * @throws UnsupportedOperationException if the matrix is non-square
   * @throws ArithmeticException if the matrix is not invertible
   */
  Matrix<T, R> inverse();
}