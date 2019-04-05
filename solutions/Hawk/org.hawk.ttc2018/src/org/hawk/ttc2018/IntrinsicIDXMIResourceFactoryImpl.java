package org.hawk.ttc2018;

import java.util.HashMap;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

public class IntrinsicIDXMIResourceFactoryImpl extends XMIResourceFactoryImpl {
	@Override
	public Resource createResource(URI uri) {
		final XMIResourceImpl r = new XMIResourceImpl(uri);
		r.setIntrinsicIDToEObjectMap(new HashMap<>());
		return r;
	}
}