package ttc2018;

import java.util.Map;

import org.apache.log4j.Level;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.viatra.query.runtime.api.ViatraQueryEngine;
import org.eclipse.viatra.query.runtime.api.ViatraQueryEngineOptions;
import org.eclipse.viatra.query.runtime.emf.EMFScope;
import org.eclipse.viatra.query.runtime.matchers.backend.QueryEvaluationHint;
import org.eclipse.viatra.query.runtime.matchers.backend.QueryHintOption;
import org.eclipse.viatra.query.runtime.rete.matcher.ReteBackendFactory;
import org.eclipse.viatra.query.runtime.rete.util.ReteHintOptions;
import org.eclipse.viatra.query.runtime.util.ViatraQueryLoggingUtil;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import Changes.ModelChangeSet;
import SocialNetwork.SocialNetworkRoot;

public abstract class Solution {
	
	protected SocialNetworkRoot socialNetwork;
	protected ResourceSet resourceSet;
	protected EMFScope scope;
	protected ViatraQueryEngine engine;
	
    public SocialNetworkRoot getSocialNetwork() {
    	return socialNetwork;
    }
    
    public ResourceSet getResourceSet() {
		return resourceSet;
	}
    
    public void setSocialNetwork(SocialNetworkRoot network, ResourceSet resourceSet) {
    	socialNetwork = network;
    	this.resourceSet = resourceSet;
    	scope = new EMFScope(getResourceSet());

    	ViatraQueryLoggingUtil.getDefaultLogger().setLevel(Level.OFF);

		Map<QueryHintOption<?>, Object> optionMap = ImmutableMap.of(ReteHintOptions.deleteRederiveEvaluation, true);
		QueryEvaluationHint hint = new QueryEvaluationHint(optionMap, ReteBackendFactory.INSTANCE);
		ViatraQueryEngineOptions options = ViatraQueryEngineOptions.defineOptions().withDefaultHint(hint).build();
    	engine = ViatraQueryEngine.on(scope, options);
    }

    public abstract String Initial();

    public abstract String Update(ModelChangeSet changes);
	
}
