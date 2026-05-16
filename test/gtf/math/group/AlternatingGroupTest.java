package gtf.math.group;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import gtf.math.Permutation;


/**
 * @author gtf
 */
public class AlternatingGroupTest {

  @Test
  public void testContains() {
    AlternatingGroup a3 = new AlternatingGroup(3);

    assertTrue(a3.contains(Permutation.identity(3)));
    assertTrue(a3.contains(new Permutation(1, 2, 0)));
    assertFalse(a3.contains(new Permutation(1, 0, 2)));
  }

  @Test
  public void testInverse() {
    AlternatingGroup a3 = new AlternatingGroup(3);
    Permutation p = new Permutation(1, 2, 0);

    assertEquals(a3.id(), a3.mul(p, a3.inv(p)));
    assertEquals(a3.id(), a3.mul(a3.inv(p), p));
  }

  @Test
  public void testComposition() {
    AlternatingGroup a3 = new AlternatingGroup(3);

    Permutation p = new Permutation(1, 2, 0);
    Permutation q = new Permutation(2, 0, 1);

    assertEquals(Permutation.identity(3), a3.mul(p, q));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOddPermutationRejectedInMul() {
    AlternatingGroup a3 = new AlternatingGroup(3);

    a3.mul(Permutation.identity(3), new Permutation(1, 0, 2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOddPermutationRejectedInInv() {
    AlternatingGroup a3 = new AlternatingGroup(3);

    a3.inv(new Permutation(1, 0, 2));
  }
}
