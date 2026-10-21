package gtf.math.geometry;

import gtf.math.algebra.Field;
import gtf.math.algebra.Module;

/**
 * A vector space over a field.
 *
 * <p>
 * Algebraically, a vector space is a module whose scalar ring is a field.
 * This interface captures that specialization without duplicating the
 * operations already defined by {@link Module}.
 * </p>
 *
 * @author gtf
 *
 * @param <V> the type of the vector-space elements
 * @param <S> the type of the scalar-field elements
 * @param <F> the scalar field
 */
public interface VectorSpace<V, S, F extends Field<S>> extends Module<V, S, F> {
}
