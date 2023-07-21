package gtf.math.matrix;

/**
 * Factory to allocate storage for matrix elements.
 * 
 * @author gtf
 *
 * @param <T> the type of the matrix elements
 */
public interface StorageFactory<T> {
  
  /**
   * Allocates storage for matrix elements.
   * 
   * @param rows The number of rows
   * @param cols The number of columns
   * @param zero The value representing zero for this matrix's elements
   * @return storage for the matrix elements
   */
  StorageModel<T> createStorage(int rows, int cols, T zero);
}
