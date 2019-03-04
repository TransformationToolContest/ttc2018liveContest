package ttc2018;

public enum ResourceType {
    XMI("xmi"), CSV("csv");

    public final String extension;
    ResourceType(String extension) {
        this.extension = extension;
    }
}
