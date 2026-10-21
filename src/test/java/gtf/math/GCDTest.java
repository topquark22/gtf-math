package gtf.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author gtf
 */
public class GCDTest {

  /*
   * GCD(x, y) = z
   */
  private long[][] cases =  new long[][] {
      {1L, 2L, 1L},      /* 0 */
      {2L, 4L, 2L},      /* 1 */
      {3L, 5L, 1L},      /* 2 */
      {25L, 50L, 25L},   /* 3 */
      {34L, 51L, 17L},   /* 4 */
      {1L, -2L, 1L},     /* 5 */
      {-2L, 4L, 2L},     /* 6 */
      {3L, -5L, 1L},     /* 7 */
      {-25L, -50L, 25L}, /* 8 */
      {34L, -51L, 17L}   /* 9 */
  };
  
  @Test
  public void execute() {
    for (int caseNo = 0; caseNo < cases.length; caseNo++) {
      long a = cases[caseNo][0];
      long b = cases[caseNo][1];
      long expected = cases[caseNo][2];

      long result = IMath.gcd(a, b);
      assertEquals("GCD(" + a + ", " + b + ") = " + expected,
          expected, result);
    }
  }
}
  