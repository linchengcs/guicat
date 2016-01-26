package crosswordsage;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;

public class Square extends JPanel
{
    public Square()
    {
    }

    private int xPos;
    private int yPos;
    private JLabel lblLetter = null;
    private JLabel lblClue = null;
    private boolean isBlank;
    private Word wordAcross = null;
    private Word wordDown = null;
    private int letterIndexAcross = 0;
    private int letterIndexDown = 0;
    private Box vertBox;
    private Box midBox;
    private Box clueBox;
    private Color resetColour;
    private boolean directionSwitch = false;
    private boolean isCorrect = true;

    public Square(int xPos, int yPos, Color c)
    {
        super();
        isBlank = true;
        this.xPos = xPos;
        this.yPos = yPos;
        setBackground(c);
        setLayout(new BorderLayout());
        lblLetter = new JLabel();
        lblClue = new JLabel();
        lblClue.setFont(new Font("Arial",Font.PLAIN,9));
        lblClue.setPreferredSize(new Dimension(20,30));
        lblClue.setMinimumSize(new Dimension(20,30));
        lblLetter.setAlignmentX(0f);
        lblLetter.setAlignmentY(0f);
        //lblLetter.setPreferredSize(new Dimension(10,10));
        //lblLetter.setMinimumSize(new Dimension(3,3));
        //lblLetter.setMaximumSize(new Dimension(this.getWidth(), this.getHeight()));
        lblClue.setText("");


        vertBox = Box.createVerticalBox();
        midBox =  Box.createHorizontalBox();
        clueBox = Box.createHorizontalBox();
        clueBox.setPreferredSize(new Dimension(10,10));
        clueBox.setMinimumSize(new Dimension(10,10));
        clueBox.setMaximumSize(new Dimension(10,10));
        midBox.setPreferredSize(new Dimension(20,20));
        midBox.setMinimumSize(new Dimension(20,20));
        midBox.add(lblLetter);
        clueBox.add(lblClue);
        vertBox.add(clueBox);
        vertBox.add(midBox);
        add(vertBox,BorderLayout.CENTER);
    }

    public void paintComponent(Graphics g)
    {

        super.paintComponent(g);
        if(!isCorrect)
        {
            Graphics2D g2 = (Graphics2D) g;
            g2.setPaint(Color.BLUE);
            g2.setStroke(new BasicStroke(2));
            g2.drawLine(0, 0, this.getWidth(), this.getHeight());
            g2.drawLine(this.getWidth(), 0, 0, this.getHeight());
        }
    }

    public int getXPos()
    {
        return xPos;
    }

    public int getYPos()
    {
        return yPos;
    }

    public void setIsBlank(boolean b)
    {
        isBlank = b;
    }

    public boolean getIsBlank()
    {
        return isBlank;
    }

    public void blank()
    {
        midBox.remove(lblLetter);
        lblLetter.setText("");
        clueBox.remove(lblClue);
        isBlank = true;
    }

    public void hideLetter()
    {
        midBox.remove(lblLetter);
    }

    public void removeLetter()
    {
        lblLetter.setText("");
        validate();
    }

    public void setNoWord()
    {
        wordAcross = null;
        wordDown = null;
    }

    public void setClueNumber(int i)
    {
        lblClue.setText(String.valueOf(i));
        clueBox.add(lblClue);
    }

    public void setLetter(String s)
    {
        lblLetter.setText(s.toUpperCase());
        midBox.add(lblLetter);
        isBlank = false;
        validate();
    }

    public void setWord(Word w)
    {
        if(w.getWordDirection() == Word.ACROSS) wordAcross = w;
        else if(w.getWordDirection() == Word.DOWN) wordDown = w;
    }

    public Word getWord()
    {
        if(!isBlank)
        {
            if (wordAcross != null)return wordAcross;
            else return wordDown;
        }
        return null;
    }

    public Word getNextWord()
    {
        if(!isBlank)
        {
            if(directionSwitch)
            {
                directionSwitch = false;
                if (wordAcross != null)return wordAcross;
                else return wordDown;
            }
            else if(!directionSwitch)
            {
                directionSwitch = true;
                if (wordDown != null)return wordDown;
                else return wordAcross;
            }

        }
        return null;

    }

    public Word getWordAcross()
    {
        return wordAcross;
    }

    public Word getWordDown()
    {
        return wordDown;
    }

    public ArrayList getWords()
    {
        ArrayList al = new ArrayList(2);
        if(wordAcross != null) al.add(wordAcross);
        if(wordDown != null) al.add(wordDown);
        return al;
    }

    public String getLetter()
    {
        return lblLetter.getText();
    }

    public boolean isAnyWordSelected()
    {
        if(wordAcross != null)
        {
            if(wordAcross.getIsSelected()) return true;
        }
        if(wordDown != null)
        {
            if(wordDown.getIsSelected()) return true;
        }
        return false;
    }

    public void setLetterIndexAcross(int i)
    {
        letterIndexAcross = i;
    }

    public int getLetterIndexAcross()
    {
        return letterIndexAcross;
    }

    public void setLetterIndexDown(int i)
    {
        letterIndexDown = i;
    }

    public int getLetterIndexDown()
    {
        return letterIndexDown;
    }

    public Color getResetColour()
    {
        return resetColour;
    }

    public void setResetColour(Color c)
    {
        resetColour = c;
    }

    public void setIsCorrect(boolean b)
    {
        isCorrect = b;
    }

    public boolean getIsCorrect()
    {
        return isCorrect;
    }

    public void setFontSize(int i)
    {
        lblLetter.setFont(new Font("Arial",Font.BOLD,i));
    }

    class MyListener implements KeyListener
    {
        public void keyPressed(KeyEvent e)
        {
            JOptionPane.showConfirmDialog(null, "Hello");
        }

        public void keyReleased(KeyEvent e)
        {
        }

        public void keyTyped(KeyEvent e)
        {
        }
    }

}
