package gtf.math.group;

import java.math.BigInteger;
import java.util.Set;


/**
 * A finite group whose elements can be explicitly enumerated.
 *
 * @param <T> the type used for group elements
 *
 * @author gtf
 */
public interface FiniteGroup<T> extends Group<T> {

  /**
   * Returns the elements of this finite group.
   *
   * @return the elements of this group
   */
  Set<T> elements();

  /**
   * Returns whether an element belongs to this group.
   *
   * @param arg the candidate element
   * @return true if the element belongs to this group
   */
  boolean contains(T arg);

  /**
   * Returns the order of this group.
   *
   * @return the number of elements in this group
   */
  BigInteger order();
}
