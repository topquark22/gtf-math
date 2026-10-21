package gtf.math.stats.source;

/**
 * Represents an information source with first-order statistics.
 * In other words, the probability of a symbol may be conditional on the
 * probability of the preceding symbol.
 *
 *@author   gtf
 */
public interface FirstOrderProbabilityModel extends ProbabilityModel {

  double getConditionalProbability(int symbol1, int symbol2);

  double getConditionalEntropy(int symbol);
}
