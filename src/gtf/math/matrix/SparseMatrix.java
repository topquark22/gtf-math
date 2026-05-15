package gtf.math.matrix;

import gtf.math.algebra.Ring;

/**
 * A matrix over a ring backed by sparse storage.
 *
 * <p>
 * The algebraic behaviour is inherited from {@link MatrixImpl}; this class
 * simply selects {@link SparseStorageFactory} as the backing storage model.
 * Unstored entries are interpreted as the zero element of the underlying ring.
 * </p>
 *
 * @author gtf
 *
 * @param <T> the type of the matrix/ring elements
 * @param <R> the type of the ring
 */
public class SparseMatrix<T, R extends Ring<T>> extends MatrixImpl<T, R> {

  /**
   * Constructs a sparse matrix over the given ring.
   *
   * @param ring the ring over which the matrix is defined
   * @param rows the number of rows
   * @param cols the number of columns
   */
  public SparseMatrix(R ring, int rows, int cols) {
    super(ring, rows, cols, new SparseStorageFactory<T>());
  }
}
