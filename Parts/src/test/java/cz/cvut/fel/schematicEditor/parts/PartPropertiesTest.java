package cz.cvut.fel.schematicEditor.parts;

import java.util.Vector;

import org.junit.Test;

import cz.cvut.fel.schematicEditor.parts.originalParts.OriginalPartProperties;
import cz.cvut.fel.schematicEditor.parts.originalParts.partProperties.ResistorProperties;

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
        OriginalPartProperties pp = new ResistorProperties("resistor A", "This is variant A");

        Vector<String> pc = new Vector<String>();
        pc.add("A, A");
        pc.add("B, BBa");

        pp.getPartPinNames();
    }
}
