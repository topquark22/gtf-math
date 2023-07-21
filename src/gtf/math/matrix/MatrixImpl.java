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
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Matrix<T, R> inverse() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Matrix<T, R> multiply(Matrix<T, R> arg) {
    // TODO Auto-generated method stub
    return null;
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
