//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package edu.umd.cs.guitar.model;

import edu.umd.cs.guitar.model.GIDGenerator;
import edu.umd.cs.guitar.model.JFCConstants;
import edu.umd.cs.guitar.model.data.AttributesType;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.ContainerType;
import edu.umd.cs.guitar.model.data.GUIStructure;
import edu.umd.cs.guitar.model.data.GUIType;
import edu.umd.cs.guitar.model.data.ObjectFactory;
import edu.umd.cs.guitar.model.data.PropertyType;
import edu.umd.cs.guitar.model.wrapper.AttributesTypeWrapper;
import edu.umd.cs.guitar.model.wrapper.ComponentTypeWrapper;
import edu.umd.cs.guitar.model.wrapper.GUITypeWrapper;
import edu.umd.cs.guitar.util.AppUtil;
import edu.umd.cs.guitar.util.GUITARLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class JFCDefaultIDGeneratorSimple implements GIDGenerator {
    static JFCDefaultIDGeneratorSimple instance = null;
    static ObjectFactory factory = new ObjectFactory();
    static final int prime = 31;
    static List<String> ID_PROPERTIES;
    static List<String> IGNORED_CLASSES;
    static List<String> IS_ADD_INDEX_CLASSES;

    public static JFCDefaultIDGeneratorSimple getInstance() {
        if(instance == null) {
            instance = new JFCDefaultIDGeneratorSimple();
        }

        return instance;
    }

    private JFCDefaultIDGeneratorSimple() {
    }

    public void generateID(GUIStructure gs) {
        Iterator i$ = gs.getGUI().iterator();

        while(i$.hasNext()) {
            GUIType gui = (GUIType)i$.next();
            this.generateGUIID(gui);
        }

    }

    private void generateGUIID(GUIType gui) {
        ContainerType container = gui.getContainer();
        if(container != null) {
            long windowHashCode = this.getWindowHashCode(gui);
            List subComponentList = container.getContents().getWidgetOrContainer();
            if(subComponentList != null) {
                Iterator i$ = subComponentList.iterator();

                while(true) {
                    ComponentType subComponent;
                    long hashcode;
                    do {
                        if(!i$.hasNext()) {
                            return;
                        }

                        subComponent = (ComponentType)i$.next();
                        AttributesType attributes = subComponent.getAttributes();
                        hashcode = windowHashCode;
                        if(attributes != null) {
                            String subContainer = "w" + windowHashCode;
                            ArrayList i$1 = new ArrayList();
                            Iterator component = attributes.getProperty().iterator();

                            while(component.hasNext()) {
                                PropertyType p = (PropertyType)component.next();
                                if(!"ID".equals(p.getName())) {
                                    i$1.add(p);
                                }

                                if("Title".equals(p.getName())) {
                                    GUITARLog.log.debug("generateGUIID: [" + subContainer + "] " + p.getValue() + "windowHashCode " + windowHashCode);
                                }
                            }

                            PropertyType component1 = factory.createPropertyType();
                            component1.setName("ID");
                            component1.getValue().add(subContainer);
                            i$1.add(0, component1);
                            attributes.setProperty(i$1);
                        }
                    } while(!(subComponent instanceof ContainerType));

                    ContainerType subContainer1 = (ContainerType)subComponent;
                    Iterator i$2 = subContainer1.getContents().getWidgetOrContainer().iterator();

                    while(i$2.hasNext()) {
                        ComponentType component2 = (ComponentType)i$2.next();
                        this.generateComponentID(component2, hashcode);
                    }
                }
            }
        }
    }

    private long getWindowHashCode(GUIType gui) {
        GUITypeWrapper wGUI = new GUITypeWrapper(gui);
        String title = wGUI.getTitle();
        AppUtil appUtil = new AppUtil();
        System.out.println("SSS " + title);
        String fuzzyTitle = appUtil.findRegexForString(title);
        long hashcode = (long)fuzzyTitle.hashCode();
        hashcode = hashcode * 2L & 4294967295L;
        return hashcode;
    }

    private void generateComponentID(ComponentType component, long windoHashCode) {
        AttributesType attributes = component.getAttributes();
        long hashcode = 1L;
        if(attributes != null) {
            long container = this.getLocalHashcode(component);
            hashcode = windoHashCode * 31L + container;
            hashcode = hashcode * 2L & 4294967295L;
            String sID = "w" + hashcode;
            ArrayList i$ = new ArrayList();
            Iterator child = attributes.getProperty().iterator();

            while(child.hasNext()) {
                PropertyType wChild = (PropertyType)child.next();
                if(!"ID".equals(wChild.getName())) {
                    i$.add(wChild);
                }

                if("Title".equals(wChild.getName())) {
                    GUITARLog.log.debug("generateComponentID [" + sID + "] " + wChild.getValue() + "windoHashCode " + windoHashCode + "localHashCode " + container);
                }
            }

            PropertyType child1 = factory.createPropertyType();
            child1.setName("ID");
            child1.getValue().add(sID);
            i$.add(0, child1);
            attributes.setProperty(i$);
        }

        if(component instanceof ContainerType) {
            ContainerType container1 = (ContainerType)component;
            List children = container1.getContents().getWidgetOrContainer();
            Iterator i$1 = children.iterator();

            while(i$1.hasNext()) {
                ComponentType child2 = (ComponentType)i$1.next();
                ComponentTypeWrapper wChild1 = new ComponentTypeWrapper(child2);
                String sClass = wChild1.getFirstValueByName("Class");
                Integer index = Integer.valueOf(children.indexOf(child2));
                long propagatedHashCode = 31L * windoHashCode + (long)index.hashCode();
                this.generateComponentID(child2, propagatedHashCode);
            }
        }

    }

    private boolean hasUniqueChildren(ComponentType component) {
        if(!(component instanceof ContainerType)) {
            return true;
        } else {
            ArrayList examinedHashCode = new ArrayList();
            ContainerType container = (ContainerType)component;
            Iterator i$ = container.getContents().getWidgetOrContainer().iterator();

            while(i$.hasNext()) {
                ComponentType child = (ComponentType)i$.next();
                long hashcode = this.getLocalHashcode(child);
                if(examinedHashCode.contains(Long.valueOf(hashcode))) {
                    return false;
                }

                examinedHashCode.add(Long.valueOf(hashcode));
            }

            return true;
        }
    }

    private long getLocalHashcode(ComponentType component) {
        boolean prime = true;
        long hashcode = 1L;
        AttributesType attributes = component.getAttributes();
        if(attributes == null) {
            return hashcode;
        } else {
            AttributesTypeWrapper wAttribute = new AttributesTypeWrapper(attributes);
            String sClass = wAttribute.getFirstValByName("Class");
            if(IGNORED_CLASSES.contains(sClass)) {
                hashcode = 31L * hashcode + (long)(sClass == null?0:sClass.hashCode());
                return hashcode;
            } else {
                List lProperty = attributes.getProperty();
                if(lProperty == null) {
                    return hashcode;
                } else {
                    Iterator i$ = lProperty.iterator();

                    while(true) {
                        List valueList;
                        do {
                            PropertyType property;
                            String name;
                            do {
                                if(!i$.hasNext()) {
                                    hashcode = hashcode * 2L & 4294967295L;
                                    return hashcode;
                                }

                                property = (PropertyType)i$.next();
                                name = property.getName();
                            } while(!ID_PROPERTIES.contains(name));

                            hashcode = 31L * hashcode + (long)(name == null?0:name.hashCode());
                            valueList = property.getValue();
                        } while(valueList == null);

                        String value;
                        for(Iterator i$1 = valueList.iterator(); i$1.hasNext(); hashcode = 31L * hashcode + (long)(value == null?0:value.hashCode())) {
                            value = (String)i$1.next();
                        }
                    }
                }
            }
        }
    }

    static {
        ID_PROPERTIES = new ArrayList(JFCConstants.ID_PROPERTIES);
        IGNORED_CLASSES = Arrays.asList(new String[]{"javax.swing.JPanel", "javax.swing.JTabbedPane", "javax.swing.JScrollPane", "javax.swing.JSplitPane", "javax.swing.Box", "javax.swing.JViewport", "javax.swing.JScrollBar", "javax.swing.JLayeredPane", "javax.swing.JList$AccessibleJList$AccessibleJListChild", "javax.swing.JList$AccessibleJList", "javax.swing.JList", "javax.swing.JScrollPane$ScrollBar", "javax.swing.plaf.metal.MetalScrollButton"});
        IS_ADD_INDEX_CLASSES = Arrays.asList(new String[]{"javax.swing.JTabbedPane$Page"});
    }
}
