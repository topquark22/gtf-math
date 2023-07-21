package gtf.math.matrix;

/**
 * Factory to allocate storage for matrix elements, backed by
 * an array.
 * 
 * @author gtf
 *
 * @param <T> the type of the matrix elements
 */
public class ArrayStorageFactory<T> implements StorageFactory<T> {

  @Override
  public StorageModel<T> createStorage(int rows, int cols, T zero) {
    return new ArrayStorageModel<T>(rows, cols, zero);
  }
}
