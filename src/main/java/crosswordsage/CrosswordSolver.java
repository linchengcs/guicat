package crosswordsage;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

public class CrosswordSolver extends JPanel
{
    private static int MARGIN_GAP = 8;

    private ArrayList clues;
    private SolverGrid grid;
    private Box vertBox;
    private Box topBox;
    private Box boxButtons;
    private Crossword cw;
    private JList jList1 = new JList();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JButton btnCheckSolution = new JButton();
    private Border brdThinGrey = BorderFactory.createLineBorder(Color.gray, 1);
    private Border brdMedBlack = BorderFactory.createLineBorder(Color.BLACK, 2);
    private JTextPane clueTextPane = new JTextPane();
    JButton btnRevealWord = new JButton();

    public CrosswordSolver(Crossword cw)
    {
        this.setLayout(new BorderLayout());
        this.cw = cw;
        grid = new SolverGrid(cw);
        DisplayGrid();
        grid.setCrossword(cw);
        grid.repopulateWords();

        //remove all listeners
        grid.removeListeners();
        grid.removeKeyListener(grid.getKeyListeners()[0]);
        grid.init();

        sortClueList();

        validate();

        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        AddListeners();

        //write the clues
        compileClues();
        jList1.setListData(clues.toArray());
    }

    private void compileClues()
    {
        ArrayList words = grid.getCrossword().getWords();
        clues = new ArrayList();

        //sort the arraylist in terms of clue number
        int index1 = 0;
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

        //sort the arraylist into two lists: down and across
        ArrayList downClues = new ArrayList();
        ArrayList acrossClues = new ArrayList();

        for(int i=0; i<words.size(); i++)
        {
            Word w = (Word)words.get(i);
            String format = null;
            if(w.getFormat() != null)
            {
                format = w.getFormat();
            }
            else
            {
                format = String.valueOf(w.getLength());
            }
            String strClue = w.getClue() + " (" + format + ")";
            Clue c = new Clue(strClue);
            c.setWord(w);

            if(w.getWordDirection() == Word.DOWN) downClues.add(c);
            else if(w.getWordDirection() == Word.ACROSS) acrossClues.add(c);
        }

        //add in across and down headings
        Clue ac = new Clue("ACROSS: ");
        ac.makeHeader();
        Clue dc = new Clue("DOWN: ");
        dc.makeHeader();
        acrossClues.add(0,ac);
        downClues.add(0,dc);

        //add gap
        Clue gapClue = new Clue(" ");
        gapClue.makeHeader();
        downClues.add(0,gapClue);

        //now merge clues into one list
        clues.addAll(acrossClues);
        clues.addAll(downClues);
    }

    private void sortClueList()
    {
        ArrayList words = grid.getCrossword().getWords();
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
    }

    private void AddListeners()
    {
        btnCheckSolution.addActionListener(new CListener());
        jList1.addMouseListener(new ListListener());

        ArrayList squares = grid.getSquares();
        for(int i=0; i<squares.size(); i++)
        {
            Square s = (Square)squares.get(i);
            s.addMouseListener(new SquareListener());
        }
    }

    public void DisplayGrid()
    {
        grid.Build();
        add(grid, BorderLayout.CENTER);
    }

    public void checkSolution()
    {
        grid.checkSolution();
    }

    private void revealWord()
    {
        grid.revealWord();
    }


    private void jbInit()
            throws Exception
    {
        vertBox = Box.createVerticalBox();
        topBox = Box.createHorizontalBox();
        boxButtons = Box.createHorizontalBox();
        topBox = Box.createHorizontalBox();
        boxButtons = Box.createHorizontalBox();
        boxButtons.setBackground(new Color(199, 223, 236));
        btnCheckSolution.setBorder(brdThinGrey);
        btnCheckSolution.setMaximumSize(new Dimension(131, 27));
        btnCheckSolution.setPreferredSize(new Dimension(131, 27));
        btnCheckSolution.setText("Check Solution");
        btnRevealWord.setBorder(brdThinGrey);
        btnRevealWord.setMaximumSize(new Dimension(160, 27));
        btnRevealWord.setPreferredSize(new Dimension(160, 27));


        grid.setMinimumSize(new Dimension(500, 500));
        grid.setPreferredSize(new Dimension(500, 500));
        grid.setMaximumSize(new Dimension(500, 500));
        jList1.setMaximumSize(new Dimension(2000, 2000));
        jList1.setMinimumSize(new Dimension(2, 17));
        jList1.setPreferredSize(new Dimension(332, 2000));
        jList1.setToolTipText("");
        jList1.setActionMap(null);
        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setMaximumSize(new Dimension(32767, 32767));
        jScrollPane1.setPreferredSize(new Dimension(100, 200));
        this.setBackground(new Color(174, 190, 212));
        clueTextPane.setBorder(brdMedBlack);
        clueTextPane.setEditable(false);
        btnRevealWord.setText("Reveal Selected Word");
        boxButtons.add(Box.createHorizontalStrut(MARGIN_GAP));
        boxButtons.add(btnRevealWord);
        boxButtons.add(Box.createHorizontalStrut(MARGIN_GAP));
        boxButtons.add(btnCheckSolution);
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
        vertBox.add(Box.createVerticalStrut(MARGIN_GAP));
        vertBox.add(boxButtons);
        vertBox.add(Box.createVerticalStrut(MARGIN_GAP));
        add(vertBox, BorderLayout.CENTER);
        grid.addMouseListener(new ListListener());
        btnRevealWord.addActionListener(new CListener());
    }

    class CListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == btnCheckSolution)
            {
                checkSolution();
            }
            else if(e.getSource() == btnRevealWord)
            {
                revealWord();
            }
        }
    }

    class SquareListener implements MouseListener
    {
        public void mouseClicked(MouseEvent e)
        {
            String s = grid.getSelectedClue();
            if(s!=null)clueTextPane.setText(s);

            //find selected words matching clue
            for(int i=0; i<clues.size(); i++)
            {
                Clue c = (Clue)clues.get(i);
                if(grid.getSelectedClue() == c.getClue())
                {
                    jList1.setSelectedValue(c,true);
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

    class ListListener implements MouseListener
    {
        public void mouseClicked(MouseEvent e)
        {
            Clue c = (Clue)jList1.getSelectedValue();
            Word w = c.getWord();
            grid.setSelectedWord(w);
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
