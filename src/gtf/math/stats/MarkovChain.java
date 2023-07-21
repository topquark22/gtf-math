package gtf.math.stats;

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

  // used during training
  private int prevSymbol = -1;

  /**
   * Constructor for the MarkovChain object.
   *
   *@param alphabetSize  size of the symbol alphabet
   */
  public MarkovChain(int alphabetSize) {
    super(alphabetSize);
    orderZeroDistribution = new OrderZeroDistribution(alphabetSize);
    digrams = new long[alphabetSize][alphabetSize];
    transitionMatrix = new double[alphabetSize][alphabetSize];
    // Note: If we left the transition matrix at all zeros,
    // then it would not satisfy the column-sum property.
    // We can fix this by assuming a uniform distribution
    // for all columns in which a symbol has not yet appeared.
    // This will lead to false results until that symbol
    // appears during training.
    for (int i = 0; i < alphabetSize; i++) {
      for (int j = 0; j < alphabetSize; j++) {
        transitionMatrix[i][j] = 1.0D / alphabetSize;
      }
    }
  }

  /**
   * Gets the total symbol count.
   *
   *@return   The count value
   */
  public long getCount() {
    return count;
  }

  /**
   * Train the distribution by telling it that a symbol has occurred.
   *
   *@param symbol2  The symbol that occurred.
   */
  public void train(int symbol2) {
    validate(symbol2);
    orderZeroDistribution.train(symbol2);
    if (prevSymbol >= 0) {
      digrams[prevSymbol][symbol2]++;
      // adjust the Markov matrix
      for (int symbol = 0; symbol < getAlphabetSize(); symbol++) {
        transitionMatrix[symbol][prevSymbol] = (double) digrams[prevSymbol][symbol]
                / orderZeroDistribution.getCountForSymbol(prevSymbol);
      }
    }
    count++;
    prevSymbol = symbol2;
  }

  /**
   * Gets the average probability of a symbol appearing.
   *
   *@param symbol  Description of the Parameter
   *@return        The probability value
   */
  public final double getProbability(int symbol) {
    return orderZeroDistribution.getProbability(symbol);
  }

  /**
   * Gets the conditional probability of a symbol appearing
   * after another symbol.
   *
   *@param symbol1  The preceding symbol
   *@param symbol2  The symbol to compute the probability of
   */
  public final double getConditionalProbability(int symbol1, int symbol2) {
    return transitionMatrix[symbol2][symbol1];
  }

  /**
   * Gets the average entropy of the Markov Chain. This is the expected
   * value of the conditional entropies over the order-zero distribution.
   * [Or over the stationary distribution?... Anyways, this is a
   * reasonable approximation]
   */
  public final double getEntropy() {
    double entropy = 0.0D;
    for (int symbol = 0; symbol < getAlphabetSize(); symbol++) {
     entropy += orderZeroDistribution.getProbability(symbol)
        * getConditionalEntropy(symbol);
    }
    return entropy;
  }

  /**
   * Computes the entropy for the next symbol, given that a symbol has just
   * appeared.
   *
   * For a Markov Chain this is just the entropy of one column of the
   * transition matrix.
   *
   *@param symbol1  The preceding symbol
   */
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

  /* *
   * Computes the stationary distribution; that is, the eigenvector
   * corresponding to the eigenvalue 1, up to a suitable scaling
   * factor.
   * /
  public double[] getStationaryDistribution() {
    // TODO
    return new double[getAlphabetSize()];
  }*/

  /**
   * Summary of the data contained in the chain.
   */
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Entropy = " + getEntropy());
    // ...
    return builder.toString();
  }
}
