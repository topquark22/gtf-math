package gtf.math.stats;

/**
 * Utility methods for descriptive statistics.
 *
 * @author gtf
 */
public final class Stats {

  private Stats() {
  }

  public static double sum(double[] values) {
    validate(values);

    double sum = 0.0;
    for (double value : values) {
      sum += value;
    }
    return sum;
  }

  public static double mean(double[] values) {
    return sum(values) / values.length;
  }

  public static double variance(double[] values) {
    validate(values);

    double mean = mean(values);
    double variance = 0.0;

    for (double value : values) {
      double delta = value - mean;
      variance += delta * delta;
    }

    return variance / values.length;
  }

  public static double sampleVariance(double[] values) {
    validateAtLeastTwo(values);

    double mean = mean(values);
    double variance = 0.0;

    for (double value : values) {
      double delta = value - mean;
      variance += delta * delta;
    }

    return variance / (values.length - 1);
  }

  public static double standardDeviation(double[] values) {
    return Math.sqrt(variance(values));
  }

  public static double sampleStandardDeviation(double[] values) {
    return Math.sqrt(sampleVariance(values));
  }

  public static double covariance(double[] x, double[] y) {
    validateSameLength(x, y);

    double meanX = mean(x);
    double meanY = mean(y);
    double covariance = 0.0;

    for (int i = 0; i < x.length; i++) {
      covariance += (x[i] - meanX) * (y[i] - meanY);
    }

    return covariance / x.length;
  }

  public static double correlation(double[] x, double[] y) {
    double covariance = covariance(x, y);
    double sigmaX = standardDeviation(x);
    double sigmaY = standardDeviation(y);

    if (sigmaX == 0.0 || sigmaY == 0.0) {
      throw new ArithmeticException("correlation undefined for zero variance");
    }

    return covariance / (sigmaX * sigmaY);
  }

  public static double min(double[] values) {
    validate(values);

    double min = values[0];
    for (int i = 1; i < values.length; i++) {
      if (values[i] < min) {
        min = values[i];
      }
    }
    return min;
  }

  public static double max(double[] values) {
    validate(values);

    double max = values[0];
    for (int i = 1; i < values.length; i++) {
      if (values[i] > max) {
        max = values[i];
      }
    }
    return max;
  }

  private static void validate(double[] values) {
    if (values == null || values.length == 0) {
      throw new IllegalArgumentException("empty data set");
    }
  }

  private static void validateAtLeastTwo(double[] values) {
    validate(values);

    if (values.length < 2) {
      throw new IllegalArgumentException("at least two values required");
    }
  }

  private static void validateSameLength(double[] x, double[] y) {
    validate(x);
    validate(y);

    if (x.length != y.length) {
      throw new IllegalArgumentException("data sets must have same length");
    }
  }
}
