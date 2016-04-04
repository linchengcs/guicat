//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package edu.umd.cs.guitar.model;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.GHashcodeGenerator;
import edu.umd.cs.guitar.model.GWindow;
import edu.umd.cs.guitar.model.JFCConstants;
import edu.umd.cs.guitar.model.data.AttributesType;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.GUIType;
import edu.umd.cs.guitar.model.data.PropertyType;
import edu.umd.cs.guitar.model.wrapper.ComponentTypeWrapper;
import edu.umd.cs.guitar.model.wrapper.GUITypeWrapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JFCDefaultHashcodeGenerator extends GHashcodeGenerator {
    static List<String> ID_PROPERTIES;
    Hashtable<List<String>, List<String>> h = new Hashtable();
    private static List<String> SIZE_ID_CLASSES;
    private static List<String> SIZE_ID_PROPERTIES;
    private static List<String> POSITION_ID_CLASSES;
    private static List<String> POSITION_ID_PROPERTIES;
    private static List<String> IGNORED_ID_CLASSES;
    static JFCDefaultHashcodeGenerator instance;

    private JFCDefaultHashcodeGenerator() {
        this.h.put(POSITION_ID_CLASSES, POSITION_ID_PROPERTIES);
        this.h.put(SIZE_ID_CLASSES, SIZE_ID_PROPERTIES);
    }

    public static JFCDefaultHashcodeGenerator getInstance() {
        if(instance == null) {
            instance = new JFCDefaultHashcodeGenerator();
        }

        return instance;
    }

    public long getHashcodeFromData(ComponentType dComponent, GUIType dWindow) {
        ComponentTypeWrapper wComponent = new ComponentTypeWrapper(dComponent);
        String sClass = wComponent.getFirstValueByName("Class");
        if(IGNORED_ID_CLASSES.contains(sClass)) {
            return 0L;
        } else {
            this.preprocessID(dComponent);
            boolean prime = true;
            long result = 1L;
            AttributesType attributes = dComponent.getAttributes();
            if(attributes == null) {
                return result;
            } else {
                List lProperty = attributes.getProperty();
                if(lProperty == null) {
                    return result;
                } else {
                    Iterator sWindowTitle = lProperty.iterator();

                    while(true) {
                        List valueList;
                        do {
                            PropertyType property;
                            do {
                                if(!sWindowTitle.hasNext()) {
                                    String sWindowTitle1 = this.getWindowTitle(dWindow);
                                    result = 31L * result + (long)(sWindowTitle1 == null?0:sWindowTitle1.hashCode());
                                    result = result * 2L & 4294967295L;
                                    return result;
                                }

                                property = (PropertyType)sWindowTitle.next();
                            } while(!ID_PROPERTIES.contains(property.getName()));

                            String name = property.getName();
                            result = 31L * result + (long)(name == null?0:name.hashCode());
                            valueList = property.getValue();
                        } while(valueList == null);

                        String value;
                        for(Iterator i$ = valueList.iterator(); i$.hasNext(); result = 31L * result + (long)(value == null?0:value.hashCode())) {
                            value = (String)i$.next();
                        }
                    }
                }
            }
        }
    }

    private String getWindowTitle(GUIType dWindow) {
        GUITypeWrapper wWindow = new GUITypeWrapper(dWindow);
        String sTitle = wWindow.getTitle();
        sTitle = sTitle.replace("*", "");
        return sTitle;
    }

    private void preprocessID(ComponentType dComponent) {
        ComponentTypeWrapper wComponent = new ComponentTypeWrapper(dComponent);
        String sClass = wComponent.getFirstValueByName("Class");
        Iterator i$ = this.h.keySet().iterator();

        while(i$.hasNext()) {
            List lClassList = (List)i$.next();
            if(lClassList.contains(sClass)) {
                ID_PROPERTIES.addAll((Collection)this.h.get(lClassList));
            }
        }

    }

    private boolean isRegMatched(String input, String regExp) {
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public long getHashcodeFromGUI(GComponent gComponent, GWindow gWindow) {
        ComponentType component = gComponent.extractProperties();
        GUIType window = gWindow.extractWindow();
        return this.getHashcodeFromData(component, window);
    }

    static {
        ID_PROPERTIES = new ArrayList(JFCConstants.ID_PROPERTIES);
        SIZE_ID_CLASSES = Arrays.asList(new String[]{"javax.swing.JRootPane", "javax.swing.JPanel", "javax.swing.JTextPane", "javax.swing.JViewport", "javax.swing.JScrollPane$ScrollBar", "javax.swing.table.JTableHeader"});
        SIZE_ID_PROPERTIES = Arrays.asList(new String[]{"height", "width"});
        POSITION_ID_CLASSES = Arrays.asList(new String[]{"javax.swing.JScrollPane$ScrollBar", "javax.swing.JTextPane", "javax.swing.JTextField", "javax.swing.JViewport"});
        POSITION_ID_PROPERTIES = Arrays.asList(new String[]{"X", "Y"});
        IGNORED_ID_CLASSES = Arrays.asList();
        instance = null;
    }
}
