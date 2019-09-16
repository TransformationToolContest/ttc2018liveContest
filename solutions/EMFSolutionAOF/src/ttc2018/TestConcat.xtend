package ttc2018

import org.eclipse.papyrus.aof.core.AOFFactory

class TestConcat {
	def static createTestBox(int base) {
		AOFFactory.INSTANCE.createSequence((1..3).map[it + base] as Integer[])
	}

	def static void main(String[] args) {
		val bs = createTestBox(0)
		bs.inspect("bs: ")
		val cs = createTestBox(3)
		cs.inspect("cs: ")
		val ds = bs.concat(cs)
		ds.inspect("ds: ")
		new BoxFuzzer(bs, [BoxFuzzer.rand.nextInt], [
			SortedBy.assertEquals(bs.snapshot.concat(cs), ds)
		])
		new BoxFuzzer(cs, [BoxFuzzer.rand.nextInt], [
			SortedBy.assertEquals(bs.snapshot.concat(cs), ds)
		])
		new BoxFuzzer(bs, [BoxFuzzer.rand.nextInt], [
			SortedBy.assertEquals(bs.snapshot.concat(cs), ds)
		])
	}
}
