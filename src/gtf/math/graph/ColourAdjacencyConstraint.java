package gtf.math.graph;


/**
 * Can be used to express a constraint on the colour assignments on a
 * colourable graph, that only depends on the colours of a node's
 * neighbours. The actual nature of the constraint may depend
 * on the graph implementation (so need not use this interface in
 * particular).
 * 
 * @author gtf
 * 
 * @param <T> the type used for the node addresses
 * @param <V> The type used for the node values. Must implement
 * a meaningful equals() method.
 */
public interface ColourAdjacencyConstraint<T, V> {
  
  /**
   * Check if the constraint would be satisfied for a given node
   * and colour.
   * 
   * @param graph the graph
   * @param node the node address
   * @param colour the value to assign (may be null)
   * @return true if the constraint would allow the colour to be
   * assigned to the node; false otherwise.
   */
  boolean check(ColourAdjacencyGraph<T, V> graph, T node, V colour);
}
