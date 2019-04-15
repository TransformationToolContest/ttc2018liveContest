package ttc2018

import org.eclipse.papyrus.aof.core.AOFFactory
import org.eclipse.papyrus.aof.core.IBox
import org.eclipse.papyrus.aof.core.IOne
import org.eclipse.papyrus.aof.core.impl.operation.Operation
import org.eclipse.papyrus.aof.core.impl.utils.DefaultObserver

class Drop<E> extends Operation<E> {

	val IBox<E> sourceBox
	val int n

	new(IBox<E> sourceBox, int n) {
		this.sourceBox = sourceBox
		this.n = n
		val l = sourceBox.length
		for(var i = n ; i < l ; i++) {
			result.add(sourceBox.get(i))
		}
		sourceBox.registerObservation(new DefaultObserver<E> {
			override added(int index, E element) {
				val b = sourceBox.length - n
				if(index >= n) {
					result.add(index - n, element)
					if(result.length > b) {
						result.removeAt(0)
					}
				} else if(result.length < b) {
					result.add(0, sourceBox.get(n))
				}
			}
			override moved(int newIndex, int oldIndex, E element) {
				if(newIndex >= n) {
					if(oldIndex >= n) {
						result.move(newIndex - n, oldIndex - n)
					} else {
						result.add(newIndex - n + 1, sourceBox.get(newIndex))
						result.removeAt(0)
					}
				} else {
					if(oldIndex >= n) {
						result.removeAt(oldIndex - n)
						result.add(0, sourceBox.get(n))
					} else {
						// nothing to do
					}
				}
			}
			override removed(int index, E element) {
				val b = sourceBox.length - n
				if(index >= n) {
					result.removeAt(index - n)
					if(result.length < b) {
						result.add(0, sourceBox.get(n - 1))
					}
				} else if(b >= 0 && result.length > b) {
					result.removeAt(0)
				}
			}
			override replaced(int index, E newElement, E oldElement) {
//				val b = sourceBox.length - n
				if(index >= n) {
					result.set(index - n, newElement)
				}
			}
		})
	}

	override isOptional() {
		n === 0 || sourceBox.isOptional
	}

	override isSingleton() {
		sourceBox.isSingleton
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

	def static createTestBox() {
		AOFFactory.INSTANCE.createSequence(1, 2, 3, 4, 5, 6)
	}

	def static void main(String[] args) {
		for(i : 0..10) {
			val bs = createTestBox
			bs.inspect("bs: ")
			val n = i
			println('''n=«n»''')
			val cs = new Drop(bs, n).result
			cs.inspect("cs: ")
			new BoxFuzzer(bs, [BoxFuzzer.rand.nextInt], [
				SortedBy.assertEquals(bs.drop(n), cs)
			], '''[«i» with n=«n»]''')
		}
	}
}