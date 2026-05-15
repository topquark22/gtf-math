package gtf.math.stats.source;

/**
 * Represents a stateful information source with an underlying
 * Markov chain model. This implementation is not synchronized.
 *
 *@author   gtf
 */
public final class MarkovChain extends ProbabilityModelBase
        implements Trainable, FirstOrderProbabilityModel {

  private int count;

  private final long[][] digrams;

  private final double[][] transitionMatrix;

  private final OrderZeroDistribution orderZeroDistribution;

  private int prevSymbol = -1;

  public MarkovChain(int alphabetSize) {
    super(alphabetSize);
    orderZeroDistribution = new OrderZeroDistribution(alphabetSize);
    digrams = new long[alphabetSize][alphabetSize];
    transitionMatrix = new double[alphabetSize][alphabetSize];
    for (int i = 0; i < alphabetSize; i++) {
      for (int j = 0; j < alphabetSize; j++) {
        transitionMatrix[i][j] = 1.0D / alphabetSize;
      }
    }
  }

  public long getCount() {
    return count;
  }

  public void train(int symbol2) {
    validate(symbol2);
    orderZeroDistribution.train(symbol2);
    if (prevSymbol >= 0) {
      digrams[prevSymbol][symbol2]++;
      for (int symbol = 0; symbol < getAlphabetSize(); symbol++) {
        transitionMatrix[symbol][prevSymbol] = (double) digrams[prevSymbol][symbol]
                / orderZeroDistribution.getCountForSymbol(prevSymbol);
      }
    }
    count++;
    prevSymbol = symbol2;
  }

  public final double getProbability(int symbol) {
    return orderZeroDistribution.getProbability(symbol);
  }

  public final double getConditionalProbability(int symbol1, int symbol2) {
    return transitionMatrix[symbol2][symbol1];
  }

  public final double getEntropy() {
    double entropy = 0.0D;
    for (int symbol = 0; symbol < getAlphabetSize(); symbol++) {
     entropy += orderZeroDistribution.getProbability(symbol)
        * getConditionalEntropy(symbol);
    }
    return entropy;
  }

  public final double getConditionalEntropy(int symbol1) {
    double entropy = 0.0D;
    for (int symbol2 = 0; symbol2 < getAlphabetSize(); symbol2++) {
      if (digrams[symbol1][symbol2] > 0) {
        double probability = transitionMatrix[symbol2][symbol1];
        entropy -= probability * Math.log(probability);
      }
    }
    return entropy / LOG_2;
  }

  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Entropy = " + getEntropy());
    return builder.toString();
  }
}
