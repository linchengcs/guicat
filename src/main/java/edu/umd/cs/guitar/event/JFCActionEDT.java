//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package edu.umd.cs.guitar.event;

import edu.umd.cs.guitar.event.GEvent;
import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.JFCXComponent;
import java.awt.Component;
import java.util.Hashtable;
import java.util.List;
import javax.accessibility.AccessibleAction;
import javax.accessibility.AccessibleContext;
import javax.swing.SwingUtilities;

public class JFCActionEDT implements GEvent {
    protected Component getComponent(GComponent gComponent) {
        JFCXComponent jxComponent = (JFCXComponent)gComponent;
        return jxComponent.getComponent();
    }

    public JFCActionEDT() {
    }

    public void perform(GComponent gComponent, Hashtable<String, List<String>> optionalData) {
        if(gComponent != null) {
            Component component = this.getComponent(gComponent);
            AccessibleContext aContext = component.getAccessibleContext();
            if(aContext != null) {
                final AccessibleAction aAction = aContext.getAccessibleAction();
                if(aAction != null) {
                    int nActions = aAction.getAccessibleActionCount();
                    if(nActions > 0) {
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                aAction.doAccessibleAction(0);
                            }
                        });
                    }

                }
            }
        }
    }

    public void perform(GComponent gComponent, List<String> parameters, Hashtable<String, List<String>> optionalData) {
        this.perform(gComponent, optionalData);
    }

    public boolean isSupportedBy(GComponent gComponent) {
        return false;
    }
}
