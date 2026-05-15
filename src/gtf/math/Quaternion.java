package gtf.math;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;


/**
 * Represents a quaternion.
 *
 * <p>
 * A quaternion has the form {@code w + xi + yj + zk}, where
 * {@code i^2 = j^2 = k^2 = ijk = -1}.
 * </p>
 *
 * @author gtf
 */
public final class Quaternion implements Serializable {

  private static final long serialVersionUID = 1L;

  public static final Quaternion ZERO = new Quaternion(0.0, 0.0, 0.0, 0.0);

  public static final Quaternion ONE = new Quaternion(1.0, 0.0, 0.0, 0.0);

  public static final Quaternion I = new Quaternion(0.0, 1.0, 0.0, 0.0);

  public static final Quaternion J = new Quaternion(0.0, 0.0, 1.0, 0.0);

  public static final Quaternion K = new Quaternion(0.0, 0.0, 0.0, 1.0);

  private double w;

  private double x;

  private double y;

  private double z;

  private transient boolean isNaN;

  private transient boolean isInfinite;

  public Quaternion(double w, double x, double y, double z) {
    setup(w, x, y, z);
  }

  private void setup(double w, double x, double y, double z) {
    if (Double.isNaN(w) || Double.isNaN(x)
        || Double.isNaN(y) || Double.isNaN(z)) {
      this.w = Double.NaN;
      this.x = Double.NaN;
      this.y = Double.NaN;
      this.z = Double.NaN;
      this.isNaN = true;
      this.isInfinite = false;
    } else if (Double.isInfinite(w) || Double.isInfinite(x)
        || Double.isInfinite(y) || Double.isInfinite(z)) {
      this.w = Double.POSITIVE_INFINITY;
      this.x = Double.POSITIVE_INFINITY;
      this.y = Double.POSITIVE_INFINITY;
      this.z = Double.POSITIVE_INFINITY;
      this.isNaN = false;
      this.isInfinite = true;
    } else {
      this.w = w;
      this.x = x;
      this.y = y;
      this.z = z;
      this.isNaN = false;
      this.isInfinite = false;
    }
  }

  private void readObject(ObjectInputStream in)
      throws IOException, ClassNotFoundException {
    in.defaultReadObject();
    setup(w, x, y, z);
  }

  /**
   * @return the real component
   */
  public double getW() {
    return w;
  }

  /**
   * @return the i component
   */
  public double getX() {
    return x;
  }

  /**
   * @return the j component
   */
  public double getY() {
    return y;
  }

  /**
   * @return the k component
   */
  public double getZ() {
    return z;
  }

  public boolean isNaN() {
    return isNaN;
  }

  public boolean isInfinite() {
    return isInfinite;
  }

  public Quaternion add(Quaternion q) {
    return new Quaternion(w + q.w, x + q.x, y + q.y, z + q.z);
  }

  public Quaternion subtract(Quaternion q) {
    return new Quaternion(w - q.w, x - q.x, y - q.y, z - q.z);
  }

  public Quaternion negate() {
    return new Quaternion(-w, -x, -y, -z);
  }

  public Quaternion multiply(double scalar) {
    return new Quaternion(scalar * w, scalar * x, scalar * y, scalar * z);
  }

  /**
   * Hamilton product of two quaternions.
   */
  public Quaternion multiply(Quaternion q) {
    return new Quaternion(
        w * q.w - x * q.x - y * q.y - z * q.z,
        w * q.x + x * q.w + y * q.z - z * q.y,
        w * q.y - x * q.z + y * q.w + z * q.x,
        w * q.z + x * q.y - y * q.x + z * q.w);
  }

  public Quaternion conjugate() {
    return new Quaternion(w, -x, -y, -z);
  }

  public double norm2() {
    return w * w + x * x + y * y + z * z;
  }

  public double norm() {
    return Math.sqrt(norm2());
  }

  public Quaternion recip() {
    double n = norm2();
    if (n == 0.0) {
      throw new ArithmeticException("division by zero");
    }
    return conjugate().multiply(1.0 / n);
  }

  public Quaternion divide(Quaternion q) {
    return multiply(q.recip());
  }

  public Quaternion normalize() {
    double n = norm();
    if (n == 0.0) {
      throw new ArithmeticException("division by zero");
    }
    return multiply(1.0 / n);
  }

  /**
   * Applies this quaternion to rotate a pure-vector quaternion.
   *
   * <p>
   * The receiver is normalized before applying the rotation.
   * </p>
   *
   * @param vector a pure-vector quaternion
   * @return the rotated pure-vector quaternion
   */
  public Quaternion rotate(Quaternion vector) {
    Quaternion unit = normalize();
    return unit.multiply(vector).multiply(unit.conjugate());
  }

  public String toString() {
    return w + (x < 0 ? "" : "+") + x + "i"
        + (y < 0 ? "" : "+") + y + "j"
        + (z < 0 ? "" : "+") + z + "k";
  }

  public boolean equals(Quaternion q) {
    if (q == null || isNaN() || q.isNaN()) {
      return false;
    }
    if (isInfinite() && q.isInfinite()) {
      return true;
    }
    return Double.doubleToLongBits(w) == Double.doubleToLongBits(q.w)
        && Double.doubleToLongBits(x) == Double.doubleToLongBits(q.x)
        && Double.doubleToLongBits(y) == Double.doubleToLongBits(q.y)
        && Double.doubleToLongBits(z) == Double.doubleToLongBits(q.z);
  }

  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof Quaternion)) {
      return false;
    }
    return equals((Quaternion) obj);
  }

  public int hashCode() {
    long wL = Double.doubleToLongBits(w);
    long xL = Double.doubleToLongBits(x);
    long yL = Double.doubleToLongBits(y);
    long zL = Double.doubleToLongBits(z);

    int result = 17;
    result = 37 * result + (int) (wL ^ (wL >>> 32));
    result = 37 * result + (int) (xL ^ (xL >>> 32));
    result = 37 * result + (int) (yL ^ (yL >>> 32));
    result = 37 * result + (int) (zL ^ (zL >>> 32));
    return result;
  }
}
