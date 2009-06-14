package cz.cvut.fel.schematicEditor.parts.lightweightParts.partProperties;

import java.util.Vector;

import cz.cvut.fel.schematicEditor.parts.PartProperties;
import cz.cvut.fel.schematicEditor.parts.PartType;
import cz.cvut.fel.schematicEditor.parts.lightweightParts.LightweightPartProperties;

/**
 * This class implements properties with are unique for resistor part.
 *
 * @author Urban Kravjansky
 */
public class ResistorProperties extends LightweightPartProperties {
    /**
     * This method instantiates new instance.
     */
    public ResistorProperties() {
        super();

        setNetlistPrototype("r<name> <connectorP> <connectorM> [<value>] [<model>] [l=<length>] [w=<width>] [temp=<temperature>]");

        // setProperty("abraka.dabra", "bububu");
        setProperty("value", "10k");
        setProperty("name", "resistor");
        setProperty("connectorP", "A");
        setProperty("connectorM", "B");
        setProperty("netlist", getNetlist());
    }

    /**
     * @see PartProperties#getPartPinNames()
     */
    public Vector<String> getPartPinNames() {
        return null;
    }

    /**
     * @see PartProperties#setPartPinNames(java.util.Vector)
     */
    public void setPartPinNames(Vector<String> partPinNames) {
    }

    /**
     * @see PartProperties#getPartType()
     */
    public PartType getPartType() {
        return PartType.RESISTOR;
    }
}
