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
public interface RingMatrix<T, R extends Ring<T>> extends Matrix<T, T, R, Module<T, T, R>> {
  
  /**
   * Returns a new square matrix of a given size, initialized to the identity
   * matrix with 1's on the diagonal.
   * 
   * @param size
   * @return
   */
  RingMatrix<T, R> identity(int size);
  

  /**
   * Multiply two matrices.
   * 
   * @param arg
   * @return
   */
  RingMatrix<T,R> multiply(RingMatrix<T, R> arg);
  
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
  RingMatrix<T, R> inverse();
}
