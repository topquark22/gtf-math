package gtf.math.algebra.ring;

import gtf.math.algebra.Ring;
import gtf.math.matrix.SparseVector;


/**
 * Ring of polynomials over a ring.
 * 
 * TODO: Simply create a Polynomial<T, R> class, and let
 * all operations be generic here.
 *
 * @author gtf
 *
 * @param <T> Type of elements in the base ring
 * @param <R> the base ring
 */
public class PolynomialRing<T, R extends Ring<T>>
    implements Ring<PolynomialRing<T, R>.Polynomial> {

  public class Polynomial {
    private final SparseVector<T, R> coefficients;

    public Polynomial() {
      coefficients = new SparseVector<T, R>(ring, 0);
    }

    /**
     * copy constructor
     * @param orig
     */
    public Polynomial(Polynomial orig) {
      coefficients = new SparseVector<T, R>(orig.coefficients);
    }

    /**
     * Make a new polynomial from an old one by multiplying it by
     * x^exp * scalar on the left.
     *
     * @param orig the original polynomial
     * @param exp the exponent shift
     * @param scalar the scalar multiplier
     */
    private Polynomial(Polynomial orig, int exp, T scalar) {
      coefficients = new SparseVector<T, R>(ring, 0);
      for (int i : orig.coefficients) {
        coefficients.set(i + exp, ring.mul(scalar, orig.coefficients.get(i)));
      }
    }
  }
  
  private final R ring;
  
  private final Polynomial zero, one;
  
  public PolynomialRing(R ring) {
    this.ring = ring;
    zero = new Polynomial();
    one = new Polynomial();
    one.coefficients.set(0, ring.id());
  }

  @Override
  public Polynomial zero() {
    return zero;
  }
  
  @Override
  public Polynomial id() {
    return one;
  }

  @Override
  public Polynomial add(Polynomial arg1, Polynomial arg2) {
    Polynomial result = new Polynomial(arg1);
    for (int i : arg2.coefficients) {
      result.coefficients.set(i,
          ring.add(result.coefficients.get(i), arg2.coefficients.get(i)));
    }
    return result;
  }

  @Override
  public Polynomial neg(Polynomial arg) {
    Polynomial result = new Polynomial();
    for (int i : arg.coefficients) {
      result.coefficients.set(i, ring.neg(arg.coefficients.get(i)));
    }
    return result;
  }

  @Override
  public Polynomial mul(Polynomial arg1, Polynomial arg2) {
    Polynomial result = new Polynomial();
    for (int i : arg1.coefficients) {
      Polynomial p = new Polynomial(arg2, i, arg1.coefficients.get(i));
      result = add(result, p);
    }
    return result;
  }

  /**
   * This ring is a module over itself.
   */
  @Override
  public Ring<Polynomial> ring() {
    return this;
  }
}
