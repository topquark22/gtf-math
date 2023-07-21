package gtf.math.matrix;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

import gtf.math.algebra.ring.Z;


/**
 * Test logic for vectors, that can be used for either
 * ArrayVector or SparseVector. Initialization of the
 * unit is done in the subclass.
 * 
 * @author gtf
 *
 */
public abstract class AbstractVectorTest {

  private static final BigInteger VAL0 = new BigInteger("1234");

  private static final BigInteger TWO = new BigInteger("2");
  
  protected static final int SIZE = 10;

  protected Vector<BigInteger, Z> unit;

  @Test
  public void testZero() {
    for (int i = 0; i < SIZE; i++) {
      BigInteger z = unit.get(i);
      assertEquals(unit.ring().zero(), z);
    }
  }
  
  @Test
  public void testSetGet() {
    for (int i = 0; i < SIZE; i++) {
      BigInteger val = VAL0;
      unit.set(i, val);
      BigInteger v = unit.get(i);
      assertEquals(val, v);
    }
  }

  @Test
  public void testDotProduct0() {
    Vector<BigInteger, Z> u2 = unit.copy();
    unit.set(0, VAL0);
    BigInteger result = unit.dotProduct(u2);
    assertEquals(BigInteger.ZERO, result);
  }
  
  @Test
  public void testDotProduct1() {
    Vector<BigInteger, Z> u2 = unit.copy();
    unit.set(0, VAL0);
    u2.set(0, VAL0);
    BigInteger result = unit.dotProduct(u2);
    assertEquals(VAL0.multiply(VAL0), result);
  }
  
  @Test
  public void testDotProduct2() {
    Vector<BigInteger, Z> u2 = unit.copy();
    unit.set(0, VAL0);
    u2.set(0, VAL0);
    unit.set(8, VAL0);
    unit.set(9, VAL0);
    u2.set(9, VAL0);
    BigInteger result = unit.dotProduct(u2);
    assertEquals(VAL0.multiply(VAL0).multiply(TWO), result);
  }
  
}
