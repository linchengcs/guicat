package crosswordsage;

import java.util.regex.Pattern;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.regex.Matcher;
import java.io.File;

public class Utilities
{
    public Utilities()
    {
    }

    public static ArrayList getMatches(String s)
    {
        File f = new File("wordlist.txt");

        s = s.toLowerCase();

        //build the pattern to match
        StringBuffer sb = new StringBuffer(100);
        sb.append("^");
        ArrayList wordList = new ArrayList();
        for(int i=0; i<s.length(); i++)
        {
            if(s.charAt(i) == '*' || s.charAt(i) == ' ')
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
            JOptionPane.showMessageDialog(null,"Crossword Sage could not find the word list. \n Please make sure the file 'wordlist.txt' is located in the same directory as the executable.", "File not found", JOptionPane.OK_OPTION);
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
        return wordList;
    }

}
