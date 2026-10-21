package gtf.math.algebra.polynomial;

import java.util.List;


/**
 * Factory to allocate storage for polynomial coefficients.
 *
 * @param <T> the coefficient type
 *
 * @author gtf
 */
public interface PolynomialStorageFactory<T> {

  /**
   * Allocates storage for polynomial coefficients.
   *
   * <p>
   * Coefficients are supplied in ascending degree order.
   * </p>
   *
   * @param coefficients the coefficients
   * @param zero the additive identity for the coefficient field
   * @return storage for the polynomial coefficients
   */
  PolynomialStorageModel<T> createStorage(List<T> coefficients, T zero);
}
