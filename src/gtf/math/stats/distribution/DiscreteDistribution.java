package gtf.math.stats.distribution;

/**
 * Represents a discrete probability distribution.
 *
 * @author gtf
 */
public interface DiscreteDistribution {

  /**
   * Evaluates the probability mass function.
   *
   * @param k discrete value
   * @return P(X = k)
   */
  double mass(int k);

  /**
   * Evaluates the cumulative distribution function.
   *
   * @param k discrete value
   * @return P(X <= k)
   */
  double cumulative(int k);
}
