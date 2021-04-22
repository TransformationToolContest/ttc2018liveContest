package de.tudresden.inf.st.ttc18live.parser.change;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSeeAlso;

/**
 * TODO: Add description.
 *
 * @author rschoene - Initial contribution
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({
    ParsedElementaryChange.class,
    ParsedAssociationCollectionInsertion.class,
    ParsedAssociationPropertyChange.class,
    ParsedAttributionPropertyChange.class,
    ParsedCompositionListInsertion.class,
    ParsedChangeTransaction.class
})
public class ParsedModelChange {
  public ParsedModelChange() {
    // empty for now
  }
}
