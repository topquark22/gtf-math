package gtf.math.stats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a collection of statistical samples with consistent
 * dimensionality.
 *
 * @author gtf
 */
public final class SampleSet implements Iterable<Sample> {

  private final List<Sample> samples;

  private final int dimensions;

  /**
   * Constructs a sample set.
   *
   * @param samples the samples
   */
  public SampleSet(List<Sample> samples) {
    if (samples == null || samples.isEmpty()) {
      throw new IllegalArgumentException("sample set is empty");
    }

    this.samples = Collections.unmodifiableList(new ArrayList<Sample>(samples));

    dimensions = samples.get(0).dimensions();

    for (Sample sample : samples) {
      if (sample.dimensions() != dimensions) {
        throw new IllegalArgumentException(
            "inconsistent sample dimensionality");
      }
    }
  }

  /**
   * Returns the number of samples.
   */
  public int size() {
    return samples.size();
  }

  /**
   * Returns the predictor dimension.
   */
  public int dimensions() {
    return dimensions;
  }

  /**
   * Returns a sample.
   *
   * @param index sample index
   */
  public Sample get(int index) {
    return samples.get(index);
  }

  /**
   * Returns the response vector.
   */
  public double[] getY() {
    double[] y = new double[samples.size()];

    for (int i = 0; i < samples.size(); i++) {
      y[i] = samples.get(i).getY();
    }

    return y;
  }

  /**
   * Returns the predictor matrix.
   */
  public double[][] getX() {
    double[][] x = new double[samples.size()][dimensions];

    for (int row = 0; row < samples.size(); row++) {
      x[row] = samples.get(row).getX();
    }

    return x;
  }

  @Override
  public Iterator<Sample> iterator() {
    return samples.iterator();
  }

  @Override
  public String toString() {
    return samples.toString();
  }
}
