package cz.cvut.fel.schematicEditor.parts.lightweightParts.partProperties;

import java.util.Vector;

import cz.cvut.fel.schematicEditor.parts.PartPropertiesInterface;
import cz.cvut.fel.schematicEditor.parts.PartType;
import cz.cvut.fel.schematicEditor.parts.lightweightParts.PartProperties;

/**
 * This class implements properties with are unique for voltage source part.
 *
 * @author Urban Kravjansky
 */
public class VoltageSourceProperties extends PartProperties {
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
     * @see PartPropertiesInterface#getNetlist()
     */
    public String getNetlist() {
        return "";
    }

    /**
     * @see PartPropertiesInterface#getPartPinNames()
     */
    public Vector<String> getPartPinNames() {
        return null;
    }

    /**
     * @see PartPropertiesInterface#setPartPinNames(java.util.Vector)
     */
    public void setPartPinNames(Vector<String> partPinNames) {
    }

    /**
     * @see PartPropertiesInterface#getPartType()
     */
    public PartType getPartType() {
        return PartType.VOLTAGE_SOURCE;
    }

    /**
     * @see PartPropertiesInterface#setNetlist(java.lang.String)
     */
    public void setNetlist(String netlist) {
        // TODO Auto-generated method stub

    }

    /**
     * @see PartPropertiesInterface#getProperty(java.lang.String)
     */
    public String getProperty(String propertyName) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see PartPropertiesInterface#setProperty(java.lang.String, java.lang.String)
     */
    public void setProperty(String propertyName, String value) {
        // TODO Auto-generated method stub

    }
}
