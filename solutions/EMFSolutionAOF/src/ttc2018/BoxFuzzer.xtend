package ttc2018

import java.util.Random
import java.util.function.Supplier
import org.eclipse.papyrus.aof.core.IBox

class BoxFuzzer<E> {
	public static val rand = new Random(0)

	def static chooseIndex(IBox<?> it) {
		rand.nextInt(it.length)
	}

	new(IBox<E> it, Supplier<E> supplier, Runnable checker) {
		this(it, supplier, checker, "")
	}

	new(IBox<E> it, Supplier<E> supplier, Runnable checker, String prefix) {
		checker.run
		for(i : 0..1000) {
			print('''«prefix»[«i»] ''')
			switch(rand.nextInt(4)) {
				case 0: {
					val index = if(length === 0) {0} else {chooseIndex}
					val v = supplier.get
					println('''ADD «v» at «index»''')
					add(index, v)
				}
				case 1: {
					if(length > 0) {
						val index = chooseIndex
						println('''REMOVE at «index»''')
						removeAt(index)
					} else {
						println("REMOVE impossible on empty box")
					}
				}
				case 2: {
					if(length > 0) {
						val newIndex = chooseIndex
						val oldIndex = chooseIndex
						println('''MOVE «newIndex», «oldIndex»''')
						move(newIndex, oldIndex)
					} else {
						println("MOVE impossible on empty box")
					}
				}
				case 3: {
					if(length > 0) {
						val index = chooseIndex
						val v = supplier.get
						println('''REPLACE at «index» by «v»''')
						set(index, v)
					} else {
						println("REPLACE impossible on empty box")
					}
				}
			}
			checker.run
		}
	}
}