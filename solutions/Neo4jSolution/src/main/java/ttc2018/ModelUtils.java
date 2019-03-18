package ttc2018;

import java.io.File;

public class ModelUtils {
    public static final String modelBasePath = "../../models/";

    public static File getResourcePath(int size, String resourceName, ResourceType resourceType) {
        return getResourcePath(modelBasePath + size, resourceName, resourceType);
    }

    public static File getResourcePath(String basePath, String resourceName, ResourceType resourceType) {
        return new File(basePath, resourceName + "." + resourceType.extension);
    }

    static File getChangesetCSVFile(int size, int sequence) {
        String resourceName = String.format("change%1$02d", sequence);

        return getResourcePath(size, resourceName, ResourceType.CSV);
    }

    static File getChangesetCSVFile(String basePath, int sequence) {
        String resourceName = String.format("change%1$02d", sequence);

        return getResourcePath(basePath, resourceName, ResourceType.CSV);
    }
}
