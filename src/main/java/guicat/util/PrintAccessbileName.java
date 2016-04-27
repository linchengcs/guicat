package guicat.util;


import javax.accessibility.AccessibleContext;
import java.awt.*;
import java.lang.reflect.Method;

public class PrintAccessbileName {
    static String indent = "";
    static int  level = 1;


    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("please provide program name");
        }
        String autMain = args[0];


        try {
            Class c = Class.forName(autMain);
            Method m = c.getMethod("main", String[].class);
            m.invoke(null, (Object)new String[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Frame[] frames = null;
        frames = Frame.getFrames();

        //mot work, frame comes out, but accessible context doesn't come out
//        int i = 0;
//        while ( (frames = Frame.getFrames()) == null ) {
//            System.out.println(frames.toString());
//        };

        int i = 1;
        for (Frame frame : frames) {
            //System.out.println("====== accessibleName for frame: " + i++ + "=============");
            dumpInfo(frame.getAccessibleContext());
        }

        for (Frame frame : frames) {
            frame.dispose();
        }
    }




    public static void dumpInfo(AccessibleContext ac) {
        /*
        if( ac.getAccessibleEditableText() != null) {
        System.out.println("------ level " + level++);
        System.out.println("    " + ac.getAccessibleName());
        System.out.println("    " + ac.getAccessibleEditableText());
        System.out.println("Description = " + ac.getAccessibleDescription());
        }
        */
        System.out.println(level + indent + ac.getAccessibleName());
        int nChildren = ac.getAccessibleChildrenCount();
        indent += "    ";
        level++;

        for (int i = 0; i < nChildren; i++){
            dumpInfo(ac.getAccessibleChild(i).getAccessibleContext());
            level--;
            indent = indent.substring(0, indent.length()-4);
        }
    }



}
