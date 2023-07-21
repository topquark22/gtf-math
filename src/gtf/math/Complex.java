package gtf.math;

import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.IOException;

/**
 * Represents a complex number.
 *
 *@author     gtf
 */
public final class Complex implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * The complex number 0.
   */
  public final static Complex ZERO = new Complex(0.0, 0.0);

  /**
   * The complex number 1.
   */
  public final static Complex ONE = new Complex(1.0, 0.0);

  /**
   * The complex number i.
   */
  public final static Complex I = new Complex(0.0, 1.0);

  /**
   * The point at infinity.
   */
  public final static Complex INFINITY =
    new Complex(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);

  private final static double root2 = Math.sqrt(2.0);

  /**
   * The real component.
   */
  private double x;

  /**
   * The imaginary component.
   */
  private double y;

  /**
   * Whether this Complex is NaN.
   */
  private transient boolean isNaN;

  /**
   * Whether this Complex is the point at infinity.
   */
  private transient boolean isInfinite;

  /**
   * Setup method (common to constructor and readObject)
   */
  private void _setup(double x, double y) {
    if (Double.isNaN(x) || Double.isNaN(y)) {
      this.x = Double.NaN;
      this.y = Double.NaN;
      this.isNaN = true;
      this.isInfinite = false;
    } else if (Double.isInfinite(x) || Double.isInfinite(y)) {
      this.x = Double.POSITIVE_INFINITY;
      this.y = Double.POSITIVE_INFINITY;
      this.isNaN = false;
      this.isInfinite = true;
    } else {
      this.x = x;
      this.y = y;
      this.isNaN = false;
      this.isInfinite = false;
    }
  }

  /**
   * Constructor for the Complex object.
   *
   *@param  x  real part
   *@param  y  imaginary part
   */
  public Complex(double x, double y) {
    _setup(x, y);
  }

  /**
   * Set up a consistent state when the object is deserialized.
   */
  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject();
    _setup(x, y);
  }

  /**
   * Get the real part of the complex number.
   *
   *@return the real part
   */
  public double getX() {
    return x;
  }

  /**
   * Get the imaginary part of the complex number.
   *
   *@return the imaginary part
   */
  public double getY() {
    return y;
  }

  /**
   * Find out whether this Complex is NaN.
   *
   *@return true if NaN
   */
  public boolean isNaN() {
    return isNaN;
  }

  /**
   * Find out whether this Complex is the point at infinity.
   *
   *@return true if it is the point at infinity
   */
  public boolean isInfinite() {
    return isInfinite;
  }

  /**
   * A string representation of this complex number.
   *
   *@return    String representation of the complex number
   */
  public String toString() {
    if (y == 0.0) {
      return ("" + x);
    }
    return (x + (y < 0 ? "" + y : "+" + y) + "i");
  }

  /**
   * Add to another complex number.
   *
   *@param  c  the summand
   *@return    the sum
   */
  public Complex add(Complex c) {
    return new Complex(x + c.x, y + c.y);
  }

  /**
   * Multiply by another complex number.
   *
   *@param  c  The multiplicand
   *@return    The complex product
   */
  public Complex multiply(Complex c) {
    return new Complex(x * c.x - y * c.y, x * c.y + y * c.x);
  }

  /**
   * Multiply by a scalar.
   *
   *@param  d  The scalar multiplicand
   *@return    The product
   */
  public Complex multiply(double d) {
    return new Complex(x * d, y * d);
  }

  /**
   * The squared norm.
   *
   *@return    The squared norm
   */
  public double norm2() {
    return x * x + y * y;
  }

  /**
   * The norm.
   *
   *@return    The norm
   */
  public double norm() {
    return Math.sqrt(norm2());
  }

  /**
   * The complex conjugate.
   *
   *@return The complex conjugate
   */
  public Complex conjugate() {
    return new Complex(x, -y);
  }

  /**
   * The reciprocal of the complex number.
   *
   *@return the reciprocal, or undefined if number is zero
   */
  public Complex recip() {
    return conjugate().multiply(1.0 / norm2());
  }

  /**
   * The argument (angle) for polar coordinates.
   *
   *@return    The argument
   */
  public double arg() {
    return Math.atan(y / x);
  }

  /**
   * A polar representation of the complex number.
   *
   *@return    polar representation of the complex number
   */
  public Complex toPolar() {
    return new Complex(norm(), arg());
  }

  /**
   * A cartesian representation from the polar representation.
   *
   *@return    A cartesian representation of the complex number
   *           from polar coordinates
   *@since
   */
  public Complex toCartesian() {
    return new Complex(x * Math.cos(y), x * Math.sin(y));
  }

  /**
   * Square root of the complex number. Branch cut is
   * along the positive real axis.
   *
   *@return    Square root of the complex number
   */
  public Complex sqrt() {
    double r = norm();
    return new Complex(
        (y < 0 ? -1 : 1) * Math.sqrt(r + x) / root2,
        Math.sqrt(r - x) / root2
        );
  }

  /**
   * Raise the complex number to an integral power.
   * Algorithm uses bit-shift multiplication.
   *
   *@param  exponent  The exponent
   *@return           The complex power
   */
  public Complex pow(int exponent) {
    if (exponent == 0) {
      return ONE;
    } else if (exponent == -1) {
      double n = norm2();
      return new Complex(x / n, -y / n);
    } else if (exponent < 0) {
      return pow(-1).pow(-exponent);
    } else {
      Complex result = ONE;
      for (int i = 30; i >= 0; i--) {
        result = result.multiply(result);
        if (((exponent >>> i) & 1) == 1) {
          result = result.multiply(this);
        }
      }
      return result;
    }
  }

  /**
   * The complex number raised to a double exponent.
   * Algorithm uses polar coordinates.
   *
   *@param  exponent  The double exponent
   *@return           The complex power
   */
  public Complex pow(double exponent) {
    Complex polar = toPolar();
    Complex polarResult = new Complex(Math.pow(polar.x, exponent), polar.y * exponent);
    return polarResult.toCartesian();
  }

  /**
   * Two complex numbers are equal if their components are equal.
   *
   *@param  c  Complex number to compare to
   *@return    Whether the values compare equal
   */
  public boolean equals(Complex c) {
    if (c == null || isNaN() || c.isNaN()) {
      return false;
    }
    if (isInfinite() && c.isInfinite()) {
      return true;
    }
    long xL = Double.doubleToLongBits(x);
    long yL = Double.doubleToLongBits(y);
    long cxL = Double.doubleToLongBits(c.x);
    long cyL = Double.doubleToLongBits(c.y);
    return (xL == cxL && yL == cyL);
  }

  /**
   * Two complex numbers are equal if their components are equal.
   *
   *@param  obj  Object to compare to
   *@return      Whether the other object is a Complex with same components.
   */
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof Complex)) {
      return false;
    }
    return equals((Complex) obj);
  }

  /**
   * Hash-code method.
   *
   *@return    A hash code for this Object
   */
  public int hashCode() {
    long xL = Double.doubleToLongBits(x);
    long yL = Double.doubleToLongBits(y);
    int xh = (int) (xL ^ (xL >>> 32));
    int yh = (int) (yL ^ (yL >>> 32));
    int result = 17;
    result = 37 * result + xh;
    result = 37 * result + yh;
    return result;
  }
}
