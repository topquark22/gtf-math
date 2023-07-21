package gtf.math.algebra;

import java.math.BigInteger;
import java.util.Collection;


/**
 * Defines operations on a finite abstract group.
 * 
 * @param <T> The type used for the group elements. Must have a meaningful
 * equals(), hashCode() methods.
 * 
 * @author gtf
 */
public interface FiniteGroup<T> extends Group<T> {

  /**
   * Returns the collection of all elements.
   * 
   * @return
   */
  Collection<T> elements();
  
  /**
   * Returns the order of the group.
   */
  BigInteger order();
}
