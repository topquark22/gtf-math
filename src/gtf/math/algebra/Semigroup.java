package gtf.math.algebra;


/**
 * Defines operations on an abstract semigroup, written multiplicatively.
 * 
 * @param <T> The type used for the semigroup elements. Must have a meaningful
 * equals(), hashCode() methods.
 * 
 * @author gtf
 */
public interface Semigroup<T> {

  /**
   * Returns the group operation on a pair of elements.
   * 
   * @param arg1
   * @param arg2
   * @return the group product of 2 elements
   */
  T mul(T arg1, T arg2);

}
