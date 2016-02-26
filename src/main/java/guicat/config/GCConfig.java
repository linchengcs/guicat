package guicat.config;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by oliver on 24/02/16.
 */
public class GCConfig {
    public static String path;
    public LinkedHashMap<String, GCEntry> config;

    private static GCConfig gcConfig = null;

    private GCConfig() {
        //    System.setProperty("guicat.conf", "conf/barad-ticket/guicat.properties");
        this.path = System.getProperty("guicat.conf");
        config = new LinkedHashMap<String, GCEntry>();
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
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                line=line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                System.out.println(line);
                String delims = ",";
                String[] tokens = line.split(delims);
                assert tokens.length == 4 : "guicat file format error.";
                GCEntry gcEntry = new GCEntry(tokens[0], tokens[1].trim(), tokens[2].trim(), tokens[3].trim());
                config.put(tokens[0], gcEntry);
            }
            reader.close();
            is.close();
            writeInitInputsFile();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void writeInitInputsFile() {
        try {
            FileWriter fw = new FileWriter("./inputs1");
            for (Map.Entry<String, GCEntry> entry : config.entrySet()) {
                fw.write(entry.getValue().initString);
                fw.write(System.getProperty( "line.separator" ));
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GCConfig gcc =  GCConfig.getInstance();
        GCEntry gce = gcc.config.get("Name");
        System.out.println(gce.methodName);
    }



}
