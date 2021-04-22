package de.tudresden.inf.st.ttc18live.parser;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;

/**
 * TODO: Add description.
 *
 * @author rschoene - Initial contribution
 */
@XmlType(namespace = "https://www.transformation-tool-contest.eu/2018/social_media")
@XmlSeeAlso({
    ParsedPost.class,
    ParsedComment.class,
    ParsedUser.class
})
public class ParsedModelElement {
  @XmlAttribute(name = "id")
  public String id;

}
