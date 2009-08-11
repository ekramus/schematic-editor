package cz.cvut.fel.schematicEditor.parts;

/**
 * This enum contains all part types available.
 *
 * @author Urban Kravjansky
 */
public enum PartType {
    /**
     * Identifier for resistor part.
     */
    RESISTOR("R", "rezistor"),
    /**
     * Identifier for inductor part.
     */
    INDUCTOR("L", "induktor"),
    /**
     * Identifier for capacitor part.
     */
    CAPACITOR("C", "capacitor"),
    /**
     * Identifier for voltage source part.
     */
    VOLTAGE_SOURCE("U", "voltage source"),
    /**
     * Identifier for current source part.
     */
    CURRENT_SOURCE("I", "current source"),
    /**
     * Identifier for nonlinear source part.
     */
    NONLINEAR_SOURCE("B", "nonlinear source"),
    /**
     * Identifier for transformator part.
     */
    TRANSFORMATOR("T", "transformator");

    private String variant;
    private String description;

    /**
     * @return the variant
     */
    public String getVariant() {
        return this.variant;
    }

    /**
     * @param variant the variant to set
     */
    private void setVariant(String variant) {
        this.variant = variant;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description the description to set
     */
    private void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method instantiates new instance.
     *
     */
    private PartType(String variant, String description) {
        setVariant(variant);
        setDescription(description);
    }
}
