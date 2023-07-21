package gtf.math.stats;

import java.io.Serializable;

/**
 * Represents a statistical information source.
 *
 *@author   gtf
 */
public interface ProbabilityModel extends Serializable {

  /**
   * Gets the size of the alphabet from which symbols are drawn.
   * Symbol values must satisfy 0 &lt;= symbol &lt; alphabetSize.
   */
  int getAlphabetSize();

  /**
   * Gets the average probability of a symbol appearing.
   *
   *@param symbol  Description of the Parameter
   *@return        The probability value
   */
  double getProbability(int symbol);

  /**
   * Gets the average entropy of this information source.
   */
  double getEntropy();
}
