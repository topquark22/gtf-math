package gtf.math.algebra.group;

import gtf.math.group.Group;


/**
 * @deprecated Use {@link gtf.math.group.CartesianProductGroup} instead.
 */
@Deprecated
public class CartesianProductGroup<T, U>
    extends gtf.math.group.CartesianProductGroup<T, U> {

  public CartesianProductGroup(Group<T> group1, Group<U> group2) {
    super(group1, group2);
  }
}
