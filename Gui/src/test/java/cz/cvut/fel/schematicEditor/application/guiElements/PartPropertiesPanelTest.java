package cz.cvut.fel.schematicEditor.application.guiElements;

import java.util.Vector;

import org.junit.Test;

import cz.cvut.fel.schematicEditor.application.guiElements.partProperties.PartPropertiesPanel;
import cz.cvut.fel.schematicEditor.element.properties.PartProperties;
import cz.cvut.fel.schematicEditor.element.properties.partProperties.ResistorProperties;

/**
 * JUnit test class for {@link PartPropertiesPanel}.
 *
 * @author Urban Kravjansky
 */
public class PartPropertiesPanelTest {
    /**
     * Test method for {@link PartPropertiesPanel#getInstance}.
     */
    @Test
    public void getInstance() {
        PartPropertiesPanel p = PartPropertiesPanel.getInstance();

        PartProperties pp = new ResistorProperties("variant", "variant desc");
        Vector<String> c = new Vector<String>();
        c.add("Alpha");
        c.add("Beta");

        p.setPartProperties(pp);
        p.setVisible(true);
    }
}
