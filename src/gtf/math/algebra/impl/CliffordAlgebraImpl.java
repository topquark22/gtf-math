package gtf.math.algebra.impl;

import gtf.math.algebra.CliffordAlgebra;
import gtf.math.algebra.Field;
import gtf.math.algebra.InnerProductSpace;


/**
 * @deprecated Use {@link gtf.math.algebra.CliffordAlgebra} instead.
 */
@Deprecated
public class CliffordAlgebraImpl<V, S, F extends Field<S>>
    extends CliffordAlgebra<V, S, F> {

  public CliffordAlgebraImpl(
      InnerProductSpace<V, S, F> vectorSpace) {
    super(vectorSpace);
  }
}
