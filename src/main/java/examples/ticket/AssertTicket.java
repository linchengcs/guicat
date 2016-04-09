package examples.ticket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by oliver on 01/04/16.
 */
public class AssertTicket {

    private JButton buyButton;
    private JButton clearButton;
    private JTextField infoField;
    JRadioButton firstClassRadio;
    JRadioButton secondClassRadio;
    ButtonGroup buttonGroup;
    JTextField nameInput;
    JTextField idInput;
    JTextField distanceInput;
    JTextField toInput;
    JComboBox ageCombo;
    JCheckBox aCheckBox;
    JCheckBox d100CheckBox;
    JCheckBox d200CheckBox;
    JCheckBox d400CheckBox;


    public  void createAndShowGUI() {

        JFrame frame = new JFrame("Ticket");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel(new GridBagLayout());
        frame.setContentPane(contentPane);

        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);

        {
            JLabel nameLabel = new JLabel("Name");
            contentPane.add(nameLabel, gbc);

            nameInput = new JTextField("oliver", 10);
            gbc.gridx = 1;
            contentPane.add(nameInput, gbc);
            nameLabel.setLabelFor(nameInput);
        }

        {
            JLabel fromLabel = new JLabel("Distance");
            gbc.gridx = 0;
            gbc.gridy = 2;
            contentPane.add(fromLabel, gbc);

            distanceInput = new JTextField("0", 10);
            gbc.gridx = 1;
            gbc.gridy = 2;
            contentPane.add(distanceInput, gbc);
            fromLabel.setLabelFor(distanceInput);
        }


        {
            JLabel ageLabel = new JLabel("Age Level");
            gbc.gridx = 0;
            gbc.gridy = 4;
            contentPane.add(ageLabel, gbc);

            String[] ages = {"Adult", "Child"};
            ageCombo = new JComboBox(ages);
            ageCombo.setSelectedIndex(0);
            gbc.gridx = 1;
            gbc.gridy = 4;
            contentPane.add(ageCombo, gbc);
            ageLabel.setLabelFor(ageCombo);
        }




        {
            JLabel priceLabel = new JLabel("Price");
            gbc.gridx = 0;
            gbc.gridy = 10;
            contentPane.add(priceLabel, gbc);
            priceLabel.setForeground(Color.BLUE);

            infoField = new JTextField("ticket price");
            gbc.gridx = 1;
            gbc.gridy = 10;
            contentPane.add(infoField, gbc);
            infoField.setEnabled(false);
        }

        {
            JLabel checkLabel = new JLabel("Coupon");
            gbc.gridx = 0;
            gbc.gridy = 5;
            contentPane.add(checkLabel, gbc);

            d100CheckBox = new JCheckBox("$100");
            gbc.gridx = 1;
            gbc.gridy = 5;
            contentPane.add(d100CheckBox, gbc);

            d200CheckBox = new JCheckBox("$200");
            gbc.gridx = 0;
            gbc.gridy = 6;
            contentPane.add(d200CheckBox, gbc);

            d400CheckBox = new JCheckBox("$400");
            gbc.gridx = 1;
            gbc.gridy = 6;
            contentPane.add(d400CheckBox, gbc);

        }

        {
            buyButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int distance = Integer.parseInt(distanceInput.getText());
                    int unit = 3;
                    String age = (String)ageCombo.getSelectedItem();
                    int coupon = 0;
                    int coeffienct = 1;
                    int price = 0;

                    if (d100CheckBox.isSelected())
                        coupon += 100;
                    if (d200CheckBox.isSelected())
                        coupon += 200;
                    if (d400CheckBox.isSelected())
                        coupon += 400;

                    if (age.equals("Child")) {
                        coeffienct = 1;
                    } else {
                        coeffienct = 2;
                    }
                    if (distance < 60) {
                        price = 500;
                    } else if (distance < 80) {
                        price = 11 * distance * coeffienct - coupon;
                    } else if (distance < 100) {
                        price = 10 * distance * coeffienct - coupon;
                    } else if (distance < 120) {
                        price = 9 * distance * coeffienct - coupon;
                    } else {
                        price = 8 * distance * coeffienct - coupon;
                    }

                    assert price > 0;

                    infoField.setText("Price is:" + price);
                    
                }
            });


        }

        frame.pack();
        frame.setVisible(true);

    }

    public static void a(){}

    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AssertTicket().createAndShowGUI();
            }
        });
    }

}
