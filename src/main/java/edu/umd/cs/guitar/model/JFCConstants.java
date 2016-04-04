//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package edu.umd.cs.guitar.model;

import edu.umd.cs.guitar.event.JFCActionHandler;
import edu.umd.cs.guitar.event.JFCEditableTextHandler;
import edu.umd.cs.guitar.event.JFCEventHandler;
import edu.umd.cs.guitar.event.JFCSelectFromParent;
import edu.umd.cs.guitar.event.JFCSelectionHandler;
import edu.umd.cs.guitar.model.wrapper.AttributesTypeWrapper;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class JFCConstants {
    public static String RESOURCE_DIR = "resources";
    public static String CONFIG_DIR;
    public static String TERMINAL_WIDGET_FILE;
    public static List<AttributesTypeWrapper> sTerminalWidgetSignature;
    public static List<String> sIgnoredWins;
    static List<String> GUI_PROPERTIES_LIST;
    static List<String> WINDOW_PROPERTIES_LIST;
    public static List<String> ID_PROPERTIES;
    public static List<Class<? extends JFCEventHandler>> DEFAULT_SUPPORTED_EVENTS;
    public static final String TITLE_TAG = "Title";
    public static final String ICON_TAG = "Icon";
    public static final String INDEX_TAG = "Index";
    public static final String LOG4J_PROPERTIES_FILE = "log4j.properties";
    /** @deprecated */
    @Deprecated
    public static int JFC_REPLAYER_TIMEOUT;

    public JFCConstants() {
    }

    static {
        CONFIG_DIR = RESOURCE_DIR + File.separator + "config";
        TERMINAL_WIDGET_FILE = "terminal_widget.ign";
        sTerminalWidgetSignature = new LinkedList();
        sIgnoredWins = new ArrayList();
        GUI_PROPERTIES_LIST = Arrays.asList(new String[]{"opaque", "height", "width", "foreground", "background", "visible", "tooltip", "font", "accelerator", "enabled", "editable", "focusable", "selected", "text"});
        WINDOW_PROPERTIES_LIST = Arrays.asList(new String[]{"layout", "x", "y", "height", "width", "opaque", "visible", "alwaysOnTop", "defaultLookAndFeelDecorated", "font", "foreground", "insets", "resizable", "background", "colorModel", "iconImage", "locale"});
        ID_PROPERTIES = Arrays.asList(new String[]{"Title", "Class", "Icon", "Index"});
        DEFAULT_SUPPORTED_EVENTS = Arrays.asList(new Class[]{JFCActionHandler.class, JFCEditableTextHandler.class, JFCSelectFromParent.class, JFCSelectionHandler.class});
        JFC_REPLAYER_TIMEOUT = 20000;
    }
}
