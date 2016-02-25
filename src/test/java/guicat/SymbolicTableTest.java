package guicat;

import guicat.config.GCConfig;
import janala.utils.Annotations;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by oliver on 24/02/16.
 */
public class SymbolicTableTest {

    @Before
    public void setup() {
        System.setProperty("guicat.conf", "conf/barad-ticket/guicat.properties");
    }
    @Test
    public void ClassTest(){
        assertNotNull(SymbolicTable.getInstance() );
    }
}
