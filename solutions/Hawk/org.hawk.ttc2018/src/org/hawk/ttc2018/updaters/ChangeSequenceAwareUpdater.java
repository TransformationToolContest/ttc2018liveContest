/*******************************************************************************
 * Copyright (c) 2018 Aston University.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License, v. 2.0 are satisfied: GNU General Public License, version 3.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-3.0
 *
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.hawk.ttc2018.updaters;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.hawk.core.IVcsManager;
import org.hawk.core.VcsCommitItem;
import org.hawk.core.graph.IGraphDatabase;
import org.hawk.core.graph.IGraphEdge;
import org.hawk.core.graph.IGraphIterable;
import org.hawk.core.graph.IGraphNode;
import org.hawk.core.graph.IGraphNodeIndex;
import org.hawk.core.graph.IGraphTransaction;
import org.hawk.core.model.IHawkModelResource;
import org.hawk.emf.EMFObject;
import org.hawk.emf.EMFWrapperFactory;
import org.hawk.graph.FileNode;
import org.hawk.graph.ModelElementNode;
import org.hawk.graph.updater.DirtyDerivedFeaturesListener;
import org.hawk.graph.updater.GraphModelBatchInjector;
import org.hawk.graph.updater.GraphModelUpdater;
import org.hawk.graph.updater.TypeCache;
import org.hawk.ttc2018.AbstractLauncher;
import org.hawk.ttc2018.IntrinsicIDXMIResourceFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Changes.ChangesPackage;
import Changes.ElementaryChange;
import Changes.ModelChangeSet;
import Changes.impl.AssociationCollectionInsertionImpl;
import Changes.impl.AssociationPropertyChangeImpl;
import Changes.impl.AttributePropertyChangeImpl;
import Changes.impl.CompositionListInsertionImpl;
import SocialNetwork.SocialNetworkPackage;
import SocialNetwork.SocialNetworkRoot;
import SocialNetwork.Submission;
import SocialNetwork.User;

/**
 * Experimental updater that changes the graph directly from a graph sequence,
 * rather than saving to XMI then reindexing.
 *
 * This class is not thread-safe.
 */
public class ChangeSequenceAwareUpdater extends GraphModelUpdater {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChangeSequenceAwareUpdater.class);
	private GraphModelBatchInjector injector;
	private IGraphNode fileNode;
	private VcsCommitItem commitItem;

	@Override
	public boolean updateStore(VcsCommitItem f, IHawkModelResource res) {
		if (f.getPath().endsWith("/" + AbstractLauncher.INITIAL_MODEL_FILENAME)) {
			return super.updateStore(f, res);
		} else {
			final IGraphDatabase g = indexer.getGraph();
			final DirtyDerivedFeaturesListener l = new DirtyDerivedFeaturesListener(g);
			final boolean hasDerived = !indexer.getDerivedAttributes().isEmpty();
			if (hasDerived) {
				indexer.addGraphChangeListener(l);
			}

			boolean success = applyChangeSequenceToGraph(f);
			if (hasDerived) {
				toBeUpdated.addAll(l.getNodesToBeUpdated());
				indexer.removeGraphChangeListener(l);
			}
			return success;
		}
	}

	protected boolean applyChangeSequenceToGraph(VcsCommitItem f) {
		commitItem = f;

		final IVcsManager vcs = f.getCommit().getDelta().getManager();
		final String revision = f.getCommit().getRevision();
		final File changeFile = vcs.importFile(revision, f.getPath(), null);

		ResourceSetImpl rs = new ResourceSetImpl();
		rs.getPackageRegistry().put(ChangesPackage.eNS_URI, ChangesPackage.eINSTANCE);
		rs.getPackageRegistry().put(SocialNetworkPackage.eNS_URI, SocialNetworkPackage.eINSTANCE);
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new IntrinsicIDXMIResourceFactoryImpl());

		try {
			final Map<String, Boolean> loadOptions = Collections.singletonMap(XMIResource.OPTION_DEFER_IDREF_RESOLUTION, true);
			final XMIResourceImpl r = (XMIResourceImpl) rs.createResource(URI.createFileURI(changeFile.getAbsolutePath()));
			final long startLoadMillis = System.currentTimeMillis();
			r.load(loadOptions);
			LOGGER.debug("Change model loaded in {}ms", System.currentTimeMillis() - startLoadMillis);

			final ModelChangeSet changeSet = (ModelChangeSet) r.getContents().get(0);
			try (IGraphTransaction tx = indexer.getGraph().beginTransaction()) {
				final IGraphDatabase graph = indexer.getGraph();
				injector = new GraphModelBatchInjector(graph, new TypeCache(), f, indexer.getCompositeGraphChangeListener());
				fileNode = graph.getFileIndex().get("id", vcs.getLocation() + GraphModelUpdater.FILEINDEX_REPO_SEPARATOR + "/" + AbstractLauncher.INITIAL_MODEL_FILENAME).getSingle();

				applyChangeSequenceToGraph(changeSet);
				tx.success();
			} catch (Exception e) {
				console.printerrln(e);
			}

			r.unload();
		} catch (IOException e) {
			console.printerrln(e);
			return false;
		}

		return true;
	}

	private void applyChangeSequenceToGraph(ModelChangeSet changeSet) throws Exception {
		for (Iterator<EObject> itEObject = EcoreUtil.getAllProperContents(changeSet, false); itEObject.hasNext(); ) {
			EObject eob = itEObject.next();
			if (eob instanceof AssociationCollectionInsertionImpl) {
				applyChangeSequenceToGraph((AssociationCollectionInsertionImpl) eob);
			} else if (eob instanceof AssociationPropertyChangeImpl) {
				applyChangeSequenceToGraph((AssociationPropertyChangeImpl) eob);
			}  else if (eob instanceof AttributePropertyChangeImpl) {
				applyChangeSequenceToGraph((AttributePropertyChangeImpl) eob);
			}  else if (eob instanceof CompositionListInsertionImpl) {
				applyChangeSequenceToGraph((CompositionListInsertionImpl) eob);
			} else if (eob instanceof ElementaryChange) {
				throw new IllegalArgumentException("Unknown elementary change " + eob);
			}
		}
	}

	private void applyChangeSequenceToGraph(CompositionListInsertionImpl change) throws Exception {
		addReference((EReference) change.getFeature(), change.basicGetAffectedElement(), change.getAddedElement());
	}

	private void applyChangeSequenceToGraph(AttributePropertyChangeImpl change) throws Exception {
		IGraphNode node = findNodeByID(change.basicGetAffectedElement());
		node.setProperty(change.getFeature().getName(), change.getNewValue());
	}

	private void applyChangeSequenceToGraph(AssociationPropertyChangeImpl change) throws Exception {
		final EReference eref = (EReference) change.getFeature();
		clearReferencesFrom(eref, change.basicGetAffectedElement());
		addReference(eref, change.basicGetAffectedElement(), change.basicGetNewValue());
	}

	private void applyChangeSequenceToGraph(AssociationCollectionInsertionImpl change) throws Exception {
		addReference((EReference) change.getFeature(), change.basicGetAffectedElement(), change.basicGetAddedElement());
	}

	private void addReference(final EReference eref, final EObject sourceEObject,
			final EObject targetEObject) throws Exception {
		final IGraphDatabase graph = indexer.getGraph();
		final IGraphNode sourceNode = findNodeByID(sourceEObject);
		final IGraphNode targetNode = findNodeByID(targetEObject);
	
		Map<String, Object> props = new HashMap<>();
		if (eref.isContainment()) {
			props.put(ModelElementNode.EDGE_PROPERTY_CONTAINMENT, "true");
		}
		if (eref.isContainer()) {
			props.put(ModelElementNode.EDGE_PROPERTY_CONTAINER, "true");
		}
	
		graph.createRelationship(sourceNode, targetNode, eref.getName(), props);
		indexer.getCompositeGraphChangeListener().referenceAddition(commitItem, sourceNode, targetNode, eref.getName(), false);
	}

	private IGraphNode findNodeByID(EObject eob) throws Exception {
		if (eob instanceof SocialNetworkRoot) {
			return new FileNode(fileNode).getRootModelElements().iterator().next().getNode();
		} else if (eob.eIsProxy()) {
			// Not a real eobject - we don't save updated objects back to the initial.xmi file
			return findNodeByID(((MinimalEObjectImpl)eob).eProxyURI().fragment(), eob);
		} else if (eob instanceof Submission) {
			return findNodeByID(((Submission)eob).getId(), eob);
		} else if (eob instanceof User) {
			return findNodeByID(((User)eob).getId(), eob);
		} else {
			throw new IllegalArgumentException("Unknown EObject type " + eob.eClass().getName());
		}
	}

	@SuppressWarnings("unchecked")
	private IGraphNode findNodeByID(final String id, final EObject eob) throws Exception {
		final IGraphNodeIndex idx = indexer.getGraph()
			.getOrCreateNodeIndex(String.format("%s##%s##%s",
				SocialNetworkPackage.eNS_URI, eob.eClass().getName(), "id"));
	
		final IGraphIterable<IGraphNode> iterableNodes = idx.get("id", id);
		final Iterator<IGraphNode> itNode = iterableNodes.iterator();
		if (itNode.hasNext()) {
			return itNode.next();
		}
	
		// Node not found: new object. Use Hawk injector to handle indexed attributes.
		final EMFObject hawkEObject = new EMFObject(eob, new EMFWrapperFactory());
		final IGraphNode newNode = injector.addEObject(fileNode, hawkEObject, false);
	
		/*
		 * Since we do not update the initial.xmi file, references from the change
		 * sequences to elements in previous change sequences would be unresolveable
		 * proxies with the normal process. We take advantage of our custom indexed
		 * attributes.
		 */
		for (EReference ref : eob.eClass().getEAllReferences()) {
			Object value = eob.eGet(ref, false);
			if (value instanceof EObject) {
				final EObject target = (EObject)value;
				addReference(ref, eob, target);
			} else if (value instanceof Iterable) {
				for (EObject target : (Iterable<EObject>)value) {
					addReference(ref, eob, target);
				}
			}
		}
	
		return newNode;
	}

	private void clearReferencesFrom(final EStructuralFeature eref, final EObject sourceEObject) throws Exception {
		final IGraphNode sourceNode = findNodeByID(sourceEObject);
		final String name = eref.getName();

		// Do a defensive copy - we are going to delete things
		final List<IGraphEdge> edges = new ArrayList<>();
		for (IGraphEdge edge : sourceNode.getEdgesWithType(name)) {
			edges.add(edge);
		}
		for (IGraphEdge edge : edges) {
			final IGraphNode oldTarget = edge.getEndNode();
			edge.delete();
			indexer.getCompositeGraphChangeListener().referenceRemoval(commitItem, sourceNode, oldTarget, name, false);
		}
	}

}
