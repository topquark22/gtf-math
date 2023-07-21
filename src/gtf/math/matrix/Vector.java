package gtf.math.matrix;

import gtf.math.algebra.Ring;


/**
 * A vector over a ring. Can be thought of as a 1-dimensional
 * matrix.
 * 
 * TODO Abstractify this to support sparse vectors.
 * 
 * @author gtf
 *
 * @param <T> the type used for the elements
 */
public interface Vector<T, R extends Ring<T>> {
  
  R ring();
  
  int size();
  
  T get(int pos);
  
  void set(int pos, T value);
  
  T dotProduct(Vector<T, R> arg);

  Vector<T, R> copy();
}
