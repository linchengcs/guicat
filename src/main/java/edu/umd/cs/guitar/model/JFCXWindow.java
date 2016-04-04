//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package edu.umd.cs.guitar.model;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.GWindow;
import edu.umd.cs.guitar.model.JFCConstants;
import edu.umd.cs.guitar.model.JFCXComponent;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.ContainerType;
import edu.umd.cs.guitar.model.data.ContentsType;
import edu.umd.cs.guitar.model.data.GUIType;
import edu.umd.cs.guitar.model.data.ObjectFactory;
import edu.umd.cs.guitar.model.data.PropertyType;
import edu.umd.cs.guitar.model.wrapper.ComponentTypeWrapper;
import java.awt.Window;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleState;
import javax.accessibility.AccessibleStateSet;

public class JFCXWindow extends GWindow {
    Window window;
    static List<String> INVALID_WINDOW_TITLE = Arrays.asList(new String[]{"", "javax.swing.Popup$HeavyWeightWindow"});

    public Window getWindow() {
        return this.window;
    }

    public JFCXWindow(Window window) {
        this.window = window;
    }

    public GUIType extractGUIProperties() {
        ObjectFactory factory = new ObjectFactory();
        GUIType retGUI = factory.createGUIType();
        AccessibleContext wContext = this.window.getAccessibleContext();
        ComponentType dWindow = factory.createComponentType();
        ComponentTypeWrapper gaWindow = new ComponentTypeWrapper(dWindow);
        dWindow = gaWindow.getDComponentType();
        gaWindow.addValueByName("Size", wContext.getAccessibleComponent().getSize().toString());
        retGUI.setWindow(dWindow);
        ContainerType dContainer = factory.createContainerType();
        ComponentTypeWrapper gaContainer = new ComponentTypeWrapper(dContainer);
        gaContainer.addValueByName("Size", wContext.getAccessibleComponent().getSize().toString());
        ComponentType dContainer1 = gaContainer.getDComponentType();
        ContentsType dContents = factory.createContentsType();
        ((ContainerType)dContainer1).setContents(dContents);
        retGUI.setContainer((ContainerType)dContainer1);
        return retGUI;
    }

    public GComponent getContainer() {
        return new JFCXComponent(this.window, this);
    }

    public boolean isModal() {
        Boolean isModal = null;

        try {
            Class[] context = new Class[0];
            Method states = this.window.getClass().getMethod("isModal", context);
            if(states != null) {
                Object obj = states.invoke(this.window, new Object[0]);
                if(obj != null) {
                    isModal = (Boolean)obj;
                }
            }
        } catch (SecurityException var5) {
            ;
        } catch (NoSuchMethodException var6) {
            ;
        } catch (IllegalArgumentException var7) {
            ;
        } catch (IllegalAccessException var8) {
            ;
        } catch (InvocationTargetException var9) {
            ;
        }

        if(isModal != null) {
            return isModal.booleanValue();
        } else {
            AccessibleContext context1 = this.window.getAccessibleContext();
            if(context1 == null) {
                return false;
            } else {
                AccessibleStateSet states1 = context1.getAccessibleStateSet();
                return states1.contains(AccessibleState.MODAL);
            }
        }
    }

    public String getTitle() {
        String sName = null;
        AccessibleContext aContext = this.window.getAccessibleContext();
        if(aContext != null) {
            sName = aContext.getAccessibleName();
            if(sName != null) {
                return sName;
            }
        }

        sName = this.window.getClass().getName();
        return sName;
    }

    public List<PropertyType> getGUIProperties() {
        return this.getGUIBeanProperties();
    }

    private List<PropertyType> getGUIBeanProperties() {
        ArrayList retList = new ArrayList();
        Method[] methods = this.window.getClass().getMethods();
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
                if(JFCConstants.WINDOW_PROPERTIES_LIST.contains(sPropertyName)) {
                    try {
                        Object value = m.invoke(this.window, new Object[0]);
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

    public int hashCode() {
        boolean prime = true;
        byte result = 1;
        int result1 = 31 * result + (this.window == null?0:this.getTitle().hashCode());
        return result1;
    }

    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        } else if(obj == null) {
            return false;
        } else if(this.getClass() != obj.getClass()) {
            return false;
        } else {
            JFCXWindow other = (JFCXWindow)obj;
            if(this.window == null) {
                if(other.window != null) {
                    return false;
                }
            } else {
                String myID = this.getTitle();
                String otherID = other.getTitle();
                if(!myID.equals(otherID)) {
                    return false;
                }
            }

            return true;
        }
    }

    public boolean isValid() {
        if(!this.window.isVisible()) {
            return false;
        } else {
            String title = this.getTitle();
            return title == null?false:!INVALID_WINDOW_TITLE.contains(title);
        }
    }

    public int getX() {
        return this.window.getX();
    }

    public int getY() {
        return this.window.getY();
    }
}
