package de.tudresden.inf.st.ttc18live;

import de.tudresden.inf.st.ttc18live.jastadd.model.List;
import de.tudresden.inf.st.ttc18live.jastadd.model.ModelChange;
import de.tudresden.inf.st.ttc18live.jastadd.model.ModelChangeSet;

public class SolutionQ1 extends Solution {

  @Override
  public String Initial() {
    return this.getSocialNetwork().query(1);
  }

  @Override
  public String Update(ModelChangeSet changes) {
    apply(changes);
    return this.getSocialNetwork().query(1);
  }

}
