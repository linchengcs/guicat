package guicat;

import guicat.config.GCConfig;
import guicat.config.GCEntry;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by oliver on 24/02/16.
 */
public class SymbolicTable {
    private static SymbolicTable instance = null;
    public LinkedHashMap<String, SymbolicEntry> symbolicTable;


    public static String currentKeyForMakeSymbolicString = "blabla";

    public static SymbolicTable getInstance() {
        if (instance == null)
            instance = new SymbolicTable();
        return instance;
    }

    private SymbolicTable(){
        symbolicTable = new LinkedHashMap<>();
        LinkedHashMap<String, GCEntry> config = GCConfig.getInstance().config;
        for (String key : (Set<String>)config.keySet()) {
            String methodName = config.get(key).methodName;
            if (GCConfig.isEnumWidget(methodName) || GCConfig.isEnumWidget(methodName))
                continue;
            SymbolicEntry symbolicEntry = new SymbolicEntry(key);
            symbolicEntry.makeSymbolic();
            symbolicTable.put(key, symbolicEntry);
        }
        //System.out.println(toString());
    }

    private void writeInitInputsFile() {
        try {
            FileWriter fw = new FileWriter("./inputs1");
            for (Map.Entry<String, SymbolicEntry> entry : symbolicTable.entrySet()) {
                fw.write(entry.getValue().getGcEntry().initString);
                fw.write(System.getProperty( "line.separator" ));
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String findKeyByStringSymbol(int symbol) {
        for (String key : (Set<String>) symbolicTable.keySet()) {
            if (symbolicTable.get(key).stringValueSymbol == symbol)
                return key;
        }
        return null;
    }

    @Override
    public String toString() {
        String s = "++++++ printing  symbolic table ++++++";
        for (String key : (Set<String>) symbolicTable.keySet()) {
            SymbolicEntry se = symbolicTable.get(key);
            s +=  "\n key=" +key + " string=" + se.symbolicString + " stringSymbol=" + se.stringValueSymbol + " intSymbol=" + se.intValueSymbol;
        }
        return s;
    }
}
