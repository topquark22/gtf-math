package gtf.math.algebra.impl;

import gtf.math.algebra.CliffordAlgebra;
import gtf.math.algebra.Field;
import gtf.math.algebra.InnerProductSpace;
import gtf.math.algebra.Multivector;


/**
 * Basic implementation of a finite-dimensional Clifford algebra.
 *
 * <p>
 * This implementation currently provides blade-grade utilities, the exterior
 * (wedge) product, Hodge dual, and the three-dimensional vector cross product.
 * </p>
 *
 * @author gtf
 *
 * @param <V> the type of the underlying vector-space elements
 * @param <S> the type of the scalar-field elements
 * @param <F> the scalar field
 */
public class CliffordAlgebraImpl<V, S, F extends Field<S>>
    implements CliffordAlgebra<V, S, F> {

  private final InnerProductSpace<V, S, F> vectorSpace;

  public CliffordAlgebraImpl(InnerProductSpace<V, S, F> vectorSpace) {
    if (vectorSpace == null) {
      throw new NullPointerException("vectorSpace");
    }
    this.vectorSpace = vectorSpace;
  }

  @Override
  public InnerProductSpace<V, S, F> vectorSpace() {
    return vectorSpace;
  }

  /**
   * Returns the grade of a basis blade.
   *
   * @param blade the blade bit mask
   * @return the blade grade
   */
  public int grade(int blade) {
    return Integer.bitCount(blade);
  }

  @Override
  public Multivector<S, F> wedge(
      Multivector<S, F> a,
      Multivector<S, F> b) {

    validateCompatible(a, b);

    F field = a.field();
    ArrayMultivector<S, F> result =
        new ArrayMultivector<S, F>(field, a.dimension());

    for (int bladeA = 0; bladeA < a.bladeCount(); bladeA++) {
      S coeffA = a.coefficient(bladeA);

      for (int bladeB = 0; bladeB < b.bladeCount(); bladeB++) {
        S coeffB = b.coefficient(bladeB);

        if ((bladeA & bladeB) != 0) {
          continue;
        }

        int blade = bladeA ^ bladeB;

        S term = field.mul(coeffA, coeffB);

        if (swapParity(bladeA, bladeB)) {
          term = field.neg(term);
        }

        result.setCoefficient(blade,
            field.add(result.coefficient(blade), term));
      }
    }

    return result;
  }

  @Override
  public Multivector<S, F> hodgeDual(Multivector<S, F> a) {

    F field = a.field();
    int pseudoscalar = (1 << a.dimension()) - 1;

    ArrayMultivector<S, F> result =
        new ArrayMultivector<S, F>(field, a.dimension());

    for (int blade = 0; blade < a.bladeCount(); blade++) {
      int dualBlade = pseudoscalar ^ blade;

      S coeff = a.coefficient(blade);

      if (swapParity(blade, dualBlade)) {
        coeff = field.neg(coeff);
      }

      result.setCoefficient(dualBlade,
          field.add(result.coefficient(dualBlade), coeff));
    }

    return result;
  }

  @Override
  public Multivector<S, F> crossProduct(
      Multivector<S, F> a,
      Multivector<S, F> b) {

    if (a.dimension() != 3 || b.dimension() != 3) {
      throw new IllegalArgumentException(
          "cross product is only defined in dimension 3");
    }

    return hodgeDual(wedge(a, b));
  }

  @Override
  public Multivector<S, F> geometricProduct(
      Multivector<S, F> a,
      Multivector<S, F> b) {

    throw new UnsupportedOperationException(
        "geometric product not implemented yet");
  }

  @Override
  public Multivector<S, F> innerProduct(
      Multivector<S, F> a,
      Multivector<S, F> b) {

    throw new UnsupportedOperationException(
        "multivector inner product not implemented yet");
  }

  private void validateCompatible(
      Multivector<S, F> a,
      Multivector<S, F> b) {

    if (a.dimension() != b.dimension()) {
      throw new IllegalArgumentException("dimension mismatch");
    }
  }

  /**
   * Returns true if the blade permutation has odd parity.
   */
  private boolean swapParity(int bladeA, int bladeB) {
    int swaps = 0;

    for (int i = 0; i < Integer.SIZE; i++) {
      if ((bladeA & (1 << i)) != 0) {
        swaps += Integer.bitCount(bladeB & ((1 << i) - 1));
      }
    }

    return (swaps & 1) != 0;
  }
}
