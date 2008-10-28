package cz.cvut.fel.schematicEditor.element.properties;

import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.element.element.part.Part;

/**
 * This class encapsulates properties specific for any {@link Part}.
 *
 * @author Urban Kravjansky
 */
public class PartProperties {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger  logger;
    /**
     * String containing part description.
     */
    private String         partDescription;
    /**
     * String containing part variant.
     */
    private String         partVariant;
    /**
     * Vector containing names of all part connectors.
     */
    private Vector<String> partConnectors;

    /**
     * Default constructor. It initializes part with default values.
     *
     * @param variant variant of part.
     * @param description desccription of part.
     */
    public PartProperties(String variant, String description) {
        logger = Logger.getLogger(this.getClass().getName());

        setPartDescription(description);
        setPartVariant(variant);
    }

    /**
     * @return the partDescription
     */
    public String getPartDescription() {
        return this.partDescription;
    }

    /**
     * @return the partVariant
     */
    public String getPartVariant() {
        return this.partVariant;
    }

    /**
     * @param partDescription the partDescription to set
     */
    private void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    /**
     * @param partVariant the partVariant to set
     */
    private void setPartVariant(String partVariant) {
        this.partVariant = partVariant;
    }

    /**
     * @param partConnectors the partConnectors to set
     */
    public void setPartConnectors(Vector<String> partConnectors) {
        this.partConnectors = partConnectors;
    }

    /**
     * Generates part connectors string. Connector names are separated using special symbol.
     *
     * @return the partConnectors
     */
    public String getPartConnectors() {
        String result = "";

        for (String connector : this.partConnectors) {
            // TODO set as configurable
            result += connector + "::";
        }

        result = result.substring(0, result.length() - 2);

        return result;
    }

    /**
     * Returns map of all properties in form of {@link Vector}. Each property is also {@link Vector} containing:
     * <dl>
     * <dt><code>name</code></dt>
     * <dd>name of property</dd>
     * <dt><code>value</code></dt>
     * <dd>current value of property</dd>
     *</dl>
     *
     * @return {@link HashMap} of all properties in form of {@link Vector}.
     */
    public HashMap<String, String> getPartPropertiesMap() {
        HashMap<String, String> result = new HashMap<String, String>();

        result.put("variant", getPartVariant());
        result.put("description", getPartDescription());
        result.put("connectors", getPartConnectors());

        return result;
    }
}
