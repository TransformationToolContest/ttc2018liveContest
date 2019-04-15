package ttc2018

import fr.eseo.atol.gen.ATOLGen
import fr.eseo.atol.gen.ATOLGen.Metamodel


@ATOLGen(transformation="src/ttc2018/Q2_ATOL.atl", metamodels = #[
	@Metamodel(name = "SN", impl=SN)
])
class Q2 implements AOFExtensions {

}
