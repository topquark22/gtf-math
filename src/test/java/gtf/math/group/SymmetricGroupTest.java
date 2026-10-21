package gtf.math.group;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import gtf.math.Permutation;


/**
 * @author gtf
 */
public class SymmetricGroupTest {

  @Test
  public void testIdentity() {
    SymmetricGroup s3 = new SymmetricGroup(3);

    assertEquals(Permutation.identity(3), s3.id());
  }

  @Test
  public void testInverse() {
    SymmetricGroup s3 = new SymmetricGroup(3);
    Permutation p = new Permutation(2, 0, 1);

    assertEquals(s3.id(), s3.mul(p, s3.inv(p)));
    assertEquals(s3.id(), s3.mul(s3.inv(p), p));
  }

  @Test
  public void testComposition() {
    SymmetricGroup s3 = new SymmetricGroup(3);

    Permutation p = new Permutation(2, 0, 1);
    Permutation q = new Permutation(1, 2, 0);

    assertEquals(Permutation.identity(3), s3.mul(p, q));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSizeMismatchRejectedInMul() {
    SymmetricGroup s3 = new SymmetricGroup(3);

    s3.mul(new Permutation(1, 0, 2), new Permutation(1, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSizeMismatchRejectedInInv() {
    SymmetricGroup s3 = new SymmetricGroup(3);

    s3.inv(new Permutation(1, 0));
  }
}
