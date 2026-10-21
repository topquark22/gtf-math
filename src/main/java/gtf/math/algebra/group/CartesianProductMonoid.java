package gtf.math.algebra.group;

import gtf.math.group.Monoid;


/**
 * @deprecated Use {@link gtf.math.group.CartesianProductMonoid} instead.
 */
@Deprecated
public class CartesianProductMonoid<T, U>
    extends gtf.math.group.CartesianProductMonoid<T, U> {

  public CartesianProductMonoid(
      Monoid<T> group1,
      Monoid<U> group2) {
    super(group1, group2);
  }
}
