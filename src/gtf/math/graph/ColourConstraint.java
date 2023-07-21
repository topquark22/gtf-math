package gtf.math.graph;


/**
 * Can be used to express a constraint on the colour assignments on a
 * colourable graph. The actual nature of the constraint may depend
 * on the graph implementation (so need not use this interface in
 * particular).
 * 
 * @author gtf
 * 
 * @param <T> the type used for the node addresses
 * @param <V> The type used for the node values. Must implement
 * a meaningful equals() method.
 */
public interface ColourConstraint<T, V> {
  
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
  boolean check(ColourableGraph<T, V> graph, T node, V colour);
}
