package gtf.math.stats.distribution;

/**
 * Represents a continuous uniform distribution on the interval [a,b].
 *
 * @author gtf
 */
public final class UniformDistribution
    implements ContinuousDistribution {

  private final double a;

  private final double b;

  private final double width;

  /**
   * Constructs a uniform distribution on [a,b].
   *
   * @param a lower bound
   * @param b upper bound
   */
  public UniformDistribution(double a, double b) {
    if (!(b > a)) {
      throw new IllegalArgumentException(
          "require b > a");
    }

    this.a = a;
    this.b = b;
    this.width = b - a;
  }

  /**
   * Returns the lower bound.
   */
  public double lowerBound() {
    return a;
  }

  /**
   * Returns the upper bound.
   */
  public double upperBound() {
    return b;
  }

  /**
   * Returns the mean.
   */
  public double mean() {
    return 0.5 * (a + b);
  }

  /**
   * Returns the variance.
   */
  public double variance() {
    return width * width / 12.0;
  }

  @Override
  public double density(double x) {
    if (x < a || x > b) {
      return 0.0;
    }

    return 1.0 / width;
  }

  @Override
  public double cumulative(double x) {
    if (x <= a) {
      return 0.0;
    }

    if (x >= b) {
      return 1.0;
    }

    return (x - a) / width;
  }
}
