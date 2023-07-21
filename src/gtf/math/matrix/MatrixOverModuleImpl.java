package gtf.math.matrix;

import gtf.math.algebra.Module;
import gtf.math.algebra.Ring;

/**
 * A matrix over a module.
 * 
 * @author gtf
 *
 * @param <T> the type of the matrix elements
 * @param <S> the type of the ring elements
 * @param <M> the module over which the matrix is defined
 * @param <R> the ring over which the module M is defined.
 */
public class MatrixOverModuleImpl<T, S, R extends Ring<S>, M extends Module<T, S, R>> implements MatrixOverModule<T, S, R, M> {

  private final R ring;
  private final M module;
  protected final StorageFactory<T> storageFactory;
  private final StorageModel<T> entries;
  
  public MatrixOverModuleImpl(R ring, M module, int rows, int cols, StorageFactory<T> storageFactory) {
    this.ring = ring;
    this.module = module;
    this.storageFactory = storageFactory;
    this.entries = storageFactory.createStorage(rows, cols, module.zero());
  }

  @Override
  public void addScalarMultiple(int sourceRow, int destRow, S scalar) {
    // FIXME this algorithm sucks for sparse matrices. Use storage model
    for (int col = 0; col < entries.cols(); col++) {
      T sourceEntry = getCell(sourceRow, col);
      T entry = getCell(destRow, col);
      entries.setCell(destRow, col, module.add(entry, module.mul(scalar, sourceEntry)));
    }
  }

  /**
   * Catenate another matrix on the right with this matrix.
   * @return an ArrayMatrix instance.
   */
  @Override
  public MatrixOverModule<T, S, R, M> catenate(MatrixOverModule<T, S, R, M> matrix) {
    if (getRows() != matrix.getRows()) {
      throw new IllegalArgumentException("can't catenate matrix with different number of rows");
    }
    
    MatrixOverModuleImpl<T, S, R, M> dest = new MatrixOverModuleImpl<T, S, R, M>(ring, module, getRows(), getCols() + matrix.getCols(), storageFactory);
    int otherCols = matrix.getCols();
    // FIXME this algorithm sucks for sparse matrices. Use storage model
    for(int row = 0; row < getRows(); row++) {
      for (int col = 0; col < getCols(); col++) {
        dest.setCell(row, col, getCell(row, col));
      }
      for (int col = 0; col < otherCols; col++) {
        dest.setCell(row, getCols() + col, matrix.getCell(row, col));
      }
    }
    return dest;
  }

  @Override
  public int getCols() {
    return entries.cols();
  }

  @Override
  public R getRing() {
    return ring;
  }

  @Override
  public M getModule() {
    return module;
  }
  
  @Override
  public int getRows() {
    return entries.rows();
  }

  @Override
  public void scalarMultiply(S scalar) {
    // FIXME this algorithm sucks for sparse matrices. Use storage model
    for (int row = 0; row < getRows(); row++) {
      scalarMultiplyRow(row, scalar);
    }
  }
  
  @Override
  public void scalarMultiplyRow(int row, S scalar) {
    // FIXME this algorithm sucks for sparse matrices. Use storage model
    for (int col = 0; col < getCols(); col++) {
      T entry = getCell(row, col);
      setCell(row, col, module.mul(scalar, entry));
    }
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
  public MatrixOverModule<T, S, R, M> submatrix(int rowStart, int numRows, int colStart, int numCols) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void swapRows(int row1, int row2) {
    // TODO Auto-generated method stub

  }

  @Override
  public MatrixOverModule<T, S, R, M> transpose() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public MatrixOverModule<T, S, R, M> deleteCol(MatrixOverModule<T, S, R, M> matrix, int col) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public MatrixOverModule<T, S, R, M> deleteRow(MatrixOverModule<T, S, R, M> matrix, int row) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public MatrixOverModule<T, S, R, M> add(MatrixOverModule<T, S, R, M> arg) {
    if (getRows() != arg.getRows() || getCols() != arg.getCols()) {
      throw new IllegalArgumentException("incompatible dimensions");
    }
    MatrixOverModule<T, S, R, M> dest = new MatrixOverModuleImpl<T, S, R, M>(ring, module, getRows(), getCols(), storageFactory);
    // FIXME this algorithm sucks for sparse matrices. Use storage model
    for (int row = 0; row < getRows(); row++) {
      for (int col = 0; col < getCols(); col++) {
        dest.setCell(row, col, module.add(getCell(row, col), arg.getCell(row, col)));
      }
    }
    return dest;
  }

}
