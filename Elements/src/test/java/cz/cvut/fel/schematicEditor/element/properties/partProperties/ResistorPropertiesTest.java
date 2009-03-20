package cz.cvut.fel.schematicEditor.element.properties.partProperties;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cz.cvut.fel.schematicEditor.element.properties.PartProperties;

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
        PartProperties pp = new ResistorProperties("resistor A", "This is variant A");

        pp.setProperty("value", "10");
        pp.setProperty("name", "R");
        pp.setProperty("connectorP", "R+");
        pp.setProperty("connectorM", "R-");

        assertEquals("rR R+ R- 10", pp.getNetlist());
    }
}
