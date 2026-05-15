package gtf.math.matrix;

import gtf.math.algebra.Module;
import gtf.math.algebra.Ring;

/**
 * Implementation of a matrix over a ring.
 * 
 * @author gtf
 *
 * @param <T> The type of the matrix elements
 * @param <R> the ring over which the elements are defined
 */
public class MatrixImpl<T, R extends Ring<T>>
  //extends MatrixOverModuleImpl<T, T, R, R>
    extends MatrixOverModuleImpl<T, T, R, Module<T, T, R>>
    implements Matrix<T, R> {

  public MatrixImpl(R ring, int rows, int cols,
      StorageFactory<T> storageFactory) {
    // Warning note: Ring<T> extends Module<T, T, Ring<T>>.
    // But suppose you had a sub-type RR which extends R.
    // Then RR is a Module<T, T, RR>, not a Module<T, T, R>.
    // However, in this case it is safe because the parameter
    // "ring" is a Module<T, T, R>.
    super(ring, (Module<T, T, R>) ring, rows, cols, storageFactory);
  }

  @Override
  public T trace() {
    if (!isSquare()) {
      throw new UnsupportedOperationException("matrix must be square");
    }
    R ring = getRing();
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
    final R ring = getRing();
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
        getRing(), size, 2 * size, storageFactory);
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
    Matrix<T, R> inverse = new MatrixImpl<T, R>(getRing(), size, size, storageFactory);
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
    R ring = getRing();
    if (isIdentity()) {
      return copyOf(arg);
    }
    if (arg.isIdentity()) {
      return copyOf(this);
    }
    Matrix<T, R> dest = new MatrixImpl<T, R>(ring, getRows(), arg.getCols(), storageFactory);
    for (int row = 0; row < getRows(); row++) {
      for (int col = 0; col < arg.getCols(); col++) {
        T sum = ring.zero();
        for (int index = 0; index < getCols(); index++) {
          sum = ring.add(sum, ring.mul(getCell(row, index), arg.getCell(index, col)));
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
    R ring = getRing();
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

  public Matrix<T, R> add(Matrix<T, R> arg) {
    return (Matrix<T, R>) super.add(arg);
  }

  private Matrix<T, R> cofactorSubmatrix(int rowToDelete, int colToDelete) {
    Matrix<T, R> minor = new MatrixImpl<T, R>(
        getRing(), getRows() - 1, getCols() - 1, storageFactory);
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
    R ring = getRing();
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
    T zero = getRing().zero();
    return zero == null ? value == null : zero.equals(value);
  }

  private Matrix<T, R> identityMatrix(int size) {
    Matrix<T, R> identity = new MatrixImpl<T, R>(getRing(), size, size, storageFactory);
    T one = getRing().id();
    for (int i = 0; i < size; i++) {
      identity.setCell(i, i, one);
    }
    return identity;
  }

  private Matrix<T, R> copyOf(Matrix<T, R> matrix) {
    Matrix<T, R> copy = new MatrixImpl<T, R>(getRing(), matrix.getRows(), matrix.getCols(), storageFactory);
    for (int row = 0; row < matrix.getRows(); row++) {
      for (int col = 0; col < matrix.getCols(); col++) {
        copy.setCell(row, col, matrix.getCell(row, col));
      }
    }
    return copy;
  }
}
