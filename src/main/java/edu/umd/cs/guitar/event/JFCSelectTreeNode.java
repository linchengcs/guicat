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
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import javax.swing.JTree;
import javax.swing.text.Position.Bias;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class JFCSelectTreeNode extends JFCEventHandler {
    public JFCSelectTreeNode() {
    }

    protected void performImpl(GComponent gComponent, Hashtable<String, List<String>> optionalData) {
        if(!(gComponent instanceof JFCXComponent)) {
            GUITARLog.log.debug("JFCXComponent! ");
        } else {
            JFCXComponent jComponent = (JFCXComponent)gComponent;
            Component component = jComponent.getComponent();
            if(!(component instanceof JTree)) {
                GUITARLog.log.debug("NOT JTree! ");
            } else {
                JTree tree = (JTree)component;
                List nodes = (List)optionalData.get("Title");
                if(nodes == null) {
                    GUITARLog.log.debug("No option! ");
                } else if(nodes.size() < 1) {
                    GUITARLog.log.debug("Selecting....");
                } else {
                    String node = (String)nodes.get(0);
                    this.expandAll(tree, true);
                    TreePath path = tree.getNextMatch(node, 0, Bias.Forward);
                    tree.setSelectionPath(path);
                }
            }
        }
    }

    protected void performImpl(GComponent gComponent, Object parameters, Hashtable<String, List<String>> optionalData) {
        this.performImpl(gComponent, optionalData);
    }

    public boolean isSupportedBy(GComponent gComponent) {
        return false;
    }

    private void expandAll(JTree tree, boolean expand) {
        TreeNode root = (TreeNode)tree.getModel().getRoot();
        this.expandAllHelper(tree, new TreePath(root), expand);
    }

    private void expandAllHelper(JTree tree, TreePath parent, boolean expand) {
        TreeNode node = (TreeNode)parent.getLastPathComponent();
        if(node.getChildCount() >= 0) {
            Enumeration e = node.children();

            while(e.hasMoreElements()) {
                TreeNode n = (TreeNode)e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                this.expandAllHelper(tree, path, expand);
            }
        }

        if(expand) {
            tree.expandPath(parent);
        } else {
            tree.collapsePath(parent);
        }

    }
}
