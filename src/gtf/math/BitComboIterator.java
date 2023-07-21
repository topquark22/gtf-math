package gtf.math;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BitComboIterator implements Iterator<Long> {

  /**
   * Number of elements in the total set
   */
  private int n;

  /**
   * Number of elements in a subset
   */
  private int m;

  private long lastVal;
  private long curVal;

  private boolean done;

  public BitComboIterator(int n, int m) {
    if (n < 0 || n > 63) {
      throw new IllegalArgumentException("numBits must be between 0 and 63");
    }
    if (m < 0 || m > n) {
      throw new IllegalArgumentException("m must be between 0 and n");
    }
    this.n = n;
    this.m = m;
    this.curVal = (1L << m) - 1L;
    this.lastVal = curVal << (n - m);
    done = false;
  }

  public boolean hasNext() {
    return !done;
  }

  public Long next() {
    if (!hasNext()) {
      throw new NoSuchElementException("Iterator is exhausted");
    }
    long ret = curVal;
    increment();
    return ret;
  }

  private void increment() {
    if (curVal == lastVal) {
      done = true;
      return;
    }
    long leastBit = truncate(curVal);
    long val = curVal + leastBit;
    long newLeast = truncate(val);
    long diff = newLeast - leastBit;
    val += addBits(diff);
    curVal = val;
  }

  public void remove() {
    throw new UnsupportedOperationException("remove() not supported");
  }

  private static long truncate(long val) {
    if (val == 0) {
      return 0;
    }
    long res = 1L;
    while ((val & 1L) == 0) {
      val >>= 1;
      res <<= 1;
    }
    return res;
  }

  private static long addBits(long val) {
    if (val == 0)
      return 0;
    while ((val & 1L) == 0) {
      val >>= 1;
    }
    val >>= 1;
    return val;
  }
}