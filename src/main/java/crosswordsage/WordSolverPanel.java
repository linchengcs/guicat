package crosswordsage;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.*;
import java.util.*;
import java.awt.*;
import java.io.*;
import javax.swing.border.Border;

public class WordSolverPanel extends JPanel
{
    private JTextField jTextField1 = new JTextField();
    private JButton jButton1 = new JButton();
    private JScrollPane jScrollPane1 = null;
    private JTextArea jTextArea1 = new JTextArea();
    private JPanel jPanel1 = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private Box box1 = Box.createHorizontalBox();
    private Box box2 = Box.createVerticalBox();
    private Box box3 = Box.createHorizontalBox();
    private JTextField tbAnagram = new JTextField();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private Border border1 = BorderFactory.createLineBorder(Color.black, 2);
    private File f;

    public WordSolverPanel()
    {
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void jbInit()
            throws Exception
    {
        f = new File("wordlist.txt");
        box3 = Box.createHorizontalBox();
        this.setBackground(new Color(184, 171, 131));
        this.setPreferredSize(new Dimension(400, 400));
        this.setLayout(borderLayout1);
        jTextField1.setBorder(BorderFactory.createLineBorder(Color.black));
        jTextField1.setPreferredSize(new Dimension(100, 21));
        jTextField1.setText("");
        jButton1.setBorder(border1);
        jButton1.setPreferredSize(new Dimension(150, 27));
        jButton1.setToolTipText("");
        jButton1.setText("Find Possible Matches");
        jScrollPane1 = new JScrollPane(jTextArea1);
        jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.
                                                  HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.
                                                VERTICAL_SCROLLBAR_ALWAYS);
        jTextArea1.setFont(new java.awt.Font("Arial", Font.PLAIN, 12));
        jTextArea1.setBorder(BorderFactory.createLineBorder(Color.black));
        jTextArea1.setColumns(30);
        jTextArea1.setRows(15);
        tbAnagram.setBorder(BorderFactory.createLineBorder(Color.black));
        tbAnagram.setPreferredSize(new Dimension(50, 21));
        jLabel1.setText("Pattern (use * for unknown letters): ");
        jLabel2.setText("Word is an anagram of: ");
        jPanel1.setBackground(new Color(174, 190, 212));
        jPanel1.setBorder(BorderFactory.createLineBorder(Color.black));
        jScrollPane1.getViewport().add(jTextArea1);
        jPanel1.add(box2);
        box2.add(box1);
        jPanel1.add(jButton1);
        box3.add(jLabel2);
        box3.add(tbAnagram);
        box1.add(jLabel1);
        box1.add(jTextField1);
        box2.add(box3);
        this.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        this.add(jPanel1, java.awt.BorderLayout.NORTH);
        jButton1.addActionListener(new ButtonListener());
    }

    class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            ArrayList words = getMatches(jTextField1.getText().toLowerCase());
            jTextArea1.setText("");

            //do pattern matches
            jTextArea1.append("####### PATTERN MATCHES #######\n");
            if(words.size() == 0)
            {

                jTextArea1.append("<NO MATCHES FOUND>");
            }
            else
            {
                for (int i = 0; i < words.size(); i++)
                {
                    jTextArea1.append((String) words.get(i));
                    jTextArea1.append("\n");
                }
            }

            if(!tbAnagram.getText().equals(""))   //only check anagrams if specified
            {
                ArrayList anWords = getAnagrams(tbAnagram.getText().toLowerCase());
                //do anagram matches
                jTextArea1.append("\n\n####### ANAGRAM MATCHES #######\n");
                if (anWords.size() == 0)
                {
                    jTextArea1.append("<NO MATCHES FOUND>");
                }
                else
                {
                    for (int i = 0; i < anWords.size(); i++)
                    {
                        if(isTightAnagram((String)anWords.get(i), tbAnagram.getText())){
                            jTextArea1.append((String) anWords.get(i));
                            jTextArea1.append("\n");
                        }
                    }
                }

                //find common words
                jTextArea1.append("\n\n####### COMMON WORDS #######\n");
                for (int i = 0; i < anWords.size(); i++)
                {
                    String s1 = (String) anWords.get(i);
                    //does this string exist in the pattern match word list?
                    for (int j = 0; j < words.size(); j++)
                    {
                        String s2 = (String) words.get(j);
                        if (s1.equals(s2))
                        {
                            jTextArea1.append(s2 + "\n");
                        }
                    }
                }
            }


            if(!tbAnagram.getText().equals(""))   //only check anagrams if specified
            {
                ArrayList anWords = getAnagrams(tbAnagram.getText().toLowerCase());
                //do anagram matches
                jTextArea1.append("\n\n####### LOOSE ANAGRAM MATCHES #######\n");
                if (anWords.size() == 0)
                {
                    jTextArea1.append("<NO MATCHES FOUND>");
                }
                else
                {
                    for (int i = 0; i < anWords.size(); i++)
                    {
                        jTextArea1.append((String) anWords.get(i));
                        jTextArea1.append("\n");
                    }
                }
            }
            jTextArea1.setCaretPosition(0);  //scroll text box to top
        }
    }

    public ArrayList getAnagrams(String s)
    {
        ArrayList wordList = new ArrayList();
        //build the pattern to match
        StringBuffer sb = new StringBuffer(15);
        sb.append("^[");
        for(int i=0; i<s.length(); i++)
        {
            sb.append(s.charAt(i));
        }
        sb.append("]{" + s.length() + "}$");

        //read from the word file
        FileReader fr = null;
        BufferedReader br = null;
        try
        {
            fr = new FileReader(f);
            br = new BufferedReader(fr);
            boolean b = true;

            //read through all lines
            while(b)
            {
                String word = br.readLine();
                if(word == null) b = false;  //end has been reached
                else
                {
                    //try and find a match to the specified pattern
                    Pattern p = Pattern.compile(sb.toString());
                    Matcher m = p.matcher(word);
                    boolean result = m.find();
                    if (result)
                    {
                        wordList.add(word);   // word was a match
                    }
                }
            }
        }
        catch (FileNotFoundException ex)
        {
            System.out.println(ex.toString());
            JOptionPane.showMessageDialog(this,"Crossword Sage could not find the word list. \n Please make sure the file 'wordlist.txt' is located in the same directory as the executable.", "File not found", JOptionPane.OK_OPTION);
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }

        return wordList;
    }

    public ArrayList getMatches(String s)
    {
        //build the pattern to match
        StringBuffer sb = new StringBuffer(15);
        sb.append("^");
        ArrayList wordList = new ArrayList();
        for(int i=0; i<s.length(); i++)
        {
            if(s.charAt(i) == '*')
            {
                sb.append(".");
            }
            else
            {
                sb.append(s.charAt(i));
            }
        }
        sb.append("$");

        //read from the word file
        FileReader fr = null;
        BufferedReader br = null;
        try
        {
            fr = new FileReader(f);
            br = new BufferedReader(fr);

            boolean b = true;

            //read through all lines
            while(b)
            {
                String word = br.readLine();
                if(word == null) b = false;  //end has been reached
                else
                {
                    //try and find a match to the specified pattern
                    Pattern p = Pattern.compile(sb.toString());
                    Matcher m = p.matcher(word);
                    boolean result = m.find();
                    if (result)
                    {
                        wordList.add(word);   // word was a match
                    }
                }
            }
        }
        catch (FileNotFoundException ex)
        {
            System.out.println(ex.toString());
            JOptionPane.showMessageDialog(this,"Crossword Sage could not find the word list. \n Please make sure the file 'wordlist.txt' is located in the same directory as the executable.", "File not found", JOptionPane.OK_OPTION);
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
        return wordList;
    }

    private boolean isTightAnagram(String s1, String s2)
    {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        char[] data1 = s1.toCharArray();
        char[] data2 = s2.toCharArray();
        Arrays.sort(data1);
        Arrays.sort(data2);
        return Arrays.equals(data1, data2);
    }
}
