//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package edu.umd.cs.guitar.model;

import edu.umd.cs.guitar.event.EventManager;
import edu.umd.cs.guitar.event.GEvent;
import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.GWindow;
import edu.umd.cs.guitar.model.JFCConstants;
import edu.umd.cs.guitar.model.data.PropertyType;
import edu.umd.cs.guitar.model.wrapper.AttributesTypeWrapper;
import edu.umd.cs.guitar.util.GUITARLog;
import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.accessibility.Accessible;
import javax.accessibility.AccessibleAction;
import javax.accessibility.AccessibleContext;
import javax.swing.JTabbedPane;

public class JFCXComponent extends GComponent {
    Component component;
    private static List<String> IGNORED_CLASS_EVENTS = Arrays.asList(new String[]{"com.jgoodies.looks.plastic.PlasticArrowButton", "com.jgoodies.looks.plastic.PlasticComboBoxButton", "com.jgoodies.looks.plastic.PlasticSpinnerUI$SpinnerArrowButton", "javax.swing.plaf.synth.SynthScrollBarUI$1", "javax.swing.plaf.synth.SynthScrollBarUI$2", "javax.swing.plaf.synth.SynthArrowButton", "javax.swing.plaf.basic.BasicComboPopup$1", "javax.swing.JScrollPane$ScrollBar", "javax.swing.plaf.metal.MetalScrollButton", "javax.swing.plaf.metal.MetalComboBoxButton", "sun.awt.X11.XFileDialogPeer$2", "javax.swing.JScrollPane$ScrollBar", "sun.swing.FilePane$1", "sun.swing.FilePane$2", "sun.swing.FilePane$3", "sun.swing.FilePane$4", "sun.swing.FilePane$5"});

    public JFCXComponent(Component component, GWindow window) {
        super(window);
        this.component = component;
    }

    public List<PropertyType> getGUIProperties() {
        ArrayList retList = new ArrayList();
        String sValue = null;
        sValue = this.getTitle();
        PropertyType p;
        ArrayList lPropertyValue;
        if(sValue != null) {
            p = factory.createPropertyType();
            p.setName("Title");
            lPropertyValue = new ArrayList();
            lPropertyValue.add(sValue);
            p.setValue(lPropertyValue);
            retList.add(p);
        }

        sValue = null;
        sValue = this.getIconName();
        if(sValue != null) {
            p = factory.createPropertyType();
            p.setName("Icon");
            lPropertyValue = new ArrayList();
            lPropertyValue.add(sValue);
            p.setValue(lPropertyValue);
            retList.add(p);
        }

        if(this.isSelectedByParent()) {
            sValue = null;
            sValue = this.getIndexInParent().toString();
            p = factory.createPropertyType();
            p.setName("Index");
            lPropertyValue = new ArrayList();
            lPropertyValue.add(sValue);
            p.setValue(lPropertyValue);
            retList.add(p);
        }

        List actionListener = this.getActionListenerClasses();
        if(actionListener != null) {
            p = factory.createPropertyType();
            p.setName("ActionListeners");
            p.setValue(actionListener);
            retList.add(p);
        }

        List lBeanProperties = this.getGUIBeanProperties();
        retList.addAll(lBeanProperties);
        return retList;
    }

    private Integer getIndexInParent() {
        AccessibleContext aContext = this.component.getAccessibleContext();
        return aContext != null?Integer.valueOf(aContext.getAccessibleIndexInParent()):Integer.valueOf(0);
    }

    private boolean isSelectedByParent() {
        Container parent = this.component.getParent();
        return parent == null?false:parent instanceof JTabbedPane;
    }

    private List<PropertyType> getGUIBeanProperties() {
        ArrayList retList = new ArrayList();
        Method[] methods = this.component.getClass().getMethods();
        Method[] arr$ = methods;
        int len$ = methods.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            Method m = arr$[i$];
            if(m.getParameterTypes().length <= 0) {
                String sMethodName = m.getName();
                String sPropertyName;
                if(sMethodName.startsWith("get")) {
                    sPropertyName = sMethodName.substring(3);
                } else {
                    if(!sMethodName.startsWith("is")) {
                        continue;
                    }

                    sPropertyName = sMethodName.substring(2);
                }

                sPropertyName = sPropertyName.toLowerCase();
                if(JFCConstants.GUI_PROPERTIES_LIST.contains(sPropertyName)) {
                    try {
                        Object value = m.invoke(this.component, new Object[0]);
                        if(value != null) {
                            PropertyType p = factory.createPropertyType();
                            ArrayList lPropertyValue = new ArrayList();
                            lPropertyValue.add(value.toString());
                            p.setName(sPropertyName);
                            p.setValue(lPropertyValue);
                            retList.add(p);
                        }
                    } catch (IllegalArgumentException var13) {
                        ;
                    } catch (IllegalAccessException var14) {
                        ;
                    } catch (InvocationTargetException var15) {
                        ;
                    }
                }
            }
        }

        return retList;
    }

    public List<GComponent> getChildren() {
        ArrayList retList = new ArrayList();
        if(this.component instanceof Container) {
            Container container = (Container)this.component;

            try {
                boolean e = false;
                int var10 = container.getComponentCount();
                if(var10 > 0) {
                    for(int aContext = 0; aContext < var10; ++aContext) {
                        Component i = container.getComponent(aContext);
                        JFCXComponent aChild = new JFCXComponent(i, this.window);
                        retList.add(aChild);
                    }
                } else {
                    AccessibleContext var11 = container.getAccessibleContext();
                    if(var11 == null) {
                        return retList;
                    }

                    var10 = var11.getAccessibleChildrenCount();

                    for(int var12 = 0; var12 < var10; ++var12) {
                        Accessible var13 = var11.getAccessibleChild(var12);
                        if(var13 instanceof Component) {
                            Component cChild = (Component)var13;
                            JFCXComponent gChild = new JFCXComponent(cChild, this.window);
                            retList.add(gChild);
                        }
                    }
                }
            } catch (Exception var9) {
                GUITARLog.log.error("getChildren");
                GUITARLog.log.error(var9);
            }
        }

        return retList;
    }

    public GComponent getParent() {
        Container parent = this.component.getParent();
        return new JFCXComponent(parent, this.window);
    }

    public boolean hasChildren() {
        AccessibleContext xContext = this.component.getAccessibleContext();
        if(xContext == null) {
            return false;
        } else {
            int nChildren = xContext.getAccessibleChildrenCount();
            if(nChildren > 0) {
                return true;
            } else {
                if(this.component instanceof Container) {
                    Container container = (Container)this.component;
                    if(container.getComponentCount() > 0) {
                        return true;
                    }
                }

                return false;
            }
        }
    }

    public Component getComponent() {
        return this.component;
    }

    public int hashCode() {
        boolean prime = true;
        int result = 1;
        List lProperties = this.getGUIProperties();
        Iterator i$ = lProperties.iterator();

        while(true) {
            List valueList;
            do {
                PropertyType property;
                do {
                    if(!i$.hasNext()) {
                        result = 31 * result + "Class".hashCode();
                        result = 31 * result + this.getClassVal().hashCode();
                        return result;
                    }

                    property = (PropertyType)i$.next();
                } while(!JFCConstants.ID_PROPERTIES.contains(property.getName()));

                String name = property.getName();
                result = 31 * result + (name == null?0:name.hashCode());
                valueList = property.getValue();
            } while(valueList == null);

            String value;
            for(Iterator i$1 = valueList.iterator(); i$1.hasNext(); result = 31 * result + (value == null?0:value.hashCode())) {
                value = (String)i$1.next();
            }
        }
    }

    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        } else if(obj == null) {
            return false;
        } else if(this.getClass() != obj.getClass()) {
            return false;
        } else {
            JFCXComponent other = (JFCXComponent)obj;
            if(this.component == null) {
                if(other.component != null) {
                    return false;
                }
            } else if(!this.component.equals(other.component)) {
                return false;
            }

            return true;
        }
    }

    public String getTitle() {
        String sName = "";
        if(this.component == null) {
            return "";
        } else {
            AccessibleContext aContext = this.component.getAccessibleContext();
            if(aContext == null) {
                return "";
            } else {
                sName = aContext.getAccessibleName();
                if(sName != null) {
                    return sName;
                } else {
                    if(sName == null) {
                        sName = this.getIconName();
                    }

                    if(sName != null) {
                        return sName;
                    } else {
                        sName = this.component.getName();
                        if(sName == null) {
                            sName = "Pos(" + this.getX() + "," + this.getY() + ")";
                        }

                        return sName;
                    }
                }
            }
        }
    }

    private String getIconName() {
        String retIcon = null;

        try {
            Class[] e = new Class[0];
            Method m = this.component.getClass().getMethod("getIcon", e);
            String sIconPath = null;
            if(m != null) {
                Object sIconElements = m.invoke(this.component, new Object[0]);
                if(sIconElements != null) {
                    sIconPath = sIconElements.toString();
                }
            }

            if(sIconPath != null && !sIconPath.contains("@")) {
                String[] sIconElements1 = sIconPath.split(File.separator);
                retIcon = sIconElements1[sIconElements1.length - 1];
                return retIcon;
            } else {
                return null;
            }
        } catch (SecurityException var6) {
            return null;
        } catch (NoSuchMethodException var7) {
            return null;
        } catch (IllegalArgumentException var8) {
            return null;
        } catch (IllegalAccessException var9) {
            return null;
        } catch (InvocationTargetException var10) {
            return null;
        }
    }

    private List<String> getActionListenerClasses() {
        ArrayList ret = null;
        Class c = this.component.getClass();

        try {
            Method e = c.getMethod("getActionListeners", new Class[0]);
            if(e.getReturnType() == ActionListener[].class) {
                ActionListener[] listeners = (ActionListener[])((ActionListener[])e.invoke(this.component, new Object[0]));
                if(listeners != null && listeners.length > 0) {
                    if(listeners.length == 1) {
                        ret = new ArrayList();
                        ret.add(listeners[0].getClass().getName());
                    } else {
                        HashSet tmp = new HashSet();
                        ActionListener[] arr$ = listeners;
                        int len$ = listeners.length;

                        for(int i$ = 0; i$ < len$; ++i$) {
                            ActionListener al = arr$[i$];
                            tmp.add(al.getClass().getName());
                        }

                        ret = new ArrayList(tmp);
                    }
                }
            }
        } catch (SecurityException var10) {
            ;
        } catch (NoSuchMethodException var11) {
            ;
        } catch (IllegalArgumentException var12) {
            ;
        } catch (IllegalAccessException var13) {
            ;
        } catch (InvocationTargetException var14) {
            ;
        }

        return ret;
    }

    public List<GEvent> getEventList() {
        ArrayList retEvents = new ArrayList();
        String sClass = this.getClassVal();
        if(IGNORED_CLASS_EVENTS.contains(sClass)) {
            return retEvents;
        } else {
            EventManager em = EventManager.getInstance();
            Iterator i$ = em.getEvents().iterator();

            while(i$.hasNext()) {
                GEvent gEvent = (GEvent)i$.next();
                if(gEvent.isSupportedBy(this)) {
                    retEvents.add(gEvent);
                }
            }

            return retEvents;
        }
    }

    public String getClassVal() {
        return this.component.getClass().getName();
    }

    public String getTypeVal() {
        String retProperty;
        if(this.isTerminal()) {
            retProperty = "TERMINAL";
        } else {
            retProperty = "SYSTEM INTERACTION";
        }

        return retProperty;
    }

    public boolean isTerminal() {
        AccessibleContext aContext = this.component.getAccessibleContext();
        if(aContext == null) {
            return false;
        } else {
            AccessibleAction aAction = aContext.getAccessibleAction();
            if(aAction == null) {
                return false;
            } else {
                String sName = this.getTitle();
                List termSig = JFCConstants.sTerminalWidgetSignature;
                Iterator i$ = termSig.iterator();

                String titleVals;
                do {
                    if(!i$.hasNext()) {
                        return false;
                    }

                    AttributesTypeWrapper sign = (AttributesTypeWrapper)i$.next();
                    titleVals = sign.getFirstValByName("Title");
                } while(titleVals == null || !titleVals.equalsIgnoreCase(sName));

                return true;
            }
        }
    }

    public boolean isEnable() {
        try {
            Class[] e = new Class[0];
            Method method = this.component.getClass().getMethod("isEnabled", e);
            Object result = method.invoke(this.component, new Object[0]);
            return result instanceof Boolean?((Boolean)result).booleanValue():false;
        } catch (Exception var4) {
            return false;
        }
    }

    public boolean isActivatedByParent() {
        if(this.component instanceof Component) {
            Container parent = this.component.getParent();
            if(parent instanceof JTabbedPane) {
                return true;
            }
        }

        return false;
    }

    public int getX() {
        Object pointer = this.component;
        if(pointer != null && !(pointer instanceof Window)) {
            int x = 0;

            while(!(pointer instanceof Window)) {
                x += ((Component)pointer).getX();
                pointer = ((Component)pointer).getParent();
                if(pointer == null) {
                    break;
                }
            }

            return x;
        } else {
            return 0;
        }
    }

    public int getY() {
        Object pointer = this.component;
        if(pointer != null && !(pointer instanceof Window)) {
            int y = 0;

            while(!(pointer instanceof Window)) {
                y += ((Component)pointer).getY();
                pointer = ((Component)pointer).getParent();
                if(pointer == null) {
                    break;
                }
            }

            return y;
        } else {
            return 0;
        }
    }
}
