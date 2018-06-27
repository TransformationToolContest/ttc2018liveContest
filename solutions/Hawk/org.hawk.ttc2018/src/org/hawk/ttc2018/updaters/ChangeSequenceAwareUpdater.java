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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
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
import org.hawk.graph.updater.GraphModelBatchInjector;
import org.hawk.graph.updater.GraphModelUpdater;
import org.hawk.graph.updater.TypeCache;
import org.hawk.ttc2018.AbstractLauncher;

import Changes.AssociationCollectionInsertion;
import Changes.AssociationPropertyChange;
import Changes.AttributePropertyChange;
import Changes.ChangesPackage;
import Changes.CompositionListInsertion;
import Changes.ElementaryChange;
import Changes.ModelChangeSet;
import SocialNetwork.SocialNetworkPackage;
import SocialNetwork.SocialNetworkRoot;
import SocialNetwork.Submission;
import SocialNetwork.User;

public class ChangeSequenceAwareUpdater extends GraphModelUpdater {

	private GraphModelBatchInjector injector;
	private IGraphNode fileNode;

	@Override
	public boolean updateStore(VcsCommitItem f, IHawkModelResource res) {
		if (f.getPath().endsWith("/" + AbstractLauncher.INITIAL_MODEL_FILENAME)) {
			return super.updateStore(f, res);
		} else {
			return applyChangeSequenceToGraph(f);
		}
	}

	protected boolean applyChangeSequenceToGraph(VcsCommitItem f) {
		final IVcsManager vcs = f.getCommit().getDelta().getManager();
		final String revision = f.getCommit().getRevision();
		final File changeFile = vcs.importFile(revision, f.getPath(), null);

		ResourceSetImpl rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		rs.getPackageRegistry().put(ChangesPackage.eNS_URI, ChangesPackage.eINSTANCE);
		rs.getPackageRegistry().put(SocialNetworkPackage.eNS_URI, SocialNetworkPackage.eINSTANCE);
		Resource r = rs.createResource(URI.createFileURI(changeFile.getAbsolutePath()));

		try {
			r.load(null);

			final ModelChangeSet changeSet = (ModelChangeSet) r.getContents().get(0);
			try (IGraphTransaction tx = indexer.getGraph().beginTransaction()) {
				final IGraphDatabase graph = indexer.getGraph();
				injector = new GraphModelBatchInjector(graph, new TypeCache(), f, indexer.getCompositeGraphChangeListener());
				fileNode = graph.getFileIndex().get("id", vcs.getLocation() + GraphModelUpdater.FILEINDEX_REPO_SEPARATOR + "/" + AbstractLauncher.INITIAL_MODEL_FILENAME).getSingle();

				applyChangeSequenceToGraph(f, changeSet);
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

	private void applyChangeSequenceToGraph(VcsCommitItem f, ModelChangeSet changeSet) throws Exception {
		for (Iterator<EObject> itEObject = changeSet.eAllContents(); itEObject.hasNext(); ) {
			EObject eob = itEObject.next();
			if (eob instanceof AssociationCollectionInsertion) {
				applyChangeSequenceToGraph(f, (AssociationCollectionInsertion) eob);
			} else if (eob instanceof AssociationPropertyChange) {
				applyChangeSequenceToGraph(f, (AssociationPropertyChange) eob);
			}  else if (eob instanceof AttributePropertyChange) {
				applyChangeSequenceToGraph(f, (AttributePropertyChange) eob);
			}  else if (eob instanceof CompositionListInsertion) {
				applyChangeSequenceToGraph(f, (CompositionListInsertion) eob);
			} else if (eob instanceof ElementaryChange) {
				throw new IllegalArgumentException("Unknown elementary change " + eob);
			}
		}
	}

	private void applyChangeSequenceToGraph(VcsCommitItem f, CompositionListInsertion change) throws Exception {
		addReference(f, (EReference) change.getFeature(), change.getAffectedElement(), change.getAddedElement());
	}

	private void addReference(VcsCommitItem f, final EReference eref, final EObject sourceEObject,
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
		indexer.getCompositeGraphChangeListener().referenceAddition(f, sourceNode, targetNode, eref.getName(), false);
	}

	private IGraphNode findNodeByID(EObject eob) throws Exception {
		if (eob.eIsProxy()) {
			// Not a real eobject - we don't save updated objects back to the initial.xmi file
			return findNodeByID(((MinimalEObjectImpl)eob).eProxyURI().fragment(), eob);
		} else if (eob instanceof Submission) {
			return findNodeByID(((Submission)eob).getId(), eob);
		} else if (eob instanceof User) {
			return findNodeByID(((User)eob).getId(), eob);
		} else if (eob instanceof SocialNetworkRoot) {
			return new FileNode(fileNode).getRootModelElements().iterator().next().getNode();
		} else {
			throw new IllegalArgumentException("Unknown EObject type " + eob.eClass().getName());
		}
	}

	private IGraphNode findNodeByID(final String id, final EObject eob) throws Exception {
		final IGraphNodeIndex idx = indexer.getGraph()
			.getOrCreateNodeIndex(String.format("%s##%s##%s",
				SocialNetworkPackage.eNS_URI, eob.eClass().getName(), "id"));

		final IGraphIterable<IGraphNode> iterableNodes = idx.get("id", id);
		final Iterator<IGraphNode> itNode = iterableNodes.iterator();
		if (itNode.hasNext()) {
			return itNode.next();
		}

		// Node not found: new object
		final EMFObject hawkEObject = new EMFObject(eob, new EMFWrapperFactory());
		final IGraphNode newNode = injector.addEObject(fileNode, hawkEObject, false);
		injector.addEReferences(hawkEObject, false);

		return newNode;
	}

	private void applyChangeSequenceToGraph(VcsCommitItem f, AttributePropertyChange change) throws Exception {
		IGraphNode node = findNodeByID(change.getAffectedElement());
		node.setProperty(change.getFeature().getName(), change.getNewValue());
	}

	private void applyChangeSequenceToGraph(VcsCommitItem f, AssociationPropertyChange change) throws Exception {
		final EReference eref = (EReference) change.getFeature();
		clearReferencesFrom(f, eref, change.getAffectedElement());
		addReference(f, eref, change.getAffectedElement(), change.getNewValue());
	}

	protected void clearReferencesFrom(VcsCommitItem f, final EStructuralFeature eref, final EObject sourceEObject) throws Exception {
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
			indexer.getCompositeGraphChangeListener().referenceRemoval(f, sourceNode, oldTarget, name, false);
		}
	}

	private void applyChangeSequenceToGraph(VcsCommitItem f, AssociationCollectionInsertion change) throws Exception {
		addReference(f, (EReference) change.getFeature(), change.getAffectedElement(), change.getAddedElement());
	}

}
