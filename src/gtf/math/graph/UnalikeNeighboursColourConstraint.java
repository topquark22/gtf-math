package gtf.math.graph;

import java.util.Collection;


/**
 * The constraint that expresses that a node may not
 * have the same colour as some of its neighbours.
 * 
 * For performance reasons, it is advised to implement
 * caching in the getNeighbourColours() method of the
 * underlying graph. The backtracker makes intensive
 * use of the constraint check in an inner loop, and an
 * inefficient implementation will impair performance.
 * 
 * @author gtf
 *
 * @param <T> The type used for the node addresses
 * @param <V> The type used for the node values. Must implement
 * a meaningful equals() method.
 */
public final class UnalikeNeighboursColourConstraint<T, V>
    implements ColourAdjacencyConstraint<T, V> {

  /**
   * The maximum number of colours that can be shared with
   * neighbours (normally 0)
   */
  private final int maxShared;
  
  /**
   * Construct the constraint that a node may not
   * have the same colour as any of its neighbours.
   */
  public UnalikeNeighboursColourConstraint() {
    this(0);
  }
  
  /**
   * Construct the constraint that a node may share up to maxShared
   * values with its neighbours.
   * 
   * @param maxShared the maximum number of colours that may be shared
   * with neighbours
   */
  public UnalikeNeighboursColourConstraint(int maxShared) {
    if (maxShared < 0) {
      throw new IllegalArgumentException("illegal maxShared[" + maxShared + "]");
    }
    this.maxShared = maxShared;
  }
  
  /**
   * Check if the constraint would be satisfied for a given node
   * and colour.
   * 
   * @param graph the graph to constrain
   * @param node the node address
   * @param colour the value to assign (may be null)
   * @return true if the constraint would allow the colour to be
   * assigned to the node; false otherwise.
   *
   * @see gtf.math.graph.ColourAdjacencyConstraint#check(gtf.math.graph.ColourableAdjacencyGraph, java.lang.Object, java.lang.Object)
   */
  public boolean check(ColourAdjacencyGraph<T, V> graph, T node, V colour) {
    if (colour == null) {
      return true;
    }
    Collection<V> neighbourColours = graph.getNeighbourColours(node);
    int shared = 0;
    for (V neighbourColour : neighbourColours) {
      if (colour.equals(neighbourColour)) {
        shared++;
      }
      if (shared > maxShared) {
        return false;
      }
    }
    return true;
  }
}
