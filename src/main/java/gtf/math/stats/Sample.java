package gtf.math.stats;

import java.util.Arrays;

/**
 * Represents a statistical sample consisting of predictor variables and
 * a response variable.
 *
 * @author gtf
 */
public final class Sample {

  private final double[] x;

  private final double y;

  /**
   * Constructs a sample.
   *
   * @param x predictor variables
   * @param y response variable
   */
  public Sample(double[] x, double y) {
    if (x == null) {
      throw new IllegalArgumentException("predictor vector is null");
    }

    this.x = x.clone();
    this.y = y;
  }

  /**
   * Returns the number of predictor variables.
   */
  public int dimensions() {
    return x.length;
  }

  /**
   * Returns a predictor variable.
   *
   * @param index predictor index
   */
  public double getX(int index) {
    return x[index];
  }

  /**
   * Returns a defensive copy of the predictor vector.
   */
  public double[] getX() {
    return x.clone();
  }

  /**
   * Returns the response variable.
   */
  public double getY() {
    return y;
  }

  @Override
  public String toString() {
    return "Sample[x=" + Arrays.toString(x) + ", y=" + y + "]";
  }
}
