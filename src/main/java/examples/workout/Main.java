package examples.workout;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    JLabel genderLabel;
    JLabel metabolismLabel;
    JLabel experienceLabel;
    JLabel ageLabel;
    JLabel heightLabel;
    JLabel weightLabel;
    JComboBox<String> genderCombo;
    JComboBox<String> metabolismCombo;
    JComboBox<String> experienceCombo;
    JTextField ageTextField;
    JTextField heightTextField;
    JTextField weightTextField;
    JButton generateButton;
    JButton clearButton;
    JTextArea resultTextArea;

    public Main() {
        JPanel contentPane = new JPanel(new GridBagLayout());
        this.setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);

        {
            genderLabel = new JLabel("Gender");
            genderCombo = new JComboBox<>(new String[] {"Male", "Female"});
            contentPane.add(genderLabel,gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            contentPane.add(genderCombo, gbc);
            genderLabel.setLabelFor(genderCombo);
        }

        {
            metabolismLabel = new JLabel("Metabolism");
            metabolismCombo = new JComboBox<>(new String[] {"Slow", "Normal", "Fast"});
            gbc.gridx = 1;
            gbc.gridy = 0;
            contentPane.add(metabolismLabel, gbc);
            gbc.gridx = 1;
            gbc.gridy = 1;
            contentPane.add(metabolismCombo, gbc);
            metabolismLabel.setLabelFor(metabolismCombo);
        }

        {
            experienceLabel = new JLabel("Experience");
            experienceCombo = new JComboBox<>(new String[] {"Beginner", "Intermediate", "Advanced"});
            gbc.gridx = 2;
            gbc.gridy = 0;
            contentPane.add(experienceLabel, gbc);
            gbc.gridx = 2;
            gbc.gridy = 1;
            contentPane.add(experienceCombo, gbc);
            experienceLabel.setLabelFor(experienceCombo);
        }

        {
            ageLabel = new JLabel("Age");
            ageTextField = new JTextField(10);
            gbc.gridx = 0;
            gbc.gridy = 2;
            contentPane.add(ageLabel, gbc);
            gbc.gridx = 0;
            gbc.gridy = 3;
            contentPane.add(ageTextField, gbc);
            ageLabel.setLabelFor(ageTextField);
        }

        {
            heightLabel = new JLabel("Height");
            heightTextField = new JTextField(10);
            gbc.gridx = 1;
            gbc.gridy = 2;
            contentPane.add(heightLabel, gbc);
            gbc.gridx = 1;
            gbc.gridy = 3;
            contentPane.add(heightTextField, gbc);
            heightLabel.setLabelFor(heightTextField);
        }

        {
            weightLabel = new JLabel("Weight");
            weightTextField = new JTextField(10);
            gbc.gridx = 2;
            gbc.gridy = 2;
            contentPane.add(weightLabel, gbc);
            gbc.gridx = 2;
            gbc.gridy = 3;
            contentPane.add(weightTextField, gbc);
            weightLabel.setLabelFor(weightTextField);
        }

        {
            generateButton = new JButton("Generate");
            gbc.gridx = 3;
            gbc.gridy = 1;
            contentPane.add(generateButton, gbc);

        }

        {
            clearButton = new JButton("Clear");
            gbc.gridx = 4;
            gbc.gridy = 1;
            contentPane.add(clearButton, gbc);
        }

        {
            resultTextArea = new JTextArea(10, 45);
            resultTextArea.setEditable(false);

            JScrollPane jScrollPane = new JScrollPane(resultTextArea);
            jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 5;
            contentPane.add(jScrollPane, gbc);
        }

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultTextArea.setText("");
            }
        });

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String workout = "";
                resultTextArea.setText(workout);
                String gender = (String)genderCombo.getSelectedItem();
                String metabolism = (String)metabolismCombo.getSelectedItem();
                String experience = (String)experienceCombo.getSelectedItem();
                int age = 1;
                float height = 100;
                float weight = 100;
                try {
                     age = Integer.parseInt(ageTextField.getText().trim());
                     height = Float.parseFloat(heightTextField.getText().trim());
                     weight = Float.parseFloat(weightTextField.getText().trim());
                } catch (Exception ex) {
                    ;
                }
                //Auiliary variables for coeficients
                int repCoeficient = 0;
                int setCoeficient = 0;
                double cardioCoeficient = 0;
                cardioCoeficient = (height - weight) / 100.0;
                //set cardio coeficient
                if (metabolism.equals("Slow")) {
                    cardioCoeficient = cardioCoeficient * 1.6;
                } else if (metabolism.equals("Normal")) {
                    cardioCoeficient = cardioCoeficient * 1.2;
                } else if (metabolism.equals( "Fast")) {
                    cardioCoeficient = cardioCoeficient * 0.9;
                }
                if (gender.equals("Male")) {
                    if (experience.equals("Beginner")) {
                        if (age >= 0 && age <= 20) {
                            cardioCoeficient = cardioCoeficient * 0.5;
                        }
                        if (age > 20 && age <= 30) {
                            cardioCoeficient = cardioCoeficient * 1.2;
                        }
                        if (age > 30 && age <= 45) {
                            cardioCoeficient = cardioCoeficient * 2.0;
                        }
                        if (age > 45) {
                            cardioCoeficient = cardioCoeficient * 1.5;
                        }
                        repCoeficient = 12;
                        setCoeficient = 2;
                    } else if (experience.equals("Intermediate")) {
                        if (age >= 0 && age <= 20) {
                            cardioCoeficient = cardioCoeficient * 0.5;
                        }
                        if (age > 20 && age <= 30) {
                            cardioCoeficient = cardioCoeficient * 1.2;
                        }
                        if (age > 30 && age <= 45) {
                            cardioCoeficient = cardioCoeficient * 2.0;
                        }
                        if (age > 45) {
                            cardioCoeficient = cardioCoeficient * 1.6;
                        }
                        repCoeficient = 10;
                        setCoeficient = 3;
                    } else if (experience.equals("Advanced")) {
                        if (age >= 0 && age <= 20) {
                            cardioCoeficient = cardioCoeficient * 0.5;
                        }
                        if (age > 20 && age <= 30) {
                            cardioCoeficient = cardioCoeficient * 1.2;
                        }
                        if (age > 30 && age <= 45) {
                            cardioCoeficient = cardioCoeficient * 2.0;
                        }
                        if (age > 45) {
                            cardioCoeficient = cardioCoeficient * 1.7;
                        }
                        repCoeficient = 8;
                        setCoeficient = 4;
                    }
                    workout = workout + "\tWEEKLY WORKOUT FOR MALE\n\n";
                    workout = workout + "Metabolism: " + metabolism + "\n";
                    workout = workout + "Experience: " + experience + "\n";
                    workout = workout + "Age: " + String.valueOf(age) + "\n";
                    workout = workout + "Weight: " + String.valueOf(weight) + "\n";
                    workout = workout + "Height: " + String.valueOf(height) + "\n";
                    //Monday
                    workout = workout + "   \nMonday:\n";
                    //Pecs
                    workout = workout + "   \n\tPecs\n";
                    workout = workout + "\t\tBench Press -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
                    if (experience.equals("Beginner")) {
                        workout = workout + "\t\tInclined Bench Press(head up) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
                    }
                    if (experience.equals("Advanced")) {
                        workout = workout + "\t\tInclined Bench Press(head up) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
                        workout = workout + "\t\tDumbell Fly -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
                    }
                    ///Triceps
                    workout = workout + "   \n\tTriceps\n";
                    workout = workout + "\t\tFrench Press -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
                    if (experience.equals("Intermediate")) {
                        workout = workout + "\t\tPull Down extensions -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
                    }
                    if (experience.equals("Advanced")) {
                        workout = workout + "\t\tBech Press(narrow grip) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
                        workout = workout + "\t\tPull Down extensions -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
                    }
                    ///Cardio
                    workout = workout + "   \n\tCardio\n";
                    workout = workout + "\t\tCardio -- " + String.valueOf(Math.round(30 * cardioCoeficient)) + "minutes\n";
                    ///Tuesday
                    workout = workout + "   \nTuesday:\n";
                    workout = workout + "\t Free day\n";
                    //Wendesday
                    workout = workout + "   \nWendesday:\n";
                    ///Back
                    workout = workout + "   \n\tBack\n";
                    workout = workout + "\t\tPull Down(behind the neck) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
                    if (experience.equals("Intermediate")){
                        workout = workout + "\t\tDumbell Rowing -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
                    }
                    if (experience.equals("Advanced")) {
                        workout = workout + "\t\tBarbel Rowing -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
                        workout = workout + "\t\tDumbell Rowing -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
                    }
                    ///Biceps
                    workout = workout + "   \n\tBiceps\n";
                    workout = workout + "\t\tBarbell Curl -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
                    if (experience.equals("Intermediate")) {
                        workout = workout + "\t\tScott Bench(with Dumbbelll) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
                    }
                    if (experience.equals("Advanced")) {
                        workout = workout + "\t\tDumbell Rowing(sitting) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
                        workout = workout + "\t\tScott Bench(with Dumbell) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
                    }
                    ///Cardio
                    workout = workout + "   \n\tCardio\n";
                    workout = workout + "\t\tCardio -- " + String.valueOf(Math.round(30 * cardioCoeficient)) + "minutes\n";
                    //Thursday
                    workout = workout + "   \nThursday:\n";
                    workout = workout + "\t Free day\n";
                    //Friday
                    workout = workout + "   \nFriday:\n";
                    ///Legs
                    workout = workout + "   \n\tLegs\n";
                    workout = workout + "\t\tLeg Extension -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
                    workout = workout + "\t\tLeg Curl -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
                    if (experience.equals("Intermediate")) {
                        workout = workout + "\t\tSquats -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
                    }
                    if (experience.equals("Advanced")) {
                        workout = workout + "\t\tSquats -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
                        workout = workout + "\t\tLeg Press -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
                    }
                    ///Shoulders
                    workout = workout + "   \n\tShoulders\n";
                    workout = workout + "\t\tBarbell Press(behind neck) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
                    if (experience.equals("Intermediate")) {
                        workout = workout + "\t\tSide Fly -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
                    }
                    if (experience.equals("Advanced")) {
                        workout = workout + "\t\tSide Fly -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
                        workout = workout + "\t\tSide Fly(inclined) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
                        workout = workout + "\t\tFront Pickups(Dumbell) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
                    }
                    ///Cardio
                    workout = workout + "   \n\tCardio\n";
                    workout = workout + "\t\tCardio -- " + String.valueOf(Math.round(30 * cardioCoeficient)) + "minutes\n";
                    //Saturday
                    workout = workout + "   \nSaturday:\n";
                    workout = workout + "\t Free day\n";
                    //Sunday
                    workout = workout + "   \nSunday:\n";
                    workout = workout + "\t Free day\n";
                } else if (gender.equals("Female")) {
                    if (experience.equals("Beginner")) {
                        if (age >= 0 && age <= 20) {
                            cardioCoeficient = cardioCoeficient * 0.7;
                        }
                        if (age > 20 && age <= 30) {
                            cardioCoeficient = cardioCoeficient * 1.5;
                        }
                        if (age > 30 && age <= 45) {
                            cardioCoeficient = cardioCoeficient * 2.2;
                        }
                        if (age > 45) {
                            cardioCoeficient = cardioCoeficient * 1.5;
                        }
                        repCoeficient = 20;
                        setCoeficient = 1;
                    } else if (experience.equals("Intermediate")) {
                        if (age >= 0 && age <= 20) {
                            cardioCoeficient = cardioCoeficient * 0.7;
                        }
                        if (age > 20 && age <= 30) {
                            cardioCoeficient = cardioCoeficient * 1.5;
                        }
                        if (age > 30 && age <= 45) {
                            cardioCoeficient = cardioCoeficient * 2.2;
                        }
                        if (age > 45) {
                            cardioCoeficient = cardioCoeficient * 1.6;
                        }
                        repCoeficient = 15;
                        setCoeficient = 2;
                    } else if (experience.equals("Advanced")) {
                        if (age >= 0 && age <= 20) {
                            cardioCoeficient = cardioCoeficient * 0.7;
                        }
                        if (age > 20 && age <= 30) {
                            cardioCoeficient = cardioCoeficient * 1.5;
                        }
                        if (age > 30 && age <= 45) {
                            cardioCoeficient = cardioCoeficient * 2.2;
                        }
                        if (age > 45) {
                            cardioCoeficient = cardioCoeficient * 1.7;
                        }
                        repCoeficient = 12;
                        setCoeficient = 3;
                    }
                    workout = workout + "\tWEEKLY WORKOUT FOR FEMALE\n\n";
                    workout = workout + "Metabolism: " + metabolism + "\n";
                    workout = workout + "Experience: " + experience + "\n";
                    workout = workout + "Age: " + String.valueOf(age) + "\n";
                    workout = workout + "Weight: " + String.valueOf(weight) + "\n";
                    workout = workout + "Height: " + String.valueOf(height) + "\n";
                    //Monday
                    workout = workout + "   \nMonday:\n";
                    ///Pecs
                    workout = workout + "   \n\tPecs\n";
                    workout = workout + "\t\tBench Press -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
                    if (experience.equals("Intermediate")) {
                        workout = workout + "\t\tInclined Dumbell Fly(head up) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
                    }
                    if (experience.equals("Advanced")) {
                        workout = workout + "\t\tInclined Dumbell Fly(head up) -- " + String.valueOf(repCoeficient + 2) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
                    }
                    ///Back
                    workout = workout + "   \n\tBack\n";
                    workout = workout + "\t\tPull down(behind the neck) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
                    if (experience.equals("Intermediate")) {
                        workout = workout + "\t\tDumbell Rowing -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
                    }
                    if (experience.equals("Advanced")) {
                        workout = workout + "\t\tDumbell Rowing -- " + String.valueOf(repCoeficient + 2) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
                    }
                    ///Cardio
                    workout = workout + "   \n\tCardio\n";
                    workout = workout + "\t\tCardio -- " + String.valueOf(Math.round(45 * cardioCoeficient)) + "minutes\n";
                    //Tuesday
                    workout = workout + "   \nTuesday:\n";
                    workout = workout + "\t Free day\n";
                    //Wendesday
                    workout = workout + "   \nWendesday:\n";
                    ///Biceps
                    workout = workout + "   \n\tBiceps\n";
                    workout = workout + "\t\tDumbell Rowing(standing) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
                    if (experience.equals("Intermediate")) {
                        workout = workout + "\t\tDumbell Rowing(standing) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
                    }
                    if (experience.equals("Advanced")) {
                        workout = workout + "\t\tDumbell Rowing(standing) -- " + String.valueOf(setCoeficient + 1) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
                    }
                    ///Triceps
                    workout = workout + "   \n\tTriceps\n";
                    workout = workout + "\t\tPull Down Extensions -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
                    if (experience.equals("Intermediate")) {
                        workout = workout + "\t\tKick Back(Dumbell) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
                    }
                    if (experience.equals("Advanced")) {
                        workout = workout + "\t\tKick Back(Dumbell) -- " + String.valueOf(setCoeficient + 1) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
                    }
                    ///Cardio
                    workout = workout + "   \n\tCardio\n";
                    workout = workout + "\t\tCardio -- " + String.valueOf(Math.round(45 * cardioCoeficient)) + "minutes\n";
                    //Thursday
                    workout = workout + "   \nThursday:\n";
                    workout = workout + "\t Free day\n";
                    //Friday
                    workout = workout + "   \nFriday:\n";
                    ///Legs
                    workout = workout + "   \n\tLegs\n";
                    workout = workout + "\t\tLeg Extension -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
                    workout = workout + "\t\tLeg Curl -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
                    if (experience.equals("Intermediate")) {
                        workout = workout + "\t\tLeg Press -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
                    }
                    if (experience.equals("Advanced")) {
                        workout = workout + "\t\tLeg Press -- " + String.valueOf(setCoeficient + 1) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
                    }
                    ///Shoulders
                    workout = workout + "   \n\tShoulders\n";
                    workout = workout + "\t\tSide Fly -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
                    if (experience.equals("Intermediate")) {
                        workout = workout + "\t\tFront Pickups(Dumbell) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
                    }
                    if (experience.equals("Advanced")) {
                        workout = workout + "\t\tSide Fly(inclined) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
                    }
                    ///Cardio
                    workout = workout + "   \n\tCardio\n";
                    workout = workout + "\t\tCardio -- " + String.valueOf(Math.round(45 * cardioCoeficient)) + "minutes\n";
                    //Saturday
                    workout = workout + "   \nSaturday:\n";
                    workout = workout + "\t Free day\n";
                    //Sunday
                    workout = workout + "   \nSunday:\n";
                    workout = workout + "\t Free day\n";
                }
                resultTextArea.setText(workout);
            }
        });



        pack();
        setVisible(true);



    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }
}
