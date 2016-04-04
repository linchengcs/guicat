//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package edu.umd.cs.guitar.model;

import edu.umd.cs.guitar.exception.ApplicationConnectException;
import edu.umd.cs.guitar.model.GApplication;
import edu.umd.cs.guitar.model.GWindow;
import edu.umd.cs.guitar.model.JFCXWindow;
import edu.umd.cs.guitar.util.GUITARLog;
import java.awt.Frame;
import java.awt.Window;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

public class JFCApplication extends GApplication {
    private Class<?> cClass;
    int iInitialDelay;
    final String[] URL_PREFIX = new String[]{"file:", "jar:", "http:"};

    public JFCApplication(String entrance, boolean useJar, String[] URLs) throws ClassNotFoundException, IOException {
        String mainClass;
        if(useJar) {
            FileInputStream is = new FileInputStream(entrance);
            JarInputStream jarStream = new JarInputStream(is);
            Manifest mf = jarStream.getManifest();
            Attributes attributes = mf.getMainAttributes();
            mainClass = attributes.getValue("Main-Class");
        } else {
            mainClass = entrance;
        }

        this.cClass = this.initilizeMainClass(mainClass, URLs);
    }

    private Class<?> initilizeMainClass(String sClassName, String[] sURLs) throws MalformedURLException, ClassNotFoundException {
        HashSet lURLs = new HashSet();
        URLClassLoader sysLoader = (URLClassLoader)ClassLoader.getSystemClassLoader();
        URL[] urls = sysLoader.getURLs();

        for(int arrayURLs = 0; arrayURLs < urls.length; ++arrayURLs) {
            lURLs.add(urls[arrayURLs]);
        }

        String[] var15 = sURLs;
        int loader = sURLs.length;

        for(int i$ = 0; i$ < loader; ++i$) {
            String sURL = var15[i$];
            String[] arr$ = this.URL_PREFIX;
            int len$ = arr$.length;

            for(int i$1 = 0; i$1 < len$; ++i$1) {
                String pref = arr$[i$1];
                if(sURL.startsWith(pref)) {
                    URL appURL = new URL(sURL);
                    lURLs.add(appURL);
                    break;
                }
            }
        }

        URL[] var16 = (URL[])lURLs.toArray(new URL[lURLs.size()]);
        URLClassLoader var17 = new URLClassLoader(var16);
        return Class.forName(sClassName, true, var17);
    }

    public void connect() throws ApplicationConnectException {
        String[] args = new String[0];
        this.connect(args);
    }

    public void connect(String[] args) throws ApplicationConnectException {
        GUITARLog.log.debug("=============================");
        GUITARLog.log.debug("Application Parameters: ");
        GUITARLog.log.debug("-----------------------------");

        for(int method = 0; method < args.length; ++method) {
            GUITARLog.log.debug("\t" + args[method]);
        }

        GUITARLog.log.debug("");

        try {
            Method var8 = this.cClass.getMethod("main", new Class[]{String[].class});
            GUITARLog.log.debug("Main method FOUND!");
            if(var8 == null) {
                throw new ApplicationConnectException();
            }

            var8.invoke((Object)null, new Object[]{args});
            GUITARLog.log.debug("Main method INVOKED!");
        } catch (NoSuchMethodException var5) {
            GUITARLog.log.debug("Coundn\'t find main method for the application");
            GUITARLog.log.error(var5);
        } catch (IllegalAccessException var6) {
            GUITARLog.log.error(var6);
        } catch (InvocationTargetException var7) {
            GUITARLog.log.error(var7);
        }

        try {
            Thread.sleep((long)this.iInitialDelay);
        } catch (InterruptedException var4) {
            GUITARLog.log.error(var4);
        }

    }

    public Set<GWindow> getAllWindow() {
        Frame[] windows = Frame.getFrames();
        HashSet retWindows = new HashSet();
        Frame[] arr$ = windows;
        int len$ = windows.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            Frame aWindow = arr$[i$];
            JFCXWindow gWindow = new JFCXWindow(aWindow);
            if(gWindow.isValid()) {
                retWindows.add(gWindow);
            }

            Set lOwnedWins = this.getAllOwnedWindow(aWindow);
            Iterator i$1 = lOwnedWins.iterator();

            while(i$1.hasNext()) {
                GWindow aOwnedWins = (GWindow)i$1.next();
                if(aOwnedWins.isValid()) {
                    retWindows.add(aOwnedWins);
                }
            }
        }

        return retWindows;
    }

    private Set<GWindow> getAllOwnedWindow(Window parent) {
        HashSet retWindows = new HashSet();
        Window[] lOwnedWins = parent.getOwnedWindows();
        Window[] arr$ = lOwnedWins;
        int len$ = lOwnedWins.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            Window aOwnedWin = arr$[i$];
            retWindows.add(new JFCXWindow(aOwnedWin));
            Set lOwnedWinChildren = this.getAllOwnedWindow(aOwnedWin);
            retWindows.addAll(lOwnedWinChildren);
        }

        return retWindows;
    }

    private class RuntimeJarFileLoader extends URLClassLoader {
        public RuntimeJarFileLoader(URL[] urls) {
            super(urls);
        }

        public void addFile(String path) throws MalformedURLException {
            String urlPath = "jar:file:" + path + "!/";
            this.addURL(new URL(urlPath));
        }

        public void addFile(String[] paths) throws MalformedURLException {
            if(paths != null) {
                for(int i = 0; i < paths.length; ++i) {
                    this.addFile(paths[i]);
                }
            }

        }
    }
}
