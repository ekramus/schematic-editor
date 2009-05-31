package cz.cvut.fel.schematicEditor.parts.originalParts.partProperties;

/**
 * This enumeration contains keys and descriptions of all voltage source part properties. It is based on netlist
 * definition string: <br/>
 * <code>v&lt;name&gt &lt;connectorP&gt; &lt;connectorM&gt; [dc &lt;dc_analysis&gt;]</code>
 *
 * @author Urban Kravjansky
 */
public enum VoltageSourcePropertiesEnum {
    /**
     * Name of voltage source part.
     */
    NAME("name", "Name of voltage source part."),
    /**
     * Name of + connector.
     */
    CONNECTOR_P("connectorP", "Name of + connector."),
    /**
     * Name of - connector.
     */
    CONNECTOR_M("connectorM", "Name of - connector."),
    /**
     * Model of resistor.
     */
    /**
     * ??
     */
    DC("dc_analysis", "value for DC/Tran analysis");

    /**
     * Key of property.
     */
    private String key;
    /**
     * Description of property.
     */
    private String description;

    /**
     * This method instantiates new instance.
     *
     * @param key key of property.
     * @param description description of key.
     */
    VoltageSourcePropertiesEnum(String key, String description) {
        setKey(key);
        setDescription(description);
    }

    /**
     * @param key the name to set
     */
    private void setKey(String key) {
        this.key = key;
    }

    /**
     * @param description the description to set
     */
    private void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return this.key;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }
}
