package gtf.math.stats.distribution;

/**
 * Represents a Poisson distribution.
 *
 * @author gtf
 */
public final class PoissonDistribution implements DiscreteDistribution {

  private final double lambda;

  /**
   * Constructs a Poisson distribution.
   *
   * @param lambda expected event rate
   */
  public PoissonDistribution(double lambda) {
    if (!(lambda > 0.0)) {
      throw new IllegalArgumentException("lambda must be positive");
    }

    this.lambda = lambda;
  }

  /**
   * Returns the mean.
   */
  public double mean() {
    return lambda;
  }

  /**
   * Returns the variance.
   */
  public double variance() {
    return lambda;
  }

  /**
   * Returns the probability mass at k.
   *
   * @param k nonnegative integer
   * @return P(X = k)
   */
  public double mass(int k) {
    if (k < 0) {
      throw new IllegalArgumentException("k must be nonnegative");
    }

    return Math.exp(-lambda)
        * Math.pow(lambda, k)
        / factorial(k);
  }

  /**
   * Returns the cumulative distribution function.
   *
   * @param k nonnegative integer
   * @return P(X <= k)
   */
  public double cumulative(int k) {
    if (k < 0) {
      throw new IllegalArgumentException("k must be nonnegative");
    }

    double sum = 0.0;

    for (int i = 0; i <= k; i++) {
      sum += mass(i);
    }

    return sum;
  }

  private static double factorial(int n) {
    double result = 1.0;

    for (int i = 2; i <= n; i++) {
      result *= i;
    }

    return result;
  }
}
