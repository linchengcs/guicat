package crosswordsage;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;

public class CrosswordCompiler extends JPanel
{
    private static int MARGIN_GAP = 8;

    private Grid grid;
    private Box vertBox;
    private Box topBox;
    private Box clueBox;
    private Box boxButtons;
    private Crossword cw;
    private JList jList1 = new JList();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JButton btnAddWord = new JButton();
    private JButton btnDeleteWord = new JButton();
    private JButton btnGetClue = new JButton();
    private JButton btnSuggestWords = new JButton();
    private JButton btnSetNumbers = new JButton();
    private JButton btnWriteClue = new JButton();
    private Border brdThinGrey = BorderFactory.createLineBorder(Color.gray, 1);
    private Border brdMedBlack = BorderFactory.createLineBorder(Color.BLACK, 2);
    private JTextPane clueTextPane = new JTextPane();
    private StringBuffer acrossClues;
    private StringBuffer downClues;
    private boolean doSaveCheck = false;

    public CrosswordCompiler(int width, int height)
    {
        acrossClues = new StringBuffer();
        downClues = new StringBuffer();
        cw = new Crossword(width, height);
        grid = new Grid(cw);
        this.setLayout(new BorderLayout());
        DisplayGrid();
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        AddListeners();

    }

    public CrosswordCompiler()
    {
    }

    public void publishCrossword()
    {
        grid.getCrossword().setIsEditable(false);
    }

    public void loadCrossword(JFrame jf)
    {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(jf);
        File f = chooser.getSelectedFile();

        ObjectInputStream ois = null;
        try
        {
            ois = new ObjectInputStream(new FileInputStream(f));

            acrossClues = new StringBuffer();
            downClues = new StringBuffer();
            cw = (Crossword) ois.readObject();

            if(cw.getIsEditable() == true)
            {

                grid = new Grid(cw);
                this.setLayout(new BorderLayout());
                DisplayGrid();
                try
                {
                    jbInit();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                AddListeners();

                grid.setCrossword(cw);
                grid.repopulateWords();
                validate();
            }
            else
            {
                JOptionPane.showMessageDialog(null,"This crossword has been published and can't be edited");
            }
            ois.close();
        }
        catch (Exception e)
        {
            System.out.print(e.getStackTrace());
        }
    }



    public void saveCrossword(JFrame jf)
    {
        JFileChooser chooser = new JFileChooser();
        chooser.showSaveDialog(jf);
        File f = chooser.getSelectedFile();

        //add file extension to file
        if(!f.getAbsolutePath().endsWith(".cws"))
        {
            f.renameTo(new File(f.getAbsolutePath() + ".cws"));
        }

        ObjectOutputStream oos = null;
        try
        {
            oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(grid.getCrossword());
            oos.close();
            doSaveCheck = false;
        }
        catch (Exception e)
        {
            System.out.print(e.getStackTrace());
        }
    }


    public void print()
    {
        String title = JOptionPane.showInputDialog(null,
                "Enter a title for the crossword:");
        CrosswordPage cp = new CrosswordPage();
        compileClues();
        cp.setCrossword(grid.getCrossword());
        cp.setTitle(title);
        cp.setAcrossClues(acrossClues.toString());
        cp.setDownClues(downClues.toString());
        cp.show();
        PrintUtilities.printComponent(cp);
    }

    public boolean getDoSaveCheck()
    {
        return doSaveCheck;
    }

    public void setDoSaveCheck(boolean b)
    {
        doSaveCheck = b;
    }



    private void compileClues()
    {
        ArrayList words = grid.getCrossword().getWords();

        //sort the arraylist in order of lowest to highest
        int index1 = 0;
        int index2 = 1;
        boolean keepLooping = true;
        while (keepLooping)
        {
            keepLooping = false;
            for (int i = 1; i < words.size(); i++)
            {
                Word w1 = (Word) words.get(i - 1);
                Word w2 = (Word) words.get(i);
                if (w2.getClueIndex() < w1.getClueIndex())
                {
                    words.remove(w2);
                    words.add(index1, w2);
                    keepLooping = true;
                }
            }
        }

        acrossClues = new StringBuffer();
        downClues = new StringBuffer();
        acrossClues.append("ACROSS: \n");
        downClues.append("DOWN: \n");
        for (int i = 0; i < words.size(); i++)
        {
            Word w = (Word) words.get(i);
            String format = null;
            if(w.getFormat() !=  null)
            {
                format = w.getFormat();
            }
            else
            {
                format = String.valueOf(w.getLength());
            }

            if (w.getWordDirection() == Word.DOWN)
            {
                downClues.append(w.getClueIndex() + ". ");
                downClues.append(w.getClue() + " (" + format + ")\n");
            }
            else if (w.getWordDirection() == Word.ACROSS)
            {
                acrossClues.append(w.getClueIndex() + ". ");
                acrossClues.append(w.getClue() + " (" + format + ")\n");
            }
        }
    }

    public void validate()
    {
        doSaveCheck = true;
        super.validate();
    }

    private void AddListeners()
    {
        btnAddWord.addActionListener(new CListener());
        btnDeleteWord.addActionListener(new CListener());
        btnSuggestWords.addActionListener(new CListener());
        jList1.addMouseListener(new ListListener());
        btnSetNumbers.addActionListener(new CListener());
        btnWriteClue.addActionListener(new CListener());
        btnGetClue.addActionListener(new CListener());

        ArrayList squares = grid.getSquares();
        for(int i=0; i<squares.size(); i++)
        {
            Square s = (Square)squares.get(i);
            s.addMouseListener(new SquareListener());
        }
    }

    public void splitSelectedWord(String format)
    {
        grid.splitSelectedWord(format);
    }

    private void deleteWord()
    {
        grid.deleteSelectedWord();
        doSaveCheck = true;
    }

    public void DisplayGrid()
    {
        grid.Build();
        add(grid, BorderLayout.CENTER);
    }

    private void jbInit()
            throws Exception
    {
        vertBox = Box.createVerticalBox();
        topBox = Box.createHorizontalBox();
        clueBox = Box.createHorizontalBox();
        boxButtons = Box.createHorizontalBox();
        topBox = Box.createHorizontalBox();
        boxButtons = Box.createHorizontalBox();
        boxButtons.setBackground(new Color(199, 223, 236));
        btnAddWord.setBorder(brdThinGrey);
        btnAddWord.setMaximumSize(new Dimension(100, 27));
        btnAddWord.setPreferredSize(new Dimension(100, 27));
        btnAddWord.setText("Add Word");
        btnGetClue.setBorder(brdThinGrey);
        btnGetClue.setMaximumSize(new Dimension(100, 27));
        btnGetClue.setPreferredSize(new Dimension(100, 27));
        btnGetClue.setText("Get Clue");
        btnDeleteWord.setBorder(brdThinGrey);
        btnDeleteWord.setMaximumSize(new Dimension(131, 27));
        btnDeleteWord.setPreferredSize(new Dimension(131, 27));
        btnDeleteWord.setText("Delete Selected Word");
        grid.setMinimumSize(new Dimension(500, 500));
        grid.setPreferredSize(new Dimension(500, 500));
        grid.setMaximumSize(new Dimension(500, 500));
        jList1.setMaximumSize(new Dimension(2147483647, 2147483647));
        jList1.setMinimumSize(new Dimension(2, 17));
        jList1.setPreferredSize(new Dimension(332, 300000));
        jList1.setToolTipText("");
        jList1.setActionMap(null);
        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setMaximumSize(new Dimension(32767, 32767));
        jScrollPane1.setPreferredSize(new Dimension(100, 200));
        btnSuggestWords.setBorder(brdThinGrey);
        btnSuggestWords.setMaximumSize(new Dimension(103, 27));
        btnSuggestWords.setPreferredSize(new Dimension(103, 27));
        btnSuggestWords.setText("Suggest Word");
        this.setBackground(new Color(174, 190, 212));
        btnSetNumbers.setBorder(brdThinGrey);
        btnSetNumbers.setMaximumSize(new Dimension(131, 27));
        btnSetNumbers.setPreferredSize(new Dimension(131, 27));
        btnSetNumbers.setToolTipText("");
        btnSetNumbers.setText("Set Clue Numbers");
        btnWriteClue.setBorder(brdThinGrey);
        btnWriteClue.setMaximumSize(new Dimension(100, 27));
        btnWriteClue.setPreferredSize(new Dimension(100, 27));
        btnWriteClue.setText("Write Clue");
        clueTextPane.setBorder(brdMedBlack);
        clueTextPane.setEditable(false);
        clueBox.add(Box.createHorizontalStrut(MARGIN_GAP));
        clueBox.add(clueTextPane);
        clueBox.add(Box.createHorizontalStrut(MARGIN_GAP));

        boxButtons.add(Box.createHorizontalStrut(MARGIN_GAP));
        boxButtons.add(btnAddWord);
        boxButtons.add(Box.createHorizontalStrut(MARGIN_GAP));
        boxButtons.add(btnSuggestWords);
        boxButtons.add(Box.createHorizontalStrut(MARGIN_GAP));
        boxButtons.add(btnSetNumbers);
        boxButtons.add(Box.createHorizontalStrut(MARGIN_GAP));
        boxButtons.add(btnWriteClue);
        boxButtons.add(Box.createHorizontalStrut(MARGIN_GAP));
        boxButtons.add(btnGetClue);
        boxButtons.add(Box.createHorizontalStrut(MARGIN_GAP));
        boxButtons.add(btnDeleteWord);
        boxButtons.add(Box.createHorizontalStrut(MARGIN_GAP));

        topBox.add(Box.createHorizontalStrut(MARGIN_GAP));
        topBox.add(grid);
        topBox.add(Box.createHorizontalStrut(MARGIN_GAP));
        topBox.add(jScrollPane1);
        topBox.add(Box.createHorizontalStrut(MARGIN_GAP));
        jScrollPane1.getViewport().add(jList1);
        vertBox.add(Box.createVerticalStrut(MARGIN_GAP));
        vertBox.add(topBox);
        vertBox.add(Box.createVerticalStrut(MARGIN_GAP));
        vertBox.add(clueBox);
        vertBox.add(Box.createVerticalStrut(MARGIN_GAP));
        vertBox.add(boxButtons);
        vertBox.add(Box.createVerticalStrut(MARGIN_GAP));
        add(vertBox, BorderLayout.CENTER);
        grid.addMouseListener(new ListListener());
    }

    class CListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getActionCommand() == "Add Word")
            {
                grid.setDoSelectStart(true);
            }
            else if (e.getActionCommand() == btnDeleteWord.getActionCommand())
            {
                deleteWord();
            }
            else if (e.getSource().equals(btnSuggestWords))
            {
                Word w = grid.getSelectedWord();
                ArrayList al = Utilities.getMatches(w.getWord());
                jList1.setListData(al.toArray());

                //move scroll bar to top
                jList1.setSelectedIndex(0);
                jScrollPane1.getVerticalScrollBar().setValue(0);
            }
            else if (e.getSource().equals(btnSetNumbers))
            {
                grid.setClueNumbers();
            }
            else if (e.getSource().equals(btnWriteClue))
            {
                grid.writeClue();
            }
            else if (e.getSource().equals(btnGetClue))
            {
                clueTextPane.setText(grid.getSelectedClue());
                validate();
            }
        }
    }

    class SquareListener implements MouseListener
    {
        public void mouseClicked(MouseEvent e)
        {
            String s = grid.getSelectedClue();
            if(s!=null)clueTextPane.setText(s);
        }

        public void mouseEntered(MouseEvent e)
        {
        }

        public void mouseExited(MouseEvent e)
        {
        }

        public void mousePressed(MouseEvent e)
        {
        }

        public void mouseReleased(MouseEvent e)
        {
        }

    }

    class ListListener implements MouseListener
    {
        public void mouseClicked(MouseEvent e)
        {
            if (e.getClickCount() == 2)
            {
                //insert selected word into the grid
                //get string selected in list
                String s = (String) jList1.getSelectedValue();
                Word w = grid.getSelectedWord();
                w.setWord(s);
                grid.repopulateWords();

                if(w.getWordDirection() == Word.ACROSS)
                {
                    //move through every square and see if it also has a down word
                    for(int i=w.getX(); i<w.getX()+w.getLength(); i++)
                    {
                        Square sq = grid.findSquare(w.getY(), i);
                        if(sq.getWordDown()!=null)
                        {
                            sq.getWordDown().addLetter(sq.getLetter(),sq.getLetterIndexDown());
                        }
                    }
                }

                if(w.getWordDirection() == Word.DOWN)
                {
                    //move through every square and see if it also has an across word
                    for(int i=w.getY(); i<w.getY() + w.getLength(); i++)
                    {
                        Square sq = grid.findSquare(i,w.getX());
                        if(sq.getWordAcross()!=null)
                        {
                            sq.getWordAcross().addLetter(sq.getLetter(),sq.getLetterIndexAcross());
                        }
                    }
                }
            }
        }

        public void mouseEntered(MouseEvent e)
        {
        }

        public void mouseExited(MouseEvent e)
        {
        }

        public void mousePressed(MouseEvent e)
        {
        }

        public void mouseReleased(MouseEvent e)
        {
        }
    }
}
