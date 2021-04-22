package de.tudresden.inf.st.ttc18live;

import de.tudresden.inf.st.ttc18live.jastadd.model.ModelChangeSet;

import de.tudresden.inf.st.ttc18live.jastadd.model.SocialNetwork;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import Changes.ChangesPackage;
import SocialNetwork.SocialNetworkPackage;
import SocialNetwork.SocialNetworkRoot;
import org.eclipse.emf.ecore.xmi.impl.XMLParserPoolImpl;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Driver for the JastAdd solution with EMF parser.
 *
 * @author rschoene - Initial contribution
 * @author jmey - Implementation of 2nd query
 */
public class LiveContestDriverEMF extends AbstractLiveContestDriver {

  public static void main(String[] args) throws IOException {
    LiveContestDriverEMF driver = new LiveContestDriverEMF();
//    Path filename = Paths.get(String.format("events-emf-%s-%s.csv", driver.getChangeSet(), driver.getQuery()));
//    driver.enableTracing(filename).mainImpl();
    driver.mainImpl();
  }

  private ResourceSet repository;
  private Translator translator;

  private Object loadFile(String path) throws IOException {
    repository.getURIConverter().getURIMap().put(URI.createFileURI(path), URI.createFileURI(getChangePath() + "/" + path));
    Resource mRes = repository.createResource(URI.createFileURI(getChangePath() + "/" + path));
    System.err.println(mRes);
    Map<String, Object> loadOptions = new HashMap<>();
    loadOptions.put(XMIResource.OPTION_DEFER_IDREF_RESOLUTION, true);
    loadOptions.put(XMIResource.OPTION_USE_PARSER_POOL, new XMLParserPoolImpl());
    loadOptions.put(XMIResource.OPTION_USE_XML_NAME_TO_FEATURE_MAP, new HashMap<>());

    mRes.load(loadOptions);
    return mRes.getContents().get(0);
  }

  @Override
  SocialNetwork LoadImpl() throws Exception {
    translator = new Translator((SocialNetworkRoot) loadFile("initial.xmi"));
    return translator.getSocialNetwork();
  }

  @Override
  protected void InitializeSpecial() {
    repository = new ResourceSetImpl();
    repository.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new IntrinsicIDXMIResourceFactoryImpl());
    repository.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
    repository.getPackageRegistry().put(SocialNetworkPackage.eINSTANCE.getNsURI(), SocialNetworkPackage.eINSTANCE);
    repository.getPackageRegistry().put(ChangesPackage.eINSTANCE.getNsURI(), ChangesPackage.eINSTANCE);
  }

  @Override
  ModelChangeSet UpdateImpl(int iteration) throws Exception {
    Changes.ModelChangeSet emfChanges = (Changes.ModelChangeSet) loadFile(String.format("change%02d.xmi", iteration));
    return translator.translateChangeSet(emfChanges);
  }
}
