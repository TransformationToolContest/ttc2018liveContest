package org.hawk.ttc2018;

import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.hawk.emf.model.EMFModelResourceFactory;

public class IntrinsicIDEMFModelResourceFactory extends EMFModelResourceFactory {

	@Override
	protected Factory createResourceFactory() {
		return new IntrinsicIDXMIResourceFactoryImpl();
	}

	@Override
	protected Map<?, ?> createEMFLoadOptions() {
		return Collections.singletonMap(XMIResource.OPTION_DEFER_IDREF_RESOLUTION, true);
	}

}
