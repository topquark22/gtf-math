package gtf.math.stats.source;

/**
 * Represents a statistical information source with a uniform,
 * independent distribution. This implementation is not synchronized.
 *
 *@author   gtf
 */
public final class UniformDistribution extends IndependentDistribution {

  private final double probability;

  private final double entropy;

  public UniformDistribution(int alphabetSize) {
    super(alphabetSize);
    probability = 1.0D / getAlphabetSize();
    entropy = Math.log(alphabetSize) / LOG_2;
  }

  public double getProbability(int symbol) {
    validate(symbol);
    return probability;
  }

  public double getEntropy() {
    return entropy;
  }
}
