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
package org.hawk.ttc2018;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dom.Operation;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.hawk.core.VcsCommitItem;
import org.hawk.core.graph.IGraphEdge;
import org.hawk.core.graph.IGraphNode;
import org.hawk.core.graph.IGraphTransaction;
import org.hawk.core.model.IHawkObject;
import org.hawk.core.query.InvalidQueryException;
import org.hawk.core.query.QueryExecutionException;
import org.hawk.core.util.GraphChangeAdapter;
import org.hawk.epsilon.emc.EOLQueryEngine;
import org.hawk.epsilon.emc.wrappers.GraphNodeWrapper;
import org.hawk.graph.ModelElementNode;
import org.hawk.ttc2018.queries.ResultComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Alternative implementation that updates query results incrementally as well.
 * Only the changed posts/comments are re-scored through the context operation
 * in EOL.
 */
public class IncrementalUpdateQueryLauncher extends AbstractIncrementalUpdateLauncher {

	protected class QueryUpdateListener extends GraphChangeAdapter {

		private final Set<Object> updatedPostIdentifiers = new HashSet<>();
		private final Set<Object> updatedCommentIdentifiers = new HashSet<>();

		public Set<Object> getUpdatedPostIdentifiers() {
			return updatedPostIdentifiers;
		}

		public Set<Object> getUpdatedCommentIdentifiers() {
			return updatedCommentIdentifiers;
		}

		@Override
		public void modelElementAddition(VcsCommitItem s, IHawkObject element, IGraphNode elementNode, boolean isTransient) {
			markUpdatedByType(element.getType().getName(), elementNode);
		}

		protected void markUpdatedByType(String typeName, IGraphNode elementNode) {
			switch (typeName) {
			case "Post":
				updatedPostIdentifiers.add(elementNode.getId());
				break;
			case "Comment":
				updatedCommentIdentifiers.add(elementNode.getId());
				break;
			}
		}

		@Override
		public void modelElementAttributeUpdate(VcsCommitItem s, IHawkObject eObject, String attrName, Object oldValue, Object newValue, IGraphNode elementNode, boolean isTransient) {
			markUpdatedByType(eObject.getType().getName(), elementNode);
		}

		@Override
		public void modelElementAttributeRemoval(VcsCommitItem s, IHawkObject eObject, String attrName, IGraphNode elementNode, boolean isTransient) {
			markUpdatedByType(eObject.getType().getName(), elementNode);
		}

		@Override
		public void referenceAddition(VcsCommitItem s, IGraphNode source, IGraphNode destination, String edgelabel, boolean isTransient) {
			markUpdatedByType(new ModelElementNode(source).getTypeNode().getTypeName(), source);
			markUpdatedByType(new ModelElementNode(destination).getTypeNode().getTypeName(), destination);
			markUpdatedByBefriending(source, destination, edgelabel);
		}

		@Override
		public void referenceRemoval(VcsCommitItem s, IGraphNode source, IGraphNode destination, String edgelabel, boolean isTransient) {
			markUpdatedByType(new ModelElementNode(source).getTypeNode().getTypeName(), source);
			markUpdatedByType(new ModelElementNode(destination).getTypeNode().getTypeName(), destination);
			markUpdatedByBefriending(source, destination, edgelabel);
		}

		protected void markUpdatedByBefriending(IGraphNode source, IGraphNode destination, String edgelabel) {
			// Two friends (un)friending each other - revise all the posts they liked (connected components will have changed)
			if ("friends".equals(edgelabel)) {
				markUpdatedByLiked(source);
				markUpdatedByLiked(destination);
			}
		}

		protected void markUpdatedByLiked(IGraphNode n) {
			for (IGraphEdge edge : n.getIncomingWithType("likedBy")) {
				markUpdatedByType(new ModelElementNode(edge.getStartNode()).getTypeNode().getTypeName(), edge.getStartNode());
			}
		}
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(IncrementalUpdateQueryLauncher.class);
	private List<List<Object>> prevResults;
	private QueryUpdateListener listener;
	private EolModule eolRescoreModule;
	private EOLQueryEngine hawkModel;

	public IncrementalUpdateQueryLauncher(Map<String, String> env) throws Exception {
		super(env);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<List<Object>> runQuery(StandaloneHawk hawk)
			throws IOException, InvalidQueryException, QueryExecutionException {
		if (prevResults == null) {
			prevResults = (List<List<Object>>) hawk.eol(query.getFullQuery());
			hawk.getIndexer().addGraphChangeListener(listener);
		} else {
			// Rescore updated posts
			Operation scoreOp;
			try {
				scoreOp = getRescoreEOLModule(hawk).getOperations().getOperation("score");
			} catch (Exception e) {
				throw new QueryExecutionException(e);
			}

			try (IGraphTransaction tx = hawk.getIndexer().getGraph().beginTransaction()) {
				if (query == Query.Controversial) {
					updateResults(hawk, scoreOp, listener.getUpdatedPostIdentifiers());
				} else {
					updateResults(hawk, scoreOp, listener.getUpdatedCommentIdentifiers());
				}
				tx.success();
			} catch (Exception e) {
				throw new QueryExecutionException(e);
			}
		}

		listener.getUpdatedCommentIdentifiers().clear();
		listener.getUpdatedPostIdentifiers().clear();
		return prevResults;
	}

	protected void updateResults(StandaloneHawk hawk, Operation scoreOp, final Set<Object> ids) throws QueryExecutionException {
		final Map<String, List<Object>> allResults = new HashMap<>();
		for (List<Object> oldResult : prevResults) {
			allResults.put(oldResult.get(0) + "", oldResult.subList(1, 3));
		}

		for (Object postID : ids) {
			final IGraphNode node = hawk.getIndexer().getGraph().getNodeById(postID);
			final GraphNodeWrapper gw = new GraphNodeWrapper(node, hawkModel);
			int score;
			try {
				score = (Integer) scoreOp.execute(gw, Collections.emptyList(), ((EolModule) scoreOp.getModule()).getContext());
			} catch (EolRuntimeException e) {
				throw new QueryExecutionException(e);
			}

			final String sIdentifier = node.getProperty("id") + "";
			final List<Object> lResult = new ArrayList<>();
			lResult.add(score);
			lResult.add(node.getProperty("timestamp"));
			allResults.put(sIdentifier, lResult);
		}

		List<List<Object>> lAllResults = new ArrayList<>(allResults.size());
		for (Entry<String, List<Object>> entry : allResults.entrySet()) {
			lAllResults.add(Arrays.asList(entry.getKey(), entry.getValue().get(0), entry.getValue().get(1)));
		}
		Collections.sort(lAllResults, new ResultComparator());
		prevResults = lAllResults.subList(0, Math.min(3, lAllResults.size()));
	}

	protected EolModule getRescoreEOLModule(StandaloneHawk hawk) throws Exception {
		if (eolRescoreModule == null) {
			eolRescoreModule = parseEOLModule(query.getFullQuery());
		} else {
			final ModelRepository modelRepository = eolRescoreModule.getContext().getModelRepository();
			modelRepository.removeModel(modelRepository.getModelByName("Changes"));
		}

		hawkModel = new EOLQueryEngine();
		hawkModel.setName("Changes");
		hawkModel.load(hawk.getIndexer());
		eolRescoreModule.getContext().getModelRepository().addModel(hawkModel);

		return eolRescoreModule;
	}

	@Override
	protected void initialization(final StandaloneHawk hawk) throws Exception {
		super.initialization(hawk);
		listener = new QueryUpdateListener();
	}

	public static void main(String[] args) {
		Map<String, String> env = System.getenv();
		try {
			new IncrementalUpdateQueryLauncher(env).run();
		} catch (Throwable e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	@Override
	protected String getTool() {
		return "HawkIncUpdateQuery";
	}

}
