package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.partPropertiesDialog.copy;

import java.awt.Dimension;
import java.util.HashMap;

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
    private static Logger               logger;
    /**
     * {@link PartPropertiesPanel} singleton instance field.
     */
    private static PartPropertiesPanel  instance                    = null;
    /**
     * Part properties, which is being manipulated with.
     */
    private PartProperties              partProperties              = null;
    /**
     * Stores reference to text field for each part property key.
     */
    private HashMap<String, JTextField> partPropertiesTextFieldsMap = null;

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

        result.setText(property.getKey() + ": ");

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
        result.setPreferredSize(new Dimension(50, 20));

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
        // clean up panel
        removeAll();
        // reinitialize partProperties
        setPartPropertiesTextFieldsMap(new HashMap<String, JTextField>());
        // add all elements
        for (Property<String, String> property : getPartProperties()) {
            add(getPropertyKeyLabel(property));
            JTextField ptf = getPropertyValueField(property);
            add(ptf);
            // add reference to text field to hash map
            getPartPropertiesTextFieldsMap().put(property.getKey(), ptf);
        }
    }

    /**
     * Actualizes properties according to filled in values.
     */
    public void actualizeProperties() {
        for (String key : getPartPropertiesTextFieldsMap().keySet()) {
            Property<String, String> p = getPartProperties().getProperty(key);
            getPartProperties().setProperty(key, getPartPropertiesTextFieldsMap().get(key).getText(),
                                            p.getDescription());
        }
    }

    /**
     * @param partPropertiesTextFieldsMap the partPropertiesTextFieldsMap to set
     */
    private void setPartPropertiesTextFieldsMap(HashMap<String, JTextField> partPropertiesTextFieldsMap) {
        this.partPropertiesTextFieldsMap = partPropertiesTextFieldsMap;
    }

    /**
     * @return the partPropertiesTextFieldsMap
     */
    private HashMap<String, JTextField> getPartPropertiesTextFieldsMap() {
        return this.partPropertiesTextFieldsMap;
    }
}
