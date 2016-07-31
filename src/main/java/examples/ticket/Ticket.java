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
    private String msg;

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


        JLabel age = new JLabel("Enter your age:", JLabel.RIGHT);
        contentPane.add(age, new GridBagConstraints(0, 3, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        final JTextField ageInput = new JTextField(8);
        contentPane.add(ageInput, new GridBagConstraints(1, 3, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        age.setLabelFor(ageInput);

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
                int tmp = 0;
                String myName = nameInput.getText();
                int myAgeInt = 0;
                String myAge = ageInput.getText();
                try {
                    myAgeInt = Integer.parseInt(myAge);
                } catch (Exception e1){}


                if (myAgeInt < 12) {
                    tmp = 100;
                } else {
                    tmp = 0;
                }

                msg = tmp + myName;
                info.setText(msg);

            }
        });


        setContentPane(contentPane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(300, 200));
        pack();


        setVisible(true);



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
