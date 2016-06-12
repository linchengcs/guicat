package examples.ticket;

//import org.chocosolver.solver.constraints.nary.nValue.amnv.rules.R;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by oliver on 01/04/16.
 */
public class RaceAndBranchTicket {

    private JButton buyButton;
    private JButton saveButton;
    private JTextField infoField;
    JTextField nameInput;
    JTextField distanceInput;
    JTextField couponInput;

    User user;

    public RaceAndBranchTicket() {
        user = new User();
    }


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

            saveButton = new JButton("Save");
            gbc.gridx = 2;
            contentPane.add(saveButton, gbc);
        }

        {
            JLabel distanceLabel = new JLabel("Distance");
            gbc.gridx = 0;
            gbc.gridy = 2;
            contentPane.add(distanceLabel, gbc);

            distanceInput = new JTextField("0", 10);
            gbc.gridx = 1;
            gbc.gridy = 2;
            contentPane.add(distanceInput, gbc);
            distanceLabel.setLabelFor(distanceInput);
        }


        {
            JLabel couponLabel = new JLabel("Coupon");
            gbc.gridx = 0;
            gbc.gridy = 3;
            contentPane.add(couponLabel, gbc);

            couponInput = new JTextField("0", 10);
            gbc.gridx = 1;
            gbc.gridy = 3;
            contentPane.add(couponInput, gbc);
            couponLabel.setLabelFor(distanceInput);


            buyButton = new JButton("Buy");
            gbc.gridx = 2;
            gbc.gridy = 3;
            contentPane.add(buyButton, gbc);

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
            buyButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int distance = Integer.parseInt(distanceInput.getText());
                    int coupon = Integer.parseInt(couponInput.getText());
                    int price = 0;

                    if (distance > 100) {
                        price = 100;
                    } else {
                        price = 50;
                    }

                    price -= coupon;

                    assert price > 0;  //bug, may fail if coupon > 100

                    user.price = price;  //bug, if click saveButton first, cause null pointer to user

                    infoField.setText("Price is:" + price);

                }
            });


            saveButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    user.username = nameInput.getText();
                    user = null;
                    infoField.setText("user saved");
                }
            });
        }

        frame.pack();
        frame.setVisible(true);

    }

   class User {
       String username;
       int price;
   }

    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new RaceAndBranchTicket().createAndShowGUI();
            }
        });
    }

}
