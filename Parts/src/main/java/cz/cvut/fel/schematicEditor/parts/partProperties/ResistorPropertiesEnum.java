package cz.cvut.fel.schematicEditor.parts.partProperties;

/**
 * This enumeration contains keys and descriptions of all resistor part properties. It is based on netlist definition
 * string: <br/>
 * <code>r&lt;name&gt; &lt;connectorP&gt; &lt;connectorM&gt; [&lt;value&gt;] [&lt;model&gt;] [l=&lt;length&gt; ] [w=&lt;width&gt;] [temp=&lt;temperature&gt;]</code>
 *
 * @author Urban Kravjansky
 */
public enum ResistorPropertiesEnum {
    /**
     * Name of resistor part.
     */
    NAME("name", "Name of resistor part."),
    /**
     * Value of resistance.
     */
    VALUE("value", "Value of resistance."),
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
    MODEL("model", "Model of resistor."),
    /**
     * ??
     **/
    LENGTH("length", "??"),
    /**
     * ??
     */
    WIDTH("width", "??"),
    /**
     * ??
     */
    TEMPERATURE("temperature", "??");

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
    ResistorPropertiesEnum(String key, String description) {
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
