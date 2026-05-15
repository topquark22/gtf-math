package gtf.math;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

/**
 * @author gtf
 */
public class BigFractionTest {

  @Test
  public void testReduction() {
    BigFraction fraction = new BigFraction(
        BigInteger.valueOf(2),
        BigInteger.valueOf(4));

    assertEquals(new BigFraction(1, 2), fraction);
  }

  @Test
  public void testAdd() {
    BigFraction a = new BigFraction(1, 2);
    BigFraction b = new BigFraction(1, 3);

    assertEquals(new BigFraction(5, 6), a.add(b));
  }

  @Test
  public void testMultiply() {
    BigFraction a = new BigFraction(2, 3);
    BigFraction b = new BigFraction(3, 5);

    assertEquals(new BigFraction(2, 5), a.multiply(b));
  }

  @Test
  public void testReciprocal() {
    BigFraction fraction = new BigFraction(2, 3);

    assertEquals(new BigFraction(3, 2), fraction.reciprocal());
  }

  @Test
  public void testCompareTo() {
    BigFraction a = new BigFraction(1, 2);
    BigFraction b = new BigFraction(2, 3);

    assertEquals(-1, Integer.signum(a.compareTo(b)));
    assertEquals(1, Integer.signum(b.compareTo(a)));
    assertEquals(0, a.compareTo(new BigFraction(1, 2)));
  }

  @Test
  public void testFloor() {
    assertEquals(BigInteger.valueOf(2),
        new BigFraction(7, 3).floor());
  }

  @Test
  public void testCeil() {
    assertEquals(BigInteger.valueOf(3),
        new BigFraction(7, 3).ceil());
  }

  @Test(expected = ArithmeticException.class)
  public void testZeroDenominatorRejected() {
    new BigFraction(BigInteger.ONE, BigInteger.ZERO);
  }

  @Test(expected = ArithmeticException.class)
  public void testReciprocalOfZeroRejected() {
    new BigFraction(0, 1).reciprocal();
  }
}
