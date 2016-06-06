package guicat;

import catg.CATG;
import guicat.config.GCConfig;
import guicat.config.GCEntry;
import janala.interpreters.IntValue;

/**
 * Created by oliver on 24/02/16.
 */
public class SymbolicEntry {
    private String accessibleName;
    private GCEntry gcEntry;

    public String symbolicString;
    public int stringValueSymbol;
    public IntValue intValue;
    public int intValueSymbol;

    public SymbolicEntry(String s) {
        this.accessibleName = s;
        this.gcEntry = GCConfig.getInstance().config.get(accessibleName);
        this.stringValueSymbol = -1;
        this.intValueSymbol = -1;
        this.intValue = null;
    }

    public void makeSymbolic() {
        SymbolicTable.currentKeyForMakeSymbolicString = accessibleName;
        String s = gcEntry.initString;
        symbolicString = CATG.readString(s);
        int concrete = 0;

        if (gcEntry.symbolicType.equals("int")) {
            if (symbolicString == null || "".equals(symbolicString)) {
                concrete = 0;
            } else {
                concrete = Integer.parseInt(symbolicString);
            }
            intValue = new IntValue(concrete);
            intValueSymbol = intValue.MAKE_SYMBOLIC(null);
        }
    }

    public GCEntry getGcEntry() {return gcEntry;}
}
