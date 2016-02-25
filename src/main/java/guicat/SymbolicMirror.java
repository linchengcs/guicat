package guicat;

import catg.CATG;
import janala.interpreters.*;
import org.apache.log4j.Logger;
import java.util.*;
import java.io.*;
import javax.swing.*;
import java.util.Hashtable;
import guicat.config.*;

public class SymbolicMirror {
    private GCConfig gccConfig;
    private SymbolicTable symbolicTable;
    private static SymbolicMirror instance = null;


    public SymbolicMirror()  {
        try {
            gccConfig = GCConfig.getInstance();
            symbolicTable = SymbolicTable.getInstance();

            iv = symbolicTable.symbolicTable.get("Enter your age:").intValue;
            itmp = symbolicTable.symbolicTable.get("Enter your age:").intValueSymbol;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String sym = null;
    public static  SymbolicMirror symbolicMirror = new SymbolicMirror();
    private Properties conf;
    public Hashtable<String, Object> symbolicVariables = new Hashtable<String, Object>();
    public static String stmp = CATG.readString("");
    public  int itmp = 0;
    public  IntValue iv = null;

    public static String sgetText(Object o) {
        if (o instanceof JTextField) {
            JTextField jTextField = (JTextField) o;
            String key = jTextField.getAccessibleContext().getAccessibleName();
            if (symbolicMirror.gccConfig.config.get(key) != null)
                return symbolicMirror.getText(key);
            else
                return jTextField.getText();
        }
        return "";

    }


    public static String getText(String key) {
      // return (String)symbolicMirror.symbolicVariables.get(key);
        return SymbolicTable.getInstance().symbolicTable.get(key).symbolicString;
    }

    public static SymbolicEntry getSymbolicEntryByKey(String key) {
        if (key != null) {
            return getInstance().symbolicTable.symbolicTable.get(key);
        }
        return null;
    }


    public static SymbolicMirror getInstance() {
        if (instance == null)
            instance = new SymbolicMirror();
        return instance;
    }

    public static void main(String[] args) {
        assert SymbolicTable.getInstance() != null : "0000000000";
        String key = "Enter your name:";
        System.out.println("key = " + key + " value " + SymbolicTable.getInstance().symbolicTable.get(key).symbolicString);
        System.out.println("what" +SymbolicTable.currentKeyForMakeSymbolicString);

        System.out.println(SymbolicTable.getInstance().toString());
    }

/*
    public SymbolicMirror() {
        String conffile = "./conf/ticket/symagent/ticket.properties";
        try {
            FileInputStream is = new FileInputStream(conffile);
            conf = new Properties();
            conf.load(is);
            is.close();
            String myconf = conf.getProperty("sym");
            System.out.println(conf.toString());
            System.out.println(conf.getProperty("Enter your name:"));
            System.out.println(conf.getProperty("Enter your age:"));
            for(String key : conf.stringPropertyNames()){
                symbolicVariables.put((String)key, CATG.readString("123"));
            }
            Object s = symbolicVariables.get("Enter your age:");
            iv = new IntValue(Integer.parseInt((String)s));
            itmp = iv.MAKE_SYMBOLIC(null);

        }
        catch(IOException ioe) {
            for(StackTraceElement ste : ioe.getStackTrace())
                ;
        }
    }
*/
        /*
    public static int sparseInt(StringValue s) {
        logger.info("call sparseInt by: " + s);
        if (o instanceof JTextField) {
            JTextField jTextField = (JTextField) o;
            String key = jTextField.getAccessibleContext().getAccessibleName();
            if (symbolicMirror.conf.getProperty(key) != null)
                return symbolicMirror.getText(key);
            else
                return Integer.parseInt(jTextField.getText());
        }
        return 0;
    }
    */

}
