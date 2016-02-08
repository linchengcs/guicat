package examples.ticket;

import org.junit.Test;
import static org.junit.Assert.*;
import javax.accessibility.*;
import java.lang.reflect.Method;
import java.awt.*;

public class TicketTest {

    @Test
    public void firstTest() {
        assertEquals(1, 1);
    }


    public void TicketTest() {
        try {
            Class c = Ticket.class;
            Method m = c.getMethod("main", String[].class);
            m.invoke(null, new Object[]{null});
        } catch (Exception e) {
            e.printStackTrace();
        }

        Frame[] frames = Frame.getFrames();

        Frame frame;
        AccessibleContext ac = frames[0].getAccessibleContext();
        AccessibleAction buy = getAccessibleBuyButton(ac);
        buy.doAccessibleAction(0);
    }

    public  AccessibleAction getAccessibleBuyButton(AccessibleContext ac) {
        if (new String("buy").equals(ac.getAccessibleName())) {
            //   System.out.println("buy");
            return ac.getAccessibleAction();
        }
        for (int i = 0; i < ac.getAccessibleChildrenCount(); i++)
            getAccessibleBuyButton(ac.getAccessibleChild(i).getAccessibleContext());
        return null;
    }
}
