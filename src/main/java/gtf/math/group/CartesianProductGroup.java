package gtf.math.group;

import gtf.math.types.Pair;


/**
 * Cartesian product of two abstract groups.
 * @author gtf
 *
 * @param <T> type of elements in the first group
 * @param <U> type of elements in the second group
 */
public class CartesianProductGroup<T, U>
    extends CartesianProductMonoid<T, U>
    implements Group<Pair<T, U>> {
  
  public CartesianProductGroup(Group<T> group1, Group<U> group2) {
    super(group1, group2);
  }
  
  public Group<T> getGroup1() {
    return (Group<T>) super.getGroup1();
  }
  
  public Group<U> getGroup2() {
    return (Group<U>) super.getGroup2();
  }
  
  public Pair<T, U> inv(Pair<T, U> arg) {
    return new Pair<T, U>(
        getGroup1().inv(arg.getX()),
        getGroup2().inv(arg.getY()));
  }
}
