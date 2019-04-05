package ttc2018;

import Changes.ModelChangeSet;
import SocialNetwork.SocialNetworkRoot;

public abstract class Solution {
	
	public static final int NB_FAMOUS_POSTS = 3;
	
	private SocialNetworkRoot socialNetwork;

    public SocialNetworkRoot getSocialNetwork() {
    	return socialNetwork;
    }
    
    public void setSocialNetwork(SocialNetworkRoot network) {
    	socialNetwork = network;
    }

    public abstract String Initial();

    public abstract String Update(ModelChangeSet changes);
	
}
