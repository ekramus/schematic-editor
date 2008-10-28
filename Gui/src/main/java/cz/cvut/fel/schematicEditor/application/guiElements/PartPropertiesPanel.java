package cz.cvut.fel.schematicEditor.application.guiElements;

import javax.swing.JPanel;

/**
 * This class implements dialog for access to part properties. It is created dynamically based on properties of each
 * part.
 *
 * @author Urban Kravjansky
 *
 */
public class PartPropertiesPanel extends JPanel {
    /**
     * {@link PartPropertiesPanel} singleton instance field.
     */
    private static PartPropertiesPanel instance = null;

    /**
     * This method instantiates new instance.
     *
     */
    private PartPropertiesPanel() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Singleton {@link PartPropertiesPanel} instance getter.
     *
     * @return singleton {@link PartPropertiesPanel} instance.
     */
    public static PartPropertiesPanel getInstance() {
        if (PartPropertiesPanel.instance == null) {
            PartPropertiesPanel.instance = new PartPropertiesPanel();
        }
        return PartPropertiesPanel.instance;
    }
}
