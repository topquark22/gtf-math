package gtf.math.algebra.polynomial;

import java.util.List;


/**
 * Dense array-backed storage for polynomial coefficients.
 *
 * @param <T> coefficient type
 *
 * @author gtf
 */
public class DensePolynomialStorageFactory<T>
    implements PolynomialStorageFactory<T> {

  @Override
  public PolynomialStorageModel<T> createStorage(List<T> coefficients,
                                                 T zero) {
    return new DensePolynomialStorageModel<T>(coefficients, zero);
  }
}
