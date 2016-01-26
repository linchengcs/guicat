package crosswordsage;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class Grid extends JPanel
{
    protected Square selectedSquare;
    protected ArrayList squares;
    protected Word selectedWord;

    private Crossword crossword;
    private Square startSquare;
    private Square endSquare;
    private boolean doSelectStart = false;
    private boolean doSelectEnd = false;

    public Grid()
    {

    }

    public Grid(Crossword cw)
    {
        this.crossword = cw;
        setFocusable(true); //allow the jpanel to receive focus
        squares = new ArrayList();
        selectedWord = null;
        setLayout(new GridLayout(cw.getHeight(), cw.getWidth()));
        addKeyListener(new KListener());
        this.setBackground(Color.WHITE);
    }

    public void print()
    {
        PrintUtilities.printComponent(this);
    }

    public void setCrossword(Crossword cw)
    {
        this.crossword = cw;

        //set all the squares in the grid to point to the right words
        ArrayList words = cw.getWords();

        for (int k = 0; k < words.size(); k++)
        {
            Word word = (Word) words.get(k);
            //fill in the spaces in between
            if (word.getWordDirection() == Word.ACROSS)
            {
                for (int i = word.getX(), letterIndex = 0;
                        i < (word.getX() + word.getLength()); i++, letterIndex++)
                {
                    Square s = findSquare(word.getY(), i);
                    s.setWord(word);
                    s.setLetterIndexAcross(letterIndex);
                }
            }
            else if (word.getWordDirection() == Word.DOWN)
            {
                for (int i = word.getY(), letterIndex = 0;
                        i < (word.getY() + word.getLength()); i++, letterIndex++)
                {
                    Square s = findSquare(i, word.getX());
                    s.setWord(word);
                    s.setLetterIndexDown(letterIndex);
                }
            }
        }
    }

    public Crossword getCrossword()
    {
        return crossword;
    }

    public void Build()
    {
        for (int i = 0; i < crossword.getHeight(); i++)
        {
            for (int j = 0; j < crossword.getWidth(); j++)
            {
                Square s = new Square(i, j, Color.BLACK);
                s.setResetColour(Color.BLACK);
                s.addMouseListener(new SquareListener());
                squares.add(s);
                s.setFontSize(220/crossword.getWidth());
                add(s);
            }
        }
    }

    public void CreateWord()
    {
        int rowStart = startSquare.getXPos();
        int colStart = startSquare.getYPos();
        int rowEnd = endSquare.getXPos();
        int colEnd = endSquare.getYPos();
        int direction = 0;
        int length = 0;

        //determine direction of word
        if (rowStart == rowEnd)
        {
            direction = Word.ACROSS;
            length = colEnd - colStart + 1;
        }
        else if (colStart == colEnd)
        {
            direction = Word.DOWN;
            length = rowEnd - rowStart + 1;
        }

        Word word = new Word(colStart, rowStart, direction, length);
        crossword.addWord(word);

        //fill in the spaces in between
        if (direction == Word.ACROSS)
        {
            for (int i = colStart, letterIndex = 0; i <= colEnd; i++,
                                                 letterIndex++)
            {
                Square s = findSquare(rowStart, i);
                formatSquare(s);
                s.setWord(word);
                s.setLetterIndexAcross(letterIndex);
                if (s.getLetter() != "")
                {
                    word.addLetter(s.getLetter(), letterIndex);
                }
                //does the square also belong to an already defined down word?
                if (s.getWordDown() != null)
                {
                    s.getWordDown().addLetter(s.getLetter(),
                                              s.getLetterIndexDown());
                }
            }
        }
        else if (direction == Word.DOWN)
        {
            for (int i = rowStart, letterIndex = 0; i <= rowEnd; i++,
                                                 letterIndex++)
            {
                Square s = findSquare(i, colStart);
                formatSquare(s);
                s.setWord(word);
                s.setLetterIndexDown(letterIndex);
                if (s.getLetter() != "")
                {
                    word.addLetter(s.getLetter(), letterIndex);
                }
                //does the square also belong to an already defined down word?
                if (s.getWordAcross() != null)
                {
                    s.getWordAcross().addLetter(s.getLetter(),
                                                s.getLetterIndexAcross());
                }
            }
        }
    }

    public void setSelectedWord(Word w)
    {
        selectedWord = w;
    }

    public void splitSelectedWord(String format)
    {
        selectedWord.setFormat(format);
    }
    public void setClueNumbers()
    {
        int clueNumber = 1;
        boolean incrementClue = false;

        //reset all word clue numbers
        ArrayList words = crossword.getWords();
        for (int i = 0; i < words.size(); i++)
        {
            Word wd = (Word) words.get(i);
            wd.setClueIndex(0);
        }

        //move through each square in order
        for (int i = 0; i < squares.size(); i++)
        {
            Square s = (Square) squares.get(i);
            if (!s.getIsBlank())
            {
                ArrayList al = s.getWords();
                for (int j = 0; j < al.size(); j++)
                {
                    Word w = (Word) al.get(j);
                    //check word has not been numbered already
                    if (w.getClueIndex() == 0)
                    {
                        //allocate the next clue number to the word
                        w.setClueIndex(clueNumber);
                        incrementClue = true;
                    }
                }
                if (incrementClue) clueNumber++;
                incrementClue = false;
            }
        }
        repopulateWords();
    }

    private void formatSquare(Square s)
    {
        s.setBackground(Color.WHITE);
        s.setResetColour(Color.WHITE);
        s.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        s.setIsBlank(false);
    }

    public void deleteSelectedWord()
    {
        //remove word from crossword list
        ArrayList al = crossword.getWords();
        al.remove(selectedWord);
        repopulateWords();
    }

    public ArrayList getSquares()
    {
        return squares;
    }

    public void setDoSelectStart(boolean b)
    {
        startSquare = null;
        doSelectStart = b;
    }

    public void setDoSelectEnd(boolean b)
    {
        doSelectEnd = b;
    }

    public boolean getDoSelectStart()
    {
        return doSelectStart;
    }

    public boolean getDoSelectEnd()
    {
        return doSelectEnd;
    }

    public Square findSquare(int x, int y)
    {
        Square s = null;
        for (int i = 0; i < squares.size(); i++)
        {
            Square s2 = (Square) squares.get(i);
            if (s2.getXPos() == x && s2.getYPos() == y)
            {
                s = s2;
            }
        }
        return s;
    }

    public String getSelectedClue()
    {
        String s = null;
        //if(selectedSquare != null)
        //{

        //  s = ((Word) selectedSquare.getWord()).getClue();
        //}

        if (selectedWord != null)
        {
            s = selectedWord.getClue();
        }
        return s;
    }

    public ArrayList getSelectedWords()
    {
        return selectedSquare.getWords();
    }

    public Word getSelectedWord()
    {
        return selectedWord;
    }

    private void blankSquares()
    {
        for (int i = 0; i < squares.size(); i++)
        {
            Square s = (Square) squares.get(i);
            s.setBackground(Color.BLACK);
            s.blank();
        }
    }

    private void dissociateSquares()
    {
        for (int i = 0; i < squares.size(); i++)
        {
            Square s = (Square) squares.get(i);
            if (s.getIsBlank())
            {
                s.setNoWord();
            }
        }
    }

    public void writeClue()
    {
        //String s = JOptionPane.showInputDialog(null,"Please enter your clue.");

        String s = JOptionPane.showInputDialog("Please enter your clue.",
                                               selectedWord.getClue());
        //Word w = selectedSquare.getWord();
        selectedWord.setClue(s);
    }

    public void hiLightSelectedWord()
    {
        if (selectedSquare != null)
        {
            ArrayList al = selectedSquare.getWords();
            if (al != null && al.size() > 0)
            {
                Word w = (Word) getSelectedWords().get(0);

                if (w.getWordDirection() == Word.ACROSS)
                {
                    for (int i = w.getX(); i < w.getX() + w.getLength(); i++)
                    {
                        Square s = findSquare(w.getY(), i);
                        if (s != selectedSquare)
                        {
                            s.setBackground(Color.PINK);
                        }
                    }
                }
                else if (w.getWordDirection() == Word.DOWN)
                {
                    for (int i = w.getY(); i < w.getY() + w.getLength(); i++)
                    {
                        Square s = findSquare(i, w.getX());
                        if (s != selectedSquare)
                        {
                            s.setBackground(Color.PINK);
                        }
                    }
                }
            }
        }
    }

    public void repopulateWords()
    {
        //set all squares to blank
        blankSquares();

        //repopulate words
        ArrayList al = crossword.getWords();
        for (int i = 0; i < al.size(); i++)
        {
            Word w = (Word) al.get(i);
            ArrayList letters = w.getLetters();

            if (w.getWordDirection() == Word.ACROSS)
            {
                for (int j = 0; j < letters.size(); j++)
                {
                    Square s = findSquare(w.getY(), w.getX() + j);
                    if (s.getLetter() == " " || s.getLetter() == "*" ||
                        s.getLetter() == "")
                    {
                        String let = (String) letters.get(j);
                        if (let == "*")
                        {
                            let = " ";
                        }
                        s.setLetter(let);
                    }

                    s.setBackground(Color.WHITE);
                    s.setBorder(BorderFactory.createLineBorder(Color.BLACK,
                            1));

                    if (s.isAnyWordSelected())
                    {
                        s.setBackground(Color.PINK);
                        s.setResetColour(Color.PINK);
                    }
                    //place the clue number
                    if (j == 0) //ie. first square of word
                    {
                        s.setClueNumber(w.getClueIndex());
                    }
                    if (s == selectedSquare)
                    {
                        s.setBackground(Color.RED);
                        s.setResetColour(Color.RED);
                    }

                }
            }
            else if (w.getWordDirection() == Word.DOWN)
            {

                for (int j = 0; j < letters.size(); j++)
                {
                    Square s = findSquare(w.getY() + j, w.getX());
                    if (s.getLetter() == " " || s.getLetter() == "*" ||
                        s.getLetter() == "")
                    {
                        String let = (String) letters.get(j);

                        if (let == "*")
                        {
                            let = " ";
                        }
                        s.setLetter(let);
                    }
                    s.setBackground(Color.WHITE);
                    s.setBorder(BorderFactory.createLineBorder(Color.BLACK,
                            1));

                    if (s.isAnyWordSelected())
                    {
                        s.setBackground(Color.PINK);
                        s.setResetColour(Color.PINK);
                    }

                    //place the clue number
                    if (j == 0) //ie. first square of word
                    {
                        s.setClueNumber(w.getClueIndex());
                    }
                    if (s == selectedSquare)
                    {
                        s.setBackground(Color.RED);
                        s.setResetColour(Color.RED);
                    }
                }
            }
        }
        //dissociate any blank squares from legacy word relationships
        dissociateSquares();
        validate();
    }

    private void jbInit()
            throws Exception
    {

    }

    /**
     * Remove listeners from all grid squares
     */
    public void removeListeners()
    {
        for (int i = 0; i < squares.size(); i++)
        {
            Square s = (Square) squares.get(i);
            SquareListener ml = (SquareListener) s.getMouseListeners()[0];
            s.removeMouseListener(ml);
        }
    }

    class SquareListener implements MouseListener
    {
        public void mouseClicked(MouseEvent e)
        {
            //return old selected square to non-selected colour
            if (selectedSquare != null)
            {
                if (selectedSquare.getIsBlank())
                {
                    selectedSquare.setBackground(Color.BLACK);
                }
                else
                {
                    selectedSquare.setBackground(Color.WHITE);
                }

                if (selectedWord != null)
                {
                    selectedWord.setIsSelected(false);
                }
            }

            requestFocus(); //give focus to the grid to capture key strokes
            Square sq = (Square) e.getSource();
            selectedSquare = sq;

            if (doSelectStart)
            {
                sq.setBackground(Color.WHITE);
                sq.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                sq.setIsBlank(false);
                startSquare = sq;
                startSquare.setBackground(Color.BLUE);
                startSquare.setResetColour(Color.BLUE);
                doSelectStart = false;
                doSelectEnd = true;
            }
            else if (doSelectEnd)
            {
                //check that this square is in line with the first
                if (sq.getX() != startSquare.getX() &&
                    sq.getY() != startSquare.getY())
                {
                    JOptionPane.showMessageDialog(null,
                            "Words must be horizontal or vertical only");
                }
                else if (sq.getX() < startSquare.getX() ||
                         sq.getY() < startSquare.getY())
                {
                    JOptionPane.showMessageDialog(null,
                            "The last square of the word must not come before the first square of the word.");
                }
                else
                {
                    sq.setBackground(Color.WHITE);
                    sq.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                    sq.setIsBlank(false);
                    endSquare = sq;
                    startSquare.setBackground(Color.WHITE);
                    startSquare.setResetColour(Color.WHITE);
                    doSelectEnd = false;
                    CreateWord();
                    selectedWord = selectedSquare.getNextWord();
                    if (selectedWord != null) selectedWord.setIsSelected(true);
                    repopulateWords();
                }
            }
            else
            {
                selectedWord = selectedSquare.getNextWord();
                if (selectedWord != null) selectedWord.setIsSelected(true);
                repopulateWords();
            }
        }

        public void mouseEntered(MouseEvent e)
        {
            Square sq = (Square) e.getSource();
            if (doSelectEnd || doSelectStart)
            {
                sq.setResetColour(sq.getBackground());
                sq.setBackground(new Color(170, 255, 175));
            }
            else
            {
                sq.setResetColour(sq.getBackground());
            }
            validate();
        }

        public void mouseExited(MouseEvent e)
        {
            Square sq = (Square) e.getSource();
            if ((doSelectEnd || doSelectStart) && sq.equals(startSquare))
            {
                sq.setBackground(Color.BLUE);
            }
            else
            {
                sq.setBackground(sq.getResetColour());
            }

            validate();
        }

        public void mousePressed(MouseEvent e)
        {
        }

        public void mouseReleased(MouseEvent e)
        {
        }
    }


    class KListener implements KeyListener
    {
        public void keyPressed(KeyEvent e)
        {
        }

        public void keyReleased(KeyEvent e)
        {
        }

        public void keyTyped(KeyEvent e)
        {
            String s = null;
            if (e.getKeyChar() == '\b')
            {
                s = " ";
            }
            else
            {
                s = String.valueOf(e.getKeyChar());
            }
            selectedSquare.setLetter(s);

            //add the letter to the backend word object(s)
            ArrayList al = selectedSquare.getWords();
            for (int i = 0; i < al.size(); i++)
            {
                Word w = (Word) al.get(i);
                if (w.getWordDirection() == Word.ACROSS)
                {
                    w.addLetter(s, selectedSquare.getLetterIndexAcross());
                }
                else if (w.getWordDirection() == Word.DOWN)
                {
                    w.addLetter(s, selectedSquare.getLetterIndexDown());
                }
            }

            //move to next square
            validate();
        }
    }
}
