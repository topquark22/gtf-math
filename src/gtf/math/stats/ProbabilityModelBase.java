package gtf.math.stats;

/**
 * Provides base support for all probability models. This implementation
 * is not synchronized.
 *
 *@author   gtf
 */
abstract class ProbabilityModelBase implements ProbabilityModel {

  private final int alphabetSize;

  /**
   * ln(2), used in computing base-2 logarithms.
   */
  public final static double LOG_2 = Math.log(2.0D);

  /**
   * Constructor for the ProbabilityModelBase object.
   *
   *@param alphabetSize  size of the symbol alphabet
   */
  public ProbabilityModelBase(int alphabetSize) {
    if (alphabetSize <= 0) {
      throw new IllegalArgumentException("invalid alphabet size " + alphabetSize);
    }
    this.alphabetSize = alphabetSize;
  }

  /**
   * Validates a symbol value against the allowed range. Should be called
   * from any public method that takes a symbol as an argument.
   *
   *@param symbol The symbol to check
   *@throws IllegalArgumentException if the symbol is out of range
   */
  protected final void validate(int symbol) {
    if (symbol < 0 || symbol >= alphabetSize) {
      throw new IllegalArgumentException("invalid symbol " + symbol);
    }
  }

  /**
   * Gets the size of the alphabet from which symbols are drawn.
   * Symbol values must satisfy 0 &lt;= symbol &lt; alphabetSize.
   */
  public final int getAlphabetSize() {
    return alphabetSize;
  }
}
