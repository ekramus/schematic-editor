package cz.cvut.fel.schematicEditor.parts;

/**
 * This class defines part property methods and thus structure.
 *
 * @author Urban Kravjansky
 *
 */
public abstract class PartProperty {
    /**
     * Property key.
     */
    private String key   = "";
    /**
     * Property value.
     */
    private Object value = null;

    /**
     * This method instantiates new instance.
     *
     * @param key key of property.
     * @param value value of property.
     *
     */
    public PartProperty(String key, Object value) {
        setKey(key);
        setValue(value);
    }

    /**
     * @param key the key to set
     */
    private void setKey(String key) {
        this.key = key;
    }

    /**
     * @param value the value to set
     */
    private void setValue(Object value) {
        this.value = value;
    }

    /**
     * Get key of property.
     *
     * @return Key of property.
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Get value of property
     *
     * @return Value of property.
     */
    public Object getValue() {
        return this.value;
    }
}
