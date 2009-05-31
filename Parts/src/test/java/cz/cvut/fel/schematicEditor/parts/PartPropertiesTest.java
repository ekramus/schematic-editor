package cz.cvut.fel.schematicEditor.parts;

import java.util.Vector;

import org.junit.Test;

import cz.cvut.fel.schematicEditor.parts.originalParts.PartProperties;
import cz.cvut.fel.schematicEditor.parts.originalParts.partProperties.ResistorProperties;

/**
 * JUnit test class for {@link PartProperties}.
 *
 * @author Urban Kravjansky
 */
public class PartPropertiesTest {
    /**
     * Test method for {@link PartProperties#getPartPinNames()}.
     */
    @Test
    public void getPartConnectors() {
        PartProperties pp = new ResistorProperties("resistor A", "This is variant A");

        Vector<String> pc = new Vector<String>();
        pc.add("A, A");
        pc.add("B, BBa");

        pp.getPartPinNames();
    }
}
