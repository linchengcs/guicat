package examples.ticket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;


public class TicketDpor extends JFrame{
    private static Logger logback = LoggerFactory.getLogger(TicketDpor.class);
    private JButton buy;
    private JTextField info;
    private JTextField nameInput;
    private JTextField ageInput;


    private String msg = "";
    private String name;
    private int age;
    private int price;

    static ArrayList<Integer> path = new ArrayList<Integer>(Arrays.asList(0,0));


    public void showTicket () {
        JPanel contentPane = new JPanel(new GridBagLayout());

        JLabel hello = new JLabel("Ticket Seller DPOR", JLabel.CENTER);
        hello.setFont(new Font("Default", Font.BOLD, 18));
        contentPane.add(hello, new GridBagConstraints(0, 0, 2, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));


        JLabel name = new JLabel("Enter your name:", JLabel.RIGHT);
        contentPane.add(name, new GridBagConstraints(0, 1, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        nameInput = new JTextField(8);
        contentPane.add(nameInput, new GridBagConstraints(1, 1, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        name.setLabelFor(nameInput);

        JLabel age = new JLabel("Enter your age:", JLabel.RIGHT);
        contentPane.add(age, new GridBagConstraints(0, 3, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        ageInput = new JTextField(8);
        contentPane.add(ageInput, new GridBagConstraints(1, 3, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        age.setLabelFor(ageInput);

        buy = new JButton("Buy");
        contentPane.add(buy, new GridBagConstraints(0, 7, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));
/*
        JButton cancel = new JButton("Cancel");
        contentPane.add(cancel, new GridBagConstraints(1, 7, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));
*/

        final JButton checknameButton = new JButton("check name");
        contentPane.add(checknameButton, new GridBagConstraints(0, 8, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));
        final JButton checkAgeButton = new JButton("check age");
        contentPane.add(checkAgeButton, new GridBagConstraints(1, 8, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));
        info = new JTextField(20);
        contentPane.add(info, new GridBagConstraints(0, 10, 2, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));
/*
        final JButton saveButton = new JButton("save");

        contentPane.add(saveButton, new GridBagConstraints(0, 9, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));


        final JButton deleteButton = new JButton("delete");

        contentPane.add(deleteButton, new GridBagConstraints(1, 9, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));
*/
        /*
        buy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String myName = nameInput.getText();
                if (nameInput.getAccessibleContext().getAccessibleName().equals("nameInputAccessibleNameTEST"))
                    nameInput.getText();

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
                logback.info(path.toString());
            }
        });
*/

        checknameButton.addActionListener(e -> checkName());
        checkAgeButton.addActionListener(e -> checkAge());
        buy.addActionListener(e -> computePrice());
   /*
        saveButton.addActionListener(e -> saveModel());
        cancel.addActionListener(e -> clear());
        deleteButton.addActionListener(e->delete());
*/
        setContentPane(contentPane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));
        pack();


        setVisible(true);



    }

    private void saveModel() {
        this.name = nameInput.getText();
        try {
            this.age = Integer.parseInt(ageInput.getText());
        } catch (NumberFormatException e) {
            msg = e.getClass().toString() ;
        }
    }

    private void checkName() {
        saveModel();
        if (name.length() <= 3)
            msg = "name is too short\n";
        else
            msg = "";
    }

    private void checkAge() {
        saveModel();
        if (age <= 12)
            msg = "age is too small";
        else
            msg = "";
    }
    private void  computePrice() {
        saveModel();
        if (msg == null || msg.isEmpty()) {
            if (age <= 18)
                price = 1;
            else
                price = 2;
            info.setText("price is: " + price);
        } else
            info.setText(msg);
    }

    private void delete() {
        name = null;
        age = 0;
    }

    private void clear() {
        info.setText("");
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
        TicketDpor t = new TicketDpor();
        t.showTicket();
    }



}
