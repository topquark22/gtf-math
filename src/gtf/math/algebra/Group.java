package gtf.math.algebra;


/**
 * Defines operations on an abstract group, written multiplicatively.
 * 
 * @param <T> The type used for the group elements. Must have a meaningful
 * equals(), hashCode() methods.
 * 
 * @author gtf
 */
public interface Group<T> extends Monoid<T> {
  
  /**
   * Returns the inverse of an element.
   * 
   * @param arg
   * @return the inverse
   */
  T inv(T arg);

}
