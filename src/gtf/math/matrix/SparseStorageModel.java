package gtf.math.matrix;

/**
 * Provides backing storage for the matrix elements
 * using sparse storage.
 * 
 * @param T the type of elements stored
 * 
 * @author gtf
 */
class SparseStorageModel<T> implements StorageModel<T> {

  private final int rows;
  private final int cols;
  private final T zero;
  
  public SparseStorageModel(int rows, int cols, T zero) {
    this.rows = rows;
    this.cols = cols;
    this.zero = zero;
    // TODO allocate data structures
  }

  @Override
  public int cols() {
    return cols;
  }

  @Override
  public int firstColStored() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int firstColStored(int row) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int firstRowStored() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int firstRowStored(int col) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public T getCell(int row, int col) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int lastColStored() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int lastColStored(int row) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int lastRowStored() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int lastRowStored(int col) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int nextInCol(int row, int col) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int nextInRow(int row, int col) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int rows() {
    return rows;
  }

  @Override
  public void setCell(int row, int col, Object value) {
    // TODO Auto-generated method stub

  }

}
