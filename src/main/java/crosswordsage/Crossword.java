package crosswordsage;

import java.util.ArrayList;
import java.io.*;

public class Crossword implements Serializable
{
    public Crossword()
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

    private ArrayList words;
    private int width;  //number of squares
    private int height; //number of squares
    private boolean isEditable;

    public Crossword(int width, int height)
    {
        isEditable = true;
        this.width = width;
        this.height = height;
        words = new ArrayList();
    }

    public boolean getIsEditable()
    {
        return isEditable;
    }

    public void setIsEditable(boolean b)
    {
        isEditable = b;
    }

    public int getHeight()
    {
        return height;
    }

    public int getWidth()
    {
        return width;
    }

    public void addWord(Word w)
    {
        words.add(w);
    }

    public ArrayList getWords()
    {
        return words;
    }

    public void setWords(ArrayList al)
    {
        words = al;
    }

    private void jbInit()
            throws Exception
    {
    }
}
