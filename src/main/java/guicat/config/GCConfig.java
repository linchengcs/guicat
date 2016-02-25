package guicat.config;

import catg.CATG;
import janala.interpreters.IntValue;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

/**
 * Created by oliver on 24/02/16.
 */
public class GCConfig {
    public static String path;
    public Hashtable<String, GCEntry> config;

    private static GCConfig gcConfig = null;

    private GCConfig() {
        System.setProperty("guicat.conf", "conf/barad-ticket/guicat.properties");
        this.path = System.getProperty("guicat.conf");
        System.out.println(this.path);
        config = new Hashtable<String, GCEntry>();
        loadConfig();
    }

    public static GCConfig getInstance() {
        if (gcConfig == null)
            gcConfig = new GCConfig();
        return gcConfig;
    }

    private void loadConfig() {
        try {
            FileInputStream is = new FileInputStream(path);
            Properties conf = new Properties();
            conf.load(is);
            is.close();
            for(String key : conf.stringPropertyNames()){
               GCEntry gcEntry = convertEntry(key, (String)conf.get(key));
               config.put(key, gcEntry);
            }
        }
        catch(IOException ioe) {
            for(StackTraceElement ste : ioe.getStackTrace())
                ;
        }
    }

    private GCEntry convertEntry(String key, String value) {
        String delims = ",";
        String[] tokens = value.split(delims);
        assert tokens.length == 3 : "guicat file format error.";
        System.out.println(tokens[2]);
        GCEntry gcEntry = new GCEntry(key, tokens[0].trim(), tokens[1].trim(), tokens[2].trim());
        return gcEntry;
    }

    public static void main(String[] args) {
        //String s= "/home/oliver/workspace/java/project/catgui2/conf/barad-ticket/guicat.properties";
        GCConfig gcc =  GCConfig.getInstance();
        GCEntry gce = gcc.config.get("Name");
        System.out.println(gce.methodName);
    }



}
