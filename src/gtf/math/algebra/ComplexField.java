package gtf.math.algebra;

import gtf.math.Complex;


/**
 * Canonical field of complex numbers represented by {@link Complex}.
 *
 * @author gtf
 */
public final class ComplexField implements Field<Complex> {

  public static final ComplexField INSTANCE = new ComplexField();

  private ComplexField() {
  }

  @Override
  public ComplexField ring() {
    return this;
  }

  @Override
  public Complex zero() {
    return Complex.ZERO;
  }

  @Override
  public Complex id() {
    return Complex.ONE;
  }

  @Override
  public Complex add(Complex arg1, Complex arg2) {
    return arg1.add(arg2);
  }

  @Override
  public Complex neg(Complex arg) {
    return arg.multiply(-1.0);
  }

  @Override
  public Complex mul(Complex arg1, Complex arg2) {
    return arg1.multiply(arg2);
  }

  @Override
  public Complex inv(Complex arg) {
    if (arg.equals(Complex.ZERO)) {
      throw new ArithmeticException("division by zero");
    }
    return arg.recip();
  }

  @Override
  public Complex divide(Complex numerator, Complex denominator) {
    if (denominator.equals(Complex.ZERO)) {
      throw new ArithmeticException("division by zero");
    }
    return numerator.multiply(denominator.recip());
  }
}
