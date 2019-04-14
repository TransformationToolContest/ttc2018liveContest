package ttc2018

import java.util.Iterator
import org.eclipse.papyrus.aof.core.AOFFactory
import org.eclipse.papyrus.aof.core.IBox
import org.eclipse.papyrus.aof.core.IOne
import org.eclipse.papyrus.aof.core.impl.BaseDelegate
import org.eclipse.papyrus.aof.core.impl.operation.Operation
import org.eclipse.papyrus.aof.core.impl.utils.DefaultObserver
import org.eclipse.xtend.lib.annotations.Data

class Reverse<E> extends Operation<E> {
	val IBox<E> sourceBox

	def conv(int index) {
		sourceBox.length - index - 1
	}

	new(IBox<E> sourceBox) {
		this.sourceBox = sourceBox
		val resultDelegate = new ReverseDelegate(this)
		this.result = (AOFFactory.INSTANCE as AOFFactory).createBox(sourceBox, resultDelegate)
		this.sourceBox.addObserver(new DefaultObserver<E> {
			override added(int index, E element) {
				resultDelegate.fireAdded(index.conv, element)
			}
			override moved(int newIndex, int oldIndex, E element) {
				resultDelegate.fireMoved(newIndex.conv, oldIndex.conv, element)
			}
			override removed(int index, E element) {
				resultDelegate.fireRemoved(index.conv, element)
			}
			override replaced(int index, E newElement, E oldElement) {
				resultDelegate.fireReplaced(index.conv, newElement, oldElement)
			}
		})
	}

	override getResultDefautElement() {
		if(sourceBox instanceof IOne<?>) {
			sourceBox.defaultElement as E
		}
	}

	@Data
	static class ReverseDelegate<E> extends BaseDelegate<E> {
		extension val Reverse<E> operation
		override get(int index) {
			sourceBox.get(index.conv)
		}
		override length() {
			sourceBox.length
		}
		override iterator() {
			new Iterator<E> {
				var index = 0
				override hasNext() {
					index < length
				}
				override next() {
					val ret = get(index)
					index++
					ret
				}
			}
		}
		override add(int index, E element) {
			sourceBox.add(index.conv, element)
		}
		override move(int newIndex, int oldIndex) {
			sourceBox.move(newIndex.conv, oldIndex.conv)
		}
		override removeAt(int index) {
			sourceBox.removeAt(index.conv)
		}
		override set(int index, E element) {
			sourceBox.set(index.conv, element)
		}
		public override fireAdded(int index, E element) {
			super.fireAdded(index, element)
		}
		public override fireRemoved(int index, E element) {
			super.fireRemoved(index, element)
		}
		public override fireMoved(int newIndex, int oldIndex, E element) {
			super.fireMoved(newIndex, oldIndex, element)
		}
		public override fireReplaced(int index, E newElement, E oldElement) {
			super.fireReplaced(index, newElement, oldElement)
		}
	}

	def static void main(String[] args) {
		val bs = AOFFactory.INSTANCE.createSequence(1, 2, 3, 4, 5, 6)
		bs.inspect("bs: ")
		val cs = new Reverse(bs).result
		cs.inspect("cs: ")
		new BoxFuzzer(bs, [BoxFuzzer.rand.nextInt], [
			SortedBy.assertEquals(bs.toList.reverseView, cs)
		])
	}
}