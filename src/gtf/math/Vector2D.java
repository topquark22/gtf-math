package gtf.math;

/**
 * <p>Represents a vector in 2-dimensional vector space, R^2.</p>
 *
 * <p>Note: This class overrides the equals() method in Point2D.
 * This is acceptable here, since we have not added an aspect
 * ([Bloch], Item 7).</p>
 *
 *@author     Geoffrey Falk
 *@see        Point2D
 */
public class Vector2D extends Point2D {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * The zero vector
   */
  public static final Vector2D ZERO = new Vector2D(0.0D, 0.0D);

  /**
   *  Constructor for the Vector2D object
   *
   *@param  x  Description of the Parameter
   *@param  y  Description of the Parameter
   */
  public Vector2D(double x, double y) {
    super(x, y);
  }

  /**
   *  Add another vector to this vector.
   *
   *@param  v  Another vector to add to this vector.
   *@return    The vector sum. NOTE: Overriding and
   *           restricting the return type from Point2D
   *           requires JDK 1.5
   */
  public Vector2D add(Vector2D v) {
    return new Vector2D(x + v.x, y + v.y);
  }

  /**
   * Multiply this vector by a scalar.
   *
   *@param  s  The scalar
   *@return    The new vector
   */
  public Vector2D multiply(double s) {
    return new Vector2D(s * x, s * y);
  }

  /**
   * Take the dot product of 2 vectors.
   *
   *@param  v  Another vector
   *@return    The dot product
   */
  public double dotProduct(Vector2D v) {
    return x * v.x + y * v.y;
  }

  /**
   * The squared norm of the vector.
   *
   *@return    The squared norm
   */
  public double norm2() {
    return x * x + y * y;
  }

  /**
   * The norm of the vector.
   *
   *@return    The squared norm
   */
  public double norm() {
    return Math.sqrt(norm2());
  }

  /**
   * The normalized unit vector. Watch out for (0,0) as the result
   * will be undefined.
   *
   *@return    The normalized unit vector
   */
  public Vector2D normalize() {
    return multiply(1.0D / norm());
  }

  /**
   * Rotates the vector counterclockwise by a given number of radians.
   *
   *@param    angle  The angle through which to rotate, in radians
   *@return          The rotated vector
   */
  public Vector2D rotate(double angle) {
    double u = Math.cos(angle);
    double v = Math.sin(angle);
    return new Vector2D(u * x - v * y, v * x + u * y);
  }

  /**
   * equals method.
   */
  public boolean equals(Object o) {
    if (! (o instanceof Vector2D)) {
      return false;
    }
    return equals((Vector2D) o);
  }
}
