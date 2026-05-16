package gtf.math.group;

import java.util.LinkedHashSet;
import java.util.Set;

import gtf.math.Permutation;


/**
 * Factory methods for finite permutation groups.
 *
 * @author gtf
 */
public final class PermutationGroups {

  private PermutationGroups() {
  }

  /**
   * Returns the symmetric group {@code S_n}.
   *
   * @param n the permutation size
   * @return the symmetric group on {@code n} letters
   */
  public static PermutationGroup symmetric(int n) {
    return new PermutationGroup(n, generateAll(n));
  }

  /**
   * Returns the alternating group {@code A_n}.
   *
   * @param n the permutation size
   * @return the alternating group on {@code n} letters
   */
  public static PermutationGroup alternating(int n) {
    LinkedHashSet<Permutation> elements = new LinkedHashSet<Permutation>();

    for (Permutation p : generateAll(n)) {
      if (isEven(p)) {
        elements.add(p);
      }
    }

    return new PermutationGroup(n, elements);
  }

  /**
   * Returns the dihedral group {@code D_n} embedded in {@code S_n}.
   *
   * @param n the number of polygon vertices
   * @return the dihedral group of order {@code 2n}
   */
  public static PermutationGroup dihedral(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException("n must be positive");
    }

    LinkedHashSet<Permutation> elements = new LinkedHashSet<Permutation>();

    for (int r = 0; r < n; r++) {
      int[] rotation = new int[n];
      int[] reflection = new int[n];

      for (int i = 0; i < n; i++) {
        rotation[i] = (i + r) % n;
        reflection[i] = (r - i + n) % n;
      }

      elements.add(new Permutation(rotation));
      elements.add(new Permutation(reflection));
    }

    return new PermutationGroup(n, elements);
  }

  private static Set<Permutation> generateAll(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException("n must be positive");
    }

    LinkedHashSet<Permutation> result = new LinkedHashSet<Permutation>();

    int[] values = new int[n];

    for (int i = 0; i < n; i++) {
      values[i] = i;
    }

    permute(values, 0, result);

    return result;
  }

  private static void permute(
      int[] values,
      int index,
      Set<Permutation> result) {

    if (index == values.length) {
      result.add(new Permutation(values.clone()));
      return;
    }

    for (int i = index; i < values.length; i++) {
      swap(values, index, i);
      permute(values, index + 1, result);
      swap(values, index, i);
    }
  }

  private static void swap(int[] values, int i, int j) {
    int temp = values[i];
    values[i] = values[j];
    values[j] = temp;
  }

  private static boolean isEven(Permutation arg) {
    boolean even = true;

    for (int i = 0; i < arg.size(); i++) {
      for (int j = i + 1; j < arg.size(); j++) {
        if (arg.image(i) > arg.image(j)) {
          even = !even;
        }
      }
    }

    return even;
  }
}
