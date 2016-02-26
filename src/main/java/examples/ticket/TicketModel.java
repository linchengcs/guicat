package examples.ticket;

public class TicketModel {
    public String name;
    public String ID;
    public int from;
    public int to;
    public int ageLevel;
    public int classLevel;
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
        msg = "";
        price = 0.0;
    }


    public boolean checkModel() {
        if (name.length() < 3) {
            msg += "too short name; ";
        } else if (name.length() > 20) {
            msg += "too long name";
        } else if (!name.matches("\\w+")) {
            msg += "your name contains inproper characters";
        }

        if (ID.matches("[0-9]+")) {
            msg += "right id; ";
        } else {
            msg += "wrong id format";
        }

        if (msg.length() > 0)
            return false;
        return true;
    }

    public void computePrice() {

        if (!checkModel()) {
            price = 1000000;
            return;
        }

        int coeficient = (classLevel == TicketModel.FIRSTCLASS) ? 1 : 2;
        int dist = to - from;
        if (ageLevel == 1) {
            if (dist < 40) {
                price = 100 * coeficient;
            } else if (dist < 45) {
                price = 110 * coeficient;
            } else if (dist < 50) {
                price = 120 * coeficient;
            } else if (dist < 70) {
                price = 140 * coeficient;
            } else if (dist < 80) {
                price = 150 * coeficient;
            } else if (dist < 85) {
                price = 155 * coeficient;
            } else if (dist < 100) {
                price = 160 * coeficient;
            }
        }

        if (ageLevel == 2) {
            if (dist < 40) {
                price = 120 * coeficient;
            } else if (dist < 45) {
                price = 130 * coeficient;
            } else if (dist < 50) {
                price = 140 * coeficient;
            } else if (dist < 70) {
                price = 150 * coeficient;
            } else if (dist < 80) {
                price = 160 * coeficient;
            } else if (dist < 85) {
                price = 175 * coeficient;
            } else if (dist < 100) {
                price = 180 * coeficient;
            }
        }
    }
}
