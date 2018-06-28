package org.hawk.ttc2018;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.hawk.emf.model.EMFModelResourceFactory;

public class IntrinsicIDEMFModelResourceFactory extends EMFModelResourceFactory {

	protected static class IntrinsicIDXMIResourceFactoryImpl extends XMIResourceFactoryImpl {
		@Override
		public Resource createResource(URI uri) {
			final XMIResourceImpl r = new XMIResourceImpl(uri);
			r.setIntrinsicIDToEObjectMap(new HashMap<>());
			return r;
		}
	}

	@Override
	protected Factory createResourceFactory() {
		return new IntrinsicIDXMIResourceFactoryImpl();
	}

	@Override
	protected Map<?, ?> createEMFLoadOptions() {
		return Collections.singletonMap(XMIResource.OPTION_DEFER_IDREF_RESOLUTION, true);
	}

}
