package de.tudresden.inf.st.ttc18live.parser;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Comment parsed from XML.
 *
 * @author rschoene - Initial contribution
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "https://www.transformation-tool-contest.eu/2018/social_media", name = "Comment")
public class ParsedComment extends ParsedSubmission {
}
