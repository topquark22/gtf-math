package gtf.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An iterator that reads ahead by 1.
 * 
 * @author gtf
 *
 * @param <E> the type of elements over which we iterate
 */
public class PeekAheadIterator<E> implements Iterator<E> {

  private final Iterator<E> iter;
  
  private boolean hadNext;
  
  private E peeked;
  
  public PeekAheadIterator(Iterator<E> iter) {
    this.iter = iter;
    hadNext = iter.hasNext();
    if (hadNext) {
      peeked = iter.next();
    }
  }
  
  public boolean hasNext() {
    return hadNext;
  }

  public E next() {
    E result = peeked;
    hadNext = iter.hasNext();
    if (hadNext) {
      peeked = iter.next();
    }
    return result;
  }

  public void remove() {
    throw new UnsupportedOperationException();
  }

  /**
   * Provide the next value that would be returned by
   * the iterator, without advancing the iterator.
   * 
   * @return
   * @throws NoSuchElementException if the iterator
   *   has no more elements
   */
  public E peek() {
    if (!hadNext) {
      throw new NoSuchElementException();
    }
    return peeked;
  }
}
