package gtf.math.matrix;

import java.util.ArrayList;
import java.util.List;

import gtf.math.algebra.Ring;


/**
 * A vector over a ring. Can be thought of as a 1-dimensional
 * matrix.
 *
 * @author gtf
 *
 * @param <T> the type used for the elements
 */
public class ArrayVector<T, R extends Ring<T>> implements Vector<T, R> {

  private final R ring;
  
  private final List<T> elements;
  
  public ArrayVector(R ring, int size) {
    this.ring = ring;
    elements = new ArrayList<T>(size);
    for (int i = 0; i < size; i++) {
      elements.add(ring.zero());
    }
  }
  
  /**
   * Copy constructor
   * 
   * @param vect Vector from which to copy
   */
  public ArrayVector(ArrayVector<T, R> vect) {
    this(vect.ring, vect.size());
    for (int i = 0; i < vect.size(); i++) {
      set(i, vect.get(i));
    }
  }
  
  public R ring() {
    return ring;
  }
  
  public int size() {
    return elements.size();
  }
  
  public T get(int pos) {
    return elements.get(pos);
  }
  
  public void set(int pos, T value) {
    elements.set(pos, value);
  }
  
  public T dotProduct(Vector<T, R> arg) {
    if (size() != arg.size()) {
      throw new IllegalArgumentException("Incompatible dimensions");
    }
    T result = ring.zero();
    for (int i = 0; i < elements.size(); i++) {
      T elt = ring.mul(get(i), arg.get(i));
      result = ring.add(result, elt);
    }
    return result;
  }

  public Vector<T, R> copy() {
    return new ArrayVector<T, R>(this);
  }
  
  /*
   * TODO equals(), hashCode()
   */
}
