package gtf.math.stats;

/**
 * Represents something (ie. a probability model) that can be "trained"
 * by pushing samples into it repeatedly.
 *
 *@author   gtf
 */
public interface Trainable {

  /**
   * Gets the size of the sample set.
   */
  long getCount();

  /**
   * Trains the distribution by providing one sample symbol. Should be
   * called repeatedly in order to train the model on a sequence of
   * samples.
   *
   *@param symbol  The symbol that occurred.
   */
  void train(int symbol);
}
