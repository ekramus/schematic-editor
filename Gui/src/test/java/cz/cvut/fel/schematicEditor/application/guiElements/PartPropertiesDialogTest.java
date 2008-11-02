package cz.cvut.fel.schematicEditor.application.guiElements;

import org.junit.Test;

/**
 * JUnit test class for {@link PartPropertiesDialog}.
 *
 * @author Urban Kravjansky
 */
public class PartPropertiesDialogTest {
    /**
     * Test method for {@link PartPropertiesDialog#PartPropertiesDialog(javax.swing.JFrame, boolean)}.
     */
    @Test
    public void PartPropertiesDialog() {
        PartPropertiesDialog p = new PartPropertiesDialog(null, false);
        p.isAccepted();
    }
}
