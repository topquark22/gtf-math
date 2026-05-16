package gtf.math.algebra.polynomial;

import java.util.Map;
import java.util.TreeMap;


/**
 * Sparse polynomial coefficient storage.
 *
 * @param <T> coefficient type
 *
 * @author gtf
 */
class SparsePolynomialStorageModel<T>
    implements PolynomialStorageModel<T> {

  private final TreeMap<Integer, T> coefficients;
  private final T zero;

  SparsePolynomialStorageModel(java.util.List<T> values, T zero) {
    this.zero = zero;
    coefficients = new TreeMap<Integer, T>();

    for (int i = 0; i < values.size(); i++) {
      T value = values.get(i);
      if (!zero.equals(value)) {
        coefficients.put(i, value);
      }
    }
  }

  @Override
  public T coefficient(int degree) {
    T value = coefficients.get(degree);
    return value == null ? zero : value;
  }

  @Override
  public int degree() {
    return coefficients.isEmpty() ? -1 : coefficients.lastKey();
  }

  @Override
  public int nextDegree(int degree) {
    for (Map.Entry<Integer, T> entry : coefficients.tailMap(degree + 1).entrySet()) {
      return entry.getKey();
    }
    return -1;
  }
}
