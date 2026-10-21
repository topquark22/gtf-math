package gtf.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class PeekAheadIteratorTest {

  private static final int NUM_ELTS = 10;
  
  private final static int NUM_TO_DISCARD = 5;
  
  private Iterator<Integer> iter;
  
  private PeekAheadIterator<Integer> unit;
  
  @Before
  public void setup() {
    // create a list of 10 elements numbered 1 to 10
    List<Integer> list = new ArrayList<Integer>(NUM_ELTS);
    for (int i = 1; i <= NUM_ELTS; i++) {
      list.add(i);
    }
    // two independent iterators; one will not affect the other
    iter = list.iterator();
    unit = new PeekAheadIterator<Integer>(list.iterator());
  }
  
  @After
  public void tearDown() {
    iter = null;
    unit = null;
  }
  
  @Test
  public void testPeekFirst() {
    int first = iter.next();
    int peeked = unit.peek();
    assertEquals(first, peeked);
  }
  
  @Test
  public void testPeekMiddle() {
    for (int i = 1; i <= NUM_TO_DISCARD; i++) {
      assertEquals(iter.hasNext(), unit.hasNext());
      int a = iter.next();
      int b = unit.next();
      assertEquals(a, b);
    }
    int middle = iter.next();
    int peeked = unit.peek();
    assertEquals(middle, peeked);
  }
  
  @Test(expected=java.util.NoSuchElementException.class)
  public void testPeekLast() {
    for (int i = 1; i <= NUM_ELTS; i++) {
      assertEquals(iter.hasNext(), unit.hasNext());
      int a = iter.next();
      int b = unit.next();
      assertEquals(a, b);
    }
    unit.peek();
  }
}
