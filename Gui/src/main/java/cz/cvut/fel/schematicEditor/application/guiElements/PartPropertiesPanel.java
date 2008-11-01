package cz.cvut.fel.schematicEditor.application.guiElements;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.element.properties.PartProperties;
import cz.cvut.fel.schematicEditor.support.Property;

/**
 * This class implements panel for access to part properties. It is created dynamically based on properties of each
 * part. It is shown inside of {@link PartPropertiesDialog}.
 *
 * @author Urban Kravjansky
 *
 */
public class PartPropertiesPanel extends JPanel {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger              logger;
    /**
     * {@link PartPropertiesPanel} singleton instance field.
     */
    private static PartPropertiesPanel instance       = null;
    /**
     * Part properties, which is being manipulated with.
     */
    private PartProperties             partProperties = null;

    /**
     * This method instantiates new instance.
     *
     */
    private PartPropertiesPanel() {
        logger = Logger.getLogger(PartPropertiesPanel.class.getName());
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

    /**
     * @return the partProperties
     */
    public PartProperties getPartProperties() {
        return this.partProperties;
    }

    /**
     * @param partProperties the partProperties to set
     */
    public void setPartProperties(PartProperties partProperties) {
        this.partProperties = partProperties;

        logger.debug("properties panel will go visible");
        for (Property<String, String> property : getPartProperties()) {
            logger.trace(property.getKey() + ": " + property.getValue());
        }
    }
}
