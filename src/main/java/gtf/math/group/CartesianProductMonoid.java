package gtf.math.group;

import gtf.math.types.Pair;


/**
 * Cartesian product of two abstract monoids.
 * @author gtf
 *
 * @param <T> type of elements in the first monoid
 * @param <U> type of elements in the second monoid
 */
public class CartesianProductMonoid<T, U>
    extends CartesianProductSemigroup<T, U>
    implements Monoid<Pair<T, U>> {
  
  private final Pair<T, U> id;
  
  public CartesianProductMonoid(
      Monoid<T> group1,
      Monoid<U> group2) {
    super(group1, group2);
    id = new Pair<T, U>(group1.id(), group2.id());
  }
  
  public Monoid<T> getGroup1() {
    return (Monoid<T>) super.getGroup1();
  }
  
  public Monoid<U> getGroup2() {
    return (Monoid<U>) super.getGroup2();
  }

  public Pair<T, U> id() {
    return id;
  }
}
