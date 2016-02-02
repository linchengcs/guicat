package examples.ticket;

import catg.CATG;

public class SingleSym {
    private static String sym = null;

    public static String getSingleSym() {
        if (sym == null)
            sym = CATG.readString("1");
        return sym;
    }

}
