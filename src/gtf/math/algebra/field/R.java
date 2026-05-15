package gtf.math.algebra.field;

import gtf.math.algebra.Field;


/**
 * Canonical field of real numbers represented by {@link Double}.
 *
 * @author gtf
 */
public final class R implements Field<Double> {

  public static final R INSTANCE = new R();

  private R() {
  }

  @Override
  public R ring() {
    return this;
  }

  @Override
  public Double zero() {
    return 0.0;
  }

  @Override
  public Double id() {
    return 1.0;
  }

  @Override
  public Double add(Double arg1, Double arg2) {
    return arg1 + arg2;
  }

  @Override
  public Double neg(Double arg) {
    return -arg;
  }

  @Override
  public Double mul(Double arg1, Double arg2) {
    return arg1 * arg2;
  }

  @Override
  public Double inv(Double arg) {
    if (arg == 0.0) {
      throw new ArithmeticException("division by zero");
    }
    return 1.0 / arg;
  }

  @Override
  public Double divide(Double numerator, Double denominator) {
    if (denominator == 0.0) {
      throw new ArithmeticException("division by zero");
    }
    return numerator / denominator;
  }
}
