package ttc2018

import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EContentAdapter
import org.eclipse.papyrus.aof.core.AOFFactory
import org.eclipse.papyrus.aof.core.IBox
import org.eclipse.papyrus.aof.core.IMetaClass
import org.eclipse.papyrus.aof.core.ISet

class AllContents {
	// warning: does not preserve ordering
	static def <C> _allContents(EObject it, IMetaClass<C> type) {
		val ret = AOFFactory.INSTANCE.<C>createSet
		// adding the EContentAdapter automatically does the initialization
		eAdapters.add(new EContentAdapter {
			override addAdapter(Notifier it) {
				super.addAdapter(it)
				if(type.isInstance(it)) {
					ret.add(it as C)
				}
			}
			override removeAdapter(Notifier it) {
				super.removeAdapter(it)
				if(type.isInstance(it)) {
					ret.remove(it as C)
				}
			}
		})
		ret
	}

	// TODO: fix this, not usable yet
	static def <C> allContents(IBox<? extends EObject> it, Class<C> type) {
		// collectMutable cannot work if ordering is not preserved
//		collectMutable[
//			it?._allContents(type) ?: emptySet
//		]
		val ret =
			if(unique) {
				AOFFactory.INSTANCE.<C>createSet
			} else {
				AOFFactory.INSTANCE.<C>createBag
			}
		ret.assign(it.map[
			eAllContents.filter(type).toIterable
		].flatten)
		// TODO: observe
		ret
	}

	static def <E> emptySet() {
		IBox.SET as ISet<E>
	}
}