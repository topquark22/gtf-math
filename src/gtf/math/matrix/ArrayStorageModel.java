package gtf.math.matrix;

/**
 * Provides backing storage for the matrix elements
 * using an array.
 * 
 * @param T the type of elements stored
 * 
 * @author gtf
 */
class ArrayStorageModel<T> implements StorageModel<T> {

  private final int rows;
  private final int cols;
  private final T zero;
  
  private final Object/*T*/[][] entries;
  
  /**
   * Allocates storage for matrix elements, backed by an array.
   * 
   * @param rows The number of rows
   * @param cols The number of columns
   * @param zero The value representing zero for this matrix's elements
   */
  ArrayStorageModel(int rows, int cols, T zero) {
    if (rows <= 0 || cols <= 0) {
      throw new IllegalArgumentException("invalid dimensions [" + rows + "," + cols + "]");
    }
    this.rows = rows;
    this.cols = cols;
    this.zero = zero;
    entries = new Object/*T*/[rows][cols];
  }
  
  @Override
  public int cols() {
    return cols;
  }

  @Override
  public int firstColStored() {
    return 0;
  }

  @Override
  public int firstColStored(int row) {
    return 0;
  }

  @Override
  public int firstRowStored() {
    return 0;
  }

  @Override
  public int firstRowStored(int col) {
    return 0;
  }

  @Override
  public T getCell(int row, int col) {
    if (entries[row][col] == null) {
      return zero;
    }
    return (T) entries[row][col];
  }

  @Override
  public int lastColStored() {
    return cols - 1;
  }

  @Override
  public int lastColStored(int row) {
    if (row < 0 || row >= rows) {
      throw new IllegalArgumentException("invalid row");
    }
    return cols - 1;
  }

  @Override
  public int lastRowStored() {
    return rows - 1;
  }

  @Override
  public int lastRowStored(int col) {
    if (col < 0 || col >= cols) {
      throw new IllegalArgumentException("invalid column");
    }
    return rows - 1;
  }

  @Override
  public int nextInCol(int row, int col) {
    if (row < 0 || row >= rows || col < 0 || col >= cols) {
      throw new IllegalArgumentException("invalid cell");
    }
    if (row == rows - 1) {
      return -1;
    }
    return row + 1;
  }

  @Override
  public int nextInRow(int row, int col) {
    if (row < 0 || row >= rows || col < 0 || col >= cols) {
      throw new IllegalArgumentException("invalid cell");
    }
    if (col == cols - 1) {
      return -1;
    }
    return 0;
  }

  @Override
  public int rows() {
    return rows;
  }

  @Override
  public void setCell(int row, int col, T value) {
    entries[row][col] = value;
  }
}
