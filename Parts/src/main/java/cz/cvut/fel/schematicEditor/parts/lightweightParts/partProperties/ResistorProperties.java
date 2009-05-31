package cz.cvut.fel.schematicEditor.parts.lightweightParts.partProperties;

import java.util.Vector;

import cz.cvut.fel.schematicEditor.parts.PartPropertiesInterface;
import cz.cvut.fel.schematicEditor.parts.PartType;
import cz.cvut.fel.schematicEditor.parts.lightweightParts.PartProperties;

/**
 * This class implements properties with are unique for resistor part.
 *
 * @author Urban Kravjansky
 */
public class ResistorProperties extends PartProperties {
    /**
     * Prototype of netlist string, where codes will be replaced with values.
     */
    private final String netlistPrototype = "r<name> <connectorP> <connectorM> [<value>] [<model>] [l=<length>] [w=<width>] [temp=<temperature>]";

    /**
     * This method instantiates new instance.
     */
    public ResistorProperties() {
        super();

        setProperty("name", "resistor");
        setProperty("value", "10k");
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
        return PartType.RESISTOR;
    }

    /**
     * @see PartPropertiesInterface#setNetlist(java.lang.String)
     */
    public void setNetlist(String netlist) {
        // TODO Auto-generated method stub

    }
}
