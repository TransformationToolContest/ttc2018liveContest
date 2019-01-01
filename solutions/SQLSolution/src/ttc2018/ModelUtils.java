package ttc2018;

import Changes.ChangesPackage;
import Changes.ModelChangeSet;
import SocialNetwork.SocialNetworkPackage;
import SocialNetwork.SocialNetworkRoot;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import java.io.File;
import java.io.IOException;

public class ModelUtils {
    public static final String modelBasePath = "../../models/";
    // for initialization see: ensureRepositoryInit
    protected static ResourceSet repository;

    public static File getResourcePath(int size, String resourceName, ResourceType resourceType) {
        return new File(modelBasePath + size, resourceName + "." + resourceType.extension);
    }

    static Resource getXMIResource(int size, String resourceName) throws IOException {
        ensureRepositoryInit();
        return repository.getResource(URI.createFileURI(ModelUtils.getResourcePath(size, resourceName, ResourceType.XMI).getCanonicalPath()), true);
    }

    static SocialNetworkRoot loadSocialNetworkFile(int size) throws IOException {
        String resourceName = "initial";

        Resource resource = getXMIResource(size, resourceName);
        return (SocialNetworkRoot) resource.getContents().get(0);
    }

    static ModelChangeSet loadChangeSetFile(int size, int sequence) throws IOException {
        String resourceName = String.format("change%1$02d", sequence);

        Resource resource = getXMIResource(size, resourceName);
        return (ModelChangeSet) resource.getContents().get(0);
    }

    protected static void ensureRepositoryInit() {
        if (repository == null) {
            repository = new ResourceSetImpl();
            repository.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
            repository.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
            repository.getPackageRegistry().put(SocialNetworkPackage.eINSTANCE.getNsURI(), SocialNetworkPackage.eINSTANCE);
            repository.getPackageRegistry().put(ChangesPackage.eINSTANCE.getNsURI(), ChangesPackage.eINSTANCE);
        }
    }

}
