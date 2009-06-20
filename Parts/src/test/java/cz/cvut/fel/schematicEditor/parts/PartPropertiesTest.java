package cz.cvut.fel.schematicEditor.parts;

import java.util.Vector;

import org.junit.Test;

import cz.cvut.fel.schematicEditor.parts.lightweightParts.LightweightPartProperties;
import cz.cvut.fel.schematicEditor.parts.lightweightParts.partProperties.ResistorProperties;

/**
 * JUnit test class for {@link LightweightPartProperties}.
 *
 * @author Urban Kravjansky
 */
public class PartPropertiesTest {
    /**
     * Test method for {@link LightweightPartProperties#getPartPinNames()}.
     */
    @Test
    public void getPartConnectors() {
        LightweightPartProperties pp = new ResistorProperties();

        Vector<String> pc = new Vector<String>();
        pc.add("A, A");
        pc.add("B, BBa");

        pp.getPartPinNames();
    }
}
