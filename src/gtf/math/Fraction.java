package gtf.math;

import java.io.Serializable;

/**
 *  Represents an immutable rational number in reduced form.
 *
 *@author     gtf
 */
public final class Fraction extends Number implements Comparable<Fraction>, Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -7476622043987421391L;

  /**
   *  The numerator.
   */
  private final long p;

  /**
   *  The denominator. It will always be nonzero and positive.
   */
  private final long q;

  /**
   *  The Fraction constant zero.
   */
  public final static Fraction ZERO = new Fraction(0);

  /**
   *  The Fraction constant one.
   */
  public final static Fraction ONE = new Fraction(1);

  /**
   *  Constructor for the Fraction object.
   *
   *@param  p  numerator
   *@param  q  denominator
   */
  public Fraction(long p, long q) {
    if (q == 0) {
      throw new ArithmeticException("/ by zero");
    }
    if (p == 0) {
      this.p = 0;
      this.q = 1;
      return;
    }
    if (q < 0) {
      p = -p;
      q = -q;
    }
    long gcd = IMath.gcd(p, q);
    this.p = p / gcd;
    this.q = q / gcd;
  }

  /**
   *  Constructor for the Fraction object that takes a long value.
   *  The denominator is set to 1.
   *
   *@param  p  A long value.
   */
  public Fraction(long p) {
    this(p, 1);
  }

  /**
   *  Gets the numerator.
   *
   *@return    The numerator value
   */
  public long getNumerator() {
    return p;
  }

  /**
   *  Gets the denominator.
   *
   *@return    The denominator value
   */
  public long getDenominator() {
    return q;
  }

  /**
   *  Multiply by a long scalar.
   *
   *@param  n  The scalar to multiply
   *@return    The result of multiplication
   */
  public Fraction multiply(long n) {
    return new Fraction(n * p, q);
  }

  /**
   *  Add to another fraction.
   *
   *@param  f  The fraction to add
   *@return    The result of addition
   */
  public Fraction add(Fraction f) {
    return new Fraction(p * f.q + f.p * q, q * f.q);
  }

  /**
   *  Subtract another fraction.
   *
   *@param  f  The fraction to subtract
   *@return    The result of subtraction
   */
  public Fraction subtract(Fraction f) {
    return new Fraction(p * f.q - f.p * q, q * f.q);
  }

  /**
   *  Multiply by another fraction.
   *
   *@param  f  The fraction to multiply
   *@return    The result of multiplication
   */
  public Fraction multiply(Fraction f) {
    return new Fraction(p * f.p, q * f.q);
  }

  /**
   *  Divide by another fraction.
   *
   *@param  f  The fraction to divide by
   *@return    The result of division
   */
  public Fraction divide(Fraction f) {
    return new Fraction(p * f.q, q * f.p);
  }

  /**
   *  Compute the negation of this fraction.
   *
   *@return    The negation
   */
  public Fraction negate() {
    return new Fraction(-p, q);
  }

  /**
   *  Compute the reciprocal of this fraction.
   *
   *@return    The reciprocal
   */
  public Fraction reciprocal() {
    return new Fraction(q, p);
  }

  /**
   *  Compute a long approximation to this fraction by taking the
   *  quotient. The result may involve loss of precision by rounding
   *  to a long value.
   *
   *@return    The quotient as a long value
   */
  public long longValue() {
    return p / q;
  }

  /**
   *  Compute an int approximation to this fraction by taking the
   *  quotient. The result may involve loss of precision by rounding
   *  to an int value.
   *
   *@return    The quotient as an int value
   */
  public int intValue() {
    return (int) (p / q);
  }

  /**
   *  Computes a floating point approximation to this fraction.
   *
   *@return    The quotient as a float value
   */
  public float floatValue() {
    return (float)p / (float)q;
  }

  /**
   *  Computes a double approximation to this fraction.
   *
   *@return    The quotient as a double value
   */
  public double doubleValue() {
    return (double)p / (double)q;
  }

  /**
   *  Return a new fraction that is the absolute value of this fraction.
   *
   *@return the absolute value
   */
  public Fraction abs() {
    return new Fraction(Math.abs(p), q);
  }

  /**
   *  Computes the largest long value not greater than this fraction.
   *
   *@return    The floor as a long value
   */
  public long floor() {
    if (q == 1) {
      return p;
    } else if (p < 0) {
      return p / q - 1;
    } else {
      return p / q;
    }
  }

  /**
   *  Computes the smallest long value not smaller than this fraction.
   *
   *@return    The ceil as a long value
   */
  public long ceil() {
    if (q == 1) {
      return p;
    } else if (p < 0) {
      return p / q;
    } else {
      return p / q + 1;
    }
  }

  /**
   *  Produces a string representation of this fraction.
   *
   *@return    The string representation
   */
  public String toString() {
    if (q == 1) {
      return "" + p;
    } else {
      return "" + p + "/" + q;
    }
  }

  /**
   * Perform a signed comparison of this Fraction to another Fraction.
   *
   *@param  f the object to be compared
   *@return the value 0 if this Fraction is equal to the argument Fraction;
   *        a value less than 0 if this Fraction is numerically less than
   *        the argument Fraction; and a value greater than 0 if this
   *        Fraction is numerically greater than the argument Fraction
   *        (signed comparison).
   */
  public int compareTo(Fraction f) {
    if (f == null) {
      throw new NullPointerException("cannot compare Fraction to null");
    }
    Fraction d = this.subtract(f);
    return IMath.signum(d.p);
  }

  /**
   *  Compares this object for equality to the specified object. The result
   *  is true if and only if the argument is not null and is a Fraction object
   *  that is numerically equal to this object.
   *
   *@param     obj - the object to compare with.
   *@return    true if the objects are numerically the same; false otherwise.
   */
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof Fraction)) {
      return false;
    }
    Fraction f = (Fraction) obj;
    return (p == f.p && q == f.q);
  }

  /**
   *  HashCode method.
   *
   *@return    a hash code for this fraction
   */
  public int hashCode() {
    return new Long(p).hashCode() ^ new Long(q).hashCode();
  }
}
