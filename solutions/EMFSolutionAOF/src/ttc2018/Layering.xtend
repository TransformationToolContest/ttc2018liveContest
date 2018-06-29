package ttc2018

import java.util.HashMap
import org.eclipse.papyrus.aof.core.AOFFactory
import org.eclipse.papyrus.aof.core.IBox
import org.eclipse.papyrus.aof.core.ISet
import org.eclipse.papyrus.aof.core.impl.operation.Operation
import org.eclipse.papyrus.aof.core.impl.utils.DefaultObserver

class Layering<E> extends Operation<ISet<E>> {
	// in : IBox<E>
	// out : ISet<ISet<E>>
	val IBox<E> source
	val cache = new HashMap<E,ISet<E>>
	val AOF = AOFFactory.INSTANCE
	val (E) => IBox<E> accessor
	
	new(IBox<E> sourceBox, (E) => IBox<E> accessor) {
		this.source = sourceBox
		this.accessor = accessor
		
		sourceBox.registerObservation(new DefaultObserver<E> {
			override added(int index, E element) {
				// since we only have mutual friendship and only add things we can simplify the problem
				// when adding an element, 
				// - get sets containing elements it can reach
				// - if none -> add a new set with this element
				// - otherwise union them all
				layer(element)
			}
			
			override moved(int newIndex, int oldIndex, E element) {
				throw new UnsupportedOperationException("TODO: auto-generated method stub")
			}
			
			override removed(int index, E element) {
				throw new UnsupportedOperationException("TODO: auto-generated method stub")
			}
			
			override replaced(int index, E newElement, E oldElement) {
				throw new UnsupportedOperationException("TODO: auto-generated method stub")
			}
		})
//		result.inspect('''layers «sourceBox.map[(it as User).name]» :
//		''')
		sourceBox.forEach[layer]
		checkCacheConsistency
	}
	
	override isOptional() {
		source.optional
	}

	override isSingleton() {
		source.singleton
	}

	override isOrdered() {
		false || singleton
	}

	override isUnique() {
		true
	}	
	
	override getResultDefautElement() {
		IBox.ONE as ISet<E>
	}
	
	def layer(E element) {
		return cache.get(element) ?: {
			val f = accessor.apply(element)
			val s = AOF.createSet(element)
			result.add(s)
			cache.put(element, s)
	//		println('''accessor friends of «element» : «f»''')
			f.collect[
				val s2 = cache.get(element)
				if (!s2.contains(it)) {
					s2.add(it)
				}
				val fs = cache.get(it)
				if (fs !== null && fs != s2) {
					result.remove(fs)
					fs.forEach[
						if (!s2.contains(it)) {
							s2.add(it)
						}
						cache.put(it, s2)
					]
					fs.clear
				}
				null
			]
			checkCacheConsistency
			s
		}
	}

	def checkCacheConsistency() {
//		for (s : cache.values) {
//			for (os : cache.values.filter[s !== it]) {
//				assertTrue("Element present in two sets !", s.length + os.length == s.union(os).length)
//			}
//		}
	}

	def static assertTrue(String msg, boolean b) {
		if(!b) {
			throw new AssertionError(msg)
		}
	}

}