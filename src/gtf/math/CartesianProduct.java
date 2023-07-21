package gtf.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Implements the Cartesian product of a list of lists. Provides
 * an Iterator to loop through the elements of the resulting
 * Cartesian product.
 *
 * @author Geoffrey Falk
 */
public final class CartesianProduct<X> implements Iterable<List<X>> {

  private final List<List<X>> data;
  private final int width;

  public CartesianProduct(List<List<X>> list) {
    width = list.size();
    data = new ArrayList<List<X>>(width);
    for (List<X> sublist : list) {
      data.add(Collections.unmodifiableList(sublist));
    }
  }

  public Iterator<List<X>> iterator() {
    return new CartesianProductIterator();
  }

  private class CartesianProductIterator implements Iterator<List<X>> {

    private final int[] counter = new int[width];
    private boolean done;

    private CartesianProductIterator() {
      done = false;
      for (int i = 0; i < width; i++) {
        if (data.get(i).size() == 0) {
          done = true;
          break;
        }
      }
    }

    public boolean hasNext() {
      return !done;
    }

    public List<X> next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      List<X> result = new ArrayList<X>(width);
      for (int i = 0; i < width; i++) {
        X element = data.get(i).get(counter[i]);
        result.add(element);
      }
      increment();
      return result;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

    private void increment() {
      assert(hasNext());
      int i = 0;
      while (i < width) {
        int j = counter[i] + 1;
        if (j < data.get(i).size()) {
          counter[i] = j;
          break;
        } else {
          counter[i++] = 0;
        }
      }
      done = (i == width);
    }
  }
}

class CartesianProductTest {
  public static void main(String[] args) {
    List<List<Integer>> y = new ArrayList<List<Integer>>();
    y.add(Arrays.asList(new Integer[] {1, 2, 3} ));
    y.add(Arrays.asList(new Integer[] {4} ));
    y.add(Arrays.asList(new Integer[] {5, 6, 7, 8, 9} ));
    y.add(Arrays.asList(new Integer[] {10, 11} ));
    CartesianProduct<Integer> cp = new CartesianProduct<Integer>(y);
    for (List<Integer> x : cp) {
      System.out.println(x);
    }
  }
}
