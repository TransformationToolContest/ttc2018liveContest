package ttc2018;

import SocialNetwork.SocialNetworkRoot
import yamtl.core.YAMTLModule

abstract class Solution {
	
	SocialNetworkRoot socialNetwork;
	protected YAMTLModule xform;
	
    def SocialNetworkRoot getSocialNetwork() {
    	return socialNetwork;
    }
    
    def void setSocialNetwork(SocialNetworkRoot network) {
    	socialNetwork = network;
    }

    def abstract String Initial();

    def abstract String Update(String deltaName);
	
}
