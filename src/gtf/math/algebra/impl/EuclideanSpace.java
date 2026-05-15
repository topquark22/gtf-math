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

  private void validate(double[] vector) {
    if (vector == null) {
      throw new NullPointerException("vector");
    }
    if (vector.length != dimension) {
      throw new IllegalArgumentException("dimension mismatch");
    }
  }
}
