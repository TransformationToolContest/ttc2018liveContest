package de.tudresden.inf.st.ttc18live;


import de.tudresden.inf.st.ttc18live.jastadd.model.ModelChangeSet;
import de.tudresden.inf.st.ttc18live.jastadd.model.SocialNetwork;

public abstract class Solution {

  private SocialNetwork socialNetwork;

  public SocialNetwork getSocialNetwork() {
    return socialNetwork;
  }

  public void setSocialNetwork(SocialNetwork network) {
    socialNetwork = network;
  }

  void apply(ModelChangeSet changes) {
    changes.apply();
    socialNetwork.flushTreeCache();
  }

  public abstract String Initial();

  public abstract String Update(ModelChangeSet changes);

}
