package gtf.math.algebra;


/**
 * Canonical field of real numbers represented by {@link Double}.
 *
 * @author gtf
 */
public final class RealField implements Field<Double> {

  public static final RealField INSTANCE = new RealField();

  private RealField() {
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
