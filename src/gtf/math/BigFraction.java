package gtf.math;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Arbitrary-precision, immutable rational numbers in reduced form.
 *
 *@author     gtf
 */
public final class BigFraction extends Number implements Comparable<BigFraction>, Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 7989881834852137145L;

  /**
   * Used to separate the numerator from the denominator.
   */
  public final static String VIRGULE = "/";

  /**
   * The numerator.
   */
  private final BigInteger p;

  /**
   * The denominator. It will always be nonzero and positive.
   */
  private final BigInteger q;

  /**
   * The BigFraction constant zero.
   */
  public final static BigFraction ZERO = new BigFraction(BigInteger.ZERO);

  /**
   * The BigFraction constant one.
   */
  public final static BigFraction ONE = new BigFraction(BigInteger.ONE);

  /**
   * Constructor for the BigFraction object.
   *
   *@param  p  numerator
   *@param  q  denominator
   */
  public BigFraction(BigInteger p, BigInteger q) {
    if (q.equals(BigInteger.ZERO)) {
      throw new ArithmeticException("/ by zero");
    }
    if (p.equals(BigInteger.ZERO)) {
      this.p = BigInteger.ZERO;
      this.q = BigInteger.ONE;
      return;
    }
    if (q.compareTo(BigInteger.ZERO) == -1) {
      p = p.negate();
      q = q.negate();
    }
    BigInteger gcd = p.gcd(q);
    this.p = p.divide(gcd);
    this.q = q.divide(gcd);
  }

  /**
   * Constructor that takes a string representation of the BigFraction.
   * The representation must be of the form: biginteger <b>or</b>
   * <i>biginteger</i> / </i>biginteger</i>, where <i>biginteger</i>
   * represents a string that can be parsed into a BigInteger.
   * Whitespace is ignored.
   *
   * @param stringRep   String representation of a BigFraction
   * @throws NumberFormatException if the string or any component
   *         that makes it up cannot be parsed
   */
  public BigFraction(String stringRep) {
    String[] parts = stringRep.split(VIRGULE, 2);
    BigFraction result;
    int count = parts.length;
    if (count == 1) {
      result = new BigFraction(new BigInteger(parts[0].trim()));
    } else if (count == 2) {
      result = new BigFraction(new BigInteger(parts[0].trim()),
                               new BigInteger(parts[1].trim()));
    } else {
      throw new NumberFormatException("Cannot parse \"" + stringRep + "\"");
    }
    this.p = result.p;
    this.q = result.q;
  }

  /**
   * Constructor for the BigFraction object that takes long arguments.
   *
   *@param  p  numerator
   *@param  q  denominator
   */
  public BigFraction(long p, long q) {
    this(new BigInteger(Long.toString(p)), new BigInteger(Long.toString(q)));
  }

  /**
   * Constructor for the BigFraction object that takes a BigInteger value.
   * The denominator is set to 1.
   *
   *@param  p  A long value.
   */
  public BigFraction(BigInteger p) {
    this(p, BigInteger.ONE);
  }

  /**
   * Constructor for the BigFraction object that takes a long value.
   * The denominator is set to 1.
   *
   *@param  p  numerator
   */
  public BigFraction(long p) {
    this(new BigInteger(Long.toString(p)));
  }

  /**
   * Gets the numerator.
   *
   *@return    The numerator value
   */
  public BigInteger getNumerator() {
    return p;
  }

  /**
   * Gets the denominator.
   *
   *@return    The denominator value
   */
  public BigInteger getDenominator() {
    return q;
  }

  /**
   * Multiply by a BigInteger scalar.
   *
   *@param  n  The scalar to multiply
   *@return    The result of multiplication
   */
  public BigFraction multiply(BigInteger n) {
    return new BigFraction(n.multiply(p), q);
  }

  /**
   * Multiply by a long scalar.
   *
   *@param  n  The scalar to multiply
   *@return    The result of multiplication
   */
  public BigFraction multiply(long n) {
    return new BigFraction(new BigInteger(Long.toString(n)).multiply(p), q);
  }

  /**
   * Add to another fraction.
   *
   *@param  f  The fraction to add
   *@return    The result of addition
   */
  public BigFraction add(BigFraction f) {
    return new BigFraction(p.multiply(f.q).add(q.multiply(f.p)), q.multiply(f.q));
  }

  /**
   * Subtract another fraction.
   *
   *@param  f  The fraction to subtract
   *@return    The result of subtraction
   */
  public BigFraction subtract(BigFraction f) {
    return new BigFraction(p.multiply(f.q).subtract(q.multiply(f.p)), q.multiply(f.q));
  }

  /**
   * Multiply by another fraction.
   *
   *@param  f  The fraction to multiply
   *@return    The result of multiplication
   */
  public BigFraction multiply(BigFraction f) {
    return new BigFraction(p.multiply(f.p), q.multiply(f.q));
  }

  /**
   * Divide by another fraction.
   *
   *@param  f  The fraction to divide by
   *@return    The result of division
   */
  public BigFraction divide(BigFraction f) {
    return new BigFraction(p.multiply(f.q), q.multiply(f.p));
  }

  /**
   * Compute the negation of this fraction.
   *
   *@return    The negation
   */
  public BigFraction negate() {
    return new BigFraction(p.negate(), q);
  }

  /**
   * Compute the reciprocal of this fraction.
   *
   *@return    The reciprocal
   */
  public BigFraction reciprocal() {
    return new BigFraction(q, p);
  }

  /**
   * Compute a long approximation to this fraction by taking the
   * quotient. The result may involve loss of precision by rounding
   * to a long value.
   *
   *@return    The quotient as a long value
   *@see java.lang.Number#longValue()
   */
  public long longValue() {
    return p.divide(q).longValue();
  }

  /**
   * Compute an int approximation to this fraction by taking the
   * quotient. The result may involve loss of precision by rounding
   * to an int value.
   *
   *@return    The quotient as an int value
   *@see java.lang.Number#intValue()
   */
  public int intValue() {
    return p.divide(q).intValue();
  }

  /**
   * Computes a floating point approximation to this fraction.
   *
   *@return    The quotient as a float value
   *@see java.lang.Number#floatValue()
   */
  public float floatValue() {
    return p.floatValue() / q.floatValue();
  }

  /**
   * Computes a double approximation to this fraction.
   *
   *@return    The quotient as a double value
   **@see java.lang.Number#doubleValue()
   */
  public double doubleValue() {
    return p.doubleValue() / q.doubleValue();
  }

  /**
   * Return a new fraction that is the absolute value of this fraction.
   *
   *@return the absolute value
   */
  public BigFraction abs() {
    return new BigFraction(p.abs(), q);
  }

  /**
   * Computes the largest BigInteger value not greater than this fraction.
   *
   *@return    The floor as a BigInteger value
   */
  public BigInteger floor() {
    if (q.equals(BigInteger.ONE)) {
      return p;
    } else if (p.compareTo(BigInteger.ZERO) == -1) {
      return p.divide(q).subtract(BigInteger.ONE);
    } else {
      return p.divide(q);
    }
  }

  /**
   * Computes the smallest BigInteger value not smaller than this fraction.
   *
   *@return    The ceil as a BigInteger value
   */
  public BigInteger ceil() {
    if (q.equals(BigInteger.ONE)) {
      return p;
    } else if (p.compareTo(BigInteger.ZERO) == -1) {
      return p.divide(q);
    } else {
      return p.divide(q).add(BigInteger.ONE);
    }
  }

  /**
   * Produces a string representation of this BigFraction.
   *
   *@return    The string representation
   */
  public String toString() {
    if (q.equals(BigInteger.ONE)) {
      return "" + p;
    } else {
      return "" + p + VIRGULE + q;
    }
  }

  /**
   * Parses a string representation of the BigFraction. The representation
   * must be of the form: biginteger <b>or</b> <i>biginteger</i> / </i>biginteger</i>,
   * where <i>biginteger</i> represents a string that can be parsed into a
   * BigInteger. Whitespace is ignored.
   *
   * @param stringRep   String representation of a BigFraction
   * @throws NumberFormatException if the string or any component
   *         that makes it up cannot be parsed
   */
  public static BigFraction parseBigFraction(String stringRep) {
    return new BigFraction(stringRep);
  }

  /**
   * Compares this BigFraction object to another BigFraction (signed comparison).
   *
   *@param  f the BigFraction to be compared
   *@return the value 0 if this BigFraction is equal to the argument BigFraction;
   *       a value less than 0 if this BigFraction is numerically less than
   *       the argument BigFraction; and a value greater than 0 if this
   *       BigFraction is numerically greater than the argument BigFraction
   *       (signed comparison).
   */
  public int compareTo(BigFraction f) {
    if (f == null) {
      throw new NullPointerException("cannot compare BigFraction to null");
    }
    BigFraction d = this.subtract(f);
    return d.getNumerator().signum();
  }

  /**
   * Compares this object to the specified object. The result is true
   * if and only if the argument is not null and is a BigFraction object
   * that is numerically equal to this object.
   *
   *@param     obj - the object to compare with.
   *@return    true if the objects are the same; false otherwise.
   */
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof BigFraction)) {
      return false;
    }
    BigFraction f = (BigFraction) obj;
    return (p.equals(f.p) && q.equals(f.q));
  }

  /**
   * HashCode method.
   *
   *@return    a hash code for this fraction
   */
  public int hashCode() {
    return p.hashCode() ^ q.hashCode();
  }
}
