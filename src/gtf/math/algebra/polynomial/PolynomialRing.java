package gtf.math.algebra.polynomial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gtf.math.algebra.Field;
import gtf.math.algebra.Ring;


/**
 * Polynomial ring F[x] over a field F.
 *
 * @param <T> coefficient type
 * @param <F> field type
 *
 * @author gtf
 */
public class PolynomialRing<T, F extends Field<T>>
    implements Ring<Polynomial<T>> {

  private final F field;
  private final PolynomialStorageFactory<T> storageFactory;

  public PolynomialRing(F field) {
    this(field, new ArrayPolynomialStorageFactory<T>());
  }

  public PolynomialRing(F field,
                        PolynomialStorageFactory<T> storageFactory) {
    if (field == null) {
      throw new NullPointerException("field");
    }
    if (storageFactory == null) {
      throw new NullPointerException("storageFactory");
    }
    this.field = field;
    this.storageFactory = storageFactory;
  }

  public F field() {
    return field;
  }

  public PolynomialStorageFactory<T> storageFactory() {
    return storageFactory;
  }

  public Polynomial<T> polynomial(List<T> coefficients) {
    return new Polynomial<T>(coefficients,
        field.zero(),
        storageFactory);
  }

  @SafeVarargs
  public final Polynomial<T> polynomial(T... coefficients) {
    return polynomial(Arrays.asList(coefficients));
  }

  @Override
  public Polynomial<T> zero() {
    return polynomial(new ArrayList<T>());
  }

  @Override
  public Polynomial<T> id() {
    return polynomial(field.id());
  }

  @Override
  public Polynomial<T> add(Polynomial<T> left, Polynomial<T> right) {
    int size = Math.max(left.degree(), right.degree()) + 1;
    List<T> coefficients = new ArrayList<T>(size);

    for (int i = 0; i < size; i++) {
      coefficients.add(field.add(left.coefficient(i), right.coefficient(i)));
    }

    return polynomial(coefficients);
  }

  @Override
  public Polynomial<T> neg(Polynomial<T> arg) {
    List<T> coefficients = new ArrayList<T>(arg.degree() + 1);
    for (int i = 0; i <= arg.degree(); i++) {
      coefficients.add(field.neg(arg.coefficient(i)));
    }
    return polynomial(coefficients);
  }

  @Override
  public Polynomial<T> mul(Polynomial<T> left, Polynomial<T> right) {
    if (left.isZero() || right.isZero()) {
      return zero();
    }

    int degree = left.degree() + right.degree();
    List<T> coefficients = new ArrayList<T>(degree + 1);

    for (int i = 0; i <= degree; i++) {
      coefficients.add(field.zero());
    }

    for (int i = 0; i <= left.degree(); i++) {
      for (int j = 0; j <= right.degree(); j++) {
        T term = field.mul(left.coefficient(i), right.coefficient(j));
        coefficients.set(i + j,
            field.add(coefficients.get(i + j), term));
      }
    }

    return polynomial(coefficients);
  }

  public Polynomial<T> scalarMultiply(T scalar, Polynomial<T> polynomial) {
    List<T> coefficients = new ArrayList<T>(polynomial.degree() + 1);
    for (int i = 0; i <= polynomial.degree(); i++) {
      coefficients.add(field.mul(scalar, polynomial.coefficient(i)));
    }
    return this.polynomial(coefficients);
  }

  public Polynomial<T> monic(Polynomial<T> polynomial) {
    if (polynomial.isZero()) {
      return polynomial;
    }
    return scalarMultiply(field.inv(polynomial.leadingCoefficient()), polynomial);
  }

  public Polynomial<T> gcd(Polynomial<T> left, Polynomial<T> right) {
    Polynomial<T> a = left;
    Polynomial<T> b = right;

    while (!b.isZero()) {
      Polynomial<T>[] result = divideAndRemainder(a, b);
      a = b;
      b = result[1];
    }

    return monic(a);
  }

  public Polynomial<T>[] divideAndRemainder(Polynomial<T> numerator,
                                            Polynomial<T> denominator) {
    if (denominator.isZero()) {
      throw new ArithmeticException("division by zero polynomial");
    }

    Polynomial<T> remainder = numerator;
    Polynomial<T> quotient = zero();

    while (!remainder.isZero()
        && remainder.degree() >= denominator.degree()) {

      int degreeDifference =
          remainder.degree() - denominator.degree();

      T scale = field.divide(remainder.leadingCoefficient(),
          denominator.leadingCoefficient());

      List<T> monomialCoefficients =
          new ArrayList<T>(degreeDifference + 1);

      for (int i = 0; i < degreeDifference; i++) {
        monomialCoefficients.add(field.zero());
      }
      monomialCoefficients.add(scale);

      Polynomial<T> monomial = polynomial(monomialCoefficients);

      quotient = add(quotient, monomial);
      remainder = add(remainder,
          neg(mul(monomial, denominator)));
    }

    @SuppressWarnings("unchecked")
    Polynomial<T>[] result = new Polynomial[] {
        quotient,
        remainder
    };

    return result;
  }

  @Override
  public Polynomial<T> divide(Polynomial<T> numerator,
                              Polynomial<T> denominator) {
    Polynomial<T>[] result = divideAndRemainder(numerator, denominator);
    if (!result[1].isZero()) {
      throw new ArithmeticException("polynomial division is not exact");
    }
    return result[0];
  }

  @Override
  public Ring<Polynomial<T>> ring() {
    return this;
  }
}
