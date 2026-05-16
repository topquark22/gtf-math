package gtf.math.algebra.polynomial;

import java.util.List;


/**
 * Factory to allocate array-backed polynomial coefficient storage.
 *
 * @param <T> coefficient type
 *
 * @author gtf
 */
public class ArrayPolynomialStorageFactory<T>
    implements PolynomialStorageFactory<T> {

  @Override
  public PolynomialStorageModel<T> createStorage(List<T> coefficients,
                                                 T zero) {
    return new ArrayPolynomialStorageModel<T>(coefficients, zero);
  }
}
