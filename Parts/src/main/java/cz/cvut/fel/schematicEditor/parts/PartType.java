package cz.cvut.fel.schematicEditor.parts;

/**
 * This enum contains all part types available.
 *
 * @author Urban Kravjansky
 */
public enum PartType {
    /**
     * Identifier for bipolar junction transistor part.
     */
    BIPOLAR_JUNCTION_TRANSISTOR("Q", "Bipolar Junction Transistor"),
    /**
     * Identifier for resistor part.
     */
    DIODE("D", "Diode"),
    /**
     * Identifier for JFET part.
     */
    JFET("J", "JFET"),
    /**
     * Identifier for MES-FET part.
     */
    MES_FET("Z", "MES-FET"),
    /**
     * Identifier for MOS-FET part.
     */
    MOS_FET("M", "MOS-FET"),
    /**
     * Identifier for Linear Current - Controlled Current Source part.
     */
    LINEAR_CURRENT_CONTROLLED_CURRENT_SOURCE("F", "Linear Current - Controlled Current Source"),
    /**
     * Identifier for Linear Voltage - Controlled Voltage Source part.
     */
    LINEAR_VOLTAGE_CONTROLLED_VOLTAGE_SOURCE("E", "Linear Voltage - Controlled Voltage Source"),
    /**
     * Identifier for Linear Voltage - Controlled Current Source part.
     */
    LINEAR_VOLTAGE_CONTROLLED_CURRENT_SOURCE("G", "Linear Voltage - Controlled Current Source"),
    /**
     * Identifier for Linear Current - Controlled Voltage Source part.
     */
    LINEAR_CURRENT_CONTROLLED_VOLTAGE_SOURCE("H", "Linear Current - Controlled Voltage Source"),
    /**
     * Identifier for Nonlinear Source ASRC part.
     */
    NONLINEAR_SOURCE_ASRC("B", "Nonlinear Source ASRC"),
    /**
     * Identifier for resistor part.
     */
    RESISTOR("R", "Resistor"),
    /**
     * Identifier for inductor part.
     */
    INDUCTOR("L", "Inductor"),
    /**
     * Identifier for capacitor part.
     */
    CAPACITOR("C", "Capacitor"),
    /**
     * Identifier for operation amp part.
     */
    OPERATION_AMP("A", "Operation Amp."),
    /**
     * Identifier for coupled inductor part.
     */
    COUPLED_INDUCTOR("K", "Coupled Inductor"),
    /**
     * Identifier for current controlled switch part.
     */
    CURRENT_CONTROLLED_SWITCH("W", "Current Controlled Switch"),
    /**
     * Identifier for current voltage switch part.
     */
    VOLTAGE_CONTROLLED_SWITCH("S", "Voltage Controlled Switch"),
    /**
     * Identifier for voltage source part.
     */
    VOLTAGE_SOURCE("V", "Voltage Source"),
    /**
     * Identifier for current source part.
     */
    CURRENT_SOURCE("I", "Current Source"),
    /**
     * Identifier for transformator part.
     */
    TRANSFORMATOR("T", "Transformator");

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
