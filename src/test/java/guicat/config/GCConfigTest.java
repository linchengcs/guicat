package guicat.config;

import org.junit.Before;
import org.junit.Test;
import guicat.config.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

/**
 * Created by oliver on 24/02/16.
 */
public class GCConfigTest {

    @Before
    public void setup() {
        System.setProperty("guicat.conf", "conf/barad-ticket/guicat.properties");
    }

    @Test
    public void GCConfigTest(){
          System.setProperty("guicat.conf", "conf/barad-ticket/guicat.properties");
        GCConfig gcc = GCConfig.getInstance();
    //    GCEntry gce = gcc.config.get("Name");

        assertNotNull(gcc);
    }
}