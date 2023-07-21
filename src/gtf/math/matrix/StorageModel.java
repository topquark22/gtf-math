package gtf.math.matrix;

/**
 * Represents the way elements are stored in a matrix.
 * Provides backing storage for the matrix elements.
 * 
 * @param T the type of elements stored
 * 
 * @author gtf
 */
interface StorageModel<T> {

  /**
   * Gets the element at the given location in the matrix.
   * 
   * @param row
   * @param col
   * @return the element.
   */
  T getCell(int row, int col);
  
  /**
   * Sets the element at the given location in the matrix.
   * 
   * @param row
   * @param col
   * @param value the value to set.
   */
  void setCell(int row, int col, T value);
  
  /**
   * Returns the number of rows in the matrix.
   * 
   * @return
   */
  int rows();
  
  /**
   * Returns the number of columns in the matrix.
   * @return
   */
  int cols();
  
  /**
   * Returns the first row index having entries stored.
   * Any rows before this must consist entirely of zero.
   * 
   * @return an index in the range 0 .. (rows - 1)
   */
  int firstRowStored();
  
  /**
   * Returns the first row index having entries stored
   * for a given column.
   * 
   * Any rows before this in the column must consist
   * entirely of zero.
   * 
   * @return an index in the range 0 .. (rows - 1)
   */
  int firstRowStored(int col);
  
  /**
   * Returns the last row index having entries stored.
   * Any rows after this must consist entirely of zero.
   * 
   * @return an index in the range 0 .. (rows - 1)
   */
  int lastRowStored();
  
  /**
   * Returns the last row index having entries stored
   * for a given column.
   * 
   * Any rows after this in the column must consist
   * entirely of zero.
   * 
   * @return an index in the range 0 .. (rows - 1)
   */
  int lastRowStored(int col);
  
  /**
   * Returns the first column index having entries stored.
   * Any columns before this must consist entirely of zero.
   * 
   * @return an index in the range 0 .. (cols - 1)
   */
  int firstColStored();
  
  /**
   * Returns the first column index having entries stored
   * for a given row.
   * 
   * Any columns before this in the row must consist
   * entirely of zero.
   * 
   * @return an index in the range 0 .. (cols - 1)
   */
  int firstColStored(int row);
  
  /**
   * Returns the last column index having entries stored.
   * Any columns after this must consist entirely of zero.
   * 
   * @return an index in the range 0 .. (cols - 1)
   */
  int lastColStored();
  
  /**
   * Returns the last column index having entries stored
   * for a given row.
   * 
   * Any columns after this in the row must consist
   * entirely of zero.
   * 
   * @return an index in the range 0 .. (cols - 1)
   */
  int lastColStored(int row);
  
  /**
   * Returns the column index of the next entry stored
   * after the given row/column position.
   * 
   * @param row
   * @param col
   * @return the next column index, or -1 if the row
   * has no more stored entries
   */
  int nextInRow(int row, int col);
  
  /**
   * Returns the row index of the next entry stored
   * after the given row/column position.
   * 
   * @param row
   * @param col
   * @return the next row index, or -1 if the column
   * has no more stored entries
   */
  int nextInCol(int row, int col);
}
