package examples.test;

import catg.CATG;
import janala.interpreters.Value;

import javax.accessibility.AccessibleAction;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleEditableText;
import java.awt.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import examples.ticket.Ticket;

/**
 * Created by rick on 11/22/15.
 */
public class GetTicket {
    public AccessibleEditableText nameInput = null;
    public AccessibleEditableText ageInput = null;
    public AccessibleAction buyAction = null;
    public static HashMap<String, Value> talbe = new HashMap<String, Value>();
    //    public static String s = CATG.readString("11");

    public static void main(String[] args) {


        //String test = CATG.readString("dsfdsf");
        try {
            Class c = Ticket.class;
            Method m = c.getMethod("main", String[].class);
            m.invoke(null, new Object[]{null});
        } catch (Exception e) {
            e.printStackTrace();
        }

        Frame[] frames = Frame.getFrames();

        Frame frame;
        int i = 0;
        //     for (; i < frames.length; i++)
        //        System.out.println(frames[i].toString());
        //    System.out.println(i);


        AccessibleContext ac = frames[0].getAccessibleContext();

        //     dumpInfo(frames[0].getAccessibleContext());
        // doMyAction(frames[0].getAccessibleContext());

        GetTicket getTicket = new GetTicket();

        getTicket.getAccessibleNameInput(ac);
        getTicket.getAccessibleAgeInput(ac);
        getTicket.getAccessibleBuyButton(ac);

        //        System.out.println(s.length() > 3 ? "gt3" : "lt3");
        try {
            i = 0;
            while (i++ < 1) {
                //                getTicket.nameInput.setTextContents("oliver");
                //                getTicket.ageInput.setTextContents(s);
                getTicket.buyAction.doAccessibleAction(0);
//                Thread.sleep(1000);
//                getTicket.nameInput.setTextContents(s);
//                getTicket.buyAction.doAccessibleAction(0);
//                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        frames[0].dispose();
    }




    public static void dumpInfo(AccessibleContext ac) {
        System.out.println("Name = " + ac.getAccessibleName());
        System.out.println("Description = " + ac.getAccessibleDescription());

        int nChildren = ac.getAccessibleChildrenCount();

        for (int i = 0; i < nChildren; i++)
            dumpInfo(ac.getAccessibleChild(i).getAccessibleContext());
    }

    public  void getAccessibleNameInput(AccessibleContext ac) {
        if (new String("Enter your name:").equals(ac.getAccessibleName())) {
            if (ac.getAccessibleEditableText() != null) {
                //     System.out.println("form get name input");
                this.nameInput = ac.getAccessibleEditableText();
            }
        }
        for (int i = 0; i < ac.getAccessibleChildrenCount(); i++) {
            getAccessibleNameInput(ac.getAccessibleChild(i).getAccessibleContext());
        }

    }

    public  void getAccessibleAgeInput(AccessibleContext ac) {
        if (new String("Enter your age:").equals(ac.getAccessibleName())) {
            if (ac.getAccessibleEditableText() != null) {
                //     System.out.println("form get name input");
                this.ageInput = ac.getAccessibleEditableText();
            }
        }
        for (int i = 0; i < ac.getAccessibleChildrenCount(); i++) {
            getAccessibleAgeInput(ac.getAccessibleChild(i).getAccessibleContext());
        }

    }
    public  void getAccessibleBuyButton(AccessibleContext ac) {
        if (new String("Buy").equals(ac.getAccessibleName())) {
            //   System.out.println("buy");
            this.buyAction = ac.getAccessibleAction();
        }
        for (int i = 0; i < ac.getAccessibleChildrenCount(); i++)
            getAccessibleBuyButton(ac.getAccessibleChild(i).getAccessibleContext());
    }


    public static void doMyAction(AccessibleContext ac) {

        if (new String("buy").equals(ac.getAccessibleName())) {
            //   System.out.println("Buy by accessible ...");
            //   System.out.println(ac.getAccessibleAction().toString());
            ac.getAccessibleAction().doAccessibleAction(0);
        }
        if (new String("input").equals(ac.getAccessibleName())) {
            //   System.out.println("Textinput by accessible ...");
            //   System.out.println(ac.getAccessibleText());
            //   System.out.println(ac.getAccessibleEditableText());
            if (ac.getAccessibleEditableText() != null)
                ac.getAccessibleEditableText().setTextContents("sdfsdf");
        }
        for (int i = 0; i < ac.getAccessibleChildrenCount(); i++)
            doMyAction(ac.getAccessibleChild(i).getAccessibleContext());
    }


}
