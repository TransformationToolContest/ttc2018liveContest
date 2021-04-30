package de.tudresden.inf.st.ttc18live.parser.change;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSeeAlso;

/**
 * TODO: Add description.
 *
 * @author rschoene - Initial contribution
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({
    ParsedAssociationCollectionInsertion.class,
    ParsedAssociationPropertyChange.class,
    ParsedAttributionPropertyChange.class,
    ParsedCompositionListInsertion.class
})
public abstract class ParsedElementaryChange extends ParsedModelChange {
  @XmlAttribute(name = "affectedElement")
  public String affectedElement;

  @XmlAttribute(name = "feature")
  public String feature;

}
