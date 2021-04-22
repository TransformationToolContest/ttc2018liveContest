package de.tudresden.inf.st.ttc18live;

import java.util.List;
import java.util.Map;

/**
 * Miscellaneous operations.
 *
 * @author rschoene - Initial contribution
 */
public class Utils {
  /** Helper method to add an element to a list and return the element */
  public static <T> T add(T element, List<T> listToAddTo) {
    listToAddTo.add(element);
    return element;
  }

  /** Helper method to put an element into a map and return the element */
  public static <K, V> V put(K key, V element, Map<K, V> mapToPutInto) {
    mapToPutInto.put(key, element);
    return element;
  }
}
