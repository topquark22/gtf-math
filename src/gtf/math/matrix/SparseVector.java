package gtf.math.matrix;

import java.util.Iterator;
import java.util.TreeMap;

import gtf.math.algebra.Ring;

import gtf.util.PeekAheadIterator;


/**
 * A sparse vector containing elements from a ring. Index may be any Comparable type.
 * 
 * @author gtf
 *
 * @param <I> the type used for the indices
 * @param <T> the type used for the elements
 * @param <R> the type of the ring
 */
public class SparseVector<T, R extends Ring<T>> implements Vector<T, R>, Iterable<Integer> {
  
  /**
   * Holds the elements of this vector. We use a TreeMap concrete type so that the keyset
   * iteration returns the elements in ascending order; the SortedMap interface
   * does not guarantee this.
   */
  private final TreeMap<Integer, T> elts;
  
  private final int size;
  
  private final R ring;
  
  /**
   * Constructor.
   * 
   * @param ring
   * @param size
   */
  public SparseVector(R ring, int size) {
    this.ring = ring;
    this.size = size;
    this.elts = new TreeMap<Integer, T>();
  }
  
  /**
   * Copy constructor
   * 
   * @param orig the Vector to copy
   */
  public SparseVector(SparseVector<T, R> orig) {
    this(orig.ring(), orig.size());
    for (int i : orig) {
      T elt = orig.get(i);
      set(i, elt);
    }
  }
  
  public R ring() {
    return ring;
  }

  public int size() {
    return size;
  }

  public T get(int index) {
    T value = elts.get(index);
    if (value == null) {
      value = ring.zero();
    }
    return value;
  }
  
  public void set(int index, T value) {
    elts.put(index, value);
  }

  /**
   * Returns an iterator over all of the indices in the vector, in ascending
   * order.
   */
  public Iterator<Integer> iterator() {
    return elts.keySet().iterator();
  }
  
  /*
   *  (non-Javadoc)
   * @see gtf.math.matrix.Vector#dotProduct(gtf.math.matrix.Vector)
   */
  public T dotProduct(Vector<T, R> arg) {
    T result;
    if (arg instanceof SparseVector) {
      result = sparseDotProduct((SparseVector<T, R>) arg);
    } else {
      result = genericDotProduct(arg);
    }
    return result;
  }
  
  private T genericDotProduct(Vector<T, R> arg) {
    T result = ring.zero();
    Iterator<Integer> iter = iterator();
    while (iter.hasNext()) {
      int index = iter.next();
      result = ring().add(result, ring().mul(get(index), arg.get(index)));
    }
    return result;
  }

  private T sparseDotProduct(SparseVector<T, R> arg) {
    T value = ring.zero();
    PeekAheadIterator<Integer> across = new PeekAheadIterator<Integer>(iterator());
    PeekAheadIterator<Integer> down = new PeekAheadIterator<Integer>(arg.iterator());
    while (across.hasNext() && down.hasNext()) {
      Integer x = across.peek();
      Integer y = down.peek();
      final int comparison = x.compareTo(y);
      if (comparison < 0) {
        across.next();
      } else if (comparison > 0) {
        down.next();
      } else {
        // x == y
        value = ring().add(value, ring().mul(get(x), arg.get(x)));
        across.next();
        down.next();
      }
    }
    return value;
  }
  
  public Vector<T, R> copy() {
    return new SparseVector<T, R>(this);
  }
  
  /*
   * TODO equals(), hashCode()
   */
}
