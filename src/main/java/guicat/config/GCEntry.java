package guicat.config;

/**
 * Created by oliver on 24/02/16.
 */
public class GCEntry {
    public String accessibleName;
    public String methodName;
    public String symbolicType;
    public String initString;

    public GCEntry(String aName, String mName, String sType, String initString) {
        this.accessibleName = aName;
        this.methodName = mName;
        this.symbolicType = sType;
        this.initString = initString;
    }
}
