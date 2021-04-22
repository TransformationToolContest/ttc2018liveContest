package de.tudresden.inf.st.ttc18live.parser;

import jakarta.xml.bind.annotation.*;
import java.util.List;

/**
 * Submission parsed from XML.
 *
 * @author rschoene - Initial contribution
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "https://www.transformation-tool-contest.eu/2018/social_media")
public class ParsedSubmission extends ParsedModelElement {

  @XmlAttribute(name = "content")
  public String content;

  @XmlAttribute(name = "timestamp")
  public String timestamp;

  @XmlAttribute(name = "submitter")
  public String submitter;

  @XmlElement(name = "comments")
  public List<ParsedComment> comments;
}
