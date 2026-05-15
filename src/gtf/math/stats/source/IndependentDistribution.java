package gtf.math.stats.source;

/**
 * Represents a statistical information source with an independent distribution.
 *
 *@author   gtf
 */
public abstract class IndependentDistribution extends ProbabilityModelBase {

  public IndependentDistribution(int alphabetSize) {
    super(alphabetSize);
  }

  public double getEntropy() {
    double entropy = 0.0D;
    for (int symbol = 0; symbol < getAlphabetSize(); symbol++) {
      double probability = getProbability(symbol);
      entropy -= probability * Math.log(probability);
    }
    return entropy / LOG_2;
  }
}
