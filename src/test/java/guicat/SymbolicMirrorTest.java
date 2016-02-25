package guicat;

import guicat.SymbolicMirror;
import org.junit.*;
import static org.junit.Assert.*;

public class SymbolicMirrorTest{

    @Test
    public void firstTest() {
        assertNotNull(SymbolicMirror.getInstance());
    }
}
