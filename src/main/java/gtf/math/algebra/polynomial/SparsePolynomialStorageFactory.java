package gtf.math.algebra.polynomial;

import java.util.List;


/**
 * Factory to allocate sparse polynomial coefficient storage.
 *
 * @param <T> coefficient type
 *
 * @author gtf
 */
public class SparsePolynomialStorageFactory<T>
    implements PolynomialStorageFactory<T> {

  @Override
  public PolynomialStorageModel<T> createStorage(List<T> coefficients,
                                                 T zero) {
    return new SparsePolynomialStorageModel<T>(coefficients, zero);
  }
}
