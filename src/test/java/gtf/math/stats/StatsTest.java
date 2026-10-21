package gtf.math.stats;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author gtf
 */
public class StatsTest {

  private static final double EPS = 1.0e-12;

  @Test
  public void testSum() {
    assertEquals(15.0, Stats.sum(data()), EPS);
  }

  @Test
  public void testMean() {
    assertEquals(3.0, Stats.mean(data()), EPS);
  }

  @Test
  public void testVariance() {
    assertEquals(2.0, Stats.variance(data()), EPS);
  }

  @Test
  public void testSampleVariance() {
    assertEquals(2.5, Stats.sampleVariance(data()), EPS);
  }

  @Test
  public void testStandardDeviation() {
    assertEquals(Math.sqrt(2.0), Stats.standardDeviation(data()), EPS);
  }

  @Test
  public void testSampleStandardDeviation() {
    assertEquals(Math.sqrt(2.5), Stats.sampleStandardDeviation(data()), EPS);
  }

  @Test
  public void testCovariance() {
    double[] x = { 1, 2, 3, 4 };
    double[] y = { 2, 4, 6, 8 };

    assertEquals(2.5, Stats.covariance(x, y), EPS);
  }

  @Test
  public void testCorrelation() {
    double[] x = { 1, 2, 3, 4 };
    double[] y = { 2, 4, 6, 8 };

    assertEquals(1.0, Stats.correlation(x, y), EPS);
  }

  @Test
  public void testMin() {
    assertEquals(1.0, Stats.min(data()), EPS);
  }

  @Test
  public void testMax() {
    assertEquals(5.0, Stats.max(data()), EPS);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRejectEmptyMean() {
    Stats.mean(new double[0]);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRejectSingleSampleVariance() {
    Stats.sampleVariance(new double[] { 1.0 });
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRejectMismatchedCovarianceInputs() {
    Stats.covariance(new double[] { 1, 2 }, new double[] { 1 });
  }

  @Test(expected = ArithmeticException.class)
  public void testRejectZeroVarianceCorrelation() {
    Stats.correlation(new double[] { 1, 1, 1 }, new double[] { 2, 3, 4 });
  }

  private double[] data() {
    return new double[] { 1, 2, 3, 4, 5 };
  }
}
