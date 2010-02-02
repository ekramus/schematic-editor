package cz.cvut.fel.schematicEditor.parts.lightweightParts.partProperties;

import cz.cvut.fel.schematicEditor.parts.PartProperties;
import cz.cvut.fel.schematicEditor.parts.PartType;
import cz.cvut.fel.schematicEditor.parts.lightweightParts.LightweightPartProperties;

/**
 * This class implements properties with are unique for diode part.
 *
 * @author Urban Kravjansky
 */
public class LinearVoltageControlledVoltageSourceProperties extends LightweightPartProperties {

    /**
     * This method instantiates new instance.
     */
    public LinearVoltageControlledVoltageSourceProperties() {
        super();

        setNetlistPrototype("");

        setProperty("value", "");
        setProperty("name", "");
        setProperty("n1", "");
        setProperty("n2", "");
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.PartProperties#getPartType()
     */
    public PartType getPartType() {
        return PartType.LINEAR_VOLTAGE_CONTROLLED_VOLTAGE_SOURCE;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.PartProperties#duplicate()
     */
    public PartProperties duplicate() {
        return new LinearVoltageControlledVoltageSourceProperties();
    }
}
