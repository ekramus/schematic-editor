package cz.cvut.fel.schematicEditor.parts.lightweightParts.partProperties;

import java.util.Vector;

import cz.cvut.fel.schematicEditor.parts.PartProperties;
import cz.cvut.fel.schematicEditor.parts.PartType;
import cz.cvut.fel.schematicEditor.parts.lightweightParts.LightweightPartProperties;

/**
 * This class implements properties with are unique for voltage source part.
 *
 * @author Urban Kravjansky
 */
public class VoltageSourceProperties extends LightweightPartProperties {
    /**
     * Prototype of netlist string, where codes will be replaced with values.
     */
    private final String netlistPrototype = "v<name> <connectorP> <connectorM> [dc <dc_analysis>]";

    /**
     * This method instantiates new instance.
     */
    public VoltageSourceProperties() {
        super();
    }

    /**
     * @see PartProperties#getNetlist()
     */
    public String getNetlist() {
        return "";
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
        return PartType.VOLTAGE_SOURCE;
    }

    /**
     * @see PartProperties#setNetlist(java.lang.String)
     */
    public void setNetlist(String netlist) {
        // TODO Auto-generated method stub

    }

    /**
     * @see PartProperties#getProperty(java.lang.String)
     */
    public String getProperty(String propertyName) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see PartProperties#setProperty(java.lang.String, java.lang.String)
     */
    public void setProperty(String propertyName, String value) {
        // TODO Auto-generated method stub

    }
}
