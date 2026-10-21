package gtf.math.algebra;


/**
 * A module over a semiring
 * @author gtf
 *
 * @param <T> the type of the module elements. Must have meaningful equals(),
 * hashCode() methods.
 * @param <S> the type of the ring elements. Must have meaningful equals(),
 * hashCode() methods.
 * @param <R> the actual type of the ring. It can be a more specific type
 * (for instance, a field).
 */
public interface Module<T, S, R extends Ring<S>> extends AbelianGroup<T> {
  
  /**
   * @return the underlying Ring
   * 
   * Note: A ring is a module over itself, so must implement this method.
   * A ring simply returns the "this" pointer (a reference to itself).
   * 
   */
  R ring();

  /**
   * Return the module operation of scalar multiplication on an element.
   * Must satisfy the axioms for a module m, scalar s, and element t:
   * 
   * <ol>
   *   <li>m.mul(m.ring().mul(s1, s2), t) == m.mul(s1, m.mul(s2, t))</li>
   *   <li>m.mul(s, m.sum(t1, t2)) == m.sum(m.mul(s, t1), m.mul(s, t2))</li>
   * </ol>
   * 
   * @param scalar
   * @param elt
   * @return
   */
  T mul(S scalar, T elt);
}
