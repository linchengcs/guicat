package examples.ticket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by rick on 9/21/15.
 */
public class Ticket extends JFrame {
    private JButton buy;
    static ArrayList<Integer> path = new ArrayList<Integer>(Arrays.asList(0,0));


    //    private String myName;
    //    private String myAge;

    public void showTicket () {
        JPanel contentPane = new JPanel(new GridBagLayout());

        JLabel hello = new JLabel("Ticket Seller", JLabel.CENTER);
        hello.setFont(new Font("Default", Font.BOLD, 18));
        contentPane.add(hello, new GridBagConstraints(0, 0, 2, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));


        JLabel name = new JLabel("Enter your name:", JLabel.RIGHT);
        contentPane.add(name, new GridBagConstraints(0, 1, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        final JTextField nameInput = new JTextField(8);
        contentPane.add(nameInput, new GridBagConstraints(1, 1, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        name.setLabelFor(nameInput);
        /*
          JLabel nameTip = new JLabel("Please enter more than 3 characters.", JLabel.RIGHT);
          nameTip.setForeground(Color.gray);
          nameTip.setFont(new Font("Default", Font.PLAIN, 12));
          contentPane.add(nameTip, new GridBagConstraints(1, 2, 2, 1, 0, 0,
          GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));
        */

        JLabel age = new JLabel("Enter your age:", JLabel.RIGHT);
        contentPane.add(age, new GridBagConstraints(0, 3, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        final JTextField ageInput = new JTextField(8);
        contentPane.add(ageInput, new GridBagConstraints(1, 3, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        age.setLabelFor(ageInput);
        /*
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
        */
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
                //String myName = CATG.readString("oliver");
                //  String myName = "oliver";
                //System.out.println(nameInput.getAccessibleContext().getAccessibleName());
                //System.out.println(ageInput.getAccessibleContext().getAccessibleName());

                //String myName = "Enter your name:".equals(nameInput.getName()) ? CATG.readString("") : nameInput.getText();
                //String myAge = ageInput.getText();

                //myName = CATG.readString("");
                //myName = SingleSym.getSingleSym();

                int myAgeInt = 0;
                //// String myAge = CATG.readString("11");
                String myAge = ageInput.getText();
                //String myAge = "11";
                try {
                    myAgeInt = Integer.parseInt(myAge);
                } catch (Exception e1){}

                String display1 = "-----> Your name: " + myName + " -----> Your age: " + myAge;
                String display = "";
                if (myName.startsWith("oliver") ) {
                    display = "Wrong name; " + display1;
                    System.out.println(display);
                    info.setText(display);
                    path.set(0, 0);
                } else {
                    display = "Righ name; " + display1;
                    System.out.println(display);
                    info.setText(display);
                    path.set(0, 1);
                }

                if (myAgeInt < 12) {
                    //   if (myAge.equals("13")) {
                    display = "Child; " + display1;
                    System.out.println(display);
                    info.setText(display);
                    path.set(1, 0);
                } else {
                    display = "Adult; " + display1;
                    System.out.println(display);
                    info.setText(display);
                    path.set(1, 1);
                }
                System.out.println(path.toString());
                writePath();
                //System.out.println((myName.length() < 3 ? "wrong name" : "right name") + " , Your name/age: " + myName +"/" +myAge );
                //System.out.println((myAge.length() >= 12 ? "adult" : "child") + " , Your name/age: " + myName + "/" + myAge);

                    /*
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
                    */
            }
        });

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
           //     writePath();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        //        cancel.addActionListener( e -> {
        //      info.setText("");
        //      nameInput.setText("");
        //      ageInput.setText("");
        //
        //  });


        setContentPane(contentPane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(300, 200));
        pack();


        setVisible(true);



    }

    public void writePath() {
        System.out.println("window closed, this is listener");
        System.out.println(path.toString());
        String pathDir = ".";

        //    File file = new File(pathDir+ "/log/ticket/path/" + System.currentTimeMillis() + ".txt");
        File file = new File(pathDir+ "/log/ticket/path/path.txt");
      //  File file = new File(pathDir+ "/path.txt");
        file.getParentFile().mkdirs();
        try {
            FileWriter writer = new FileWriter(file, true);
            writer.append(path.toString());
            writer.append("\n");
            writer.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static void main(String[] args) {
      //  System.out.println(args.toString());
        for (int i = 0; i < args.length; i++) ;

//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                new Ticket().showTicket();
//            }
//        });

        //        SingleSym ss = SingleSym.singleSym;
        Ticket t = new Ticket();
        t.showTicket();
    }



}
