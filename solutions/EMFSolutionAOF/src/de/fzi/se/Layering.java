package de.fzi.se;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Layering<T> {

    private EdgeSelector<T> edgeSelector;
    private HashMap<T, Integer> lowlink = new HashMap<T, Integer>();
    private HashMap<T, Integer> indices = new HashMap<T, Integer>();
    private Stack<T> stack = new Stack<T>();
    private int index = 0;

    private List<List<T>> layers = new ArrayList<List<T>>();

    public Layering(EdgeSelector<T> edgeSelector)
    {
        this.edgeSelector = edgeSelector;
    }

    private void Tarjan(T node)
    {
        indices.put(node, index);
        lowlink.put(node, index);
        index++;
        stack.push(node);

        Iterable<T> connected = edgeSelector.getEdgesFor(node);
        if (connected != null)
        {
            for (T n2 : connected)
            {
                if (!indices.containsKey(n2))
                {
                    Tarjan(n2);
                    lowlink.put(node, Math.min(lowlink.get(node), indices.get(n2)));
                }
                else if (stack.contains(n2))
                {
                    lowlink.put(node, Math.min(lowlink.get(node), indices.get(n2)));
                }
            }
        }

        if (lowlink.get(node) == indices.get(node))
        {
            List<T> layer = new ArrayList<T>();
            T w = null;
            do
            {
                w = stack.pop();
                layer.add(w);
            } while (node != w);
            layers.add(layer);
        }
    }
    
    private void reset() {
        indices.clear();
        lowlink.clear();
        stack.clear();
        layers.clear();
    }

    /**
     * Creates a layering of the given elements
     * @param nodes The collection of nodes that make up the graph
     * @return A list of strongly connected components
     */
    public List<List<T>> CreateLayers(Iterable<T> nodes) {
        reset();
        for (T node : nodes)
        {
            if (!indices.containsKey(node))
            {
                Tarjan(node);
            }
        }
        return layers;
    }

    /**
     * Creates a layering of the given elements
     * @param root The root element of the graph
     * @return A list of strongly connected components
     */
    public List<List<T>> CreateLayers(T root) {
        reset();
        Tarjan(root);
        return layers;
    }
}
