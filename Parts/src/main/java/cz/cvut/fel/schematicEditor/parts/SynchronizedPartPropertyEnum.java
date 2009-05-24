package cz.cvut.fel.schematicEditor.parts;

/**
 * @author Urban Kravjansky
 *
 */
public enum SynchronizedPartPropertyEnum {
    /**
     * Definition of property.
     */
    DEFINITION("definition"),
    /**
     * Czech name of property.
     */
    NAME_CS("name_cs"),
    /**
     * English name of property.
     */
    NAME_EN("name_en"),
    /**
     * Type of property field.
     */
    TYPE("type"),
    /**
     * Method of validation.
     */
    VALIDATE("validate"),
    /**
     * Czech help.
     */
    HELP_CS("help_cs"),
    /**
     * English help.
     */
    HELP_EN("help_en"),
    /**
     * Type of usage.
     */
    USE("use"),
    /**
     * Indicates, whether property is shown or not.
     */
    SHOW("show");

    /**
     * Regexp {@link String} value of specified enum.
     */
    private String value;

    /**
     * This method instantiates new instance.
     *
     * @param value value of property, which is added.
     *
     */
    private SynchronizedPartPropertyEnum(String value) {
        setValue(value);
    }

    /**
     * @return the value
     */
    public String getValue() {
        return this.value;
    }

    /**
     * @param value the value to set
     */
    private void setValue(String value) {
        this.value = value;
    }
}
