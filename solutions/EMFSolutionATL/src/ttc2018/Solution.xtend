package ttc2018;

import Changes.ModelChangeSet;
import SocialNetwork.SocialNetworkRoot;

abstract class Solution {
	
    var SocialNetworkRoot socialNetwork;

    def SocialNetworkRoot getSocialNetwork() {
    	return socialNetwork;
    }
    
    def void setSocialNetwork(SocialNetworkRoot network) {
    	socialNetwork = network;
    }

    def abstract String Initial();

    def abstract String Update(ModelChangeSet changes);
	
}
