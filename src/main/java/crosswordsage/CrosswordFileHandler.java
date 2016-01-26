package crosswordsage;

import java.io.File;
import java.io.ObjectInputStream;
import javax.swing.JFileChooser;
import java.io.FileInputStream;
import javax.swing.JFrame;

public class CrosswordFileHandler
{
    public CrosswordFileHandler()
    {
    }

    public static Crossword loadCrossword(JFrame jf)
    {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(jf);
        File f = chooser.getSelectedFile();

        ObjectInputStream ois = null;
        try
        {
            ois = new ObjectInputStream(new FileInputStream(f));
            Crossword cw = (Crossword)ois.readObject();
            ois.close();
            return cw;
        }
        catch (Exception e)
        {
            System.out.print(e.getStackTrace());
        }
        return null;
    }
}
