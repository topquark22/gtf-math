package gtf.math.matrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;

import gtf.math.algebra.ring.Z;


public class ArrayVectorTest {

  private static final int SIZE = 4;

  private Vector<BigInteger, Z> unit;

  @Before
  public void setup() {
    unit = new ArrayVector<BigInteger, Z>(new Z(), SIZE);
  }

  @Test
  public void testSize() {
    assertEquals(SIZE, unit.size());
  }

  @Test
  public void testInitialValuesAreZero() {
    for (int i = 0; i < SIZE; i++) {
      assertEquals(BigInteger.ZERO, unit.get(i));
    }
  }

  @Test
  public void testSetAndGet() {
    unit.set(2, BigInteger.valueOf(17));

    assertEquals(BigInteger.valueOf(17), unit.get(2));
  }

  @Test
  public void testDotProduct() {
    Vector<BigInteger, Z> other = new ArrayVector<BigInteger, Z>(new Z(), SIZE);

    unit.set(0, BigInteger.valueOf(2));
    unit.set(1, BigInteger.valueOf(3));
    unit.set(2, BigInteger.valueOf(5));
    unit.set(3, BigInteger.valueOf(7));

    other.set(0, BigInteger.valueOf(11));
    other.set(1, BigInteger.valueOf(13));
    other.set(2, BigInteger.valueOf(17));
    other.set(3, BigInteger.valueOf(19));

    assertEquals(BigInteger.valueOf(279), unit.dotProduct(other));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDotProductRejectsIncompatibleDimensions() {
    unit.dotProduct(new ArrayVector<BigInteger, Z>(new Z(), SIZE + 1));
  }

  @Test
  public void testCopy() {
    unit.set(1, BigInteger.valueOf(23));

    Vector<BigInteger, Z> copy = unit.copy();

    assertNotSame(unit, copy);
    assertEquals(unit.size(), copy.size());
    assertEquals(BigInteger.valueOf(23), copy.get(1));

    unit.set(1, BigInteger.valueOf(29));

    assertEquals(BigInteger.valueOf(23), copy.get(1));
  }
}
