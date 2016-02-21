package instrument;

import catg.CATG;
import org.apache.log4j.Logger;
import java.util.*;
import java.io.*;
import javax.swing.*;
import java.util.Map;
import java.util.Hashtable;

public class SymbolicMirror {
    private static Logger logger = Logger.getLogger(SymbolicMirror.class);
    private static String sym = null;
    public static  SymbolicMirror symbolicMirror = new SymbolicMirror();
    private Properties conf;
    public Hashtable<String, Object> symbolicVariables = new Hashtable<String, Object>();
    public static String stmp = CATG.readString("");
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
            //    symbolicVariables.put("parseInt", CATG.readInt(0));
            logger.info("read auto sym properties, create sym variables");
        }
        catch(IOException ioe) {
            for(StackTraceElement ste : ioe.getStackTrace())
                ;
        }
    }

    public static String sgetText(Object o) {
        logger.info("call get sgetText by:  " + o.toString());
        if (o instanceof JTextField) {
            JTextField jTextField = (JTextField) o;
            String key = jTextField.getAccessibleContext().getAccessibleName();
            if (symbolicMirror.conf.getProperty(key) != null)
                return symbolicMirror.getText(key);
            else
                return jTextField.getText();
        }
        return "";

    }
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
    public static String getText(String key) {
        return (String)symbolicMirror.symbolicVariables.get(key);
    }

    public static String getSymbolicMirror() {
        if (sym == null)
            sym = CATG.readString("1");
        return sym;
    }



}
