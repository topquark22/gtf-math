package gtf.math;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Enumerate all combinations of a set of items
 */
public class EnumCombinations<X> implements Iterable<Collection<X>> {

  private final List<X> items;

  private int subsetSize;

  public EnumCombinations(Collection<X> items, int subsetSize) {
    this.items = Collections.unmodifiableList(new ArrayList<X>(items));
    this.subsetSize = subsetSize;
  }

  public Iterator<Collection<X>> iterator() {
    if (subsetSize < 0 || subsetSize > items.size()) {
      return new Iterator<Collection<X>>() {
        public boolean hasNext() {
          return false;
        }

        public Collection<X> next() {
          throw new NoSuchElementException("Empty iterator");
        }

        public void remove() {
          throw new UnsupportedOperationException("Empty iterator");
        }
      };
    }
    return new ComboIterator(subsetSize);
  }

  private class ComboIterator implements Iterator<Collection<X>> {
    private int m, n;
    private BitComboIterator bci;

    private ComboIterator(int subsetSize) {
      m = subsetSize;
      n = items.size();
      bci = new BitComboIterator(n, m);
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

    public boolean hasNext() {
      return bci.hasNext();
    }

    public Collection<X> next() {
      long nextBits = bci.next();
      return subcollectionByBits(nextBits);
    }

    private Collection<X> subcollectionByBits(long nextBits) {
      Collection<X> c = new ArrayList<X>();
      int i = 0;
      while(nextBits > 0) {
        if ((nextBits & 1L) == 1L) {
          c.add(items.get(i));
        }
        i++;
        nextBits >>= 1;
      }
      return c;
    }
  }
}