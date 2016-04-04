//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package edu.umd.cs.guitar.event;

import edu.umd.cs.guitar.event.GThreadEvent;
import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.JFCXComponent;
import java.awt.Component;

public abstract class JFCEventHandler extends GThreadEvent {
    public JFCEventHandler() {
    }

    protected Component getComponent(GComponent gComponent) {
        JFCXComponent jxComponent = (JFCXComponent)gComponent;
        return jxComponent.getComponent();
    }
}
