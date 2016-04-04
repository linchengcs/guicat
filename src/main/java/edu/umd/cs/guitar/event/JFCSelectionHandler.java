//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package edu.umd.cs.guitar.event;

import edu.umd.cs.guitar.event.JFCActionHandler;
import edu.umd.cs.guitar.event.JFCEventHandler;
import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.JFCXComponent;
import edu.umd.cs.guitar.util.GUITARLog;
import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.List;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleSelection;

public class JFCSelectionHandler extends JFCEventHandler {
    Integer selectedIndex;

    public JFCSelectionHandler() {
    }

    protected void performImpl(GComponent component, Hashtable<String, List<String>> optionalData) {
    }

    protected void performImpl(GComponent gComponent, Object parameters, Hashtable<String, List<String>> optionalData) {
        if(parameters instanceof List) {
            List component = (List)parameters;
            if(component == null) {
                this.selectedIndex = Integer.valueOf(0);
            } else {
                String aContext = component.size() != 0?(String)component.get(0):"0";

                try {
                    this.selectedIndex = Integer.valueOf(Integer.parseInt(aContext));
                } catch (Exception var14) {
                    this.selectedIndex = Integer.valueOf(0);
                }
            }
        }

        if(gComponent != null) {
            Component component1 = this.getComponent(gComponent);
            AccessibleContext aContext1 = component1.getAccessibleContext();
            if(aContext1 != null) {
                AccessibleSelection aSelection = aContext1.getAccessibleSelection();
                if(aSelection != null) {
                    try {
                        Method selectionMethod = gComponent.getClass().getMethod("setSelectedIndex", new Class[]{Component.class});
                        selectionMethod.invoke(component1, new Object[]{this.selectedIndex});
                    } catch (SecurityException var9) {
                        GUITARLog.log.error(var9);
                    } catch (NoSuchMethodException var10) {
                        GUITARLog.log.error(var10);
                    } catch (IllegalArgumentException var11) {
                        GUITARLog.log.error(var11);
                    } catch (IllegalAccessException var12) {
                        GUITARLog.log.error(var12);
                    } catch (InvocationTargetException var13) {
                        GUITARLog.log.error(var13);
                    }

                }
            }
        }
    }

    public boolean isSupportedBy(GComponent gComponent) {
        if(!(gComponent instanceof JFCXComponent)) {
            return false;
        } else {
            JFCActionHandler gFilterEvent = new JFCActionHandler();
            if(gFilterEvent.isSupportedBy(gComponent)) {
                return false;
            } else {
                JFCXComponent jComponent = (JFCXComponent)gComponent;
                Component component = jComponent.getComponent();
                AccessibleContext aContext = component.getAccessibleContext();
                if(aContext == null) {
                    return false;
                } else {
                    AccessibleSelection event = aContext.getAccessibleSelection();
                    return event != null;
                }
            }
        }
    }
}
