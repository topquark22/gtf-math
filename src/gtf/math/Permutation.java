package gtf.math;

import java.util.Arrays;


/**
 * Immutable permutation of the set {@code {0, ..., n - 1}}.
 *
 * <p>
 * A permutation stores the image of each index. For example, if
 * {@code p.image(2) == 0}, then the permutation sends index {@code 2} to
 * index {@code 0}.
 * </p>
 *
 * @author gtf
 */
public final class Permutation {

  private final int[] images;

  /**
   * Creates a permutation from the images of {@code 0, ..., n - 1}.
   *
   * @param images the image of each index
   */
  public Permutation(int... images) {
    if (images == null) {
      throw new NullPointerException("images");
    }

    validate(images);
    this.images = images.clone();
  }

  /**
   * Creates the identity permutation of a given size.
   *
   * @param size the size
   * @return the identity permutation
   */
  public static Permutation identity(int size) {
    if (size < 0) {
      throw new IllegalArgumentException("size must be non-negative");
    }

    int[] images = new int[size];

    for (int i = 0; i < size; i++) {
      images[i] = i;
    }

    return new Permutation(images);
  }

  /**
   * @return the number of elements permuted
   */
  public int size() {
    return images.length;
  }

  /**
   * Returns the image of one index.
   *
   * @param index the index
   * @return the image of the index
   */
  public int image(int index) {
    if (index < 0 || index >= size()) {
      throw new ArrayIndexOutOfBoundsException(index);
    }

    return images[index];
  }

  /**
   * @return the inverse permutation
   */
  public Permutation inverse() {
    int[] inverse = new int[size()];

    for (int i = 0; i < size(); i++) {
      inverse[images[i]] = i;
    }

    return new Permutation(inverse);
  }

  /**
   * Composes this permutation with another permutation.
   *
   * <p>
   * The resulting permutation is {@code this} after {@code arg}; that is,
   * {@code result.image(i) == this.image(arg.image(i))}.
   * </p>
   *
   * @param arg the permutation applied first
   * @return the composite permutation
   */
  public Permutation compose(Permutation arg) {
    if (arg == null) {
      throw new NullPointerException("arg");
    }
    if (arg.size() != size()) {
      throw new IllegalArgumentException("size mismatch");
    }

    int[] result = new int[size()];

    for (int i = 0; i < size(); i++) {
      result[i] = image(arg.image(i));
    }

    return new Permutation(result);
  }

  /**
   * @return a defensive copy of the permutation images
   */
  public int[] toArray() {
    return images.clone();
  }

  @Override
  public boolean equals(Object arg) {
    if (this == arg) {
      return true;
    }
    if (!(arg instanceof Permutation)) {
      return false;
    }

    return Arrays.equals(images, ((Permutation) arg).images);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(images);
  }

  @Override
  public String toString() {
    return Arrays.toString(images);
  }

  private static void validate(int[] images) {
    boolean[] seen = new boolean[images.length];

    for (int i = 0; i < images.length; i++) {
      int image = images[i];

      if (image < 0 || image >= images.length) {
        throw new IllegalArgumentException("invalid image: " + image);
      }
      if (seen[image]) {
        throw new IllegalArgumentException("duplicate image: " + image);
      }

      seen[image] = true;
    }
  }
}
