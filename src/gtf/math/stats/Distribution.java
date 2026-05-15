package gtf.math.stats;

/**
 * Represents a mathematical probability distribution.
 *
 * @author gtf
 */
public interface Distribution {

  /**
   * Evaluates the probability density (continuous case) or probability
   * mass (discrete case) at a point.
   *
   * @param x the evaluation point
   * @return the density or mass at x
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
