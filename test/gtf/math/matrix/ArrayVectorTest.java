package gtf.math.matrix;

import gtf.math.algebra.ring.Z;

import java.math.BigInteger;

import org.junit.Before;


public class ArrayVectorTest extends AbstractVectorTest {
  
  @Before
  public void setup() {
    unit = new ArrayVector<BigInteger, Z>(new Z(), SIZE);
  }

}
