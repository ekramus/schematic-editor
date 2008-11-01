package cz.cvut.fel.schematicEditor.application.guiElements;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
     * Creates {@link JLabel} with text from {@link Property} key.
     *
     * @param property {@link Property} to retrieve key.
     * @return {@link JLabel} instance with text from {@link Property} key.
     */
    private JLabel getPropertyKeyLabel(Property<String, String> property) {
        JLabel result = new JLabel();

        result.setText(property.getKey());

        return result;
    }

    /**
     * Creates {@link JTextField} with text from {@link Property} value.
     *
     * @param property {@link Property} to retrieve value.
     * @return {@link JTextField} instance with text from {@link Property} value.
     */
    private JTextField getPropertyValueField(Property<String, String> property) {
        JTextField result = new JTextField();

        result.setText(property.getValue());

        return result;
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

        logger.debug("properties panel will be rebuilded");
        for (Property<String, String> property : getPartProperties()) {
            add(getPropertyKeyLabel(property));
            add(getPropertyValueField(property));
        }
    }
}
