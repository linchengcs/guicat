package examples.radioButton;
/*
 * Copyright (c) 1995 - 2008 Sun Microsystems, Inc.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Sun Microsystems nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import catg.CATG;

public class Project extends JFrame implements ActionListener {

    public JRadioButton w1, w2, w3, w4;
    public JTextField w5;
    public JButton w6, w7;
    public JMenuBar menuBar;
    public JMenu options, shape, color;
    public JMenuItem about;
    public JRadioButtonMenuItem circle, none, square, black, blue, cyan,
        darkgray, gray, green, lightgray, magenta, orange, pink, red,
        white, yellow;
    private JPanel contentPane;
    private Border loweredetched = BorderFactory
        .createEtchedBorder(EtchedBorder.LOWERED);

    public static final int CIRCLE = 0;
    public static final int SQUARE = 1;
    public int currentShape = CIRCLE;
    public boolean shapeDrawn = false;
    public Color customColor;
    public JPanel drawEmpty = new JPanel();
    public JPanel drawShape = new JPanel();
    public W1Listener w1Listener = new W1Listener();
    public W2Listener w2Listener = new W2Listener();
    public W3Listener w3Listener = new W3Listener();
    public W4Listener w4Listener = new W4Listener();
    public W5Listener w5Listener = new W5Listener();
    public W6Listener w6Listener = new W6Listener();
    public W7Listener w7Listener = new W7Listener();

    public Project() {
        currentShape = CATG.readInt(0);
        shapeDrawn = CATG.readBool(true);
        System.out.println(shapeDrawn ? "yes" : "no");

        // Create the radio buttons.
        w1 = new JRadioButton("Circle");
        w1.setSelected(true);
        w1.setToolTipText("Change shape to Circle.");

        w2 = new JRadioButton("Square");
        w2.setToolTipText("Change shape to Square.");

        // Group the radio buttons.
        ButtonGroup selectShape = new ButtonGroup();
        selectShape.add(w1);
        selectShape.add(w2);

        // Register a listener for the radio buttons.
        w1.addActionListener(w1Listener);
        w2.addActionListener(w2Listener);

        // Create the radio buttons.
        w3 = new JRadioButton("Color");
        w3
            .setToolTipText("Choose a custom color instead of the default color.");

        w4 = new JRadioButton("None");
        w4.setSelected(true);
        w4.setToolTipText("Change the color to the default.");

        // Group the radio buttons.
        ButtonGroup selectFillColor = new ButtonGroup();
        selectFillColor.add(w3);
        selectFillColor.add(w4);

        // Register a listener for the radio buttons.
        w3.addActionListener(w3Listener);
        w4.addActionListener(w4Listener);

        // Add text field
        w5 = new JTextField(5);
        w5.addActionListener(w5Listener);
        w5.setEnabled(false);
        w5.setToolTipText("Type a custom color for the shape. Pressing enter or the Create Shape button changes the shape to this color.");

        w6 = new JButton("Create Shape");
        w6.setVerticalTextPosition(AbstractButton.CENTER);
        w6.setHorizontalTextPosition(AbstractButton.CENTER); // aka LEFT, for
        // left-to-right
        // locales
        w6.setToolTipText("Creates a shape based on the current settings.");

        w7 = new JButton("Reset");
        w7.setVerticalTextPosition(AbstractButton.CENTER);
        w7.setHorizontalTextPosition(AbstractButton.CENTER);
        w7.setToolTipText("Resets the window to the start state.");

        // Listen for actions on buttons.
        w6.addActionListener(w6Listener);
        w7.addActionListener(w7Listener);

        menuBar = new JMenuBar();

        options = new JMenu("Options");
        options.setMnemonic(KeyEvent.VK_O);

        about = new JMenuItem("About");
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
                                                    ActionEvent.ALT_MASK));
        about.addActionListener(new AboutListener());

        shape = new JMenu("Shape");
        shape.setMnemonic(KeyEvent.VK_S);

        ButtonGroup chooseShape = new ButtonGroup();
        circle = new JRadioButtonMenuItem("Circle");
        circle.setSelected(true);
        circle.setMnemonic(KeyEvent.VK_I);
        circle.addActionListener(w1Listener);
        chooseShape.add(circle);

        square = new JRadioButtonMenuItem("Square");
        square.setMnemonic(KeyEvent.VK_Q);
        square.addActionListener(w2Listener);
        chooseShape.add(square);

        color = new JMenu("Color");
        color.setMnemonic(KeyEvent.VK_C);

        none = new JRadioButtonMenuItem("None");
        none.setSelected(true);
        none.setMnemonic(KeyEvent.VK_N);
        none.addActionListener(new NoneListener());

        black = new JRadioButtonMenuItem("Black");
        black.setMnemonic(KeyEvent.VK_B);
        black.addActionListener(new BlackListener());

        blue = new JRadioButtonMenuItem("Blue");
        blue.setMnemonic(KeyEvent.VK_U);
        blue.addActionListener(new BlueListener());

        cyan = new JRadioButtonMenuItem("Cyan");
        cyan.setMnemonic(KeyEvent.VK_C);
        cyan.addActionListener(new CyanListener());

        darkgray = new JRadioButtonMenuItem("Dark Gray");
        darkgray.setMnemonic(KeyEvent.VK_D);
        darkgray.addActionListener(new DarkGrayListener());

        gray = new JRadioButtonMenuItem("Gray");
        gray.setMnemonic(KeyEvent.VK_G);
        gray.addActionListener(new GrayListener());

        green = new JRadioButtonMenuItem("Green");
        green.setMnemonic(KeyEvent.VK_E);
        green.addActionListener(new GreenListener());

        lightgray = new JRadioButtonMenuItem("Light Gray");
        lightgray.setMnemonic(KeyEvent.VK_L);
        lightgray.addActionListener(new LightGrayListener());

        magenta = new JRadioButtonMenuItem("Magenta");
        magenta.setMnemonic(KeyEvent.VK_M);
        magenta.addActionListener(new MagentaListener());

        orange = new JRadioButtonMenuItem("Orange");
        orange.setMnemonic(KeyEvent.VK_O);
        orange.addActionListener(new OrangeListener());

        pink = new JRadioButtonMenuItem("Pink");
        pink.setMnemonic(KeyEvent.VK_P);
        pink.addActionListener(new PinkListener());

        red = new JRadioButtonMenuItem("Red");
        red.setMnemonic(KeyEvent.VK_R);
        red.addActionListener(new RedListener());

        white = new JRadioButtonMenuItem("White");
        white.setMnemonic(KeyEvent.VK_W);
        white.addActionListener(new WhiteListener());

        yellow = new JRadioButtonMenuItem("Yellow");
        yellow.setMnemonic(KeyEvent.VK_Y);
        yellow.addActionListener(new YellowListener());

        ButtonGroup chooseColor = new ButtonGroup();
        chooseColor.add(none);
        chooseColor.add(black);
        chooseColor.add(blue);
        chooseColor.add(cyan);
        chooseColor.add(darkgray);
        chooseColor.add(gray);
        chooseColor.add(green);
        chooseColor.add(lightgray);
        chooseColor.add(magenta);
        chooseColor.add(orange);
        chooseColor.add(pink);
        chooseColor.add(red);
        chooseColor.add(white);
        chooseColor.add(yellow);

        shape.add(circle);
        shape.add(square);

        color.add(none);
        color.addSeparator();
        color.add(black);
        color.add(blue);
        color.add(cyan);
        color.add(darkgray);
        color.add(gray);
        color.add(green);
        color.add(lightgray);
        color.add(magenta);
        color.add(orange);
        color.add(pink);
        color.add(red);
        color.add(white);
        color.add(yellow);

        options.add(about);
        options.add(shape);
        options.add(color);
        menuBar.add(options);

        // Put the radio buttons in a panel.
        JPanel radioPanel1 = new JPanel(new GridLayout(0, 2));
        radioPanel1.add(w1);
        radioPanel1.add(w2);
        TitledBorder radioPanel1Border = BorderFactory.createTitledBorder(
                                                                          loweredetched, "Select Shape");
        radioPanel1.setBorder(radioPanel1Border);

        JPanel radioPanel2 = new JPanel(new GridLayout(0, 2));
        radioPanel2.add(w3);
        radioPanel2.add(w4);
        radioPanel2.add(w5);
        TitledBorder radioPanel2Border = BorderFactory.createTitledBorder(
                                                                          loweredetched, "Select Fill Color");
        radioPanel2.setBorder(radioPanel2Border);

        // Create and set up content panel.
        contentPane = new JPanel(new GridBagLayout());
        contentPane.add(radioPanel1, new GridBagConstraints(0, 0, 1, 1, 0, 0,
                                                            GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
                                                                                                                           0, 0, 0, 0), 0, 0));
        contentPane.add(radioPanel2, new GridBagConstraints(0, 1, 1, 1, 0, 0,
                                                            GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
                                                                                                                           0, 0, 0, 0), 0, 0));
        contentPane.add(w6, new GridBagConstraints(0, 2, 1, 1, 0, 0,
                                                   GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5,
                                                                                                                5, 5, 5), 0, 0));
        contentPane.add(w7, new GridBagConstraints(1, 2, 1, 1, 0, 0,
                                                   GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5,
                                                                                                                5, 5, 5), 0, 0));
        setContentPane(contentPane);
        setJMenuBar(menuBar);
        setResizable(false);
        addRenderedShape(drawEmpty);
        w6.requestFocusInWindow();
        setTitle("Radio Button Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addRenderedShape(JPanel draw) {
        contentPane.remove(drawShape);
        drawShape = draw;
        TitledBorder drawShapeBorder = BorderFactory.createTitledBorder(
                                                                        loweredetched, "Rendered Shape");
        drawShapeBorder.setTitleJustification(TitledBorder.CENTER);
        drawShape.setBorder(drawShapeBorder);
        contentPane.add(drawShape, new GridBagConstraints(1, 0, 1, 2, 0, 0,
                                                          GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
                                                                                                                         0, 0, 0, 0), 90, 0));
        pack();

        // drawShape.repaint();
    }

    public void chooseRenderedShape() {
        if (currentShape == SQUARE) {
            addRenderedShape(new SquarePanel(customColor));
        } else {
            addRenderedShape(new CirclePanel(customColor));
        }
    }

    public void clickColor(Color c, String s) {
        customColor = c;
        w3.setSelected(true);
        w5.setEnabled(true);
        w5.setText(s);
        if (shapeDrawn) {
            chooseRenderedShape();
        }
    }

    public void changeColor(String color) {
        String colorLowercase = color.toLowerCase();
        if (colorLowercase.contains("black")) {
            customColor = Color.BLACK;
            black.setSelected(true);
        } else if (colorLowercase.contains("blue")) {
            customColor = Color.BLUE;
            blue.setSelected(true);
        } else if (colorLowercase.contains("cyan")) {
            customColor = Color.CYAN;
            cyan.setSelected(true);
        } else if (colorLowercase.contains("darkgray")
                   || colorLowercase.contains("dark gray")) {
            customColor = Color.DARK_GRAY;
            darkgray.setSelected(true);
        } else if (colorLowercase.contains("gray")) {
            customColor = Color.GRAY;
            gray.setSelected(true);
        } else if (colorLowercase.contains("green")) {
            customColor = Color.GREEN;
            green.setSelected(true);
        } else if (colorLowercase.contains("lightgray")
                   || colorLowercase.contains("light gray")) {
            customColor = Color.LIGHT_GRAY;
            lightgray.setSelected(true);
        } else if (colorLowercase.contains("magenta")) {
            customColor = Color.MAGENTA;
            magenta.setSelected(true);
        } else if (colorLowercase.contains("orange")) {
            customColor = Color.ORANGE;
            orange.setSelected(true);
        } else if (colorLowercase.contains("pink")) {
            customColor = Color.PINK;
            pink.setSelected(true);
        } else if (colorLowercase.contains("red")) {
            customColor = Color.RED;
            red.setSelected(true);
        } else if (colorLowercase.contains("white")) {
            customColor = Color.WHITE;
            white.setSelected(true);
        } else if (colorLowercase.contains("yellow")) {
            customColor = Color.YELLOW;
            yellow.setSelected(true);
        } else if (colorLowercase.equals("")) {
            customColor = null;
            none.setSelected(true);
        } else {
            JOptionPane.showMessageDialog(null, w5.getText()
                                          + " is not a color choice.", "",
                                          JOptionPane.WARNING_MESSAGE);
            w5.setText(returnString(customColor));
        }
    }

    public Color returnColor(String color) {
        String colorLowercase = color.toLowerCase();
        if (colorLowercase.contains("black")) {
            return Color.BLACK;
        } else if (colorLowercase.contains("blue")) {
            return Color.BLUE;
        } else if (colorLowercase.contains("cyan")) {
            return Color.CYAN;
        } else if (colorLowercase.contains("darkgray")
                   || colorLowercase.contains("dark gray")) {
            return Color.DARK_GRAY;
        } else if (colorLowercase.contains("gray")) {
            return Color.GRAY;
        } else if (colorLowercase.contains("green")) {
            return Color.GREEN;
        } else if (colorLowercase.contains("lightgray")
                   || colorLowercase.contains("light gray")) {
            return Color.LIGHT_GRAY;
        } else if (colorLowercase.contains("magenta")) {
            return Color.MAGENTA;
        } else if (colorLowercase.contains("orange")) {
            return Color.ORANGE;
        } else if (colorLowercase.contains("pink")) {
            return Color.PINK;
        } else if (colorLowercase.contains("red")) {
            return Color.RED;
        } else if (colorLowercase.contains("white")) {
            return Color.WHITE;
        } else if (colorLowercase.contains("yellow")) {
            return Color.YELLOW;
        } else if (colorLowercase.equals("")) {
            return null;
        } else {
            return customColor;
        }
    }

    public String returnString(Color c) {
        if (c.equals(Color.BLACK)) {
            return "Black";
        } else if (c.equals(Color.BLUE)) {
            return "Blue";
        } else if (c.equals(Color.CYAN)) {
            return "Cyan";
        } else if (c.equals(Color.DARK_GRAY)) {
            return "Dark Gray";
        } else if (c.equals(Color.GRAY)) {
            return "Gray";
        } else if (c.equals(Color.GREEN)) {
            return "Green";
        } else if (c.equals(Color.LIGHT_GRAY)) {
            return "Light Gray";
        } else if (c.equals(Color.MAGENTA)) {
            return "Magenta";
        } else if (c.equals(Color.ORANGE)) {
            return "Orange";
        } else if (c.equals(Color.PINK)) {
            return "Pink";
        } else if (c.equals(Color.RED)) {
            return "Red";
        } else if (c.equals(Color.WHITE)) {
            return "White";
        } else if (c.equals(Color.YELLOW)) {
            return "Yellow";
        } else {
            return "";
        }
    }

    public void actionPerformed(ActionEvent e) {
        //overrides the abstract method
    }

    /** Listens to the radio buttons. */
    class W1Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            circle.setSelected(true);
            w1.setSelected(true);
            if (shapeDrawn && currentShape != CIRCLE) {
                addRenderedShape(new CirclePanel(customColor));
            }
            currentShape = CIRCLE;
        }
    }

    class W2Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            square.setSelected(true);
            w2.setSelected(true);
            if (shapeDrawn && currentShape != SQUARE) {
                addRenderedShape(new SquarePanel(customColor));
            }
            currentShape = SQUARE;
        }
    }


    class W3Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            w5.setEnabled(true);
            changeColor(w5.getText());
            if (shapeDrawn) {
                chooseRenderedShape();
            }
        }
    }

    class W4Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            w5.setEnabled(false);
            customColor = null;
            none.setSelected(true);
            if (shapeDrawn) {
                chooseRenderedShape();
            }
        }
    }

    class W5Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            changeColor(w5.getText());
            if (shapeDrawn) {
                chooseRenderedShape();
            }
        }
    }

    class W6Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            changeColor(w5.getText());
            chooseRenderedShape();
            shapeDrawn = true;
        }
    }

    class W7Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            w1.setSelected(true);
            circle.setSelected(true);
            currentShape = CIRCLE;
            shapeDrawn = false;
            customColor = null;
            w4.setSelected(true);
            none.setSelected(true);
            w5.setText("");
            w5.setEnabled(false);
            w6.requestFocusInWindow();
            addRenderedShape(new JPanel());
        }
    }

    class AboutListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            JOptionPane
                .showMessageDialog(
                                   null,
                                   "This program draws circles and squares.\nTo draw a shape, click the create shape button.\nThe shape may be changed using the\non screen buttons or the options menu.",
                                   "About", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    class NoneListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            customColor = null;
            w4.setSelected(true);
            w5.setEnabled(false);
            w5.setText("");
            if (shapeDrawn) {
                chooseRenderedShape();
            }
        }
    }

    class BlackListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            clickColor(Color.BLACK, "Black");
        }
    }

    class BlueListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            clickColor(Color.BLUE, "Blue");
        }
    }

    class CyanListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            clickColor(Color.CYAN, "Cyan");
        }
    }

    class DarkGrayListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            clickColor(Color.DARK_GRAY, "Dark Gray");
        }
    }

    class GrayListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            clickColor(Color.GRAY, "Gray");
        }
    }

    class GreenListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            clickColor(Color.GREEN, "Green");
        }
    }

    class LightGrayListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            clickColor(Color.LIGHT_GRAY, "Light Gray");
        }
    }

    class MagentaListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            clickColor(Color.MAGENTA, "Magenta");
        }
    }

    class OrangeListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            clickColor(Color.ORANGE, "Orange");
        }
    }

    class PinkListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            clickColor(Color.PINK, "Pink");
        }
    }

    class RedListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            clickColor(Color.RED, "Red");
        }
    }

    class WhiteListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            clickColor(Color.WHITE, "White");
        }
    }

    class YellowListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            clickColor(Color.YELLOW, "Yellow");
        }
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI() {
        // Create and set up the window.
        Project frame = new Project();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowGUI();
                }
            });
    }
}

/** Panel on which to draw a circle. **/
class CirclePanel extends JPanel {
    public Color customColor;

    public CirclePanel() {
        super();
    }

    public CirclePanel(Color c) {
        super();
        customColor = c;
    }

    public void paintComponent(Graphics g) {
        // First paint background
        super.paintComponent(g);
        // set color
        if (customColor != null) {
            g.setColor(customColor);
        }
        // draw circle
        g.drawOval(20, 35, 70, 70);
    } // paintComponent

} // class CirclePanel

/** Panel on which to draw a square. **/
class SquarePanel extends JPanel {
    public Color customColor;

    public SquarePanel() {
        super();
    }

    public SquarePanel(Color c) {
        super();
        customColor = c;
    }

    public void paintComponent(Graphics g) {
        // First paint background
        super.paintComponent(g);
        // set color
        if (customColor != null) {
            g.setColor(customColor);
        }
        // draw a square
        g.drawRect(20, 35, 70, 70);
    } // paintComponent

} // class SquarePanel
