package cz.cvut.fel.schematicEditor.element.properties;

import java.util.Vector;

import org.junit.Test;

import cz.cvut.fel.schematicEditor.element.properties.partProperties.ResistorProperties;

/**
 * JUnit test class for {@link PartProperties}.
 *
 * @author Urban Kravjansky
 */
public class PartPropertiesTest {
    /**
     * Test method for {@link PartProperties#getPartConnectors()}.
     */
    @Test
    public void getPartConnectors() {
        PartProperties pp = new ResistorProperties("variant A", "This is variant A");

        Vector<String> pc = new Vector<String>();
        pc.add("A, A");
        pc.add("B, BBa");
        pp.setPartConnectors(pc);

        pp.getPartConnectors();
    }
}
