package de.tudresden.inf.st.ttc18live.parser.change;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

/**
 * TODO: Add description.
 *
 * @author rschoene - Initial contribution
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "http://nmf.codeplex.com/changes", name = "AttributePropertyChange")
public class ParsedAttributionPropertyChange extends ParsedAttributeChange {
  @XmlAttribute(name = "newValue")
  public String newValue;

}
