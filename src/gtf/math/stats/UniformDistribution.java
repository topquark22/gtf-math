package gtf.math.stats;

/**
 * Represents a statistical information source with a uniform,
 * independent distribution. This implementation is not synchronized.
 *
 *@author   gtf
 */
public final class UniformDistribution extends IndependentDistribution {

  private final double probability;

  private final double entropy;

  /**
   *Constructor for the UniformDistribution object.
   *
   *@param alphabetSize  Description of the Parameter
   */
  public UniformDistribution(int alphabetSize) {
    super(alphabetSize);
    probability = 1.0D / getAlphabetSize();
    entropy = Math.log(alphabetSize) / LOG_2;
  }

  /**
   * Gets the average probability of a symbol appearing.
   *
   *@param symbol  Description of the Parameter
   *@return        The probability value
   */
  public double getProbability(int symbol) {
    validate(symbol);
    return probability;
  }

  /**
   * Gets the entropy.
   */
  public double getEntropy() {
    return entropy;
  }
}

