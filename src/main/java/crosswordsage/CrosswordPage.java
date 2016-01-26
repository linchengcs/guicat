package crosswordsage;

import java.awt.print.*;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CrosswordPage extends JFrame
{
    private Box mainBox = Box.createVerticalBox();
    private JLabel lblTitle = new JLabel();
    private Box cwBox = Box.createHorizontalBox();
    private Box clueBox = Box.createHorizontalBox();
    private JTextPane tpAcross = new JTextPane();
    private JTextPane tpDown = new JTextPane();
    private Grid grid;
    private String downClues;
    private String acrossClues;
    private Crossword crossword;

    public CrosswordPage()
    {
        lblTitle.setText("");
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void setCrossword(Grid g)
    {
        grid = g;
        cwBox.add(g);

        this.setBounds(0,0,g.getWidth()+10,g.getHeight()+300);

        //remove all letters
        ArrayList squares = grid.getSquares();
        for(int i=0; i<squares.size(); i++)
        {
            Square s = (Square)squares.get(i);
            s.removeLetter();
        }
    }

    public void setTitle(String s)
    {
        lblTitle.setText(s);
    }

    public void setCrossword(Crossword cw)
    {
        crossword = cw;
        grid = new Grid(cw);
        grid.setMinimumSize(new Dimension(400, 400));
        grid.setPreferredSize(new Dimension(400, 400));
        grid.setMaximumSize(new Dimension(400, 400));
        grid.setBackground(Color.WHITE);

        grid.Build();

        grid.setCrossword(cw);
        grid.repopulateWords();

        cwBox.add(grid);
        this.setBounds(0,0,grid.getMinimumSize().width+10,grid.getMinimumSize().height+300);

        //remove all letters
        ArrayList squares = grid.getSquares();
        for(int i=0; i<squares.size(); i++)
        {
            Square s = (Square)squares.get(i);
            s.removeLetter();
        }
        grid.removeListeners();
    }

    public void setDownClues(String s)
    {
        downClues = s;
        tpDown.setText(s);
    }

    public void setAcrossClues(String s)
    {
        acrossClues = s;
        tpAcross.setText(s);
    }

    private void jbInit()
            throws Exception
    {
        this.setBounds(0, 0, 800, 800);
        this.setBackground(Color.WHITE);
        this.getContentPane().setBackground(Color.WHITE);
        clueBox = Box.createHorizontalBox();
        cwBox = Box.createHorizontalBox();
        cwBox.setAlignmentX(0f);
        mainBox.setBackground(new Color(199, 223, 236));
        tpAcross.setText("jTextPane1");
        tpDown.setText("jTextPane2");
        this.getContentPane().add(mainBox, BorderLayout.CENTER);
        mainBox.add(lblTitle);
        mainBox.add(Box.createVerticalStrut(10));
        mainBox.add(cwBox);
        mainBox.add(clueBox);
        clueBox.add(tpAcross);
        clueBox.add(tpDown);
        clueBox.setMaximumSize(new Dimension(1000,1000));
    }
}
