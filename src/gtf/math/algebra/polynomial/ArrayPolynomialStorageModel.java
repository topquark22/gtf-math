package gtf.math.algebra.polynomial;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * Array-backed polynomial coefficient storage.
 *
 * @param <T> coefficient type
 *
 * @author gtf
 */
class ArrayPolynomialStorageModel<T>
    implements PolynomialStorageModel<T> {

  private final List<T> coefficients;
  private final T zero;
  private final int degree;

  ArrayPolynomialStorageModel(List<T> coefficients, T zero) {
    this.zero = zero;

    int degree = coefficients.size() - 1;
    while (degree >= 0 && zero.equals(coefficients.get(degree))) {
      degree--;
    }

    this.degree = degree;

    this.coefficients = new ArrayList<T>(degree + 1);
    for (int i = 0; i <= degree; i++) {
      this.coefficients.add(coefficients.get(i));
    }
  }

  @Override
  public T coefficient(int degree) {
    if (degree < 0 || degree >= coefficients.size()) {
      return zero;
    }
    return coefficients.get(degree);
  }

  @Override
  public int degree() {
    return degree;
  }

  @Override
  public int nextDegree(int degree) {
    for (int i = degree + 1; i < coefficients.size(); i++) {
      if (!zero.equals(coefficients.get(i))) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public PolynomialTermIterator<T> iterator() {
    return new PolynomialTermIterator<T>() {
      private int nextDegree = nextDegree(-1);

      @Override
      public boolean hasNext() {
        return nextDegree >= 0;
      }

      @Override
      public PolynomialTerm<T> next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }
        int degree = nextDegree;
        T coefficient = coefficient(degree);
        nextDegree = nextDegree(degree);
        return new PolynomialTerm<T>(degree, coefficient);
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }
}
