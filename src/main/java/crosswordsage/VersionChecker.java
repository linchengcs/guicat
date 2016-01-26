package crosswordsage;

import java.net.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class VersionChecker implements Runnable
{
    private HttpURLConnection conn;
    private String currentVersion;
    private String proxyName;
    private String proxyPort;
    private String proxyUser;
    private String proxyPassword;
    private boolean useProxy = false;
    private int currentNum1;
    private int currentNum2;
    private int currentNum3;


    public VersionChecker(String currentVersion)
    {
        this.currentVersion = currentVersion;
        tokeniseVersion();
    }

    public VersionChecker(String currentVersion, String prName, String prPort, String prUser, String prPassword)
    {
        this(currentVersion);
        useProxy = true;
        this.proxyName = prName;
        this.proxyPort = prPort;
        this.proxyPassword = prPassword;
        this.proxyUser = prUser;
    }

    private void tokeniseVersion()
    {
        StringTokenizer st = new StringTokenizer(currentVersion, ".");
        currentNum1 = Integer.parseInt(st.nextToken());
        currentNum2 = Integer.parseInt(st.nextToken());
        currentNum3 = Integer.parseInt(st.nextToken());
    }

    public String init()
            throws MalformedURLException, IOException
    {
        URL server = null;
        server = new URL("http://crosswordsage.sourceforge.net/version.txt");
        if(useProxy)
        {
            Properties sysProp = System.getProperties();
            sysProp.setProperty("http.proxyHost", proxyName);
            sysProp.setProperty("http.proxyPort", proxyPort);
            Authenticator.setDefault(new SimpleAuthenticator(proxyUser,proxyPassword));
        }
        HttpURLConnection conn = (HttpURLConnection) server.openConnection();
        conn.connect();
        System.out.println(conn.getResponseCode());
        System.out.println(conn.getHeaderField("location"));
        InputStream in = conn.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String s = br.readLine();
        //Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", "start", "http://sourceforge.net/projects/crosswordsage"});
        return s;
    }

    public void run()
    {
        try
        {
            String latestVersion = init();
            StringTokenizer st = new StringTokenizer(latestVersion,".");
            int num1 = Integer.parseInt(st.nextToken());
            int num2 = Integer.parseInt(st.nextToken());
            int num3 = Integer.parseInt(st.nextToken());

            boolean isNewer = false;

            //check if latest version is newer...
            if(num1 > currentNum1)
            {
                isNewer = true;
            }
            else if(num2 > currentNum2) isNewer = true;
            else if(num3 > currentNum3) isNewer = true;
            else isNewer = false;

            if(isNewer)
            {
                JOptionPane.showMessageDialog(null, "A newer version of crossword sage is available.\n Version " + latestVersion + "\nYou can disable the auto version checking feature in the preferences.");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Congratulations! You are using the latest version of Crossword Sage. \nYou can disable the auto version checking feature in the preferences.");
            }
        }
        catch (MalformedURLException ex)
        {
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }
        catch(ConnectException ex)
        {
            //may not be an internet connection, so do nothing
        }
        catch (IOException ex)
        {
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }
    }
}
