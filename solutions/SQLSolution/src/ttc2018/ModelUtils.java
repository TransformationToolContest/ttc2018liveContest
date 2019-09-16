package ttc2018;

import Changes.ChangesPackage;
import Changes.ModelChangeSet;
import SocialNetwork.SocialNetworkPackage;
import SocialNetwork.SocialNetworkRoot;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ModelUtils {
    public static final String modelBasePath = "../../models/";
    // for initialization see: ensureRepositoryInit
    protected static ResourceSet repository;

    public static File getResourcePath(int size, String resourceName, ResourceType resourceType) {
        return getResourcePath(modelBasePath + size, resourceName, resourceType);
    }

    public static File getResourcePath(String basePath, String resourceName, ResourceType resourceType) {
        return new File(basePath, resourceName + "." + resourceType.extension);
    }

    static Resource getXMIResource(int size, String resourceName) throws IOException {
        ensureRepositoryInit();
        return repository.getResource(URI.createFileURI(ModelUtils.getResourcePath(size, resourceName, ResourceType.XMI).getCanonicalPath()), true);
    }

    static Resource getXMIResource(String basePath, String resourceName) throws IOException {
        ensureRepositoryInit();
        return repository.getResource(URI.createFileURI(ModelUtils.getResourcePath(basePath, resourceName, ResourceType.XMI).getCanonicalPath()), true);
    }

    static SocialNetworkRoot loadSocialNetworkFile(int size) throws IOException {
        String resourceName = "initial";

        Resource resource = getXMIResource(size, resourceName);
        return (SocialNetworkRoot) resource.getContents().get(0);
    }

    static SocialNetworkRoot loadSocialNetworkFile(String basePath) throws IOException {
        String resourceName = "initial";

        Resource resource = getXMIResource(basePath, resourceName);
        return (SocialNetworkRoot) resource.getContents().get(0);
    }

    static ModelChangeSet loadChangeSetFile(int size, int sequence) throws IOException {
        String resourceName = String.format("change%1$02d", sequence);

        Resource resource = getXMIResource(size, resourceName);
        return (ModelChangeSet) resource.getContents().get(0);
    }

    static ModelChangeSet loadChangeSetFile(String basePath, int sequence) throws IOException {
        String resourceName = String.format("change%1$02d", sequence);

        Resource resource = getXMIResource(basePath, resourceName);
        return (ModelChangeSet) resource.getContents().get(0);
    }

    static File getChangesetCSVFile(int size, int sequence) {
        String resourceName = String.format("change%1$02d", sequence);

        return getResourcePath(size, resourceName, ResourceType.CSV);
    }

    static File getChangesetCSVFile(String basePath, int sequence) {
        String resourceName = String.format("change%1$02d", sequence);

        return getResourcePath(basePath, resourceName, ResourceType.CSV);
    }

    protected static void ensureRepositoryInit() {
        if (repository == null) {
            repository = new ResourceSetImpl();
            repository.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new Resource.Factory() {
                @Override
                public Resource createResource(URI uri) {
                    XMIResourceImpl ret = new XMIResourceImpl(uri);
                    ret.setIntrinsicIDToEObjectMap(new HashMap<>());
                    ret.getDefaultLoadOptions().put(XMIResource.OPTION_DEFER_IDREF_RESOLUTION, true);
                    ret.getDefaultLoadOptions().put(XMIResource.OPTION_USE_PARSER_POOL, new XMLParserPoolImpl());
                    ret.getDefaultLoadOptions().put(XMIResource.OPTION_USE_XML_NAME_TO_FEATURE_MAP, new HashMap<>());
                    return ret;
                }
            });
            repository.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
            repository.getPackageRegistry().put(SocialNetworkPackage.eINSTANCE.getNsURI(), SocialNetworkPackage.eINSTANCE);
            repository.getPackageRegistry().put(ChangesPackage.eINSTANCE.getNsURI(), ChangesPackage.eINSTANCE);
        }
    }

}
