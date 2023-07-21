package gtf.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author gtf
 */
public class LCMTest {

  /*
   * LCM(x, y) = z
   */
  private long[][] cases =  new long[][] {
      {1L, 2L, 2L},      /* 0 */
      {2L, 4L, 4L},      /* 1 */
      {3L, 5L, 15L},     /* 2 */
      {25L, 50L, 50L},   /* 3 */
      {34L, 51L, 102L},  /* 4 */
      {1L, -2L, 2L},     /* 5 */
      {-2L, 4L, 4L},     /* 6 */
      {3L, -5L, 15L},    /* 7 */
      {-25L, -50L, 50L}, /* 8 */
      {34L, -51L, 102L}  /* 9 */
  };
  
  @Test
  public void execute() {
    for (int caseNo = 0; caseNo < cases.length; caseNo++) {
      long a = cases[caseNo][0];
      long b = cases[caseNo][1];
      long expected = cases[caseNo][2];

      long result = IMath.lcm(a, b);
      assertEquals("LCM(" + a + ", " + b + ") = " + expected,
          expected, result);
    }
  }
}
  