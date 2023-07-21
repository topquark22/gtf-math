package gtf.math.algebra.ring;

import java.math.BigInteger;

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
public class PolynomialRing<T, R extends Ring<T>> implements Ring<PolynomialRing<T, R>.Polynomial> {

  public class Polynomial {
    private final SparseVector<BigInteger, T, R> coefficients;
    public Polynomial() {
      coefficients = new SparseVector<BigInteger, T, R>(ring);
    }
    /**
     * copy constructor
     * @param orig
     */
    public Polynomial(Polynomial orig) {
      coefficients = new SparseVector<BigInteger, T, R>(orig.coefficients);
    }
    /**
     * Make a new polynomial from an old one by multiplying it by x^exp * scalar (on the left).
     * @param n
     */
    private Polynomial(Polynomial orig, BigInteger exp, T scalar) {
      coefficients = new SparseVector<BigInteger, T, R>(ring);
      for (BigInteger i : orig.coefficients) {
        coefficients.set(i.add(exp), ring.mul(scalar, orig.coefficients.get(i)));
      }
    }
  }
  
  private final R ring;
  
  private final Polynomial zero, one;
  
  public PolynomialRing(R ring) {
    this.ring = ring;
    zero = new Polynomial();
    one = new Polynomial();
    one.coefficients.set(BigInteger.ONE, ring.id());
  }

  //@Override
  public Polynomial zero() {
    return zero;
  }
  
  //@Override
  public Polynomial id() {
    return one;
  }

  //@Override
  public Polynomial add(Polynomial arg1, Polynomial arg2) {
    Polynomial result = new Polynomial(arg1);
    for (BigInteger i : arg2.coefficients) {
      result.coefficients.set(i, ring.add(result.coefficients.get(i), arg2.coefficients.get(i)));
    }
    return result;
  }

  //@Override
  public Polynomial neg(Polynomial arg) {
    Polynomial result = new Polynomial();
    for (BigInteger i : arg.coefficients) {
      result.coefficients.set(i, ring.neg(arg.coefficients.get(i)));
    }
    return result;
  }

  //@Override
  public Polynomial mul(Polynomial arg1, Polynomial arg2) {
    Polynomial result = new Polynomial();
    for (BigInteger i : arg1.coefficients) {
      Polynomial p = new Polynomial(arg2, i, arg1.coefficients.get(i));
      result = add(result, p);
    }
    return result;
  }

  /**
   * This ring is a module over itself.
   */
  //@Override
  public Ring<Polynomial> ring() {
    return this;
  }
}
