package crosswordsage;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class PreferenceScreen extends JFrame
{
    JCheckBox cbNewerVersions = new JCheckBox();
    JButton btnSave = new JButton();
    private Preferences prefs;
    JPanel jPanel1 = new JPanel();
    JTextField tbProxyName = new JTextField();
    Box box2 = Box.createVerticalBox();
    JTextField tbProxyPort = new JTextField();
    JCheckBox cbUseProxy = new JCheckBox();
    JTextField tbUserName = new JTextField();
    JTextField tbPassword = new JTextField();
    JLabel jLabel1 = new JLabel();
    Box box1 = Box.createHorizontalBox();
    Box box3 = Box.createHorizontalBox();
    Box box4 = Box.createHorizontalBox();
    Box box5 = Box.createHorizontalBox();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    public PreferenceScreen()
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

    public PreferenceScreen(Preferences p)
    {
        this.prefs = p;
        this.setBounds(100,100,400,300);
        try
        {
            jbInit();
            addListeners();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void addListeners()
    {
        btnSave.addActionListener(new BListener());
        cbUseProxy.addActionListener(new BListener());
    }

    private void jbInit()
            throws Exception
    {
        box5 = Box.createHorizontalBox();
        box4 = Box.createHorizontalBox();
        box3 = Box.createHorizontalBox();
        box1 = Box.createHorizontalBox();
        box2 = Box.createVerticalBox();
        cbNewerVersions.setText("Auto-check for newer versions");
        btnSave.setText("Save");
        tbProxyName.setText("");
        tbProxyPort.setText("");
        cbUseProxy.setText("Use proxy server");
        tbUserName.setText("");
        tbPassword.setText("");
        jLabel1.setText("Proxy Address: ");
        jLabel2.setText("Proxy Port: ");
        jLabel3.setText("User Name: ");
        jLabel4.setText("Password: ");
        jPanel1.add(box2);
        box1.add(jLabel1);
        box1.add(tbProxyName);
        box5.add(jLabel2);
        box5.add(tbProxyPort);
        box4.add(jLabel3);
        box4.add(tbUserName);
        box3.add(jLabel4);
        box3.add(tbPassword);

        box2.setAlignmentX(0f);
        box2.add(cbNewerVersions);
        box2.add(cbUseProxy);
        box2.add(box1);
        box2.add(box5);
        box2.add(box4);
        box2.add(box3);
        box2.add(btnSave);
        this.getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);
        cbNewerVersions.setSelected(prefs.getCheckNewVersions());
        cbUseProxy.setSelected(prefs.getUseProxy());
        tbPassword.setText(prefs.getProxyPassword());
        tbProxyName.setText(prefs.getProxy());
        tbProxyPort.setText(prefs.getProxyPort());
        tbUserName.setText(prefs.getProxyUserName());

        enableProxyOptions();
    }

    private void enableProxyOptions()
    {
        tbPassword.setEnabled(cbUseProxy.isSelected());
        tbProxyName.setEnabled(cbUseProxy.isSelected());
        tbProxyPort.setEnabled(cbUseProxy.isSelected());
        tbUserName.setEnabled(cbUseProxy.isSelected());
    }

    private void save()
    {
        prefs.setCheckNewVersions(cbNewerVersions.isSelected());
        prefs.setUseProxy(cbUseProxy.isSelected());
        prefs.setProxy(tbProxyName.getText());
        prefs.setProxyPassword(tbPassword.getText());
        prefs.setProxyPort(tbProxyPort.getText());
        prefs.setProxyUserName(tbUserName.getText());
        setVisible(false);

    }
    class BListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            enableProxyOptions();
            if(e.getSource() == btnSave)
            {
                save();
            }
        }
    }
}
