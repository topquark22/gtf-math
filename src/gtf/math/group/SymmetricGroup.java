package gtf.math.group;

import gtf.math.Permutation;


/**
 * Symmetric group on {@code n} letters.
 *
 * <p>
 * Elements are permutations of {@code {0, ..., n - 1}}. The group operation
 * is composition, with {@code mul(arg1, arg2)} meaning {@code arg1} after
 * {@code arg2}.
 * </p>
 *
 * @author gtf
 */
public class SymmetricGroup implements Group<Permutation> {

  private final int size;
  private final Permutation id;

  /**
   * Creates the symmetric group on {@code size} letters.
   *
   * @param size the number of letters permuted
   */
  public SymmetricGroup(int size) {
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

  private void checkElement(Permutation arg) {
    if (arg == null) {
      throw new NullPointerException("arg");
    }
    if (arg.size() != size) {
      throw new IllegalArgumentException("permutation size mismatch");
    }
  }
}
