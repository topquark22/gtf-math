package gtf.math.algebra;


/**
 * Defines operations on an abstract monoid, written multiplicatively.
 * 
 * @param <T> The type used for the monoid elements. Must have a meaningful
 * equals(), hashCode() methods.
 * 
 * @author gtf
 */
public interface Monoid<T> extends Semigroup<T> {

  /**
   * Returns the identity element.
   * 
   * @return the identity element
   */
  T id();
}
