package gtf.math.algebra.group;

import gtf.math.algebra.Semigroup;
import gtf.math.types.Pair;


/**
 * Cartesian product of two abstract semigroups.
 * @author gtf
 *
 * @param <T> type of elements in the first semigroup
 * @param <U> type of elements in the second semigroup
 */
public class CartesianProductSemigroup<T, U> implements Semigroup<Pair<T, U>> {

  private final Semigroup<T> group1;
  
  private final Semigroup<U> group2;
  
  public CartesianProductSemigroup(Semigroup<T> group1, Semigroup<U> group2) {
    this.group1 = group1;
    this.group2 = group2;
  }
  
  public Semigroup<T> getGroup1() {
    return group1;
  }
  
  public Semigroup<U> getGroup2() {
    return group2;
  }

  public Pair<T, U> mul(Pair<T, U> arg1, Pair<T, U> arg2) {
    return new Pair<T, U>(getGroup1().mul(arg1.getX(), arg2.getX()), getGroup2().mul(arg1.getY(), arg2.getY()));
  }
}
