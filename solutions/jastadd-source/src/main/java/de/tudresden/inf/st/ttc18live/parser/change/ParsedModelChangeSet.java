package de.tudresden.inf.st.ttc18live.parser.change;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * TODO: Add description.
 *
 * @author rschoene - Initial contribution
 */
@XmlRootElement(namespace = "http://nmf.codeplex.com/changes", name = "ModelChangeSet")
@XmlAccessorType(XmlAccessType.FIELD)
public class ParsedModelChangeSet {
  @XmlElement(name = "changes")
  public List<ParsedModelChange> changes;
}
