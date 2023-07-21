package gtf.math.stats;

/**
 * Represents an information source with first-order statistics.
 * In other words, the probability of a symbol may be conditional on the
 * probability of the preceding symbol.
 *
 *@author   gtf
 */
public interface FirstOrderProbabilityModel extends ProbabilityModel {

  /**
   * Gets the conditional probability of a symbol appearing
   * after another symbol.
   *
   *@param symbol1  The preceding symbol
   *@param symbol2  The symbol to compute the probability of
   */
  double getConditionalProbability(int symbol1, int symbol2);

  /**
   * Gets the entropy given that a symbol has just appeared.
   *
   *@param symbol  The preceding symbol
   */
  double getConditionalEntropy(int symbol);

}
