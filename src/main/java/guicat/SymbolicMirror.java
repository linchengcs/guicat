package guicat;

import guicat.config.GCConfig;
import guicat.config.GCEntry;

import javax.swing.*;
import java.util.Map;
import java.util.logging.Logger;

public class SymbolicMirror {
    private static Logger log = Logger.getLogger("SymbolicMirror");

    public static String sgetText(Object o) {
        if (o instanceof JTextField) {
            JTextField jTextField = (JTextField) o;
            String key = jTextField.getAccessibleContext().getAccessibleName();
            if (getConfig().get(key) != null) {
       //         log.info("instrumented getText of :" + key);
                return getText(key);
            } else {
      //          log.info("skipped getText of: " + key);
                return jTextField.getText();
            }
        }
        return "";

    }


    public static String getText(String key) {
        SymbolicEntry se = getSymbolicEntryByKey(key);
        if (se != null)
            return se.symbolicString;
        return null;
    }

    public static SymbolicEntry getSymbolicEntryByKey(String key) {
        if (key != null) {
            return getSymbolicTable().get(key);
        }
        return null;
    }

    public static Map<String, GCEntry> getConfig() {
        return GCConfig.getInstance().config;
    }

    public static Map<String, SymbolicEntry> getSymbolicTable() {
        return SymbolicTable.getInstance().symbolicTable;
    }

    public static void main(String[] args) {
        assert SymbolicTable.getInstance() != null : "0000000000";
        String key = "Enter your name:";
        System.out.println("key = " + key + " value " + SymbolicTable.getInstance().symbolicTable.get(key).symbolicString);
        System.out.println("what" +SymbolicTable.currentKeyForMakeSymbolicString);

        System.out.println(SymbolicTable.getInstance().toString());
    }


}
