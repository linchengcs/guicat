package guicat;

import guicat.config.GCConfig;

import java.util.Hashtable;
import java.util.Set;

/**
 * Created by oliver on 24/02/16.
 */
public class SymbolicTable {
    private static SymbolicTable instance = null;
    public Hashtable<String, SymbolicEntry> symbolicTable;


    public static String currentKeyForMakeSymbolicString = "blabla";

    public static SymbolicTable getInstance() {
        if (instance == null)
            instance = new SymbolicTable();
        return instance;
    }

    private SymbolicTable(){
        symbolicTable = new Hashtable<>();
        Hashtable config = GCConfig.getInstance().config;
        for (String key : (Set<String>)config.keySet()) {
            SymbolicEntry symbolicEntry = new SymbolicEntry(key);
            symbolicEntry.makeSymbolic();
            symbolicTable.put(key, symbolicEntry);
        }

        System.out.println(toString());
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
