package crosswordsage;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;


public class MainScreen extends JFrame
{
    Preferences prefs = new Preferences();

    //define menu variables
    JMenuBar jMenuBar1 = new JMenuBar();
    JMenu mTools = new JMenu();
    JMenuItem mTools_Solve = new JMenuItem();
    JPanel mainPanel = new JPanel();

    BorderLayout borderLayout1 = new BorderLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    JMenu mHelp = new JMenu();
    JMenuItem mHelp_About = new JMenuItem();
    JMenuItem mHelp_Instructions = new JMenuItem();
    JMenu mFile = new JMenu();
    JMenuItem mFile_Save = new JMenuItem();
    JMenuItem mFile_Load = new JMenuItem();

    private static final String VERSION_NUMBER = "0.3.5";
    private CrosswordCompiler cc;
    private boolean crosswordCompilerActive = false;
    JMenuItem mFile_Print = new JMenuItem();
    JMenuItem mFile_SolveCrossword = new JMenuItem();
    JMenuItem mFile_NewCrossword = new JMenuItem();
    JMenu mAction = new JMenu();
    JMenuItem mAction_Publish = new JMenuItem();
    JMenu mEdit = new JMenu();
    JMenuItem mEdit_Split = new JMenuItem();
    JMenuItem mTools_Version = new JMenuItem();
    JMenuItem mFile_Preferences = new JMenuItem();

    public MainScreen()
    {
        loadPreferences();

        try
        {
            jbInit();
            if(prefs.getCheckNewVersions()) checkNewVersions();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        MainScreen mainscreen = new MainScreen();
    }

    private void jbInit()
            throws Exception
    {
        this.show();
        this.getContentPane().setBackground(SystemColor.control);
        this.setJMenuBar(jMenuBar1);
        this.setTitle("Crossword Sage");
        this.getContentPane().setLayout(borderLayout1);
        this.setBounds(0, 0, 700, 700);
        this.addWindowListener(new WinListener());
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        BuildMenu();

        jMenuBar1.setBackground(new Color(199, 223, 236));
        jMenuBar1.setBorder(BorderFactory.createLineBorder(Color.black));
        mTools.setBackground(new Color(199, 223, 236));
        mTools_Solve.setBackground(new Color(199, 223, 236));
        mHelp.setBackground(new Color(199, 223, 236));
        mHelp.setText("Help");
        mHelp_About.setText("About");
        mHelp_About.setBackground(new Color(199, 223, 236));
        mHelp_About.setActionCommand("About");
        mHelp_Instructions.setBackground(new Color(199, 223, 236));
        mHelp_Instructions.setText("Instructions");
        mFile.setBackground(new Color(199, 223, 236));
        mFile.setText("File");
        mFile_Save.setBackground(new Color(199, 223, 236));
        mFile_Save.setEnabled(false);
        mFile_Save.setText("Save Crossword");
        mFile_Load.setBackground(new Color(199, 223, 236));
        mFile_Load.setText("Load Crossword to Edit");
        mFile_Print.setBackground(new Color(199, 223, 236));
        mFile_Print.setText("Print Crossword");
        mFile_SolveCrossword.setText("Load Crossword to Solve");
        mFile_SolveCrossword.setBackground(new Color(199, 223, 236));
        mFile_NewCrossword.setText("New Crossword");
        mFile_NewCrossword.setBackground(new Color(199, 223, 236));
        mAction.setText("Action");
        mAction.setBackground(new Color(199, 223, 236));
        mAction_Publish.setText("Publish Crossword");
        mAction_Publish.setBackground(new Color(199, 223, 236));
        mEdit.setText("Edit");
        mEdit.setBackground(new Color(199, 223, 236));
        mEdit_Split.setText("Split Word");
        mEdit_Split.setBackground(new Color(199, 223, 236));
        mTools_Version.setText("Check Version");
        mTools_Version.setBackground(new Color(199, 223, 236));
        mFile_Preferences.setText("Preferences");
        mFile_Preferences.setBackground(new Color(199, 223, 236));

        this.getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);
        mainPanel.setBackground(new Color(174, 190, 212));
        mainPanel.setLayout(borderLayout2);
        jMenuBar1.add(mFile);
        jMenuBar1.add(mEdit);

        jMenuBar1.add(mTools);
        jMenuBar1.add(mAction);
        jMenuBar1.add(mHelp);

        mainPanel.add(new WordSolverPanel());
        mHelp.add(mHelp_About);
        mHelp.add(mHelp_Instructions);
        validate();
        mFile.add(mFile_NewCrossword);
        mFile.addSeparator();
        mFile.add(mFile_Save);
        mFile.addSeparator();
        mFile.add(mFile_SolveCrossword);
        mFile.add(mFile_Load);
        mFile.addSeparator();
        mFile.add(mFile_Print);
        mFile.add(mFile_Preferences);
        mAction.add(mAction_Publish);
        mEdit.add(mEdit_Split);
        mTools.add(mTools_Version);
    }

    private void BuildMenu()
    {
        mTools.setText("Tools");
        mTools_Solve.setText("Solve New Word");
        jMenuBar1.add(mTools);
        mTools.add(mTools_Solve);
        mTools.setActionCommand("Solve");

        //add listeners
        mTools_Solve.addActionListener(new MenuListener());
        mHelp_About.addActionListener(new MenuListener());
        mHelp_Instructions.addActionListener(new MenuListener());
        mFile_Save.addActionListener(new MenuListener());
        mFile_Load.addActionListener(new MenuListener());
        mFile_Print.addActionListener(new MenuListener());
        mFile_SolveCrossword.addActionListener(new MenuListener());
        mFile_NewCrossword.addActionListener(new MenuListener());
        mAction_Publish.addActionListener(new MenuListener());
        mEdit_Split.addActionListener(new MenuListener());
        mTools_Version.addActionListener(new MenuListener());
        mFile_Preferences.addActionListener(new MenuListener());
    }

    private void loadPreferences()
    {
        prefs = new Preferences();
        File prefFile = new File("config.txt");
        ObjectInputStream ois;
        try
        {
            //prefFile.createNewFile();
            ois = new ObjectInputStream(new FileInputStream(prefFile));

            prefs = (Preferences)ois.readObject();
            ois.close();
        }
        catch (FileNotFoundException ex)
        {
            JProgressBar jBar = new JProgressBar();
            jBar.setValue(100);
            jBar.setString("Building preference file...");
        }
        catch (EOFException ex)
        {
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }
        catch (IOException ex)
        {
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }
        catch (ClassNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }

    }

    private void savePreferences()
    {
        File f = new File("config.txt");
        ObjectOutputStream oos;
        try
        {
            f.createNewFile();
            oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(prefs);
            oos.close();
        }
        catch (IOException ex)
        {
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }
    }


    private void showCrosswordBuilder()
    {
        mainPanel.removeAll();
        try
        {
            int size = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Please enter grid size (2-20)...", null));
            if (size >= 2 && size <= 20)
            {
                cc = new CrosswordCompiler(size, size);
                cc.setDoSaveCheck(true);
                mFile_Save.setEnabled(true);
                mainPanel.add(cc);
                validate();
            }
            else
            {
                JOptionPane.showMessageDialog(null,
                                              "Crosswords must be between 2 and 20 squares in width", null,
                                              JOptionPane.ERROR_MESSAGE);
                showCrosswordBuilder();
            }
        }catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(null,
                                              "Please enter a numerical value between 2 and 20", null,
                                              JOptionPane.ERROR_MESSAGE);
                showCrosswordBuilder();
        }
    }

    private void saveCrossword()
    {
        cc.saveCrossword(this);
        cc.setDoSaveCheck(false);
    }

    private void solveCrossword()
    {
        Crossword cw = CrosswordFileHandler.loadCrossword(this);
        mainPanel.removeAll();
        mainPanel.add(new CrosswordSolver(cw));
        validate();
    }

    private void loadCrossword()
    {
        mainPanel.removeAll();
        cc = new CrosswordCompiler();
        mainPanel.add(cc);
        mFile_Save.setEnabled(true);
        cc.loadCrossword(this);
    }

    private void splitSelectedWord()
    {
        String s = JOptionPane.showInputDialog(null,"Specify the format of the word. (eg. 3-2,4,4)");
        cc.splitSelectedWord(s);
    }

    private void publishCrossword()
    {
        int response = JOptionPane.showConfirmDialog(null, "A published crossword can be solved but not edited. Are you sure you want to continue?", null,
                JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.NO_OPTION)
        {

        }
        else if (response == JOptionPane.YES_OPTION)
        {
            cc.publishCrossword();
            cc.saveCrossword(this);
        }
    }

    private void printCrossword()
    {
        cc.print();
    }


    private void showVersion()
    {
        JOptionPane.showMessageDialog(this, "Crossword Sage Version "+ VERSION_NUMBER + ". \n For further information, downloads and \n" +
                "the latest version of Crossword Sage, \n visit http://crosswordsage.sourceforge.net");
    }

    private void checkNewVersions()
    {
        if (prefs.getUseProxy())
        {
            Thread vcThread = new Thread(new VersionChecker(VERSION_NUMBER,
                    prefs.getProxy(), prefs.getProxyPort(),
                    prefs.getProxyUserName(),
                    prefs.getProxyPassword()));
            vcThread.start();
        }
        else
        {
            Thread vcThread = new Thread(new VersionChecker(VERSION_NUMBER));
            vcThread.start();
        }
    }

    private void showPreferences()
    {
        PreferenceScreen pp = new PreferenceScreen(prefs);
        pp.show();
    }


    private void checkSave()
    {
        if(cc!=null)
        {
            if (cc.getDoSaveCheck())
            {
                int response = JOptionPane.showConfirmDialog(null,
                        "You currently have an open crossword you are building. \nWould you like to save before closing?", "Save?",
                        JOptionPane.YES_NO_CANCEL_OPTION);
                if (response == JOptionPane.YES_OPTION)
                {
                    saveCrossword();
                    System.exit(0);
                }
                else if (response == JOptionPane.NO_OPTION)
                {
                    System.exit(0);
                }
                else if (response == JOptionPane.CANCEL_OPTION)
                {
                }
            }
            else
            {
                basicClose();
            }
        }
        else
        {
            basicClose();
        }
    }

    private void basicClose()
    {
        int i = JOptionPane.showConfirmDialog(null,"Close Crossword Sage?","Confirm Action", JOptionPane.YES_NO_OPTION);
        if(i==JOptionPane.YES_OPTION) System.exit(0);
    }

    private void showInstructions()
    {
        JOptionPane.showMessageDialog(this,
                "Search the word list for a word pattern, anagram or combination of both. \n" +
            "To specify a pattern simply put a '*' in place of any letters you don't know. \n" +
            "eg. you know the 2nd and 6th letter of a 7 letter word --> *e***f*");
    }


    class WinListener implements WindowListener
    {
        public void windowActivated(WindowEvent e)
        {
        }

        public void windowClosed(WindowEvent e)
        {
        }

        public void windowClosing(WindowEvent e)
        {
            savePreferences();
            checkSave();
        }

        public void windowDeactivated(WindowEvent e)
        {
        }

        public void windowDeiconified(WindowEvent e)
        {
        }

        public void windowIconified(WindowEvent e)
        {
        }

        public void windowOpened(WindowEvent e)
        {
        }
    }


    class MenuListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getActionCommand() == "Solve New Word")
            {
                mainPanel.removeAll();
                mainPanel.add(new WordSolverPanel());
            }
            else if(e.getSource() == mFile_NewCrossword)
            {
                showCrosswordBuilder();
            }
            else if (e.getActionCommand() == "About")
            {
                showVersion();
            }
            else if (e.getActionCommand() == "Instructions")
            {
                //showInstructions();
            }
            else if(e.getSource().equals(mFile_Load))
            {
                loadCrossword();
            }
            else if(e.getSource().equals(mFile_Save))
            {
                saveCrossword();
            }
            else if(e.getSource().equals(mFile_Print))
            {
                printCrossword();
            }
            else if(e.getSource().equals(mFile_SolveCrossword))
            {
                solveCrossword();
            }
            else if(e.getSource().equals(mAction_Publish))
            {
                publishCrossword();
            }
            else if(e.getSource().equals(mEdit_Split))
            {
                splitSelectedWord();
            }
            else if(e.getSource() == mFile_Preferences)
            {
                showPreferences();
            }
            else if(e.getSource().equals(mTools_Version))
            {
                checkNewVersions();
            }
            validate();
        }
    }
}
