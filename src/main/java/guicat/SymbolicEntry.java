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
     //   assert SymbolicTable.currentKeyForMakeSymbolicString == null : "strange, why symbolicTable.currentKeyForMakeSymbolicString not null?";
        SymbolicTable.currentKeyForMakeSymbolicString = accessibleName;
        System.out.println(SymbolicTable.currentKeyForMakeSymbolicString);
        String s = gcEntry.initString;
        symbolicString = CATG.readString(s);

        if (gcEntry.symbolicType.equals("int")) {
            intValue = new IntValue(0);
            intValueSymbol = intValue.MAKE_SYMBOLIC(null);
        }
    }

}
