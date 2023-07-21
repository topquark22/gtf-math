package gtf.math.algebra.group;

import gtf.math.algebra.Monoid;
import gtf.math.types.Pair;


/**
 * Cartesian product of two abstract monoids.
 * @author gtf
 *
 * @param <T> type of elements in the first monoid
 * @param <U> type of elements in the second monoid
 */
public class CartesianProductMonoid<T, U> extends CartesianProductSemigroup<T, U> implements Monoid<Pair<T, U>> {
  
  /**
   * The identity element
   */
  private final Pair<T, U> id;
  
  /**
   * Constructor
   * 
   * @param group1 the first monoid
   * @param group2 the second monoid
   */
  public CartesianProductMonoid(Monoid<T> group1, Monoid<U> group2) {
    super(group1, group2);
    id = new Pair<T, U>(group1.id(), group2.id());
  }
  
  /**
   *  Narrowed return type.
   * 
   * @see gtf.math.algebra.group.CartesianProductSemigroup#getGroup1()
   */
  public Monoid<T> getGroup1() {
    return (Monoid<T>) super.getGroup1();
  }
  
  /**
   *  Narrowed return type.
   * 
   * @see gtf.math.algebra.group.CartesianProductSemigroup#getGroup2()
   */
  public Monoid<U> getGroup2() {
    return (Monoid<U>) super.getGroup2();
  }

  /**
   * It is safe to return the field id because Pair is immutable (as long as
   * nobody makes internal modifications to its entries).
   */
  public Pair<T, U> id() {
    return id;
  }
}
