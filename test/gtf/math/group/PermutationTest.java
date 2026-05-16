package gtf.math.group;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import gtf.math.group.Permutation;

/**
 * @author gtf
 */
public class PermutationTest {

  @Test
  public void testIdentity() {
    Permutation p = Permutation.identity(4);

    assertEquals(4, p.size());
    assertEquals(0, p.image(0));
    assertEquals(1, p.image(1));
    assertEquals(2, p.image(2));
    assertEquals(3, p.image(3));
  }

  @Test
  public void testInverse() {
    Permutation p = new Permutation(2, 0, 1);
    Permutation inverse = p.inverse();

    assertArrayEquals(new int[] {1, 2, 0}, inverse.toArray());
    assertEquals(Permutation.identity(3), p.compose(inverse));
    assertEquals(Permutation.identity(3), inverse.compose(p));
  }

  @Test
  public void testCompose() {
    Permutation p = new Permutation(2, 0, 1);
    Permutation q = new Permutation(1, 2, 0);

    assertArrayEquals(new int[] {0, 1, 2}, p.compose(q).toArray());
  }

  @Test
  public void testEquality() {
    Permutation p = new Permutation(1, 0, 2);
    Permutation q = new Permutation(1, 0, 2);
    Permutation r = new Permutation(2, 1, 0);

    assertTrue(p.equals(q));
    assertEquals(p.hashCode(), q.hashCode());
    assertFalse(p.equals(r));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDuplicateImageRejected() {
    new Permutation(0, 1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeImageRejected() {
    new Permutation(0, -1, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOutOfRangeImageRejected() {
    new Permutation(0, 1, 3);
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void testImageIndexOutOfRangeRejected() {
    new Permutation(0, 1, 2).image(3);
  }
}
