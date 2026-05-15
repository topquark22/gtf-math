package gtf.math.algebra.field;

import gtf.math.Complex;
import gtf.math.algebra.ComplexField;
import gtf.math.algebra.Field;


/**
 * Canonical field of complex numbers represented by {@link Complex}.
 *
 * @author gtf
 */
public final class C implements Field<Complex> {

  public static final C INSTANCE = new C();

  private final ComplexField delegate = ComplexField.INSTANCE;

  private C() {
  }

  @Override
  public C ring() {
    return this;
  }

  @Override
  public Complex zero() {
    return delegate.zero();
  }

  @Override
  public Complex id() {
    return delegate.id();
  }

  @Override
  public Complex add(Complex arg1, Complex arg2) {
    return delegate.add(arg1, arg2);
  }

  @Override
  public Complex neg(Complex arg) {
    return delegate.neg(arg);
  }

  @Override
  public Complex mul(Complex arg1, Complex arg2) {
    return delegate.mul(arg1, arg2);
  }

  @Override
  public Complex inv(Complex arg) {
    return delegate.inv(arg);
  }

  @Override
  public Complex divide(Complex numerator, Complex denominator) {
    return delegate.divide(numerator, denominator);
  }
}
