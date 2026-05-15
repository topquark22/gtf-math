package gtf.math.algebra;


/**
 * Defines operations on an abstract ring. That is, it has an abelian
 * group structure and also a multiplicative semigroup. It is also a
 * module over itself.
 * 
 * @param <T> The type used for the ring elements. Must have meaningful
 * equals(), hashCode() methods.
 * 
 * Note: There should be a way to refer to its own type, say
 * "public interface Ring<T> extends Module<T, T, self>"
 * 
 * @author gtf
 */
public interface Ring<T> extends Monoid<T>, Module<T, T, Ring<T>> {
  
  /**
   * Divides one element by another.
   *
   * <p>
   * Not every ring admits a meaningful division operation. However, for
   * practical computational purposes, many useful rings (fields, Euclidean
   * domains, etc.) do support some notion of division. Implementations that
   * do not support division may throw UnsupportedOperationException.
   * </p>
   *
   * @param numerator the numerator
   * @param denominator the denominator
   * @return numerator / denominator
   * @throws UnsupportedOperationException if division is not supported
   */
  default T divide(T numerator, T denominator) {
    throw new UnsupportedOperationException();
  }
  
}
