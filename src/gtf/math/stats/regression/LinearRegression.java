package gtf.math.stats.regression;

import gtf.math.stats.Sample;
import gtf.math.stats.SampleSet;

/**
 * Ordinary least-squares linear regression.
 *
 * @author gtf
 */
public final class LinearRegression {

  private LinearRegression() {
  }

  /**
   * Fits a linear model using ordinary least squares.
   *
   * @param samples the sample set
   * @return fitted linear model
   */
  public static LinearModel fit(SampleSet samples) {
    if (samples == null) {
      throw new IllegalArgumentException("samples are null");
    }

    if (samples.dimensions() != 1) {
      throw new UnsupportedOperationException(
          "currently supports only one predictor variable");
    }

    int n = samples.size();

    double sumX = 0.0;
    double sumY = 0.0;
    double sumXX = 0.0;
    double sumXY = 0.0;

    for (Sample sample : samples) {
      double x = sample.getX(0);
      double y = sample.getY();

      sumX += x;
      sumY += y;
      sumXX += x * x;
      sumXY += x * y;
    }

    double denominator = n * sumXX - sumX * sumX;

    if (denominator == 0.0) {
      throw new ArithmeticException("singular regression system");
    }

    double slope = (n * sumXY - sumX * sumY) / denominator;

    double intercept = (sumY - slope * sumX) / n;

    return new LinearModel(intercept, new double[] { slope });
  }
}
