package examples.ticket;

import catg.CATG;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by rick on 9/21/15.
 */
public class TicketAllWidgets extends JFrame {
    private JButton buy;

    public TicketAllWidgets () {
        JPanel contentPane = new JPanel(new GridBagLayout());

        JLabel hello = new JLabel("Ticket Seller", JLabel.CENTER);
        hello.setFont(new Font("Default", Font.BOLD, 24));
        contentPane.add(hello, new GridBagConstraints(0, 0, 2, 1, 0, 0,
                                                      GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));


        JLabel name = new JLabel("Enter your name:", JLabel.RIGHT);
        contentPane.add(name, new GridBagConstraints(0, 1, 1, 1, 0, 0,
                                                     GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        final JTextField nameInput = new JTextField(10);
        contentPane.add(nameInput, new GridBagConstraints(1, 1, 1, 1, 0, 0,
                                                          GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        name.setLabelFor(nameInput);

        JLabel nameTip = new JLabel("Please enter more than 3 characters.", JLabel.RIGHT);
        nameTip.setForeground(Color.gray);
        nameTip.setFont(new Font("Default", Font.PLAIN, 12));
        contentPane.add(nameTip, new GridBagConstraints(1, 2, 2, 1, 0, 0,
                                                        GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));


        JLabel age = new JLabel("Enter your age:", JLabel.RIGHT);
        contentPane.add(age, new GridBagConstraints(0, 3, 1, 1, 0, 0,
                                                    GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        final JTextField ageInput = new JTextField(10);
        contentPane.add(ageInput, new GridBagConstraints(1, 3, 1, 1, 0, 0,
                                                         GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        age.setLabelFor(ageInput);

        JLabel ageTip = new JLabel("Please enter a number.", JLabel.RIGHT);
        ageTip.setForeground(Color.gray);
        ageTip.setFont(new Font("Default", Font.PLAIN, 12));
        contentPane.add(ageTip, new GridBagConstraints(1, 4, 2, 1, 0, 0,
                                                       GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));

        JLabel genderLabel = new JLabel("gender:", JLabel.RIGHT);
        contentPane.add(genderLabel, new GridBagConstraints(0, 5, 1, 1, 0, 0,
                                                       GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        String[] genderString = {"male", "female"};
        JComboBox gender = new JComboBox(genderString);
        gender.setSelectedIndex(0);
        contentPane.add(gender, new GridBagConstraints(1, 5, 1, 1, 0, 0,
                                                       GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        genderLabel.setLabelFor(gender);

        JLabel typeLabel = new JLabel("type:", JLabel.RIGHT);
        contentPane.add(typeLabel, new GridBagConstraints(0, 6, 1, 1, 0, 0,
                                                       GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        String[] ticketTypeString = {"bus", "train", "flight"};
        JList ticketType = new JList(ticketTypeString);
        ticketType.setSelectedIndices(new int[]{0,2});
        contentPane.add(ticketType, new GridBagConstraints(1, 6, 1, 1, 0, 0,
                                                       GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        typeLabel.setLabelFor(ticketType);

        buy = new JButton("Buy");
        contentPane.add(buy, new GridBagConstraints(0, 7, 1, 1, 0, 0,
                                                    GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));

        JButton cancel = new JButton("Cancel");
        contentPane.add(cancel, new GridBagConstraints(1, 7, 1, 1, 0, 0,
                                                       GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));

        final JLabel info = new JLabel();
        contentPane.add(info, new GridBagConstraints(0, 8, 2, 1, 0, 0,
                                                     GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));

        buy.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String myName = nameInput.getText();
                    //                String myAge = ageInput.getText();

                    myName = CATG.readString("");
                    System.out.println(myName.length() < 3 ? "wrong name" : "right name");

                    int myAge = CATG.readInt(0);
                    if ( myAge < 12){
                        info.setText("Hi, child");
                        info.setText("Hi, people");
                    }
                    System.out.println(myAge < 12 ? "child" : "people");

                    String gender = CATG.readString("");
                    System.out.println(gender.equals("male") ? "male" : "female");

                    int[] myTicketType = ticketType.getSelectedIndices();
                    int index = CATG.readInt(0);
                    if (ticketTypeString[index] == "flight" )
                        System.out.println("your are taking flight");
                }
            });

        cancel.addActionListener( e -> {
                info.setText("");
                nameInput.setText("");
                ageInput.setText("");
            });


        //        EventQueue eq = getToolkit().getSystemEventQueue();
        //        eq.postEvent(new ActionEvent(buy, ActionEvent.ACTION_LAST, "buy"));
        //      eq.postEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

        //       System.out.println("the EventQueue is " + eq);


        setContentPane(contentPane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));
        pack();


        setVisible(true);



    }



    public static void main(String[] args) {
        /*
          SwingUtilities.invokeLater(new Runnable() {
          public void run() {
          new Ticket();
          }
          });
        */
        /*
          int ii = CATG.readInt(1);
          System.out.println(ii > 0 ? "CATG true" : "CATG false");
          try {
          Thread.sleep(2000);
          } catch (InterruptedException e) {
          e.printStackTrace();
          }
        */
        new TicketAllWidgets();
    }



}
