package ttc2018

import org.eclipse.papyrus.aof.core.IBox
import org.eclipse.papyrus.aof.core.impl.operation.Operation
import java.util.Set
import java.util.HashSet
import org.eclipse.papyrus.aof.core.impl.utils.DefaultObserver
import org.eclipse.xtend.lib.annotations.Data

// WARNING: the current version assumes that source and other boxes are Sets
class Intersection<E> extends Operation<E> {
	val IBox<E> source
	val Set<E> sourceSet
	val IBox<E> other
	val Set<E> otherSet

	new(IBox<E> source, IBox<E> other) {
		this.source = source
		this.sourceSet = new HashSet<E>(source.length)
		this.other = other;
		this.otherSet = new HashSet<E>(other.length)
		val result = getResult
		for(e : source) {
			sourceSet.add(e)
		}
		for(e : other) {
			otherSet.add(e)
			if(sourceSet.contains(e)) {
				result.add(e)
			}
		}
		source.addObserver(new SourceObserver(sourceSet, otherSet, result))
		other.addObserver(new SourceObserver(otherSet, sourceSet, result))
	}

	@Data
	static class SourceObserver<E> extends DefaultObserver<E> {
		val Set<E> sourceSet
		val Set<E> otherSet
		val IBox<E> result
		override added(int index, E element) {
			sourceSet.add(element)
			if(otherSet.contains(element)) {
				result.add(element)
			}
		}
		override moved(int newIndex, int oldIndex, E element) {
			// nothing to do
		}
		override removed(int index, E element) {
			sourceSet.remove(element)
			result.remove(element)
		}
		override replaced(int index, E newElement, E oldElement) {
			removed(index, oldElement)
			added(index, newElement)
		}
	}

	override getResultDefautElement() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

	override isOptional() {
		true
	}

	override isSingleton() {
		false
	}

	override isOrdered() {
		false
	}

	override isUnique() {
		true
	}
}

