package gtf.math.graph.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import gtf.math.graph.ColourAdjacencyConstraint;
import gtf.math.graph.ColourAdjacencyGraph;
import gtf.math.graph.ColourConstraintViolation;
import gtf.math.graph.ColourableGraph;
import gtf.math.graph.GraphTopology;
import gtf.math.types.ColourSpace;
import gtf.math.types.Copyable;

public class ColourableGraphImpl<T, V>
    implements ColourableGraph<T, V>, ColourAdjacencyGraph<T, V>,
               Copyable<ColourableGraph<T, V>> {
  
  private final GraphTopology<T> topology;
  private final Map<T, V> colours;
  private final ColourSpace<V> colourSpace;
  private final Collection<V> allColours;
  private final ColourAdjacencyConstraint<T, V> constraint;
  private final Map<T, Collection<V>> possibleColoursCache;
  private final Map<T, Collection<V>> neighbourColoursCache;
  private final boolean checkConstraintOnSetColour;
  
  public ColourableGraphImpl(GraphTopology<T> topology, ColourSpace<V> colourSpace) {
    this(topology, colourSpace, null, false);
  }
  
  public ColourableGraphImpl(GraphTopology<T> topology, ColourSpace<V> colourSpace,
      ColourAdjacencyConstraint<T, V> constraint) {
    this(topology, colourSpace, constraint, true);
  }
  
  public ColourableGraphImpl(GraphTopology<T> topology, ColourSpace<V> colourSpace,
      ColourAdjacencyConstraint<T, V> constraint, boolean checkConstraintOnSetColour) {
    this.topology = topology;
    this.colourSpace = colourSpace;
    this.constraint = constraint;
    this.checkConstraintOnSetColour = checkConstraintOnSetColour;
    colours = new HashMap<T, V>(topology.getNodeCount());
    allColours = colourSpace.getColours();
    possibleColoursCache = new HashMap<T, Collection<V>>(topology.getNodeCount());
    neighbourColoursCache = new HashMap<T, Collection<V>>(topology.getNodeCount());
  }
  
  public void setColour(T nodeAddress, V colour) {
    boolean ok = (!checkConstraintOnSetColour) || (constraint == null)
        || constraint.check(this, nodeAddress, colour);

    if (ok) {
      colours.put(nodeAddress, colour);
      possibleColoursCache.clear();
      neighbourColoursCache.clear();
    } else {
      throw new ColourConstraintViolation(this, nodeAddress, colour);
    }
  }
  
  public Collection<V> getNeighbourColours(T nodeAddress) {
    Collection<V> result = neighbourColoursCache.get(nodeAddress);

    if (result == null) {
      result = new HashSet<V>(colourSpace.getNumberOfColours());

      for (T neighbour : topology.getNeighbours(nodeAddress)) {
        V colour = getColour(neighbour);

        if (colour != null) {
          result.add(colour);
        }
      }

      neighbourColoursCache.put(nodeAddress, result);
    }

    return result;
  }

  public V getColour(T nodeAddress) {
    return colours.get(nodeAddress);
  }
  
  public boolean isComplete() {
    return colours.size() == topology.getNodeCount();
  }
  
  public Collection<V> getPossibleColoursForNode(T node) {
    V existingColour = getColour(node);

    if (existingColour != null) {
      return Collections.singleton(existingColour);
    }

    if (constraint == null) {
      return allColours;
    }

    Collection<V> result = possibleColoursCache.get(node);

    if (result == null) {
      result = new ArrayList<V>(colours.size());

      for (V colour : allColours) {
        if (constraint.check(this, node, colour)) {
          result.add(colour);
        }
      }

      possibleColoursCache.put(node, result);
    }

    return result;
  }
  
  public ColourableGraph<T, V> copyOf() {
    ColourableGraphImpl<T, V> copy =
        new ColourableGraphImpl<T, V>(
            topology,
            colourSpace,
            constraint,
            checkConstraintOnSetColour);

    for (T node : topology.getAllNodes()) {
      V colour = getColour(node);

      if (colour != null) {
        copy.colours.put(node, colour);
      }
    }

    return copy;
  }
  
  public GraphTopology<T> getTopology() {
    return topology;
  }
}
