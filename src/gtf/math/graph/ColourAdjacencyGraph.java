package gtf.math.graph;

import java.util.Collection;


/**
 * A graph whose constraint cares about the colours of adjacent
 * nodes.
 * 
 * @author gtf
 *
 * @param <T> The type used for the node addresses
 * @param <C> The type used for the node values
 */
public interface ColourAdjacencyGraph<T, V> extends ColourableGraph<T, V> {

  /**
   * Returns the set of distinct colours of the neighbours of this
   * node. Null (uncoloured) is not included as a value.
   * 
   * It is advisable to cache the results of this method for
   * performance reasons.
   * 
   * @param nodeAddress
   * @return
   */
  Collection<V> getNeighbourColours(T nodeAddress);
}
