package de.tudresden.inf.st.ttc18live.parser;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Post parsed from XML.
 *
 * @author rschoene - Initial contribution
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "https://www.transformation-tool-contest.eu/2018/social_media", name = "Post")
public class ParsedPost extends ParsedSubmission {
}
