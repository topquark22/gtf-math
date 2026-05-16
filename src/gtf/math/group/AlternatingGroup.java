package gtf.math.group;

import gtf.math.Permutation;


/**
 * Alternating group on {@code n} letters.
 *
 * <p>
 * Elements are even permutations of {@code {0, ..., n - 1}}. The group
 * operation is composition, with {@code mul(arg1, arg2)} meaning {@code arg1}
 * after {@code arg2}.
 * </p>
 *
 * @author gtf
 */
public class AlternatingGroup implements Group<Permutation> {

  private final int size;
  private final Permutation id;

  /**
   * Creates the alternating group on {@code size} letters.
   *
   * @param size the number of letters permuted
   */
  public AlternatingGroup(int size) {
    if (size <= 0) {
      throw new IllegalArgumentException("size must be positive");
    }

    this.size = size;
    id = Permutation.identity(size);
  }

  /**
   * @return the number of letters permuted
   */
  public int size() {
    return size;
  }

  public Permutation inv(Permutation arg) {
    checkElement(arg);
    return arg.inverse();
  }

  public Permutation mul(Permutation arg1, Permutation arg2) {
    checkElement(arg1);
    checkElement(arg2);
    return arg1.compose(arg2);
  }

  public Permutation id() {
    return id;
  }

  /**
   * Returns whether a permutation is an element of this alternating group.
   *
   * @param arg the permutation
   * @return true if the permutation has the right size and is even
   */
  public boolean contains(Permutation arg) {
    return arg != null && arg.size() == size && isEven(arg);
  }

  private void checkElement(Permutation arg) {
    if (arg == null) {
      throw new NullPointerException("arg");
    }
    if (arg.size() != size) {
      throw new IllegalArgumentException("permutation size mismatch");
    }
    if (!isEven(arg)) {
      throw new IllegalArgumentException("permutation is odd");
    }
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
