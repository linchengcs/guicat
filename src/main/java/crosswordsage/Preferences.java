package crosswordsage;

import java.io.*;

public class Preferences implements Serializable
{
    private boolean checkNewVersions = true;
    private boolean useProxy = false;
    private String proxy = null;
    private String proxyPort = null;
    private String proxyUserName = null;
    private String proxyPassword = null;

    public Preferences()
    {
    }

    public boolean getCheckNewVersions()
    {
        return checkNewVersions;
    }

    public void setCheckNewVersions(boolean b)
    {
        checkNewVersions = b;
    }

    public void setUseProxy(boolean b)
    {
        useProxy = b;
    }

    public boolean getUseProxy()
    {
        return useProxy;
    }

    public void setProxy(String s)
    {
        this.proxy = s;
    }

    public String getProxy()
    {
        return proxy;
    }

    public void setProxyPort(String s)
    {
        proxyPort = s;
    }

    public String getProxyPort()
    {
        return proxyPort;
    }

    public String getProxyUserName()
    {
        return proxyUserName;
    }

    public void setProxyUserName(String s)
    {
        proxyUserName = s;
    }

    public String getProxyPassword()
    {
        return proxyPassword;
    }

    public void setProxyPassword(String s)
    {
        proxyPassword = s;
    }
}
