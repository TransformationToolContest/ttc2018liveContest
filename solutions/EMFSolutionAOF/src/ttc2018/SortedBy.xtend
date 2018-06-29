package ttc2018

import java.util.ArrayList
import java.util.Iterator
import java.util.Random
import java.util.function.Consumer
import java.util.function.Function
import org.eclipse.papyrus.aof.core.AOFFactory
import org.eclipse.papyrus.aof.core.IBox
import org.eclipse.papyrus.aof.core.IOne
import org.eclipse.papyrus.aof.core.ISequence
import org.eclipse.papyrus.aof.core.IUnaryFunction
import org.eclipse.papyrus.aof.core.impl.operation.Operation
import org.eclipse.papyrus.aof.core.impl.utils.DefaultObserver

// TODO:
//	- make result box wrap the tree instead of using a default ArrayList
//	- fix remaining balancing issues
//	- improve tests
//	- currently a reversed sort... add a parameter for direction
// DONE:
//	- Node.remove
//	- observe inner boxes: need to pass the old value for removal...
//	- remove checkConsistency in "production"
//	- balanced tree
//		- we already have the size of each node, so we should be able to add the balancing of a
//		https://en.wikipedia.org/wiki/Weight-balanced_tree
class SortedBy<E, C extends Comparable<?>> extends Operation<E> {
	val IBox<E> sourceBox
	val IUnaryFunction<E, IOne<C>>[] bodies

	var Node<E> root = null

	def passiveBodies() {
		bodies.map[body | [body.apply(it).get] as Function<E, C>]
	}
	def add(E it) {
		bodies.forEach[body |
			val b = body.apply(it)
			b.registerObservation(new DefaultObserver<C> {
				override added(int index, C element) {
					throw new UnsupportedOperationException("TODO: auto-generated method stub")
				}
				override moved(int newIndex, int oldIndex, C element) {
					throw new UnsupportedOperationException("TODO: auto-generated method stub")
				}
				override removed(int index, C element) {
					throw new UnsupportedOperationException("TODO: auto-generated method stub")
				}
				override replaced(int index, C newElement, C oldElement) {
					b.set(oldElement)
					result.removeAt(remove(it, passiveBodies))
					checkConsistency
					b.set(newElement)
					result.add(add(it, passiveBodies), it)
					checkConsistency
				}
			})
		]
		add(it, passiveBodies)
	}

	new(IBox<E> sourceBox, IUnaryFunction<E, IOne<C>>...bodies) {
		this.sourceBox = sourceBox
		this.bodies = bodies
		for(e : sourceBox) {
			add(e)
		}
		infix[result.add(it)]
		checkConsistency
		sourceBox.registerObservation(new DefaultObserver<E> {
			override added(int index, E e) {
				val i = add(e)
				assertEquals("", e, root.get(i))
				result.add(i, e)
				assertEquals("", e, result.get(i))
				checkConsistency
			}
			override moved(int newIndex, int oldIndex, E element) {
				// nothing to do
			}
			override removed(int index, E element) {
				result.removeAt(
					remove(element, passiveBodies)
				)
				checkConsistency
			}
			override replaced(int index, E newElement, E oldElement) {
				removed(index, oldElement)
				added(index, newElement)
			}
		})
	}

	override isOptional() {
		sourceBox.isOptional
	}

	override isSingleton() {
		sourceBox.isSingleton
	}

	override isOrdered() {
		true
	}

	override isUnique() {
		sourceBox.isUnique
	}

	override getResultDefautElement() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

	def infix(Consumer<E> consumer) {
		if(root !== null) {
			root.infix(consumer)
		}
	}

	def add(E e, Function<E, ? extends Comparable<?>>...bodies) {
		if(root === null) {
			root = new Node<E>(e, null)
			0
		} else {
			root.add(e, bodies)
		}
	}

	def remove(E e, Function<E, ? extends Comparable<?>>...bodies) {
		if(e == root.value) {
			if(root.lower === null) {
				if(root.upper === null) {
					root = null
				} else {
					root = root.upper
					root.checkConsistency(bodies)
				}
				return 0
			} else {
				val pos = root.lowerSize
				if(root.upper === null) {
					root = root.lower
				} else {
					if(root.lower.size < root.upper.size) {
						val m = root.upper.min
						root.value = m.value
						m.replaceInParent(m.upper)
//						root.balanceR
					} else {
						val m = root.lower.max
						root.value = m.value
						m.replaceInParent(m.lower)
//						root.balanceL
					}
				}
				root.checkConsistency(bodies)
				return pos
			}
		} else {
			root.remove(e, bodies)
		}
	}

	static val debug =
//		true
		SortedBy.desiredAssertionStatus	// use the fact that asserts are enabled

	def checkConsistency() {
		if(debug) {
			root?.checkConsistency(passiveBodies)
			val l = new ArrayList
			infix[l.add(it)]
			assertEquals("result vs. tree", result, l)
			if(balance && root !== null) {
//				assertTrue("unbalanced tree", root.isBalanced)	// why does this fail?
				assertTrue("really unbalanced tree", root.depth <= root.size.log2 + 4)
			}
		}
	}

	static val balance = true

	def static log2(int it) {
		Math.log(it) / Math.log(2)
	}

	static class Node<E> {
		var E value
		var Node<E> parent = null

		var size = 1
		var Node<E> lower = null
		var Node<E> upper = null

		new(E value, Node<E> parent) {
			this.value = value
			this.parent = parent
		}

		def void checkConsistency(Function<E, ? extends Comparable<?>>...bodies) {
			if(debug) {
				if(parent === null) {
					println('''size=«size», log2(size)=«Math.log(size) / Math.log(2)», depth=«depth»''')
				}
				if(lower !== null) {
					assertTrue("", value.lt(lower.value, bodies.iterator) || lower.value.eq(value, bodies))
					assertEquals("lower.parent", lower.parent, this)
					lower.checkConsistency(bodies)
				}
				if(upper !== null) {
					assertTrue("", upper.value.lt(value, bodies.iterator) || upper.value.eq(value, bodies))
					assertEquals("upper.parent", upper.parent, this)
					upper.checkConsistency(bodies)
				}
				checkSize
			}
		}

		def checkSize() {
			assertEquals("", lowerSize + upperSize + 1, size)
		}

		// https://yoichihirai.com/bst.pdf
		static val delta = 3
		static val gamma = 2

		static def <E> csize(Node<E> a) {
			if(a === null) {0} else {a.size}
		}
		static def <E> boolean isBalancedPredicate(Node<E> a, Node<E> b) {
			val x = a.csize
			val y = b.csize
			x + y <= 1 || delta * x >= y
		}

		def boolean isBalanced() {
			isBalancedPredicate(lower, upper) && isBalancedPredicate(upper, lower) &&
			if(lower !== null) {lower.isBalanced} else {true} && if(upper !== null) {upper.isBalanced} else {true}
		}

		static def <E> isSingle(Node<E> a, Node<E> b) {
			a.csize < gamma * b.csize
		}

		def  balanceL() {
			if(!isBalancedPredicate(lower, upper)) {
				if(balance) rotateL
			}
		}

		def rotateL() {
			if(isSingle(upper.lower, upper.upper)) {
				singleL
			} else {
				doubleL
			}
		}

		def E get(int i) {
			if(i === lowerSize) {
				value
			} else if(i < lowerSize) {
				lower.get(i)
			} else {
				upper.get(i - lowerSize - 1)
			}
		}

		def singleL() {
			val tmp = value
			val x = lower
			val y = upper.lower
			val z = upper.upper

			value = upper.value
			lower = upper
			upper = z
			if(z !== null) {
				z.parent = this
			}

			lower.value = tmp
			lower.lower = x
			if(x !== null) {
				x.parent = lower
			}
			lower.upper = y
			if(y !== null) {
				y.parent = lower
			}

			lower.size = lower.lower.csize + lower.upper.csize + 1
			size = lower.csize + upper.csize + 1
		}

		def doubleL() {
			val tmp = value
			val x = lower
			val y0 = upper.lower.lower
			val y1 = upper.lower.upper

			value = upper.lower.value
			lower = upper.lower
			lower.parent = this
			upper.lower = y1
			if(y1 !== null) {
				y1.parent = upper
			}
			lower.lower = x
			if(x !== null) {
				x.parent = lower
			}
			lower.upper = y0
			if(y0 !== null) {
				y0.parent = lower
			}

			lower.value = tmp

			lower.size = lower.lower.csize + lower.upper.csize + 1
			upper.size = upper.lower.csize + upper.upper.csize + 1
			size = lower.csize + upper.csize + 1
		}

		def  balanceR() {
			if(!isBalancedPredicate(upper, lower)) {
				if(balance) rotateR
			}
		}

		def rotateR() {
			if(isSingle(lower.upper, lower.lower)) {
				singleR
			} else {
				doubleR
			}
		}

		def singleR() {
			val tmp = value
			val x = lower.lower
			val y = lower.upper
			val z = upper

			value = lower.value
			upper = lower
			lower = x
			if(x !== null) {
				x.parent = this
			}

			upper.value = tmp
			upper.lower = y
			if(y !== null) {
				y.parent = upper
			}
			upper.upper = z
			if(z !== null) {
				z.parent = upper
			}

			upper.size = upper.lower.csize + upper.upper.csize + 1
			size = lower.csize + upper.csize + 1
		}

		def doubleR() {
			val tmp = value
			val y0 = lower.upper.lower
			val y1 = lower.upper.upper
			val z = upper

			value = lower.upper.value
			upper = lower.upper
			upper.parent = this
			upper.lower = y1
			if(y1 !== null) {
				y1.parent = upper
			}
			upper.upper = z
			if(z !== null) {
				z.parent = upper
			}
			lower.upper = y0
			if(y0 !== null) {
				y0.parent = lower
			}

			upper.value = tmp

			lower.size = lower.lower.csize + lower.upper.csize + 1
			upper.size = upper.lower.csize + upper.upper.csize + 1
			size = lower.csize + upper.csize + 1
		}

		def eq(E a, E b, Function<E, ? extends Comparable<?>>...bodies) {
			bodies.map[apply(a)] == bodies.map[apply(b)]
		}

		def int depth() {
			if(lower === null) {
				if(upper === null) {
					1
				} else {
					upper.depth + 1
				}
			} else {
				if(upper === null) {
					lower.depth + 1
				} else {
					Math.max(lower.depth, upper.depth) + 1
				}
			}
		}

		def Node<E> min() {
			if(lower === null) {
				this
			} else {
				lower.min
			}
		}

		def Node<E> max() {
			if(upper === null) {
				this
			} else {
				upper.max
			}
		}

		def replaceInParent(Node<E> newNode) {
			if(parent.lower == this) {
				parent.lower = newNode
			} else {
				parent.upper = newNode
			}
			if(newNode !== null) {
				newNode.parent = parent
			}
			parent.decrementSize
		}

		def void decrementSize() {
			size--
			if(parent !== null) {
				parent.decrementSize
			}
		}

		def int remove(E e, Function<E, ? extends Comparable<?>>...bodies) {
			if(value == e) {
				if(lower === null) {
					if(upper === null) {
						replaceInParent(null)
					} else {
						replaceInParent(upper)
					}
					return 0
				} else {
					val pos = lowerSize
					if(upper === null) {
						replaceInParent(lower)
					} else {
						if(lower.size < upper.size) {
							val m = upper.min
							value = m.value
							m.replaceInParent(m.upper)
						} else {
							val m = lower.max
							value = m.value
							m.replaceInParent(m.lower)
						}
					}
					return pos
				}
			} else if(value.lt(e, bodies.iterator)) {
				val r = lower.remove(e, bodies)
				checkConsistency(bodies)
				balanceL
				r
			} else {
				val r = upper.remove(e, bodies) + lowerSize + 1
				balanceR
				r
			}
		}

		def boolean lt(E a, E b, Iterator<Function<E, ? extends Comparable<?>>> bodies) {
			if(bodies.hasNext) {
				val body = bodies.next
				val ba = body.apply(a) as Comparable<Object>
				val bb = body.apply(b) as Comparable<Object>
				if(ba < bb) {
					true
				} else if(ba == bb) {
					a.lt(b, bodies)
				} else {
					false
				}
			} else {
				false
			}
		}

		def lowerSize() {
			if(lower === null) {
				0
			} else {
				lower.size
			}
		}

		def upperSize() {
			if(upper === null) {
				0
			} else {
				upper.size
			}
		}

		def int add(E e, Function<E, ? extends Comparable<?>>...bodies) {
			size++
			val ret =
//				if(e.lt(value, bodies.iterator)) {
				if(value.lt(e, bodies.iterator)) {	// reversing sort
					if(lower === null) {
						lower = new Node(e, this)
						0
					} else {
						val r = lower.add(e, bodies)
						balanceR
						r
					}
				} else {
					if(upper === null) {
						upper = new Node(e, this)
						size - 1
					} else {
						val r = 1 + upper.add(e, bodies) + lowerSize
						balanceL
						r
					}
				}
			ret
		}

//		def iterator() {
//			TODO: use guava?
//		}
		def void infix(Consumer<E> consumer) {
			if(lower !== null) {
				lower.infix(consumer)
			}
			consumer.accept(value)
			if(upper !== null) {
				upper.infix(consumer)
			}
		}

		override toString() {
			val ret = new StringBuilder
			infix[
				if(ret.length !== 0) {
					ret.append(", ")
				}
				if(it == value) {
					ret.append("*")
				}
				ret.append(it)
			]
			'''[«ret.toString»]'''
		}
	}

	def static <E> testOne(Pair<Integer, ISequence<E>> it, (E)=>Integer body1, (E)=>Integer body2) {
		value.inspect('''unsorted[«key»]: ''')
		new SortedBy(value, [
			AOFFactory.INSTANCE.createOne(body1.apply(it))
		], [
			AOFFactory.INSTANCE.createOne(body2.apply(it))
		]).result.inspect('''sorted[«key»]  : ''')
	}

	def static <E> assertEquals(Iterable<E> expected, Iterable<E> actual) {
		assertEquals("", expected, actual)
	}

	def static <E> assertEquals(String msg, Iterable<E> expected, Iterable<E> actual) {
		val ite = expected.iterator
		val ita = actual.iterator
		var i = 0
		while(ite.hasNext && ita.hasNext) {
			val e = ite.next
			val a = ita.next
			assertTrue('''«msg»[«i»]: expected «expected», but got «actual»''', e == a)
			i++
		}
		assertEquals('''«msg».size''', ite.hasNext, ita.hasNext)
	}

	def static <E> assertEquals(String msg, E expected, E actual) {
		assertTrue('''«msg»: expected «expected», but got «actual»''', expected == actual)
	}
	def static assertTrue(String msg, boolean b) {
		if(!b) {
			throw new AssertionError(msg)
		}
	}

	static val rand = new Random(0)

	def static chooseIndex(IBox<?> it) {
		rand.nextInt(it.length)
	}

	def static void main(String[] args) {
		val bss = #[
			AOFFactory.INSTANCE.createSequence(1, 2, 3, 4, 5, 6),
			AOFFactory.INSTANCE.createSequence(6, 5, 4, 3, 2, 1),
			AOFFactory.INSTANCE.createSequence(6, 4, 5, 1, 2, 3)
		]
		for(it : bss.indexed) {
			val cs = testOne([it], [0])
			assertEquals(value.sort.reverse, cs)
			value.add(3, 8)
			assertEquals(value.sort.reverse, cs)
			value.add(6, -1)
			assertEquals(value.sort.reverse, cs)
			value.removeAt(5)
			assertEquals(value.sort.reverse, cs)
			for(i : 0..1000) {
				print('''[«i»] ''')
				switch(rand.nextInt(4)) {
					case 0: {
						val index = if(value.length === 0) {0} else {value.chooseIndex}
						val v = rand.nextInt
						println('''ADD «value» at «index»''')
						value.add(index, v)
					}
					case 1: {
						if(value.length > 0) {
							val index = value.chooseIndex
							println('''REMOVE at «index»''')
							value.removeAt(index)
						} else {
							println("REMOVE impossible on empty box")
						}
					}
					case 2: {
						if(value.length > 0) {
							val newIndex = value.chooseIndex
							val oldIndex = value.chooseIndex
							println('''MOVE «newIndex», «oldIndex»''')
							value.move(newIndex, oldIndex)
						} else {
							println("MOVE impossible on empty box")
						}
					}
					case 3: {
						if(value.length > 0) {
							val index = value.chooseIndex
							val v = rand.nextInt
							println('''REPLACE at «index» by «v»''')
							value.set(index, v)
						} else {
							println("REPLACE impossible on empty box")
						}
					}
				}
				assertEquals(value.sort.reverse, cs)
			}
		}
		val ps = AOFFactory.INSTANCE.createSequence(
			1 -> 0, 2 -> 0, 3 -> 0, 4 -> 0, 5 -> 0, 6 -> 0
		)
		val cs = (bss.size -> ps).testOne([key], [value])
		assertEquals(ps.sortBy[key * 1000 + value].reverse, cs)
		ps.add(3, 8 -> 0)
		assertEquals(ps.sortBy[key * 1000 + value].reverse, cs)
		ps.add(6, -1 -> 0)
		assertEquals(ps.sortBy[key * 1000 + value].reverse, cs)
		ps.add(3, 1 -> 0)
		assertEquals(ps.sortBy[key * 1000 + value].reverse, cs)
		ps.add(3, 1 -> 1)
		assertEquals(ps.sortBy[key * 1000 + value].reverse, cs)
		ps.add(3, 1 -> -1)
		assertEquals(ps.sortBy[key * 1000 + value].reverse, cs)
	}
}