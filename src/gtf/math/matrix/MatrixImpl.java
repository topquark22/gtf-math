package gtf.math.matrix;

import gtf.math.algebra.Ring;

/**
 * Implementation of a matrix over a ring.
 * Note that for some operations such as row-reduce and inverse(),
 * the elements are expected to have a multiplicative inverse.
 * If they do not, then an UnsupportedOperationException is thrown.
 *
 * @author gtf
 *
 * @param <T> The type of the matrix elements
 * @param <R> the ring over which the elements are defined
 */
public class MatrixImpl<T, R extends Ring<T>> implements Matrix<T, R> {

  private final R ring;
  protected final StorageFactory<T> storageFactory;
  private final StorageModel<T> entries;

  public MatrixImpl(R ring, int rows, int cols,
      StorageFactory<T> storageFactory) {
    if (ring == null) {
      throw new NullPointerException("ring");
    }
    if (storageFactory == null) {
      throw new NullPointerException("storageFactory");
    }
    this.ring = ring;
    this.storageFactory = storageFactory;
    this.entries = storageFactory.createStorage(rows, cols, ring.zero());
  }

  @Override
  public R getRing() {
    return ring;
  }

  @Override
  public int getRows() {
    return entries.rows();
  }

  @Override
  public int getCols() {
    return entries.cols();
  }

  @Override
  public T getCell(int row, int col) {
    return entries.getCell(row, col);
  }

  @Override
  public void setCell(int row, int col, T value) {
    entries.setCell(row, col, value);
  }

  @Override
  public Matrix<T, R> add(Matrix<T, R> arg) {
    if (arg == null) {
      throw new NullPointerException("arg");
    }
    if (getRows() != arg.getRows() || getCols() != arg.getCols()) {
      throw new IllegalArgumentException("incompatible dimensions");
    }

    Matrix<T, R> dest = new MatrixImpl<T, R>(
        ring, getRows(), getCols(), storageFactory);

    for (int row = 0; row < getRows(); row++) {
      for (int col = 0; col < getCols(); col++) {
        dest.setCell(row, col,
            ring.add(getCell(row, col), arg.getCell(row, col)));
      }
    }

    return dest;
  }

  @Override
  public Matrix<T, R> transpose() {
    Matrix<T, R> dest = new MatrixImpl<T, R>(
        ring, getCols(), getRows(), storageFactory);

    for (int row = 0; row < getRows(); row++) {
      for (int col = 0; col < getCols(); col++) {
        dest.setCell(col, row, getCell(row, col));
      }
    }

    return dest;
  }

  @Override
  public Matrix<T, R> submatrix(
      int rowStart,
      int numRows,
      int colStart,
      int numCols) {

    validateSubmatrix(rowStart, numRows, colStart, numCols);

    Matrix<T, R> dest = new MatrixImpl<T, R>(
        ring, numRows, numCols, storageFactory);

    for (int row = 0; row < numRows; row++) {
      for (int col = 0; col < numCols; col++) {
        dest.setCell(row, col, getCell(rowStart + row, colStart + col));
      }
    }

    return dest;
  }

  @Override
  public Matrix<T, R> catenate(Matrix<T, R> matrix) {
    if (matrix == null) {
      throw new NullPointerException("matrix");
    }
    if (getRows() != matrix.getRows()) {
      throw new IllegalArgumentException(
          "can't catenate matrix with different number of rows");
    }

    Matrix<T, R> dest = new MatrixImpl<T, R>(
        ring, getRows(), getCols() + matrix.getCols(), storageFactory);

    copyCells(this, dest, 0);
    copyCells(matrix, dest, getCols());

    return dest;
  }

  @Override
  public Matrix<T, R> deleteRow(Matrix<T, R> matrix, int row) {
    if (matrix == null) {
      throw new NullPointerException("matrix");
    }
    if (row < 0 || row >= matrix.getRows()) {
      throw new ArrayIndexOutOfBoundsException(row);
    }

    Matrix<T, R> dest = new MatrixImpl<T, R>(
        ring, matrix.getRows() - 1, matrix.getCols(), storageFactory);

    int destRow = 0;
    for (int sourceRow = 0; sourceRow < matrix.getRows(); sourceRow++) {
      if (sourceRow != row) {
        for (int col = 0; col < matrix.getCols(); col++) {
          dest.setCell(destRow, col, matrix.getCell(sourceRow, col));
        }
        destRow++;
      }
    }

    return dest;
  }

  @Override
  public Matrix<T, R> deleteCol(Matrix<T, R> matrix, int col) {
    if (matrix == null) {
      throw new NullPointerException("matrix");
    }
    if (col < 0 || col >= matrix.getCols()) {
      throw new ArrayIndexOutOfBoundsException(col);
    }

    Matrix<T, R> dest = new MatrixImpl<T, R>(
        ring, matrix.getRows(), matrix.getCols() - 1, storageFactory);

    for (int row = 0; row < matrix.getRows(); row++) {
      int destCol = 0;
      for (int sourceCol = 0; sourceCol < matrix.getCols(); sourceCol++) {
        if (sourceCol != col) {
          dest.setCell(row, destCol, matrix.getCell(row, sourceCol));
          destCol++;
        }
      }
    }

    return dest;
  }

  @Override
  public void scalarMultiply(T scalar) {
    for (int row = 0; row < getRows(); row++) {
      scalarMultiplyRow(row, scalar);
    }
  }

  @Override
  public void scalarMultiplyRow(int row, T scalar) {
    validateRow(row);

    for (int col = 0; col < getCols(); col++) {
      setCell(row, col, ring.mul(scalar, getCell(row, col)));
    }
  }

  @Override
  public void addScalarMultiple(int sourceRow, int destRow, T scalar) {
    validateRow(sourceRow);
    validateRow(destRow);

    for (int col = 0; col < getCols(); col++) {
      T sourceEntry = getCell(sourceRow, col);
      T destEntry = getCell(destRow, col);
      setCell(destRow, col,
          ring.add(destEntry, ring.mul(scalar, sourceEntry)));
    }
  }

  @Override
  public void swapRows(int row1, int row2) {
    validateRow(row1);
    validateRow(row2);

    if (row1 == row2) {
      return;
    }

    for (int col = 0; col < getCols(); col++) {
      T temp = getCell(row1, col);
      setCell(row1, col, getCell(row2, col));
      setCell(row2, col, temp);
    }
  }

  @Override
  public T trace() {
    if (!isSquare()) {
      throw new UnsupportedOperationException("matrix must be square");
    }
    T trace = ring.zero();
    for (int i = 0; i < getRows(); i++) {
      trace = ring.add(trace, getCell(i, i));
    }
    return trace;
  }

  @Override
  public T determinant() {
    if (!isSquare()) {
      throw new UnsupportedOperationException("matrix must be square");
    }
    final int size = getRows();
    if (size == 0) {
      return ring.id();
    }
    if (size == 1) {
      return getCell(0, 0);
    }
    if (size == 2) {
      return ring.add(
          ring.mul(getCell(0, 0), getCell(1, 1)),
          ring.neg(ring.mul(getCell(0, 1), getCell(1, 0))));
    }
    T det = ring.zero();
    for (int col = 0; col < size; col++) {
      T entry = getCell(0, col);
      Matrix<T, R> minor = cofactorSubmatrix(0, col);
      T term = ring.mul(entry, minor.determinant());
      if ((col & 1) == 1) {
        term = ring.neg(term);
      }
      det = ring.add(det, term);
    }
    return det;
  }

  @Override
  public Matrix<T, R> inverse() {
    if (!isSquare()) {
      throw new UnsupportedOperationException("matrix must be square");
    }
    int size = getRows();
    MatrixImpl<T, R> augmented = new MatrixImpl<T, R>(
        ring, size, 2 * size, storageFactory);
    Matrix<T, R> identity = identityMatrix(size);
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        augmented.setCell(row, col, getCell(row, col));
        augmented.setCell(row, size + col, identity.getCell(row, col));
      }
    }
    T scale = augmented.rowReduce(size);
    if (isZero(scale) || !leftHalfIsIdentity(augmented, size)) {
      throw new ArithmeticException("matrix is not invertible");
    }
    Matrix<T, R> inverse = new MatrixImpl<T, R>(ring, size, size, storageFactory);
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        inverse.setCell(row, col, augmented.getCell(row, size + col));
      }
    }
    return inverse;
  }

  @Override
  public Matrix<T, R> multiply(Matrix<T, R> arg) {
    if (arg == null) {
      throw new NullPointerException("arg");
    }
    if (getCols() != arg.getRows()) {
      throw new IllegalArgumentException("incompatible dimensions");
    }
    if (isIdentity()) {
      return copyOf(arg);
    }
    if (arg.isIdentity()) {
      return copyOf(this);
    }
    Matrix<T, R> dest = new MatrixImpl<T, R>(
        ring, getRows(), arg.getCols(), storageFactory);
    for (int row = 0; row < getRows(); row++) {
      for (int col = 0; col < arg.getCols(); col++) {
        T sum = ring.zero();
        for (int index = 0; index < getCols(); index++) {
          sum = ring.add(sum,
              ring.mul(getCell(row, index), arg.getCell(index, col)));
        }
        dest.setCell(row, col, sum);
      }
    }
    return dest;
  }

  @Override
  public T rowReduce(int workingColumns) {
    if (workingColumns < 0 || workingColumns > getCols()) {
      throw new IllegalArgumentException("invalid number of working columns");
    }
    T factorReciprocal = ring.id();
    int pivotRow = 0;
    for (int col = 0; col < workingColumns && pivotRow < getRows(); col++) {
      int pivot = findPivot(pivotRow, col);
      if (pivot < 0) {
        continue;
      }
      if (pivot != pivotRow) {
        swapRows(pivot, pivotRow);
        factorReciprocal = ring.neg(factorReciprocal);
      }
      T pivotValue = getCell(pivotRow, col);
      factorReciprocal = ring.mul(factorReciprocal, pivotValue);
      scalarMultiplyRow(pivotRow, ring.divide(ring.id(), pivotValue));
      for (int row = 0; row < getRows(); row++) {
        if (row != pivotRow) {
          T entry = getCell(row, col);
          if (!isZero(entry)) {
            addScalarMultiple(pivotRow, row, ring.neg(entry));
          }
        }
      }
      pivotRow++;
    }
    if (pivotRow < getRows()) {
      return ring.zero();
    }
    return factorReciprocal;
  }

  private Matrix<T, R> cofactorSubmatrix(int rowToDelete, int colToDelete) {
    Matrix<T, R> minor = new MatrixImpl<T, R>(
        ring, getRows() - 1, getCols() - 1, storageFactory);
    int destRow = 0;
    for (int row = 0; row < getRows(); row++) {
      if (row == rowToDelete) {
        continue;
      }
      int destCol = 0;
      for (int col = 0; col < getCols(); col++) {
        if (col == colToDelete) {
          continue;
        }
        minor.setCell(destRow, destCol, getCell(row, col));
        destCol++;
      }
      destRow++;
    }
    return minor;
  }

  private int findPivot(int startRow, int col) {
    for (int row = startRow; row < getRows(); row++) {
      if (!isZero(getCell(row, col))) {
        return row;
      }
    }
    return -1;
  }

  private boolean leftHalfIsIdentity(Matrix<T, R> matrix, int size) {
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        T expected = row == col ? ring.id() : ring.zero();
        if (!expected.equals(matrix.getCell(row, col))) {
          return false;
        }
      }
    }
    return true;
  }

  private boolean isZero(T value) {
    T zero = ring.zero();
    return zero == null ? value == null : zero.equals(value);
  }

  private Matrix<T, R> identityMatrix(int size) {
    Matrix<T, R> identity = new MatrixImpl<T, R>(ring, size, size, storageFactory);
    T one = ring.id();
    for (int i = 0; i < size; i++) {
      identity.setCell(i, i, one);
    }
    return identity;
  }

  private Matrix<T, R> copyOf(Matrix<T, R> matrix) {
    Matrix<T, R> copy = new MatrixImpl<T, R>(
        ring, matrix.getRows(), matrix.getCols(), storageFactory);
    copyCells(matrix, copy, 0);
    return copy;
  }

  private void copyCells(
      Matrix<T, R> source,
      Matrix<T, R> dest,
      int colOffset) {

    for (int row = 0; row < source.getRows(); row++) {
      for (int col = 0; col < source.getCols(); col++) {
        dest.setCell(row, colOffset + col, source.getCell(row, col));
      }
    }
  }

  private void validateSubmatrix(
      int rowStart,
      int numRows,
      int colStart,
      int numCols) {

    if (numRows < 0) {
      throw new NegativeArraySizeException("numRows");
    }
    if (numCols < 0) {
      throw new NegativeArraySizeException("numCols");
    }
    if (rowStart < 0 || colStart < 0
        || rowStart + numRows > getRows()
        || colStart + numCols > getCols()) {
      throw new ArrayIndexOutOfBoundsException("invalid submatrix range");
    }
  }

  private void validateRow(int row) {
    if (row < 0 || row >= getRows()) {
      throw new ArrayIndexOutOfBoundsException(row);
    }
  }
}
