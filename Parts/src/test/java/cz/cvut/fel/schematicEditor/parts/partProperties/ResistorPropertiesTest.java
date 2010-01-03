package cz.cvut.fel.schematicEditor.parts.partProperties;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cz.cvut.fel.schematicEditor.parts.lightweightParts.LightweightPartProperties;
import cz.cvut.fel.schematicEditor.parts.lightweightParts.partProperties.ResistorProperties;

/**
 * JUnit test class for {@link ResistorProperties}.
 *
 * @author Urban Kravjansky
 */
public class ResistorPropertiesTest {
    /**
     * Test method for {@link ResistorProperties#getNetlist()}.
     */
    @Test
    public void getNetlist() {
        LightweightPartProperties pp = new ResistorProperties();

        pp.setProperty("value", "10");
        pp.setProperty("name", "R");
        pp.setProperty("n1", "R+");
        pp.setProperty("n2", "R-");

        assertEquals("rR R+ R- 10", pp.getNetlist());
    }
}
