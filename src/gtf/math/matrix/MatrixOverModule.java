package gtf.math.matrix;

import gtf.math.algebra.Module;
import gtf.math.algebra.Ring;


/**
 * A matrix over an abstract module only.
 * 
 * Many of the well-known operations on matrices (such as multiplication) depend
 * on the matrix being defined over a Ring (see Matrix).
 * 
 * @author gtf
 *
 * @param <T> the type of the matrix elements
 * @param <S> the type of the ring elements
 * @param <M> the module over which the matrix is defined
 * @param <R> the ring over which the module M is defined.
 */
public interface MatrixOverModule<T, S, R extends Ring<S>, M extends Module<T, S, R>> {

  R getRing();
  
  M getModule();
  
  int getRows();
  
  int getCols();
  
  T getCell(int row, int col);
  
  void setCell(int row, int col, T value);
  
  /** 
   * Add a matrix of the same dimensions.
   * 
   * @param arg
   * @return
   */
  MatrixOverModule<T, S, R, M> add(MatrixOverModule<T, S, R, M> arg);
  
  /**
   * Computes the transpose of the present matrix (rows and columns interchanged).
   */
  MatrixOverModule<T, S, R, M> transpose();
  
  /**
   * Returns a submatrix (a range of rows and columns) from the present
   * matrix.
   *
   *@param rowStart  The starting row
   *@param numRows   The number of rows
   *@param colStart  The starting column
   *@param numCols   The number of columns
   *@throws ArrayIndexOutOfBoundsException if any of the cells would lie
   *        beyond the size of this matrix
   *@throws NegativeArraySizeException if the number of rows or columns for
   *        the result are negative
   */
  MatrixOverModule<T, S, R, M> submatrix(int rowStart, int numRows, int colStart, int numCols);

  /**
   * Stick another matrix together with the given matrix (on its right).
   * The two matrices must have the same number of rows.
   *
   *@param matrix                     The matrix to catenate
   *@throws IllegalArgumentException  if the matrices do not have
   *        the same number of rows
   */
  MatrixOverModule<T, S, R, M> catenate(MatrixOverModule<T, S, R, M> matrix);
  
  
  /**
   * Return a new matrix with a given row deleted.
   * 
   * @param matrix
   * @param row
   * @return
   */
  MatrixOverModule<T, S, R, M> deleteRow(MatrixOverModule<T, S, R, M> matrix, int row);
  
  /**
   * Return a new matrix with a given column deleted.
   * 
   * @param matrix
   * @param row
   * @return
   */
  MatrixOverModule<T, S, R, M> deleteCol(MatrixOverModule<T, S, R, M> matrix, int col);
  
  /**
   * Multiply the entire matrix by a scalar.
   * 
   *@param scalar  The scalar to multiply by
   */
  void scalarMultiply(S scalar);
  
  /**
   * Multiply one row of the matrix by a scalar.
   *
   *@param row     The row number
   *@param scalar  The scalar to multiply by
   */
  void scalarMultiplyRow(int row, S scalar);

  /**
   * Adds a scalar multiple of one row to another.
   *
   *@param sourceRow  The source row number
   *@param destRow    The destination row number
   *@param scalar     The scalar multiple
   */
  void addScalarMultiple(int sourceRow, int destRow, S scalar);

  /**
   * Swaps (interchanges) two rows of the matrix.
   *
   *@param row1  The 1st row number
   *@param row2  The 2nd row number
   */
  void swapRows(int row1, int row2);

}
