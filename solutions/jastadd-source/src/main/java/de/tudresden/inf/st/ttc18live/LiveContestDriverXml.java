package de.tudresden.inf.st.ttc18live;

import de.tudresden.inf.st.ttc18live.jastadd.model.ModelChangeSet;
import de.tudresden.inf.st.ttc18live.jastadd.model.SocialNetwork;
import de.tudresden.inf.st.ttc18live.parser.ParsedSocialNetwork;
import de.tudresden.inf.st.ttc18live.parser.change.ParsedModelChangeSet;
import de.tudresden.inf.st.ttc18live.translator.XmlToJastaddTranslator;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Driver for the JastAdd solution with custom XML parser.
 *
 * @author rschoene - Initial contribution
 * @author jmey - Implementation of 2nd query
 */
public class LiveContestDriverXml extends AbstractLiveContestDriver {

  public static void main(String[] args) {
    LiveContestDriverXml driver = new LiveContestDriverXml();
//    Path filename = Paths.get(String.format("events-emf-%s-%s.csv", driver.getChangeSet(), driver.getQuery()));
//    driver.enableTracing(filename).mainImpl();
    driver.mainImpl();
  }

  private XmlToJastaddTranslator translator = new XmlToJastaddTranslator();

  @Override
  SocialNetwork LoadImpl() throws Exception {
    JAXBContext jc = JAXBContext.newInstance(ParsedSocialNetwork.class);
    Unmarshaller unmarshaller = jc.createUnmarshaller();
    Path model1Content = Paths.get(getChangePath(), "initial.xmi");
    ParsedSocialNetwork parsedSocialNetwork = (ParsedSocialNetwork) unmarshaller.unmarshal(model1Content.toFile());
//    logger.info("Users: {}, Posts: {}",
//        parsedSocialNetwork.users.size(), parsedSocialNetwork.posts.size());
    return translator.translateSocialNetwork(parsedSocialNetwork);
  }

  @Override
  protected void InitializeSpecial() {
    // empty
  }

  @Override
  ModelChangeSet UpdateImpl(int iteration) throws Exception {
    JAXBContext jc = JAXBContext.newInstance(ParsedModelChangeSet.class);
    Unmarshaller unmarshaller = jc.createUnmarshaller();
    unmarshaller.setEventHandler(new jakarta.xml.bind.helpers.DefaultValidationEventHandler());
    Path modelContent = Paths.get(getChangePath(), String.format("change%02d.xmi", iteration));
//    logger.debug("Parsing {}", modelContent);
    ParsedModelChangeSet parsedModelChangeSet = (ParsedModelChangeSet) unmarshaller.unmarshal(modelContent.toFile());

    return translator.translateModelChangeSet(parsedModelChangeSet);
  }
}
