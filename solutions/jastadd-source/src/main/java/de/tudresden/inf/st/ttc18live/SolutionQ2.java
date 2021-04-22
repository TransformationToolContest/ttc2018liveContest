package de.tudresden.inf.st.ttc18live;

import de.tudresden.inf.st.ttc18live.jastadd.model.ModelChangeSet;

public class SolutionQ2 extends Solution {

  @Override
  public String Initial() {
    return getSocialNetwork().query(2);
  }

  @Override
  public String Update(ModelChangeSet changes) {
    apply(changes);
    return getSocialNetwork().query(2);
  }

}
