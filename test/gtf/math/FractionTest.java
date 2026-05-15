package gtf.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author gtf
 */
public class FractionTest {

  @Test
  public void testReduction() {
    assertEquals(new Fraction(1, 2), new Fraction(2, 4));
  }

  @Test
  public void testNegativeDenominatorNormalization() {
    assertEquals(new Fraction(-1, 2), new Fraction(1, -2));
    assertEquals(new Fraction(1, 2), new Fraction(-1, -2));
  }

  @Test
  public void testAdd() {
    Fraction a = new Fraction(1, 2);
    Fraction b = new Fraction(1, 3);

    assertEquals(new Fraction(5, 6), a.add(b));
  }

  @Test
  public void testMultiply() {
    Fraction a = new Fraction(2, 3);
    Fraction b = new Fraction(3, 5);

    assertEquals(new Fraction(2, 5), a.multiply(b));
  }

  @Test
  public void testDivide() {
    Fraction a = new Fraction(2, 3);
    Fraction b = new Fraction(4, 5);

    assertEquals(new Fraction(5, 6), a.divide(b));
  }

  @Test
  public void testReciprocal() {
    assertEquals(new Fraction(3, 2), new Fraction(2, 3).reciprocal());
  }

  @Test
  public void testCompareTo() {
    Fraction a = new Fraction(1, 2);
    Fraction b = new Fraction(2, 3);

    assertEquals(-1, Integer.signum(a.compareTo(b)));
    assertEquals(1, Integer.signum(b.compareTo(a)));
    assertEquals(0, a.compareTo(new Fraction(1, 2)));
  }

  @Test
  public void testFloor() {
    assertEquals(2L, new Fraction(7, 3).floor());
  }

  @Test
  public void testCeil() {
    assertEquals(3L, new Fraction(7, 3).ceil());
  }

  @Test
  public void testDoubleValue() {
    assertEquals(0.5, new Fraction(1, 2).doubleValue(), 0.0);
  }

  @Test
  public void testFloatValue() {
    assertEquals(0.5f, new Fraction(1, 2).floatValue(), 0.0f);
  }

  @Test(expected = ArithmeticException.class)
  public void testZeroDenominatorRejected() {
    new Fraction(1, 0);
  }

  @Test(expected = ArithmeticException.class)
  public void testDivideByZeroRejected() {
    new Fraction(1, 2).divide(Fraction.ZERO);
  }

  @Test(expected = ArithmeticException.class)
  public void testReciprocalOfZeroRejected() {
    Fraction.ZERO.reciprocal();
  }
}
