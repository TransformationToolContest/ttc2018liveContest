package ttc2018;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.m2m.atl.emftvm.EmftvmFactory;
import org.eclipse.m2m.atl.emftvm.ExecEnv;
import org.eclipse.m2m.atl.emftvm.Metamodel;
import org.eclipse.m2m.atl.emftvm.Model;
import org.eclipse.m2m.atl.emftvm.impl.resource.EMFTVMResourceFactoryImpl;
import org.eclipse.m2m.atl.emftvm.util.ClassModuleResolver;
import org.eclipse.m2m.atl.emftvm.util.ModuleResolver;
import org.eclipse.m2m.atl.emftvm.util.TimingData;

import Changes.ModelChange;
import Changes.ModelChangeSet;
import SocialNetwork.SocialNetworkPackage;
import SocialNetwork.SocialNetworkRoot;
import SocialNetwork.Submission;

public class SolutionQ1 extends Solution {

	public static String run(String moduleName, SocialNetworkRoot socialNetwork) {
		ExecEnv env = EmftvmFactory.eINSTANCE.createExecEnv();
		ResourceSet rs = new ResourceSetImpl();
	
		Metamodel inMetamodel = EmftvmFactory.eINSTANCE.createMetamodel();
		inMetamodel.setResource(SocialNetworkPackage.eINSTANCE.getPost().eResource());
		env.registerMetaModel("SN", inMetamodel);

		// loading models
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("emftvm", new EMFTVMResourceFactoryImpl());

		Model inModel = EmftvmFactory.eINSTANCE.createModel();
		inModel.setResource(socialNetwork.eResource());
		env.registerInputModel("IN", inModel);


		ModuleResolver mr = new ClassModuleResolver(SolutionQ1.class);
		TimingData td = new TimingData();
		env.loadModule(mr, moduleName);
		td.finishLoading();
		List<Submission> result = (List<Submission>)env.run(td);
		td.finish();

		return result.stream().map(Submission::getId).collect(Collectors.joining("|"));
	}

	@Override
	public String Initial() {
		return run("Q1", this.getSocialNetwork());
	}

	@Override
	public String Update(ModelChangeSet changes) {
		EList<ModelChange> coll = changes.getChanges();
		for (ModelChange change : coll) {
			change.apply();
		}
		return Initial();
	}

}
