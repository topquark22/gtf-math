package gtf.math.algebra.polynomial;

import java.util.Iterator;


/**
 * Iterator over nonzero polynomial terms.
 *
 * @param <T> coefficient type
 *
 * @author gtf
 */
public interface PolynomialTermIterator<T>
    extends Iterator<PolynomialTerm<T>> {
}
