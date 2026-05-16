package gtf.math.geometry;

import gtf.math.algebra.Field;
import gtf.math.types.Pair;


/**
 * Cartesian product of two finite-dimensional vector spaces over the same
 * field.
 *
 * <p>
 * Addition and scalar multiplication are defined componentwise. The dimension
 * is the sum of the two component dimensions, and the distinguished basis is
 * obtained by concatenating the component bases.
 * </p>
 *
 * @author gtf
 *
 * @param <V> the type of elements in the first vector space
 * @param <W> the type of elements in the second vector space
 * @param <S> the type of the scalar-field elements
 * @param <F> the scalar field
 */
public class CartesianProductVectorSpace<V, W, S, F extends Field<S>>
    implements FiniteDimensionalVectorSpace<Pair<V, W>, S, F> {

  private final FiniteDimensionalVectorSpace<V, S, F> space1;
  private final FiniteDimensionalVectorSpace<W, S, F> space2;

  public CartesianProductVectorSpace(
      FiniteDimensionalVectorSpace<V, S, F> space1,
      FiniteDimensionalVectorSpace<W, S, F> space2) {
    if (space1 == null) {
      throw new NullPointerException("space1");
    }
    if (space2 == null) {
      throw new NullPointerException("space2");
    }
    if (space1.ring() != space2.ring()) {
      throw new IllegalArgumentException("vector spaces must be over the same field");
    }
    this.space1 = space1;
    this.space2 = space2;
  }

  public FiniteDimensionalVectorSpace<V, S, F> getSpace1() {
    return space1;
  }

  public FiniteDimensionalVectorSpace<W, S, F> getSpace2() {
    return space2;
  }

  @Override
  public F ring() {
    return space1.ring();
  }

  @Override
  public Pair<V, W> zero() {
    return new Pair<V, W>(space1.zero(), space2.zero());
  }

  @Override
  public Pair<V, W> add(Pair<V, W> arg1, Pair<V, W> arg2) {
    return new Pair<V, W>(
        space1.add(arg1.getX(), arg2.getX()),
        space2.add(arg1.getY(), arg2.getY()));
  }

  @Override
  public Pair<V, W> neg(Pair<V, W> arg) {
    return new Pair<V, W>(
        space1.neg(arg.getX()),
        space2.neg(arg.getY()));
  }

  @Override
  public Pair<V, W> mul(S scalar, Pair<V, W> elt) {
    return new Pair<V, W>(
        space1.mul(scalar, elt.getX()),
        space2.mul(scalar, elt.getY()));
  }

  @Override
  public int dimension() {
    return space1.dimension() + space2.dimension();
  }

  @Override
  public Pair<V, W> basisVector(int index) {
    if (index < 0 || index >= dimension()) {
      throw new ArrayIndexOutOfBoundsException(index);
    }
    if (index < space1.dimension()) {
      return new Pair<V, W>(space1.basisVector(index), space2.zero());
    }
    return new Pair<V, W>(
        space1.zero(),
        space2.basisVector(index - space1.dimension()));
  }

  @Override
  public S coordinate(Pair<V, W> vector, int index) {
    if (index < 0 || index >= dimension()) {
      throw new ArrayIndexOutOfBoundsException(index);
    }
    if (index < space1.dimension()) {
      return space1.coordinate(vector.getX(), index);
    }
    return space2.coordinate(vector.getY(), index - space1.dimension());
  }
}
