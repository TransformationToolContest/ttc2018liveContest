package ttc2018;

import SocialNetwork.SocialNetworkRoot
import yamtl.core.YAMTLModule

public abstract class Solution {
	
	private SocialNetworkRoot socialNetwork;
	protected YAMTLModule xform;
	
    def public SocialNetworkRoot getSocialNetwork() {
    	return socialNetwork;
    }
    
    def public void setSocialNetwork(SocialNetworkRoot network) {
    	socialNetwork = network;
    }

    def public abstract String Initial();

    def public abstract String Update(String deltaName);
	
}
