package gtf.math.stats;

/**
 * Represents a continuous probability distribution.
 *
 * @author gtf
 */
public interface ContinuousDistribution {

  /**
   * Evaluates the probability density function.
   *
   * @param x the evaluation point
   * @return the density at x
   */
  double density(double x);

  /**
   * Evaluates the cumulative distribution function.
   *
   * @param x the evaluation point
   * @return P(X <= x)
   */
  double cumulative(double x);
}
