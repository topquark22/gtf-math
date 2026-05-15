package gtf.math.matrix;

import gtf.math.algebra.Module;
import gtf.math.algebra.Ring;


/**
 * A matrix over an abstract ring. This means it supports
 * multiplication, row-reduction and determinant calculations.
 * 
 * @author gtf
 *
 * @param <T> the type of the matrix/ring elements
 * @param <R> the type of the ring
 */
public interface Matrix<T, R extends Ring<T>> extends MatrixOverModule<T, T, R, Module<T, T, R>> {

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
   * Multiply two matrices.
   * 
   * @param arg
   * @return
   */
  Matrix<T,R> multiply(Matrix<T, R> arg);
  
  /**
   * Converts the matrix to reduced row-echelon form in-place,
   * restricting the reduction to the first specified number of
   * columns of the matrix. This can be used to solve systems of
   * equations.
   *
   *@param workingColumns  The number of columns to work on. After
   *       the given number of columns have been reduced, the matrix
   *       will not be reduced any further.
   *@return the reciprocal of the aggregate scalar factor by which the
   *        rows of the matrix have been multiplied, or R.zero()
   *        if the matrix is not of full row-rank relative to the
   *        first number of columns; adjusted by sign for row swaps.
   */
  public T rowReduce(int workingColumns);
  
  /**
   * Computes the determinant of the given matrix.
   *
   *@throws UnsupportedOperationException if the given matrix is not square
   */
  T determinant();

  /**
   * Computes the inverse of the matrix. The matrix must be a square
   * and invertible.
   *
   *@return                                the inverse matrix
   *@throws UnsupportedOperationException  if the matrix is non-square
   *@throws ArithmeticException            if the matrix is not invertible
   */
  Matrix<T, R> inverse();
}
