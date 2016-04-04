//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package edu.umd.cs.guitar.event;

import edu.umd.cs.guitar.event.JFCEventHandler;
import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.JFCXComponent;
import edu.umd.cs.guitar.util.GUITARLog;
import java.awt.Component;
import java.awt.Container;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.List;

public class JFCSelectFromParent extends JFCEventHandler {
    public JFCSelectFromParent() {
    }

    protected void performImpl(GComponent gComponent, Hashtable<String, List<String>> optionalData) {
        Component cChild = ((JFCXComponent)gComponent).getComponent();
        Component aParent = this.getSelectableParent(cChild);
        if(aParent != null) {
            try {
                Method selectionMethod = aParent.getClass().getMethod("setSelectedComponent", new Class[]{Component.class});
                selectionMethod.invoke(aParent, new Object[]{cChild});
            } catch (SecurityException var7) {
                GUITARLog.log.error(var7);
            } catch (NoSuchMethodException var8) {
                GUITARLog.log.error(var8);
            } catch (IllegalArgumentException var9) {
                GUITARLog.log.error(var9);
            } catch (IllegalAccessException var10) {
                GUITARLog.log.error(var10);
            } catch (InvocationTargetException var11) {
                GUITARLog.log.error(var11);
            }
        }

    }

    protected void performImpl(GComponent gComponent, Object parameters, Hashtable<String, List<String>> optionalData) {
        Integer index = Integer.valueOf(0);

        try {
            List component = (List)parameters;
            if(component == null) {
                index = Integer.valueOf(0);
            } else {
                index = Integer.valueOf(component.size() != 0?Integer.parseInt((String)component.get(0)):0);
            }
        } catch (Exception var14) {
            var14.printStackTrace();
        }

        Component component1 = ((JFCXComponent)gComponent).getComponent();
        Component parent = this.getSelectableParent(component1);
        GUITARLog.log.debug("!!!!!Parent:" + parent);
        if(parent != null) {
            System.out.println("GOT HERE");

            try {
                Method selectionMethod = parent.getClass().getMethod("setSelectedIndex", new Class[]{Integer.TYPE});
                selectionMethod.invoke(parent, new Object[]{index});
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

    private Component getSelectableParent(Component component) {
        if(component == null) {
            return null;
        } else {
            Container parent = component.getParent();
            Method[] methods = parent.getClass().getMethods();
            Method[] arr$ = methods;
            int len$ = methods.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                Method m = arr$[i$];
                if("setSelectedComponent".equals(m.getName())) {
                    return parent;
                }
            }

            return this.getSelectableParent(parent);
        }
    }

    public boolean isSupportedBy(GComponent gComponent) {
        return false;
    }
}
