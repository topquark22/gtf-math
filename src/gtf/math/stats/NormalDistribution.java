package gtf.math.stats;

/**
 * Represents a normal (Gaussian) distribution.
 *
 * @author gtf
 */
public final class NormalDistribution implements ContinuousDistribution {

  private static final double SQRT_2PI = Math.sqrt(2.0 * Math.PI);

  private final double mean;

  private final double variance;

  private final double sigma;

  /**
   * Constructs a normal distribution.
   *
   * @param mean the mean
   * @param variance the variance
   */
  public NormalDistribution(double mean, double variance) {
    if (!(variance > 0.0)) {
      throw new IllegalArgumentException("variance must be positive");
    }

    this.mean = mean;
    this.variance = variance;
    this.sigma = Math.sqrt(variance);
  }

  /**
   * Returns the mean.
   */
  public double mean() {
    return mean;
  }

  /**
   * Returns the variance.
   */
  public double variance() {
    return variance;
  }

  /**
   * Returns the standard deviation.
   */
  public double standardDeviation() {
    return sigma;
  }

  @Override
  public double density(double x) {
    double z = (x - mean) / sigma;

    return Math.exp(-0.5 * z * z) / (sigma * SQRT_2PI);
  }

  @Override
  public double cumulative(double x) {
    double z = (x - mean) / sigma;

    return 0.5 * (1.0 + erf(z / Math.sqrt(2.0)));
  }

  /**
   * Approximation of the error function.
   * Abramowitz and Stegun 7.1.26.
   */
  private static double erf(double x) {
    double sign = Math.signum(x);
    x = Math.abs(x);

    double t = 1.0 / (1.0 + 0.3275911 * x);

    double y = 1.0 - (((((1.061405429 * t
        - 1.453152027) * t)
        + 1.421413741) * t
        - 0.284496736) * t
        + 0.254829592) * t * Math.exp(-x * x);

    return sign * y;
  }
}
