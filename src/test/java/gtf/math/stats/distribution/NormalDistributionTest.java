package gtf.math.stats.distribution;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author gtf
 */
public class NormalDistributionTest {

  private static final double EPS = 1.0e-6;

  @Test
  public void testStandardNormalDensityAtZero() {
    NormalDistribution n = new NormalDistribution(0.0, 1.0);

    assertEquals(0.3989422804, n.density(0.0), EPS);
  }

  @Test
  public void testStandardNormalCumulativeAtZero() {
    NormalDistribution n = new NormalDistribution(0.0, 1.0);

    assertEquals(0.5, n.cumulative(0.0), EPS);
  }

  @Test
  public void testStandardNormalSymmetry() {
    NormalDistribution n = new NormalDistribution(0.0, 1.0);

    assertEquals(n.density(-1.5), n.density(1.5), EPS);
  }

  @Test
  public void testInverseCumulative95Percent() {
    NormalDistribution n = new NormalDistribution(0.0, 1.0);

    assertEquals(1.6448536269,
        n.inverseCumulative(0.95),
        1.0e-4);
  }

  @Test
  public void testInverseCumulative975Percent() {
    NormalDistribution n = new NormalDistribution(0.0, 1.0);

    assertEquals(1.9599639845,
        n.inverseCumulative(0.975),
        1.0e-4);
  }

  @Test
  public void testCumulativeInverseRoundTrip() {
    NormalDistribution n = new NormalDistribution(10.0, 4.0);

    double x = 12.5;

    assertEquals(x,
        n.inverseCumulative(n.cumulative(x)),
        1.0e-4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRejectNonPositiveVariance() {
    new NormalDistribution(0.0, 0.0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRejectProbabilityZero() {
    new NormalDistribution(0.0, 1.0).inverseCumulative(0.0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRejectProbabilityOne() {
    new NormalDistribution(0.0, 1.0).inverseCumulative(1.0);
  }
}
