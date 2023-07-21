package gtf.math;

import java.io.Serializable;

/*
 * Watch out for serialization issues, since this
 * class is not final and can be extended [Bloch]
 */

/**
 * <p>Represents a point in 2-dimensional Euclidean space, E^2.
 * Since E^2 is a torsor over 2-dimensional vector space R^2, one
 * cannot add two Point2D objects; but one can however add a
 * Vector2D to a Point2D. Similarly, one can subtract two Point2D
 * objects and obtain a Vector2D.</p>
 *
 *@author     Geoffrey Falk
 *@see        Vector2D
 */
public class Point2D implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * The X component
   */
  public final double x;

  /**
   * The Y component
   */
  public final double y;

  /**
   *  Constructor for the Point2D object
   *
   *@param  x  Description of the Parameter
   *@param  y  Description of the Parameter
   */
  public Point2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Gets the x coordinate.
   */
  public double getX() {
    return x;
  }

  /**
   * Gets the y coordinate.
   */
  public double getY() {
    return y;
  }

  /**
   * Add a vector to this Point2D.
   *
   *@param  v  The vector to add
   */
  public Point2D add(Vector2D v) {
    return new Point2D(x + v.x, y + v.y);
  }

  /**
   * Subtract 2 Point2Ds, returning a vector.
   *
   *@param   p  The point to subtract
   */
  public Vector2D subtract(Point2D p) {
    return new Vector2D(x - p.x, y - p.y);
  }

  /**
   * toString method.
   */
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("(");
    sb.append(Double.toString(x));
    sb.append(", ");
    sb.append(Double.toString(y));
    sb.append(")");
    return sb.toString();
  }

  /**
   * equals method with a tolerance.
   */
  public boolean nearlyEquals(Point2D p, double epsilon) {
    if (p == null) {
      return false;
    }
    return (subtract(p).norm2() < epsilon * epsilon);
  }

  /**
   * equals method.
   */
  public boolean equals(Object o) {
    if (! (o instanceof Point2D)) {
      return false;
    }
    return equals((Point2D) o);
  }

  /**
   * equals method.
   */
  public boolean equals(Point2D p) {
    if (p == null) {
      return false;
    }
    long xL = Double.doubleToLongBits(x);
    long yL = Double.doubleToLongBits(y);
    long pxL = Double.doubleToLongBits(p.x);
    long pyL = Double.doubleToLongBits(p.y);
    return (xL == pxL && yL == pyL);
  }

  /**
   * Returns a hash code for this object.
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
