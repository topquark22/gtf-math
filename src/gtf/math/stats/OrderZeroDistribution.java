package gtf.math.stats;

/**
 * A trainable statistical information source with zero-order statistics.
 * This implementation is not synchronized.
 *
 *@author   gtf
 */
public final class OrderZeroDistribution extends IndependentDistribution
     implements Trainable {

  private final long[] frequency;

  private long count = 0;

  /**
   * Constructor for the OrderZeroDistribution object.
   *
   *@param alphabetSize  size of the symbol alphabet
   */
  public OrderZeroDistribution(int alphabetSize) {
    super(alphabetSize);
    frequency = new long[alphabetSize];
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
   * Gets the number of occurrences of a specific symbol.
   *
   *@param symbol  The symbol to check
   */
  public long getCountForSymbol(int symbol) {
    return frequency[symbol];
  }

  /**
   * Train the distribution by telling it that a symbol has occurred.
   *
   *@param symbol  The symbol that occurred.
   */
  public void train(int symbol) {
    validate(symbol);
    frequency[symbol]++;
    count++;
  }

  /**
   * Gets the average probability of a symbol appearing.
   *
   *@param symbol  The symbol to check
   *@return        The probability value
   */
  public double getProbability(int symbol) {
    validate(symbol);
    return (double) frequency[symbol] / count;
  }
}
