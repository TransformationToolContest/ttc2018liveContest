package de.tudresden.inf.st.ttc18live.parser;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Root node SocialNetwork parsed from XML.
 *
 * @author rschoene - Initial contribution
 */
@XmlRootElement(namespace = "https://www.transformation-tool-contest.eu/2018/social_media", name = "SocialNetworkRoot")
@XmlAccessorType(XmlAccessType.FIELD)
public class ParsedSocialNetwork {
  @XmlElement(name = "users")
  public List<ParsedUser> users;

  @XmlElement(name = "posts")
  public List<ParsedPost> posts;
}
