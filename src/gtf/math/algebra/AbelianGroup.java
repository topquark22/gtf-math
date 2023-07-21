package gtf.math.algebra;


/**
 * Defines operations on an abstract abelian group, written additively.
 * 
 * @param <T> The type used for the group elements. Must have a meaningful
 * equals(), hashCode() methods.
 * 
 * @author gtf
 */
public interface AbelianGroup<T> {

  /**
   * Returns the zero element.
   * 
   * @return
   */
  T zero();
  
  /**
   * Returns the sum of two elements.
   *    
   * @param arg1
   * @param arg2
   * @return
   */
  T add(T arg1, T arg2);
  
  /**
   * Returns the additive inverse of an element.
   */
  T neg(T arg);
}
