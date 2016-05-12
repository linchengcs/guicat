package examples.ticket;

import static examples.ticket.BaradTicket.path;

public class TicketModel {
    public String name;
    public String ID;
    public int from;
    public int to;
    public int ageLevel;
    public int classLevel;
    public int coupon;
    final public static int CHILD = 1;
    final public static int ADULT = 2;
    final public static int FIRSTCLASS = 1;
    final public static int SECONDCLASS = 2;
    public String msg;
    public double price;

    public TicketModel() {
        name = "oliver";
        ID = "123456";
        from = 0;
        to = 50;
        ageLevel = TicketModel.CHILD;
        classLevel = TicketModel.FIRSTCLASS;
        coupon = 0;
        msg = "";
        price = 0.0;
    }


    public boolean checkModel() {
        msg = "";


        if (name.length() < 3) {
            msg += "too short name; ";
            path.set(3, 0);
        } else if (name.length() > 20) {
            msg += "too long name";
            path.set(3, 1);
        }

        if (ID.equals("oliver")) {
            msg += "wrong id format";
            path.set(4, 0);
        } else {
            msg += "right id format";
            path.set(4, 1);
        }

        return msg.isEmpty();
    }

    public void computePrice() {

        int coeficient = 0;
        if (classLevel == TicketModel.FIRSTCLASS) {
            coeficient = 1;
            path.set(5, 0);
        } else {
            coeficient = 2;
            path.set(5, 1);
        }
        int dist = to - from;
        if (ageLevel == 1) {
            if (dist < 40) {
                price = 100 * coeficient;
                path.set(7, 0);
            }  else  if (dist < 45) {
                price = 110 * coeficient;
                path.set(7, 1);
            } else if (dist < 50) {
                price = 120 * coeficient;
                path.set(7, 2);
            } else if (dist < 70) {
                price = 140 * coeficient;
                path.set(7, 3);
            } else if (dist < 80) {
                price = 150 * coeficient;
                path.set(7, 4);
            } else if (dist < 85) {
                price = 155 * coeficient;
                path.set(7, 5);
            } else if (dist < 100) {
                price = 160 * coeficient;
                path.set(7, 6);
            }
            path.set(6, 0);
        }

        if (ageLevel == 2) {
            if (dist < 40) {
                price = 120 * coeficient;
                path.set(7, 0);
            }  else if (dist < 45) {
                price = 130 * coeficient;
                path.set(7, 1);
            } else if (dist < 50) {
                price = 140 * coeficient;
                path.set(7, 2);
            } else if (dist < 70) {
                price = 150 * coeficient;
                path.set(7, 3);
            } else if (dist < 80) {
                price = 160 * coeficient;
                path.set(7, 4);
            } else if (dist < 85) {
                price = 175 * coeficient;
                path.set(7, 5);
            } else if (dist < 100) {
                price = 180 * coeficient;
                path.set(7, 6);
            }
            path.set(6,1);
        }

        price = price - coupon;
    }

    public String toString() {
        String ans = "customer " + name + "saved \n";
        return ans;
    }
}
