package gtf.math.algebra.polynomial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Immutable polynomial with coefficients stored in ascending degree order.
 *
 * <p>
 * The coefficient at index 0 is the constant coefficient, index 1 is the
 * coefficient of x, and so on. Arithmetic is supplied by {@link PolynomialRing},
 * which knows the coefficient field.
 * </p>
 *
 * @param <T> the coefficient type
 *
 * @author gtf
 */
public final class Polynomial<T> {

  private final PolynomialStorageModel<T> storage;
  private final T zero;

  Polynomial(List<T> coefficients, T zero,
      PolynomialStorageFactory<T> storageFactory) {
    if (coefficients == null) {
      throw new NullPointerException("coefficients");
    }
    if (zero == null) {
      throw new NullPointerException("zero");
    }
    if (storageFactory == null) {
      throw new NullPointerException("storageFactory");
    }

    for (int i = 0; i < coefficients.size(); i++) {
      if (coefficients.get(i) == null) {
        throw new NullPointerException("coefficient " + i);
      }
    }

    this.zero = zero;
    this.storage = storageFactory.createStorage(coefficients, zero);
  }

  public int degree() {
    return storage.degree();
  }

  public boolean isZero() {
    return degree() < 0;
  }

  public T coefficient(int degree) {
    if (degree < 0) {
      throw new IllegalArgumentException("degree must be non-negative");
    }
    return storage.coefficient(degree);
  }

  public T leadingCoefficient() {
    if (isZero()) {
      return zero;
    }
    return coefficient(degree());
  }

  public int nextDegree(int degree) {
    return storage.nextDegree(degree);
  }

  public List<T> coefficients() {
    List<T> coefficients = new ArrayList<T>(degree() + 1);
    for (int i = 0; i <= degree(); i++) {
      coefficients.add(coefficient(i));
    }
    return Collections.unmodifiableList(coefficients);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Polynomial<?>)) {
      return false;
    }
    Polynomial<?> other = (Polynomial<?>) obj;
    return coefficients().equals(other.coefficients());
  }

  @Override
  public int hashCode() {
    return coefficients().hashCode();
  }

  @Override
  public String toString() {
    if (isZero()) {
      return "0";
    }
    StringBuilder builder = new StringBuilder();
    for (int i = degree(); i >= 0; i--) {
      T coefficient = coefficient(i);
      if (zero.equals(coefficient)) {
        continue;
      }
      if (builder.length() > 0) {
        builder.append(" + ");
      }
      builder.append(coefficient);
      if (i > 0) {
        builder.append("*x");
        if (i > 1) {
          builder.append('^').append(i);
        }
      }
    }
    return builder.toString();
  }
}
