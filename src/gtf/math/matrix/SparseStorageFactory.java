package gtf.math.matrix;

/**
 * Factory to allocate storage for matrix elements, backed by
 * sparse storage.
 * 
 * @author gtf
 *
 * @param <T> the type of the matrix elements
 */
public class SparseStorageFactory<T> implements StorageFactory<T> {

  @Override
  public StorageModel<T> createStorage(int rows, int cols, T zero) {
    return new SparseStorageModel<T>(rows, cols, zero);
  }
}
