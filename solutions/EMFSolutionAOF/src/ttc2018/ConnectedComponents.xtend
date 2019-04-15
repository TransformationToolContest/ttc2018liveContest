package ttc2018

import java.util.HashMap
import java.util.HashSet
import org.eclipse.papyrus.aof.core.AOFFactory
import org.eclipse.papyrus.aof.core.IBox
import org.eclipse.papyrus.aof.core.ISet
import org.eclipse.papyrus.aof.core.impl.operation.Operation
import org.eclipse.papyrus.aof.core.impl.utils.DefaultObserver

// WARNING: propagation does not preserve order
// And removal is not implemented yet, because it was not necessary for the TTC2018 live contest
class ConnectedComponents<E> extends Operation<ISet<E>> {
	// in : IBox<E>
	// out : ISet<ISet<E>>
	val IBox<E> source
	val setsCache = new HashMap<E,ISet<E>>
	val AOF = AOFFactory.INSTANCE
	val (E) => IBox<E> accessor
	
	new(IBox<E> sourceBox, (E) => IBox<E> accessor) {
		this.source = sourceBox
		this.accessor = accessor
		
		sourceBox.registerObservation(new DefaultObserver<E> {
			override added(int index, E element) {
				addNode(element)
			}
			
			override moved(int newIndex, int oldIndex, E element) {
				// nothing to do
			}
			
			override removed(int index, E element) {
				// TODO: this method should:
				//	- update the result (and the caches), which is not trivial
				//	- remove our "inner observer" (added in the addNode method)
				throw new UnsupportedOperationException("TODO: auto-generated method stub")
			}
			
			override replaced(int index, E newElement, E oldElement) {
				removed(index, oldElement)
				added(index, newElement)
			}
		})
		sourceBox.forEach[addNode]
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

	val edgesCache = new HashSet<E> //keep track of nodes we installed our collect on
	def addNode(E element) {
		if (!edgesCache.contains(element)) {
			val edges = accessor.apply(element)
			edgesCache.add(element)

			// DONE: use an observer that would be notified for additions, but also for removals (for which collect is not "notified")
			edges.forEach[e |
				connectTwo(element, e)
			]

			// in case edges is empty
			if (!setsCache.containsKey(element)) { // node has not already been added so we create a set of it
				val resSet = AOF.createSet(element)
				setsCache.put(element, resSet)
				result.add(resSet)
			}

			edges.addObserver(new DefaultObserver<E> {
				override added(int index, E e) {
					connectTwo(element, e)
				}
				override moved(int newIndex, int oldIndex, E element) {
					// nothing to do
				}
				override removed(int index, E e) {
					throw new UnsupportedOperationException("TODO: auto-generated method stub")
				}
				override replaced(int index, E newElement, E oldElement) {
					removed(index, oldElement)
					added(index, newElement)
				}
			})
		}
	}

	def connectTwo(E element, E e) {
		val set = setsCache.get(element)	// we need to get the current set (the one we created may have been merged into another one)
		val otherSet = setsCache.get(e)
		if(otherSet === null) {
			if(set === null) {
				val resSet = AOF.createSet(element, e)
				setsCache.put(element, resSet)
				setsCache.put(e, resSet)
				result.add(resSet)
			} else {
				// edge not preset in the cache, just add it
				set.add(e)
				setsCache.put(e, set)
			}
		} else if(set === null) {
			otherSet.add(element)
			setsCache.put(element, otherSet)
		} else if(set !== otherSet) {
			// this egde is already in a set
			// we need to merge both sets
			result.remove(otherSet)
			otherSet.forEach[
				set.add(it)
				setsCache.put(it, set)
			]
		}
	}

	def checkCacheConsistency() {
//		for (s : setsCache.values) {
//			for (os : setsCache.values.filter[s !== it]) {
//				assertTrue("Element present in two sets !", s.length + os.length == s.snapshot.union(os.snapshot).length)
//			}
//		}
//
//		// warning: the following line calls the accessor, which does not necessarily have a cache...
//		// this will increase memory consumption
//		val layering = new de.fzi.se.Layering[u | accessor.apply(u)]
//		val comps = layering.CreateLayers(source)
//		val sizesVerified = comps.sortBy[size]
//		val sizesOperation = result.toList.sortBy[length]
//		assertTrue("Sets do not have the same size", sizesOperation.size == sizesVerified.size)
//		assertTrue("Sets should be equals",
//			(0..<sizesVerified.size).forall[i |
//				sizesOperation.get(i).length == sizesVerified.get(i).length
//				&&
//				sizesOperation.get(i).forall[
//					sizesVerified.get(i).contains(it)
//				]
//			]
//		)
	}

	def static assertTrue(String msg, boolean b) {
		if(!b) {
			throw new AssertionError(msg)
		}
	}

}
