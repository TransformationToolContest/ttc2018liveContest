package ttc2018

import fr.eseo.atol.gen.ATOLGen
import fr.eseo.atol.gen.ATOLGen.Metamodel

@ATOLGen(transformation="src/ttc2018/Q1_ATOL.atl", metamodels = #[
	@Metamodel(name = "SN", impl=SN)
])
class Q1 implements AOFExtensions {
}