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
import edu.umd.cs.guitar.model.data.ContainerType;
import edu.umd.cs.guitar.model.data.GUIType;
import edu.umd.cs.guitar.model.data.PropertyType;
import edu.umd.cs.guitar.model.wrapper.ComponentTypeWrapper;
import edu.umd.cs.guitar.model.wrapper.GUITypeWrapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class JFCContextHashcodeGenerator extends GHashcodeGenerator {
    static List<String> ID_PROPERTIES;
    Hashtable<List<String>, List<String>> h = new Hashtable();
    private static List<String> SIZE_ID_CLASSES;
    private static List<String> SIZE_ID_PROPERTIES;
    private static List<String> POSSITION_ID_CLASSES;
    private static List<String> IGNORED_ID_CLASSES;
    private static List<String> POSSITION_ID_PROPERTIES;
    static JFCContextHashcodeGenerator instance;
    LinkedList<ComponentType> path = new LinkedList();

    public static JFCContextHashcodeGenerator getInstance() {
        if(instance == null) {
            instance = new JFCContextHashcodeGenerator();
        }

        return instance;
    }

    public JFCContextHashcodeGenerator() {
        this.h.put(POSSITION_ID_CLASSES, POSSITION_ID_PROPERTIES);
        this.h.put(SIZE_ID_CLASSES, SIZE_ID_PROPERTIES);
    }

    public long getHashcodeFromData(ComponentType component, GUIType dWindow) {
        ComponentTypeWrapper wComponent = new ComponentTypeWrapper(component);
        String sClass = wComponent.getFirstValueByName("Class");
        if(IGNORED_ID_CLASSES.contains(sClass)) {
            return 0L;
        } else {
            ContainerType root = dWindow.getContainer();
            boolean prime = true;
            long result = 1L;
            result = 31L * result + this.getWindowHashcode(dWindow);
            result = 31L * result + this.getComponnentTreeHashcode(component, root);
            result = result * 2L & 4294967295L;
            return result;
        }
    }

    long getComponnentTreeHashcode(ComponentType component, ComponentType root) {
        ComponentTypeWrapper wRoot = new ComponentTypeWrapper(root);
        wRoot.parseData();
        ComponentTypeWrapper target = this.findNode(component, wRoot);
        System.out.println("Component:" + target.getFirstValueByName("Title"));
        long result = 1L;
        long prime = 31L;

        ArrayList path;
        for(path = new ArrayList(); !wRoot.equals(target); target = target.getParent()) {
            result = result * prime + this.getComponnentHashcode(target.getDComponentType());
            path.add(target);
        }

        path.add(wRoot);
        Iterator i$ = path.iterator();

        while(i$.hasNext()) {
            ComponentTypeWrapper comp = (ComponentTypeWrapper)i$.next();
            System.out.print(comp.getFirstValueByName("Title") + ", ");
        }

        System.out.println();
        result = result * prime + this.getComponnentHashcode(wRoot.getDComponentType());
        return result;
    }

    ComponentTypeWrapper findNode(ComponentType component, ComponentTypeWrapper wRoot) {
        Object result = null;
        if(component.equals(wRoot.getDComponentType())) {
            return wRoot;
        } else {
            List lChildren = wRoot.getChildren();
            if(lChildren == null) {
                return (ComponentTypeWrapper)result;
            } else {
                Iterator i$ = wRoot.getChildren().iterator();

                ComponentTypeWrapper find;
                do {
                    if(!i$.hasNext()) {
                        return (ComponentTypeWrapper)result;
                    }

                    ComponentTypeWrapper child = (ComponentTypeWrapper)i$.next();
                    find = this.findNode(component, child);
                } while(find == null);

                return find;
            }
        }
    }

    long getComponnentHashcode(ComponentType component) {
        ComponentTypeWrapper wComponent = new ComponentTypeWrapper(component);
        String sClass = wComponent.getFirstValueByName("Class");
        if(IGNORED_ID_CLASSES.contains(sClass)) {
            return 0L;
        } else {
            this.preprocessID(component);
            boolean prime = true;
            long result = 1L;
            AttributesType attributes = component.getAttributes();
            if(attributes == null) {
                return result;
            } else {
                List lProperty = attributes.getProperty();
                if(lProperty == null) {
                    return result;
                } else {
                    Iterator i$ = lProperty.iterator();

                    while(true) {
                        List valueList;
                        do {
                            PropertyType property;
                            do {
                                if(!i$.hasNext()) {
                                    result = result * 2L & 4294967295L;
                                    return result;
                                }

                                property = (PropertyType)i$.next();
                            } while(!ID_PROPERTIES.contains(property.getName()));

                            String name = property.getName();
                            result = 31L * result + (long)(name == null?0:name.hashCode());
                            valueList = property.getValue();
                        } while(valueList == null);

                        String value;
                        for(Iterator i$1 = valueList.iterator(); i$1.hasNext(); result = 31L * result + (long)(value == null?0:value.hashCode())) {
                            value = (String)i$1.next();
                        }
                    }
                }
            }
        }
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

    long getWindowHashcode(GUIType dWindow) {
        GUITypeWrapper wWindow = new GUITypeWrapper(dWindow);
        return (long)wWindow.getTitle().hashCode();
    }

    public long getHashcodeFromGUI(GComponent gComponent, GWindow gWindow) {
        return 0L;
    }

    static {
        ID_PROPERTIES = new ArrayList(JFCConstants.ID_PROPERTIES);
        SIZE_ID_CLASSES = Arrays.asList(new String[]{"javax.swing.JRootPane", "javax.swing.JPanel", "javax.swing.JTextPane", "javax.swing.JViewport", "javax.swing.JScrollPane$ScrollBar", "javax.swing.table.JTableHeader"});
        SIZE_ID_PROPERTIES = Arrays.asList(new String[]{"height", "width"});
        POSSITION_ID_CLASSES = Arrays.asList(new String[]{"javax.swing.JScrollPane$ScrollBar", "javax.swing.JTextPane", "javax.swing.JTextField", "javax.swing.JViewport"});
        IGNORED_ID_CLASSES = Arrays.asList(new String[]{"javax.swing.plaf.metal.MetalScrollButton", "javax.swing.JScrollPane$ScrollBar", "javax.swing.JSpinner$NumberEditor", "javax.swing.plaf.basic.BasicComboPopup$1", "javax.swing.plaf.basic.BasicComboPopup"});
        POSSITION_ID_PROPERTIES = Arrays.asList(new String[]{"x", "y"});
        instance = null;
    }
}
