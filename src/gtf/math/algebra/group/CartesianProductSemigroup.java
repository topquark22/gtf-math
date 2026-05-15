package gtf.math.algebra.group;

import gtf.math.group.Semigroup;


/**
 * @deprecated Use {@link gtf.math.group.CartesianProductSemigroup} instead.
 */
@Deprecated
public class CartesianProductSemigroup<T, U>
    extends gtf.math.group.CartesianProductSemigroup<T, U> {

  public CartesianProductSemigroup(
      Semigroup<T> group1,
      Semigroup<U> group2) {
    super(group1, group2);
  }
}
