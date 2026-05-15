package gtf.math.algebra.impl;

import gtf.math.algebra.InnerProductSpace;
import gtf.math.algebra.RealField;


/**
 * Standard Euclidean vector space R^n.
 *
 * <p>
 * Vectors are represented as {@code double[]} coordinate arrays relative to
 * the standard basis.
 * </p>
 *
 * @author gtf
 */
public class EuclideanSpace
    implements InnerProductSpace<double[], Double, RealField> {

  private final int dimension;

  public EuclideanSpace(int dimension) {
    if (dimension < 0) {
      throw new IllegalArgumentException(
          "dimension must be non-negative");
    }
    this.dimension = dimension;
  }

  @Override
  public RealField ring() {
    return RealField.INSTANCE;
  }

  @Override
  public double[] zero() {
    return new double[dimension];
  }

  @Override
  public double[] add(double[] arg1, double[] arg2) {
    validate(arg1);
    validate(arg2);

    double[] result = new double[dimension];

    for (int i = 0; i < dimension; i++) {
      result[i] = arg1[i] + arg2[i];
    }

    return result;
  }

  @Override
  public double[] neg(double[] arg) {
    validate(arg);

    double[] result = new double[dimension];

    for (int i = 0; i < dimension; i++) {
      result[i] = -arg[i];
    }

    return result;
  }

  @Override
  public double[] mul(Double scalar, double[] elt) {
    validate(elt);

    double[] result = new double[dimension];

    for (int i = 0; i < dimension; i++) {
      result[i] = scalar * elt[i];
    }

    return result;
  }

  @Override
  public int dimension() {
    return dimension;
  }

  @Override
  public double[] basisVector(int index) {
    if (index < 0 || index >= dimension) {
      throw new ArrayIndexOutOfBoundsException(index);
    }

    double[] result = new double[dimension];
    result[index] = 1.0;
    return result;
  }

  @Override
  public Double coordinate(double[] vector, int index) {
    validate(vector);

    if (index < 0 || index >= dimension) {
      throw new ArrayIndexOutOfBoundsException(index);
    }

    return vector[index];
  }

  /**
   * Computes the vector cross product in R^3.
   *
   * <p>
   * This operation is only defined for the three-dimensional Euclidean space.
   * Conceptually, it is the Hodge dual of the wedge product.
   * </p>
   *
   * @param arg1 the first vector
   * @param arg2 the second vector
   * @return the cross product
   */
  public double[] crossProduct(double[] arg1, double[] arg2) {
    if (dimension != 3) {
      throw new UnsupportedOperationException(
          "cross product is only defined in dimension 3");
    }

    validate(arg1);
    validate(arg2);

    return new double[] {
        arg1[1] * arg2[2] - arg1[2] * arg2[1],
        arg1[2] * arg2[0] - arg1[0] * arg2[2],
        arg1[0] * arg2[1] - arg1[1] * arg2[0]
    };
  }

  private void validate(double[] vector) {
    if (vector == null) {
      throw new NullPointerException("vector");
    }
    if (vector.length != dimension) {
      throw new IllegalArgumentException("dimension mismatch");
    }
  }
}
