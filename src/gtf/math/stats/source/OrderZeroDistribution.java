package gtf.math.stats.source;

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

  public OrderZeroDistribution(int alphabetSize) {
    super(alphabetSize);
    frequency = new long[alphabetSize];
  }

  public long getCount() {
    return count;
  }

  public long getCountForSymbol(int symbol) {
    return frequency[symbol];
  }

  public void train(int symbol) {
    validate(symbol);
    frequency[symbol]++;
    count++;
  }

  public double getProbability(int symbol) {
    validate(symbol);
    return (double) frequency[symbol] / count;
  }
}
