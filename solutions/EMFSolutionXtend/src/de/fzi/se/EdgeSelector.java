package de.fzi.se;

public interface EdgeSelector<T> {
	Iterable<T> getEdgesFor(T element);
}
