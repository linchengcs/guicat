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
    private String path;
    private Map<String, GCEntry> config;

    public GCConfig(String path) {
        this.path = path;
        config = new Hashtable<>();
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
        String delims = "|";
        String[] tokens = value.split(delims);
        GCEntry gcEntry = new GCEntry(key, tokens[0], tokens[1]);
        return gcEntry;
    }


}
