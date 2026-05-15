package gtf.math.matrix;

import java.util.NavigableMap;
import java.util.TreeMap;

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

  private final NavigableMap<Integer, NavigableMap<Integer, T>> rowEntries;
  private final NavigableMap<Integer, NavigableMap<Integer, T>> colEntries;
  
  public SparseStorageModel(int rows, int cols, T zero) {
    if (rows <= 0 || cols <= 0) {
      throw new IllegalArgumentException("invalid dimensions [" + rows + "," + cols + "]");
    }
    this.rows = rows;
    this.cols = cols;
    this.zero = zero;
    this.rowEntries = new TreeMap<Integer, NavigableMap<Integer, T>>();
    this.colEntries = new TreeMap<Integer, NavigableMap<Integer, T>>();
  }

  @Override
  public int cols() {
    return cols;
  }

  @Override
  public int firstColStored() {
    return colEntries.isEmpty() ? -1 : colEntries.firstKey();
  }

  @Override
  public int firstColStored(int row) {
    validateRow(row);
    NavigableMap<Integer, T> rowMap = rowEntries.get(row);
    return rowMap == null || rowMap.isEmpty() ? -1 : rowMap.firstKey();
  }

  @Override
  public int firstRowStored() {
    return rowEntries.isEmpty() ? -1 : rowEntries.firstKey();
  }

  @Override
  public int firstRowStored(int col) {
    validateCol(col);
    NavigableMap<Integer, T> colMap = colEntries.get(col);
    return colMap == null || colMap.isEmpty() ? -1 : colMap.firstKey();
  }

  @Override
  public T getCell(int row, int col) {
    validateCell(row, col);
    NavigableMap<Integer, T> rowMap = rowEntries.get(row);
    if (rowMap == null) {
      return zero;
    }
    T value = rowMap.get(col);
    return value == null ? zero : value;
  }

  @Override
  public int lastColStored() {
    return colEntries.isEmpty() ? -1 : colEntries.lastKey();
  }

  @Override
  public int lastColStored(int row) {
    validateRow(row);
    NavigableMap<Integer, T> rowMap = rowEntries.get(row);
    return rowMap == null || rowMap.isEmpty() ? -1 : rowMap.lastKey();
  }

  @Override
  public int lastRowStored() {
    return rowEntries.isEmpty() ? -1 : rowEntries.lastKey();
  }

  @Override
  public int lastRowStored(int col) {
    validateCol(col);
    NavigableMap<Integer, T> colMap = colEntries.get(col);
    return colMap == null || colMap.isEmpty() ? -1 : colMap.lastKey();
  }

  @Override
  public int nextInCol(int row, int col) {
    validateCell(row, col);
    NavigableMap<Integer, T> colMap = colEntries.get(col);
    if (colMap == null) {
      return -1;
    }
    Integer nextRow = colMap.higherKey(row);
    return nextRow == null ? -1 : nextRow.intValue();
  }

  @Override
  public int nextInRow(int row, int col) {
    validateCell(row, col);
    NavigableMap<Integer, T> rowMap = rowEntries.get(row);
    if (rowMap == null) {
      return -1;
    }
    Integer nextCol = rowMap.higherKey(col);
    return nextCol == null ? -1 : nextCol.intValue();
  }

  @Override
  public int rows() {
    return rows;
  }

  @Override
  public void setCell(int row, int col, T value) {
    validateCell(row, col);
    if (isZero(value)) {
      removeCell(row, col);
    } else {
      setNonzeroCell(row, col, value);
    }
  }

  private void setNonzeroCell(int row, int col, T value) {
    NavigableMap<Integer, T> rowMap = rowEntries.get(row);
    if (rowMap == null) {
      rowMap = new TreeMap<Integer, T>();
      rowEntries.put(row, rowMap);
    }
    rowMap.put(col, value);

    NavigableMap<Integer, T> colMap = colEntries.get(col);
    if (colMap == null) {
      colMap = new TreeMap<Integer, T>();
      colEntries.put(col, colMap);
    }
    colMap.put(row, value);
  }

  private void removeCell(int row, int col) {
    NavigableMap<Integer, T> rowMap = rowEntries.get(row);
    if (rowMap != null) {
      rowMap.remove(col);
      if (rowMap.isEmpty()) {
        rowEntries.remove(row);
      }
    }

    NavigableMap<Integer, T> colMap = colEntries.get(col);
    if (colMap != null) {
      colMap.remove(row);
      if (colMap.isEmpty()) {
        colEntries.remove(col);
      }
    }
  }

  private boolean isZero(T value) {
    if (value == null) {
      return true;
    }
    return zero == null ? value == null : zero.equals(value);
  }

  private void validateCell(int row, int col) {
    validateRow(row);
    validateCol(col);
  }

  private void validateRow(int row) {
    if (row < 0 || row >= rows) {
      throw new IllegalArgumentException("invalid row");
    }
  }

  private void validateCol(int col) {
    if (col < 0 || col >= cols) {
      throw new IllegalArgumentException("invalid column");
    }
  }

}
