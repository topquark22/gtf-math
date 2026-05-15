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

      MatrixOverModule<T, T, R, Module<T, T, R>> minorWithColumn =
          deleteCol(submatrix(1, size - 1, 0, size), col);

      Matrix<T, R> minor = (Matrix<T, R>) minorWithColumn;

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
    // TODO Auto-generated method stub
    return null;
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
    // TODO Auto-generated method stub
    return null;
  }

  public Matrix<T, R> add(Matrix<T, R> arg) {
    return (Matrix<T, R>) super.add(arg);
  }
    
    
}
