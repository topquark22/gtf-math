package gtf.math.group;

import java.math.BigInteger;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import gtf.math.Permutation;


/**
 * Finite group of permutations under composition.
 *
 * @author gtf
 */
public class PermutationGroup implements FiniteGroup<Permutation> {

  private final int size;
  private final Set<Permutation> elements;
  private final Permutation id;

  /**
   * Creates a permutation group from a set of permutations.
   *
   * @param size the permutation size
   * @param elements the group elements
   */
  public PermutationGroup(int size, Set<Permutation> elements) {
    if (size <= 0) {
      throw new IllegalArgumentException("size must be positive");
    }
    if (elements == null) {
      throw new NullPointerException("elements");
    }

    this.size = size;
    this.id = Permutation.identity(size);

    LinkedHashSet<Permutation> copy = new LinkedHashSet<Permutation>();

    for (Permutation p : elements) {
      checkElementSize(p);
      copy.add(p);
    }

    if (!copy.contains(id)) {
      throw new IllegalArgumentException("group must contain identity");
    }

    this.elements = Collections.unmodifiableSet(copy);
  }

  public Set<Permutation> elements() {
    return elements;
  }

  public boolean contains(Permutation arg) {
    return elements.contains(arg);
  }

  public BigInteger order() {
    return BigInteger.valueOf(elements.size());
  }

  public Permutation inv(Permutation arg) {
    checkContains(arg);

    Permutation inverse = arg.inverse();

    if (!contains(inverse)) {
      throw new IllegalStateException("inverse not contained in group");
    }

    return inverse;
  }

  public Permutation mul(Permutation arg1, Permutation arg2) {
    checkContains(arg1);
    checkContains(arg2);

    Permutation result = arg1.compose(arg2);

    if (!contains(result)) {
      throw new IllegalStateException("group not closed under composition");
    }

    return result;
  }

  public Permutation id() {
    return id;
  }

  private void checkContains(Permutation arg) {
    checkElementSize(arg);

    if (!contains(arg)) {
      throw new IllegalArgumentException("permutation not contained in group");
    }
  }

  private void checkElementSize(Permutation arg) {
    if (arg == null) {
      throw new NullPointerException("arg");
    }
    if (arg.size() != size) {
      throw new IllegalArgumentException("permutation size mismatch");
    }
  }
}
