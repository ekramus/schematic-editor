package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.partPropertiesDialog;

import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.parts.PartProperty;
import cz.cvut.fel.schematicEditor.parts.originalParts.OriginalPartProperties;

/**
 * This class implements panel for access to part properties. It is created dynamically based on properties of each
 * part. It is shown inside of {@link PartPropertiesDialog}.
 *
 * @author Urban Kravjansky
 *
 */
public class PartPropertiesDialogPanel extends JPanel {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger               logger;
    /**
     * {@link PartPropertiesDialogPanel} singleton instance field.
     */
    private static PartPropertiesDialogPanel  instance                    = null;
    /**
     * Part properties, which is being manipulated with.
     */
    private OriginalPartProperties              partProperties              = null;
    /**
     * Stores reference to text field for each part property key.
     */
    private HashMap<String, JTextField> partPropertiesTextFieldsMap = null;

    /**
     * This method instantiates new instance.
     *
     */
    private PartPropertiesDialogPanel() {
        logger = Logger.getLogger(PartPropertiesDialogPanel.class.getName());
    }

    /**
     * Singleton {@link PartPropertiesDialogPanel} instance getter.
     *
     * @return singleton {@link PartPropertiesDialogPanel} instance.
     */
    public static PartPropertiesDialogPanel getInstance() {
        if (PartPropertiesDialogPanel.instance == null) {
            PartPropertiesDialogPanel.instance = new PartPropertiesDialogPanel();
        }
        return PartPropertiesDialogPanel.instance;
    }

    /**
     * Creates {@link JLabel} with text from {@link PartProperty} key.
     *
     * @param property {@link PartProperty} to retrieve key.
     * @return {@link JLabel} instance with text from {@link PartProperty} key.
     */
    private JLabel getPropertyKeyLabel(PartProperty<String, String> property) {
        JLabel result = new JLabel();

        result.setText(property.getKey() + ": ");

        return result;
    }

    /**
     * Creates {@link JTextField} with text from {@link PartProperty} value.
     *
     * @param property {@link PartProperty} to retrieve value.
     * @return {@link JTextField} instance with text from {@link PartProperty} value.
     */
    private JTextField getPropertyValueField(PartProperty<String, String> property) {
        JTextField result = new JTextField();

        result.setText(property.getValue());
        result.setPreferredSize(new Dimension(50, 20));

        return result;
    }

    /**
     * @return the partProperties
     */
    public OriginalPartProperties getPartProperties() {
        return this.partProperties;
    }

    /**
     * @param partProperties the partProperties to set
     */
    public void setPartProperties(OriginalPartProperties partProperties) {
        this.partProperties = partProperties;

        logger.debug("properties panel will be rebuilded");
        // clean up panel
        removeAll();
        // reinitialize partProperties
        setPartPropertiesTextFieldsMap(new HashMap<String, JTextField>());
        // add all elements
        for (PartProperty<String, String> property : getPartProperties()) {
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
            PartProperty<String, String> p = getPartProperties().getProperty(key);
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
