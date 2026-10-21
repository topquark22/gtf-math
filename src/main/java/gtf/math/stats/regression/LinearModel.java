package gtf.math.stats.regression;

import java.util.Arrays;

/**
 * Represents a fitted linear model.
 *
 * @author gtf
 */
public final class LinearModel {

  private final double intercept;

  private final double[] coefficients;

  /**
   * Constructs a linear model.
   *
   * @param intercept the intercept term
   * @param coefficients the regression coefficients
   */
  public LinearModel(double intercept, double[] coefficients) {
    if (coefficients == null) {
      throw new IllegalArgumentException("coefficients are null");
    }

    this.intercept = intercept;
    this.coefficients = coefficients.clone();
  }

  /**
   * Returns the intercept.
   */
  public double intercept() {
    return intercept;
  }

  /**
   * Returns the number of predictor variables.
   */
  public int dimensions() {
    return coefficients.length;
  }

  /**
   * Returns a regression coefficient.
   *
   * @param index coefficient index
   */
  public double coefficient(int index) {
    return coefficients[index];
  }

  /**
   * Returns a defensive copy of the coefficient vector.
   */
  public double[] coefficients() {
    return coefficients.clone();
  }

  /**
   * Predicts a response value.
   *
   * @param x predictor vector
   * @return predicted response
   */
  public double predict(double[] x) {
    if (x == null) {
      throw new IllegalArgumentException("predictor vector is null");
    }

    if (x.length != coefficients.length) {
      throw new IllegalArgumentException(
          "predictor dimension mismatch");
    }

    double y = intercept;

    for (int i = 0; i < coefficients.length; i++) {
      y += coefficients[i] * x[i];
    }

    return y;
  }

  @Override
  public String toString() {
    return "LinearModel[intercept=" + intercept
        + ", coefficients=" + Arrays.toString(coefficients) + "]";
  }
}
