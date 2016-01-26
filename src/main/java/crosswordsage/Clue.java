package crosswordsage;

public class Clue
{
    private Word word;
    private String clue;
    private boolean isHeader = false;

    public Clue()
    {
    }

    public Clue(String s)
    {
        clue = s;
    }

    public void makeHeader()
    {
        isHeader = true;
    }

    public Word getWord()
    {
        return word;
    }

    public void setWord(Word w)
    {
        word = w;
    }

    public String getClue()
    {
        return clue;
    }

    public void setClue(String s)
    {
        clue = s;
    }

    public String toString()
    {
        String s = null;
        if(!isHeader)
        {
            s = String.valueOf(word.getClueIndex()) + ". " + clue;
        }
        else
        {
            s = clue;
        }
        return s;
    }
}
