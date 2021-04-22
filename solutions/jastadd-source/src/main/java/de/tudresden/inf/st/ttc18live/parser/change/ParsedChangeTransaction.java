package de.tudresden.inf.st.ttc18live.parser.change;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * TODO: Add description.
 *
 * @author rschoene - Initial contribution
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "http://nmf.codeplex.com/changes", name = "ChangeTransaction")
public class ParsedChangeTransaction extends ParsedModelChange {
  @XmlElement(name = "sourceChange")
  public ParsedModelChange sourceChange;

  @XmlElement(name = "nestedChanges")
  public List<ParsedModelChange> nestedChanges;
}
