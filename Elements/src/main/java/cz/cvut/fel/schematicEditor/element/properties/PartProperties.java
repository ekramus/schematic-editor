package cz.cvut.fel.schematicEditor.element.properties;

import java.util.Vector;

import cz.cvut.fel.schematicEditor.element.element.part.Part;

/**
 * This class encapsulates properties specific for any {@link Part}.
 *
 * @author Urban Kravjansky
 */
public class PartProperties {
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
     * @return the partConnectors
     */
    public Vector<String> getPartConnectors() {
        return this.partConnectors;
    }

    /**
     * Returns list of all properties in form of {@link Vector}. Each property is also {@link Vector} containing:
     * <dl>
     * <dt><code>name</code></dt>
     * <dd>name of property</dd>
     * <dt><code>value</code></dt>
     * <dd>current value of property</dd>
     *</dl>
     *
     * @return List of all properties in form of {@link Vector}.
     */
    public Vector<Vector<String>> getPartPropertiesVector() {
        Vector<Vector<String>> result = new Vector<Vector<String>>();

        Vector<String> pv = new Vector<String>();
        pv.add("variant");
        pv.add(getPartVariant());
        result.add(pv);

        pv = new Vector<String>();
        pv.add("description");
        pv.add(getPartDescription());
        result.add(pv);

        pv = new Vector<String>();
        pv.add("connectors");
        pv.addAll(getPartConnectors());
        result.add(pv);

        return result;
    }
}
