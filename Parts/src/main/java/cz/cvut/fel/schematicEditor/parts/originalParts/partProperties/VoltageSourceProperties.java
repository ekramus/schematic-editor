package cz.cvut.fel.schematicEditor.parts.originalParts.partProperties;

import java.util.Vector;

import cz.cvut.fel.schematicEditor.parts.PartType;
import cz.cvut.fel.schematicEditor.parts.originalParts.OriginalPartProperties;

/**
 * This class implements properties with are unique for voltage source part.
 *
 * @author Urban Kravjansky
 */
public class VoltageSourceProperties extends OriginalPartProperties {
    /**
     * Prototype of netlist string, where codes will be replaced with values.
     */
    private final String netlistPrototype = "v<name> <connectorP> <connectorM> [dc <dc_analysis>]";

    /**
     * This method instantiates new instance.
     *
     * @param variant part variant.
     * @param description part description.
     */
    public VoltageSourceProperties(String variant, String description) {
        super(variant, description);

        // add voltage source specific values into constructor
        for (CapacitorPropertiesEnum capacitorProperty : CapacitorPropertiesEnum.values()) {
            setProperty(capacitorProperty.getKey(), "");
        }
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.originalParts.OriginalPartProperties.properties.SynchronizedPartProperties#getNetlist()
     */
    @Override
    public String getNetlist() {
        return expandPrototype(this.netlistPrototype, this);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.originalParts.OriginalPartProperties.properties.SynchronizedPartProperties#getPartPinNames()
     */
    @Deprecated
    @Override
    public Vector<String> getPartPinNames() {
        Vector<String> result = new Vector<String>();

        result.add(getProperty(VoltageSourcePropertiesEnum.CONNECTOR_M.getKey()).getValue());
        result.add(getProperty(VoltageSourcePropertiesEnum.CONNECTOR_P.getKey()).getValue());

        return result;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.originalParts.OriginalPartProperties.properties.SynchronizedPartProperties#setPartPinNames(java.util.Vector)
     */
    @Override
    public void setPartPinNames(Vector<String> partPinNames) {
        setProperty(VoltageSourcePropertiesEnum.CONNECTOR_M.getKey(), partPinNames.get(0));
        setProperty(VoltageSourcePropertiesEnum.CONNECTOR_P.getKey(), partPinNames.get(1));
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.originalParts.OriginalPartProperties.properties.SynchronizedPartProperties#getPartType()
     */
    @Override
    public PartType getPartType() {
        return PartType.VOLTAGE_SOURCE;
    }
}
