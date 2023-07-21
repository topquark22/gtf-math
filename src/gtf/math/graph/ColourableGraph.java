package gtf.math.graph;

import gtf.math.types.Copyable;

import java.util.Collection;


/**
 * A graph whose nodes may be coloured with different values
 * from a colour space. They may also be constrained somehow.
 * The exact nature of any constraint depends on the implementation.
 * 
 * @author gtf
 *
 * @param <T> The type used for the node addresses. Must implement
 * a meaningful equals() and hashCode().
 * @param <C> The type used for the node values. Must implement
 * a meaningful equals() method.
 */
public interface ColourableGraph<T, V> extends Copyable<ColourableGraph<T, V>> {
  
  /**
   * Gets the underlying graph topology.
   */
  GraphTopology<T> getTopology();
  
  /**
   * Sets the colour of a node of the graph. It is not mandatory for the
   * constraint to be checked here; however, if not, it is up to the caller
   * to ensure that the graph is kept in a consistent state.
   * 
   * @param nodeAddress
   * @param colour
   * @throws ColourConstraintViolation if the constraint is checked and the
   * colour is inconsistent with the constraint.
   */
  void setColour(T nodeAddress, V colour);

  /**
   * Gets the colour that has been assigned at a given node address.
   * 
   * @param nodeAddress
   * @return
   */
  V getColour(T nodeAddress);
  
  /**
   * @return true if all of the nodes in the graph have had
   * colours assigned to them.
   */
  boolean isComplete();
  
  /**
   * Gets the possible colours that could be assigned to this
   * node address, according to the constraint.
   * 
   * It is advisable to cache the results of this method for
   * performance reasons.
   * 
   * @param node
   * @throws IllegalArgumentException if the node is already
   * assigned a colour.
   */
  Collection<V> getPossibleColoursForNode(T node);
}
