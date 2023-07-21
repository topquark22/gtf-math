package gtf.math.stats;

/**
 * Represents a statistical information source with an independent distribution.
 *
 *@author   gtf
 */
public abstract class IndependentDistribution extends ProbabilityModelBase {

  /**
   * Constructor for the IndependentDistribution object.
   *
   *@param alphabetSize  size of the symbol alphabet
   */
  public IndependentDistribution(int alphabetSize) {
    super(alphabetSize);
  }

  /**
   * Gets the entropy of this information source.
   */
  public double getEntropy() {
    double entropy = 0.0D;
    for (int symbol = 0; symbol < getAlphabetSize(); symbol++) {
      double probability = getProbability(symbol);
      entropy -= probability * Math.log(probability);
    }
    return entropy / LOG_2;
  }
}
