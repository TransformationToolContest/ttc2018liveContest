package ttc2018

import org.eclipse.papyrus.aof.core.IBox
import org.eclipse.papyrus.aof.core.IOne
import org.eclipse.papyrus.aof.core.impl.operation.Operation
import org.eclipse.papyrus.aof.core.impl.utils.DefaultObserver

class Take<E> extends Operation<E> {

	val IBox<E> sourceBox
	val int n

	new(IBox<E> sourceBox, int n) {
		this.sourceBox = sourceBox
		this.n = n
		var i = 0
		val iter = sourceBox.iterator
		while(i < n && iter.hasNext()) {
			result.add(iter.next)
			i++
		}
		sourceBox.registerObservation(new DefaultObserver<E> {
			override added(int index, E element) {
				if(index < n) {
					result.add(index, element)
					result.removeAt(n)
				}
			}
			override moved(int newIndex, int oldIndex, E element) {
				if(newIndex < n) {
					if(oldIndex < n) {
						result.move(newIndex, oldIndex)
					} else {
						result.set(newIndex, sourceBox.get(oldIndex))
					}
				} else {
					if(oldIndex < n) {
						removed(oldIndex, element)
					} else {
						// nothing to do
					}
				}
			}
			override removed(int index, E element) {
				if(index < n) {
					result.removeAt(index)
					result.add(sourceBox.get(n - 1))
				}
			}
			override replaced(int index, E newElement, E oldElement) {
				if(index < n) {
					result.set(index, newElement)
				}
			}
		})
	}

	override isOptional() {
		sourceBox.isOptional
	}

	override isSingleton() {
		n === 1
	}

	override isOrdered() {
		sourceBox.isOrdered || isSingleton
	}

	override isUnique() {
		sourceBox.isUnique || isSingleton
	}

	override getResultDefautElement() {
		(sourceBox as IOne<E>).defaultElement
	}
}