//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package edu.umd.cs.guitar.model;

import edu.umd.cs.guitar.model.DefaultIDGenerator;
import edu.umd.cs.guitar.model.JFCConstants;
import edu.umd.cs.guitar.model.data.ObjectFactory;
import java.util.ArrayList;
import java.util.List;

public class JFCDefaultIDGenerator extends DefaultIDGenerator {
    static JFCDefaultIDGenerator instance = null;
    static ObjectFactory factory = new ObjectFactory();
    static final int prime = 31;
    static List<String> ID_PROPERTIES;

    public static JFCDefaultIDGenerator getInstance() {
        if(instance == null) {
            instance = new JFCDefaultIDGenerator();
        }

        return instance;
    }

    private JFCDefaultIDGenerator() {
        super(ID_PROPERTIES);
    }

    static {
        ID_PROPERTIES = new ArrayList(JFCConstants.ID_PROPERTIES);
    }
}
