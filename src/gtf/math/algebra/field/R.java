package gtf.math.algebra.field;

import gtf.math.algebra.Field;
import gtf.math.algebra.RealField;


/**
 * Canonical field of real numbers represented by {@link Double}.
 *
 * @author gtf
 */
public final class R implements Field<Double> {

  public static final R INSTANCE = new R();

  private final RealField delegate = RealField.INSTANCE;

  private R() {
  }

  @Override
  public R ring() {
    return this;
  }

  @Override
  public Double zero() {
    return delegate.zero();
  }

  @Override
  public Double id() {
    return delegate.id();
  }

  @Override
  public Double add(Double arg1, Double arg2) {
    return delegate.add(arg1, arg2);
  }

  @Override
  public Double neg(Double arg) {
    return delegate.neg(arg);
  }

  @Override
  public Double mul(Double arg1, Double arg2) {
    return delegate.mul(arg1, arg2);
  }

  @Override
  public Double inv(Double arg) {
    return delegate.inv(arg);
  }

  @Override
  public Double divide(Double numerator, Double denominator) {
    return delegate.divide(numerator, denominator);
  }
}
