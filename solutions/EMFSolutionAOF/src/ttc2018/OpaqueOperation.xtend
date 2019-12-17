package ttc2018

import java.util.function.Supplier
import org.eclipse.papyrus.aof.core.AOFFactory
import org.eclipse.papyrus.aof.core.IBox
import org.eclipse.papyrus.aof.core.IObserver
import org.eclipse.papyrus.aof.core.IOne
import org.eclipse.papyrus.aof.core.impl.utils.DefaultObserver

/*
 * Users have to be very careful to observe all necessary boxes.
 */
class OpaqueOperation<R> extends DefaultObserver<Object> {
	val Supplier<R> supplier
	val IOne<R> result

	new(Supplier<R> supplier) {
		this.supplier = supplier
		this.result = AOFFactory.INSTANCE.createOne(supplier.get)
	}

	def getResult() {
		result
	}

	def <E> observe(IBox<E> it) {
		addObserver(this as IObserver<E>)
		this
	}

	var IObserver<IBox<Object>> outerObserverCache = null

	def getOuterObserver() {
		if(outerObserverCache === null) {
			outerObserverCache = new DefaultObserver<IBox<Object>> {
				override added(int index, IBox<Object> it) {
					addObserver(OpaqueOperation.this as IObserver<Object>)
				}
				override moved(int newIndex, int oldIndex, IBox<Object> element) {
					// nothing to do
				}
				override removed(int index, IBox<Object> it) {
					removeObserver(OpaqueOperation.this as IObserver<Object>)
				}
				override replaced(int index, IBox<Object> newElement, IBox<Object> oldElement) {
					removed(index, oldElement)
					added(index, newElement)
				}
			}
		}
		outerObserverCache
	}

	def <E> observeInners(IBox<IBox<E>> it) {
		forEach[
			addObserver(this as IObserver<E>)
		]
		addObserver(outerObserver as IObserver<?> as IObserver<IBox<E>>)
		this
	}

	def update() {
		result.set(supplier.get)
	}

	override added(int index, Object element) {
		update
	}

	override moved(int newIndex, int oldIndex, Object element) {
		update
	}

	override removed(int index, Object element) {
		update
	}

	override replaced(int index, Object newElement, Object oldElement) {
		update
	}
}
