package de.tudresden.inf.st.ttc18live.parser.change;

import de.tudresden.inf.st.ttc18live.parser.ParsedModelElement;

import jakarta.xml.bind.annotation.*;

/**
 * TODO: Add description.
 *
 * @author rschoene - Initial contribution
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "http://nmf.codeplex.com/changes", name = "CompositionListInsertion")
public class ParsedCompositionListInsertion extends ParsedCompositionChange {
  @XmlElement(name = "addedElement")
  public ParsedModelElement addedElement;

  @XmlAttribute(name = "index")
  public Integer index = 0;
}
