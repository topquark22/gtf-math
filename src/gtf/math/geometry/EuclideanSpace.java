package gtf.math.geometry;

import gtf.math.algebra.field.R;
import gtf.math.geometry.impl.ArrayMultivector;


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
    implements InnerProductSpace<double[], Double, R> {

  private final int dimension;

  public EuclideanSpace(int dimension) {
    if (dimension < 0) {
      throw new IllegalArgumentException(
          "dimension must be non-negative");
    }
    this.dimension = dimension;
  }

  /**
   * @return the standard Euclidean plane R^2
   */
  public static EuclideanSpace r2() {
    return new EuclideanSpace(2);
  }

  /**
   * @return the standard Euclidean space R^3
   */
  public static EuclideanSpace r3() {
    return new EuclideanSpace(3);
  }

  /**
   * Returns the standard Euclidean space R^n.
   *
   * @param dimension the dimension n
   * @return R^n
   */
  public static EuclideanSpace rn(int dimension) {
    return new EuclideanSpace(dimension);
  }

  @Override
  public R ring() {
    return R.INSTANCE;
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
   * Computes the Euclidean norm of a vector.
   *
   * @param vector the vector
   * @return the Euclidean norm
   */
  public double norm(double[] vector) {
    return Math.sqrt(norm2(vector));
  }

  /**
   * Computes the squared Euclidean norm of a vector.
   *
   * @param vector the vector
   * @return the squared Euclidean norm
   */
  public double norm2(double[] vector) {
    validate(vector);
    return innerProduct(vector, vector);
  }

  /**
   * Computes the Euclidean distance between two vectors.
   *
   * @param arg1 the first vector
   * @param arg2 the second vector
   * @return the Euclidean distance
   */
  public double distance(double[] arg1, double[] arg2) {
    return norm(add(arg1, neg(arg2)));
  }

  /**
   * Returns a normalized copy of a non-zero vector.
   *
   * @param vector the vector
   * @return the normalized vector
   */
  public double[] normalize(double[] vector) {
    double n = norm(vector);
    if (n == 0.0) {
      throw new ArithmeticException("division by zero");
    }
    return mul(1.0 / n, vector);
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

  /**
   * Converts an ordinary vector to a grade-1 multivector.
   *
   * @param vector the vector
   * @return the corresponding grade-1 multivector
   */
  public Multivector<Double, R> toMultivector(double[] vector) {
    validate(vector);

    ArrayMultivector<Double, R> result =
        new ArrayMultivector<Double, R>(R.INSTANCE, dimension);

    for (int i = 0; i < dimension; i++) {
      result.setCoefficient(1 << i, vector[i]);
    }

    return result;
  }

  /**
   * Extracts the grade-1 vector part of a multivector.
   *
   * @param multivector the multivector
   * @return the vector part
   */
  public double[] fromMultivector(Multivector<Double, R> multivector) {
    if (multivector == null) {
      throw new NullPointerException("multivector");
    }
    if (multivector.dimension() != dimension) {
      throw new IllegalArgumentException("dimension mismatch");
    }

    double[] result = new double[dimension];

    for (int i = 0; i < dimension; i++) {
      result[i] = multivector.coefficient(1 << i);
    }

    return result;
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
