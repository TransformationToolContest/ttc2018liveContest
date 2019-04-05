package ttc2018

import java.util.Comparator
import com.google.common.collect.ImmutableList

class Greatest3<E> {
		val Comparator<E> comp
		new(Comparator<E> comp) {
			this.comp = comp
		}
		var E a = null
		var E b = null
		var E c = null
		def add(E i) {
			if(comp.compare(i, b) > 0) {
				if(comp.compare(i, a) > 0) {
					c = b
					b = a
					a = i
				} else {
					c = b
					b = i
				}
			} else {
				if(comp.compare(i, c) > 0) {
					c = i
				} else {
					// do nothing
				}
			}
		}
		def Greatest3<E> merge(Greatest3<E> other) {
			add(other.a)
			add(other.b)
			add(other.c)
			this
		}
		def asList() {
			ImmutableList.of(a, b, c)
		}
}
