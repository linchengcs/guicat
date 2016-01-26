package crosswordsage;

import java.util.*;
import java.io.*;

public class Word implements Serializable
{
    private int x;  //position on grid
    private int y;
    private int length;
    private String word;  //the actual word
    private ArrayList letters;
    private int wordDirection;
    private int clueIndex;
    private String clue;
    private boolean isSelected;
    private String format = null;

    public final static int DOWN = 0;
    public final static int ACROSS = 1;

    public Word(int x, int y, int wordDirection, int length)
    {
        this.x =x;
        this.y = y;
        this.wordDirection = wordDirection;
        this.length = length;
        clue = null;
        letters = new ArrayList(length);
        for(int i=0; i<length; i++)
        {
            letters.add("*");
        }
        clueIndex = 0;
    }

    public void setWord(String s)
    {
        word = s;
        syncLetters();
    }

    public void setFormat(String s)
    {
        format = s;
    }

    public String getFormat()
    {
        return format;
    }

    public void setClue(String s)
    {
        clue = s;
    }

    public String getClue()
    {
        return clue;
    }

    public String getWord()
    {
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<length; i++)
        {
            sb.append(letters.get(i));
        }
        return sb.toString();
    }

    public int getWordDirection()
    {
        return wordDirection;
    }

    public void addLetter(String s, int index)
    {
        letters.set(index,s);
    }

    public ArrayList getLetters()
    {
        return letters;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void setClueIndex(int i)
    {
        clueIndex = i;
    }

    public int getClueIndex()
    {
        return clueIndex;
    }

    public int getLength()
    {
        return length;
    }

    public boolean getIsSelected()
    {
        return isSelected;
    }

    public void setIsSelected(boolean b)
    {
        isSelected = b;
    }

    private void syncLetters()
    {
        for(int i=0; i<length; i++)
        {
            char c = word.charAt(i);
            letters.set(i,String.valueOf(c));
        }
    }
}
