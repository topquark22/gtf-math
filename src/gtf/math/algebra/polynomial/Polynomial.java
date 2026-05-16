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

  private final List<T> coefficients;
  private final T zero;

  Polynomial(List<T> coefficients, T zero) {
    if (coefficients == null) {
      throw new NullPointerException("coefficients");
    }
    if (zero == null) {
      throw new NullPointerException("zero");
    }

    int degree = coefficients.size() - 1;
    while (degree >= 0 && zero.equals(coefficients.get(degree))) {
      degree--;
    }

    List<T> copy = new ArrayList<T>();
    for (int i = 0; i <= degree; i++) {
      T coefficient = coefficients.get(i);
      if (coefficient == null) {
        throw new NullPointerException("coefficient " + i);
      }
      copy.add(coefficient);
    }
    this.coefficients = Collections.unmodifiableList(copy);
    this.zero = zero;
  }

  public int degree() {
    return coefficients.size() - 1;
  }

  public boolean isZero() {
    return coefficients.isEmpty();
  }

  public T coefficient(int degree) {
    if (degree < 0) {
      throw new IllegalArgumentException("degree must be non-negative");
    }
    if (degree >= coefficients.size()) {
      return zero;
    }
    return coefficients.get(degree);
  }

  public T leadingCoefficient() {
    if (isZero()) {
      return zero;
    }
    return coefficients.get(coefficients.size() - 1);
  }

  public List<T> coefficients() {
    return coefficients;
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
    return coefficients.equals(other.coefficients);
  }

  @Override
  public int hashCode() {
    return coefficients.hashCode();
  }

  @Override
  public String toString() {
    if (isZero()) {
      return "0";
    }
    StringBuilder builder = new StringBuilder();
    for (int i = coefficients.size() - 1; i >= 0; i--) {
      T coefficient = coefficients.get(i);
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
