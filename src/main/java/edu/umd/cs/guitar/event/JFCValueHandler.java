//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package edu.umd.cs.guitar.event;

import edu.umd.cs.guitar.event.JFCActionHandler;
import edu.umd.cs.guitar.event.JFCEventHandler;
import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.JFCXComponent;
import java.awt.Component;
import java.util.Hashtable;
import java.util.List;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleValue;

public class JFCValueHandler extends JFCEventHandler {
    public JFCValueHandler() {
    }

    protected void performImpl(GComponent component, Hashtable<String, List<String>> optionalData) {
    }

    protected void performImpl(GComponent gComponent, Object parameters, Hashtable<String, List<String>> optionalData) {
    }

    public boolean isSupportedBy(GComponent gComponent) {
        JFCActionHandler gFilterEvent = new JFCActionHandler();
        if(gFilterEvent.isSupportedBy(gComponent)) {
            return false;
        } else if(!(gComponent instanceof JFCXComponent)) {
            return false;
        } else {
            JFCXComponent jComponent = (JFCXComponent)gComponent;
            Component component = jComponent.getComponent();
            AccessibleContext aContext = component.getAccessibleContext();
            if(aContext == null) {
                return false;
            } else {
                AccessibleValue event = aContext.getAccessibleValue();
                return event != null;
            }
        }
    }
}
