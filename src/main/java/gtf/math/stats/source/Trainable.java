package gtf.math.stats.source;

/**
 * Represents something (ie. a probability model) that can be "trained"
 * by pushing samples into it repeatedly.
 *
 *@author   gtf
 */
public interface Trainable {

  long getCount();

  void train(int symbol);
}
