package gtf.math.geometry.impl;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import gtf.math.algebra.Field;
import gtf.math.geometry.VectorSpace;

/**
 * Generic implementation of a vector space over a field.
 *
 * <p>
 * This class supplies the structural operations for a vector space without
 * assuming any particular representation for the vector elements.
 * </p>
 *
 * @author gtf
 *
 * @param <V> the type of the vector-space elements
 * @param <S> the type of the scalar-field elements
 * @param <F> the scalar field
 */
public class VectorSpaceImpl<V, S, F extends Field<S>>
    implements VectorSpace<V, S, F> {

  private final F field;

  private final Supplier<V> zeroSupplier;
  private final BinaryOperator<V> addOperator;
  private final UnaryOperator<V> negOperator;
  private final BiFunction<S, V, V> scalarMultiplyOperator;

  public VectorSpaceImpl(
      F field,
      Supplier<V> zeroSupplier,
      BinaryOperator<V> addOperator,
      UnaryOperator<V> negOperator,
      BiFunction<S, V, V> scalarMultiplyOperator) {

    if (field == null) {
      throw new NullPointerException("field");
    }
    if (zeroSupplier == null) {
      throw new NullPointerException("zeroSupplier");
    }
    if (addOperator == null) {
      throw new NullPointerException("addOperator");
    }
    if (negOperator == null) {
      throw new NullPointerException("negOperator");
    }
    if (scalarMultiplyOperator == null) {
      throw new NullPointerException("scalarMultiplyOperator");
    }

    this.field = field;
    this.zeroSupplier = zeroSupplier;
    this.addOperator = addOperator;
    this.negOperator = negOperator;
    this.scalarMultiplyOperator = scalarMultiplyOperator;
  }

  @Override
  public F ring() {
    return field;
  }

  public F field() {
    return field;
  }

  @Override
  public V zero() {
    return zeroSupplier.get();
  }

  @Override
  public V add(V arg1, V arg2) {
    return addOperator.apply(arg1, arg2);
  }

  @Override
  public V neg(V arg) {
    return negOperator.apply(arg);
  }

  @Override
  public V mul(S scalar, V elt) {
    return scalarMultiplyOperator.apply(scalar, elt);
  }
}
