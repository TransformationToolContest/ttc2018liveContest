package ttc2018

import org.eclipse.papyrus.aof.core.AOFFactory
import org.eclipse.papyrus.aof.core.IBox
import org.eclipse.papyrus.aof.core.impl.operation.Operation
import org.eclipse.papyrus.aof.core.impl.utils.DefaultObserver

class Sum extends Operation<Integer> {

	val IBox<Integer> sourceBox

	new(IBox<Integer> sourceBox) {
		this.sourceBox = sourceBox
		result.set(0, sourceBox.reduce[$0+$1] ?: 0)
		registerObservation(sourceBox, new DefaultObserver<Integer> {
			override added(int index, Integer element) {
				result.set(0, result.get(0) + element)
			}
			override moved(int newIndex, int oldIndex, Integer element) {
				// nothing to do
			}
			override removed(int index, Integer element) {
				result.set(0, result.get(0) - element)
			}
			override replaced(int index, Integer newElement, Integer oldElement) {
				result.set(0, result.get(0) - oldElement + newElement)
			}
		})
	}

	override isOptional() {
		false
	}

	override isSingleton() {
		true
	}

	override isOrdered() {
		true
	}

	override isUnique() {
		true
	}

	override getResultDefautElement() {
		0
	}

	def static void main(String[] args) {
		val bs = AOFFactory.INSTANCE.createSequence(1, 2, 3, 4, 5, 6)
		bs.inspect("bs: ")
		val cs = new Sum(bs).result
		cs.inspect("cs: ")
		new BoxFuzzer(bs, [BoxFuzzer.rand.nextInt], [
			SortedBy.assertEquals("", bs.reduce[$0+$1]?:0, cs.get(0))
		])
	}
}