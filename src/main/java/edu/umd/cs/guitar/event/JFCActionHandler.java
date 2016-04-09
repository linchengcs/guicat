
package edu.umd.cs.guitar.event;


import edu.umd.cs.guitar.event.JFCEditableTextHandler;
import edu.umd.cs.guitar.event.JFCEventHandler;
import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.JFCXComponent;
import java.awt.Component;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.accessibility.AccessibleAction;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleSelection;
import javax.accessibility.AccessibleValue;
import javax.swing.SwingUtilities;

public class JFCActionHandler extends JFCEventHandler {
    public JFCActionHandler() {
    }


    public void performImpl(GComponent gComponent, Hashtable<String, List<String>> optionalData) {
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

    @Override
    protected void performImpl(GComponent gComponent, Object parameters, Hashtable<String, List<String>> optionalData) {

        if(gComponent != null) {
            Component component = this.getComponent(gComponent);
            AccessibleContext aContext = component.getAccessibleContext();

            try {

                //support JComboBox, all selectable
                AccessibleSelection aSelection = aContext.getAccessibleSelection();
                if(aSelection != null) {
                    aSelection.clearAccessibleSelection();
                    List<String> lParameter = (List<String>)parameters;

                    for (String i : lParameter) {
                        int index = Integer.parseInt(i);
                        aSelection.addAccessibleSelection(index);
                    }
                }

                //support JCheckBox  all valuable
                AccessibleValue aValue = aContext.getAccessibleValue();
                if (aValue != null) {
                    List<String> lParameter = (List<String>)parameters;

                    for (String i : lParameter) {
                        int index = Integer.parseInt(i);
                        aValue.setCurrentAccessibleValue(Integer.valueOf(index));
                        break; //only the first one
                    }
                }

            } catch (Exception var11) {
                var11.printStackTrace();
            }
        }

        this.performImpl(gComponent, optionalData);
    }

    public boolean isSupportedBy(GComponent gComponent) {
        JFCEditableTextHandler gFilterEvent = new JFCEditableTextHandler();
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
                AccessibleAction event = aContext.getAccessibleAction();
                return event != null;
            }
        }
    }
}