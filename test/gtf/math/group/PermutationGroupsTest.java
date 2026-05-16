package gtf.math.group;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Test;

import gtf.math.Permutation;


/**
 * @author gtf
 */
public class PermutationGroupsTest {

  @Test
  public void testSymmetricGroupOrder() {
    FiniteGroup<Permutation> s4 = PermutationGroups.symmetric(4);

    assertEquals(BigInteger.valueOf(24), s4.order());
  }

  @Test
  public void testAlternatingGroupOrder() {
    FiniteGroup<Permutation> a4 = PermutationGroups.alternating(4);

    assertEquals(BigInteger.valueOf(12), a4.order());
  }

  @Test
  public void testDihedralGroupOrder() {
    FiniteGroup<Permutation> d5 = PermutationGroups.dihedral(5);

    assertEquals(BigInteger.valueOf(10), d5.order());
  }

  @Test
  public void testAlternatingGroupClosure() {
    FiniteGroup<Permutation> a3 = PermutationGroups.alternating(3);

    Permutation p = new Permutation(1, 2, 0);
    Permutation q = new Permutation(2, 0, 1);

    assertTrue(a3.contains(a3.mul(p, q)));
  }

  @Test
  public void testDihedralGroupContainsIdentity() {
    FiniteGroup<Permutation> d4 = PermutationGroups.dihedral(4);

    assertTrue(d4.contains(Permutation.identity(4)));
  }
}
