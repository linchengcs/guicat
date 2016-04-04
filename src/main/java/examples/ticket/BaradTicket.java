package examples.ticket;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class BaradTicket {

    private JButton buyButton;
    private JButton clearButton;
    private JTextField infoField;
    JRadioButton firstClassRadio;
    JRadioButton secondClassRadio;
    ButtonGroup buttonGroup;
    JTextField nameInput;
    JTextField idInput;
    JTextField fromInput;
    JTextField toInput;
    JComboBox ageCombo;
    JCheckBox aCheckBox;


    private TicketModel ticketModel;

    public  void createAndShowGUI() {
        ticketModel = new TicketModel();

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
            JLabel idLabel = new JLabel("ID");
            gbc.gridx = 0;
            gbc.gridy = 1;
            contentPane.add(idLabel, gbc);

            idInput = new JTextField("123456", 10);
            gbc.gridx = 1;
            gbc.gridy = 1;
            contentPane.add(idInput, gbc);
            idLabel.setLabelFor(idInput);idInput.getAccessibleContext().getAccessibleName();
        }

        {
            JLabel fromLabel = new JLabel("From");
            gbc.gridx = 0;
            gbc.gridy = 2;
            contentPane.add(fromLabel, gbc);

            fromInput = new JTextField("0", 10);
            gbc.gridx = 1;
            gbc.gridy = 2;
            contentPane.add(fromInput, gbc);
            fromLabel.setLabelFor(fromInput);
        }

        {
            JLabel toLabel = new JLabel("To");
            gbc.gridx = 0;
            gbc.gridy = 3;
            contentPane.add(toLabel, gbc);

             toInput = new JTextField("50", 10);
            gbc.gridx = 1;
            gbc.gridy = 3;
            contentPane.add(toInput, gbc);
            toLabel.setLabelFor(toInput);
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
            JLabel classLabel = new JLabel("Class Level");
            gbc.gridx = 0;
            gbc.gridy = 5;
            contentPane.add(classLabel, gbc);

            firstClassRadio = new JRadioButton("1st");
            firstClassRadio.setActionCommand("1st");
            firstClassRadio.setSelected(true);
            secondClassRadio = new JRadioButton("2nd");
            secondClassRadio.setActionCommand("2nd");

            buttonGroup = new ButtonGroup();
            buttonGroup.add(firstClassRadio);
            buttonGroup.add(secondClassRadio);

            gbc.gridx = 1;
            gbc.gridy = 5;
            contentPane.add(firstClassRadio, gbc);

            gbc.gridx = 2;
            gbc.gridy = 5;
            contentPane.add(secondClassRadio, gbc);
        }


        {
             buyButton = new JButton("Buy Ticket");
            gbc.gridx = 0;
            gbc.gridy = 6;
            contentPane.add(buyButton, gbc);

             clearButton = new JButton("Clear");
            gbc.gridx = 1;
            gbc.gridy = 6;
            contentPane.add(clearButton, gbc);
        }

        {
            JLabel priceLabel = new JLabel("Price");
            gbc.gridx = 0;
            gbc.gridy = 7;
            contentPane.add(priceLabel, gbc);
            priceLabel.setForeground(Color.BLUE);

             infoField = new JTextField("ticket price");
            gbc.gridx = 1;
            gbc.gridy = 7;
            contentPane.add(infoField, gbc);
            infoField.setEnabled(false);
        }

        {
            JLabel checkLabel = new JLabel("ACheckBox");
            gbc.gridx = 0;
            gbc.gridy = 8;
            contentPane.add(checkLabel, gbc);

            aCheckBox = new JCheckBox("MyCheckBox");
            gbc.gridx = 1;
            gbc.gridy = 8;
            contentPane.add(aCheckBox, gbc);
            checkLabel.setLabelFor(aCheckBox);
            aCheckBox.setSelected(true);

        }

        {
            buyButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ticketModel.name = nameInput.getText();
                    ticketModel.ID = idInput.getText();
                    ticketModel.from = Integer.parseInt(fromInput.getText());
                    ticketModel.to = Integer.parseInt(toInput.getText());
                    String info = "";

                    String tmpAgeString = (String)ageCombo.getSelectedItem();
                    if (tmpAgeString.equals("Child")) {
                        ticketModel.ageLevel = 1;
                        info = ("Child");
                    }
                    if (tmpAgeString.equals("Adult")) {
                        ticketModel.ageLevel = 2;
                        info = ("Adult");
                    }


                    if (aCheckBox.isSelected()) {
                        info += ("+Y");
                    } else {
                        info += ("+N!");
                    }

                    infoField.setText(info);
                    /*
                    System.out.println( ticketModel.name.equals("oliver")  ? "" : "");
                    System.out.println( ticketModel.to  >= 5 ? "" : "");
                    System.out.println( ticketModel.from  < 1 ? "" : "");
                    */


                    //ticketModel.computePrice();
                   // infoField.setText(String.valueOf(ticketModel.price));
                    /*
                    if (ticketModel.to - ticketModel.from  < 40 ) {
                        System.out.println("less");

                    }else {
                        System.out.println("more");
                    }
                    */

/*
                    if(ticketModel.checkModel()) {
                        ticketModel.computePrice();
                        infoField.setText(String.valueOf(ticketModel.price));
                    } else {
                        infoField.setText(ticketModel.msg);
                    }
*/
                }
            });

            firstClassRadio.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String s = e.getActionCommand();
                        if (s.equals("1st"))
                            ticketModel.classLevel = 1;
                        if (s.equals("2nd"))
                            ticketModel.classLevel = 2;
                    }
                });

            secondClassRadio.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String s = e.getActionCommand();
                        if (s.equals("1st"))
                            ticketModel.classLevel = 1;
                        if (s.equals("2nd"))
                            ticketModel.classLevel = 2;
                    }
                });

            clearButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        infoField.setText("");
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
                new BaradTicket().createAndShowGUI();
            }
        });
    }

}
