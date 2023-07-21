package gtf.math.algebra;


/**
 * Defines operations on an abstract field. That is, it has an abelian
 * group structure and also a multiplicative abstract group.
 * 
 * @param <T> The type used for the field elements. Must have meaningful
 * equals(), hashCode() methods.
 * 
 * @author gtf
 */
public interface Field<T> extends Ring<T> {

  /**
   * Returns the multiplicative inverse of a non-zero element.
   * 
   * @param arg
   * @return the inverse
   * @throws ArithmeticException if the argument represents the zero element.
   */
  T inv(T arg);

}
