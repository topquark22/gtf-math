package gtf.math.graph;

import java.util.Collection;

/**
 * Defines the topology of a graph (directed or undirected).
 * 
 * It is parametrized by a type T, used as the type for a
 * node address. Objects of type T are "keys" that refer
 * to the location of a particular node within the graph.
 * Any class of objects may be used for this. Normally the
 * node type is a data object that contains information about
 * where the node is located in the graph (such as coordinates
 * etc.) The node type must implement a meaningful equals()
 * and hashCode(), as it is likely to be used in a HashMap.
 * 
 * @author gtf
 *
 * @param <T> the type used for the node addresses. Must implement
 * a meaningful equals() and hashCode().
 */
public interface GraphTopology<T> {

  Collection<T> getAllNodes();

  Collection<T> getNeighbours(T nodeAddr);

  int getNodeCount();
}
