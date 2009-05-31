package cz.cvut.fel.schematicEditor.parts.lightweightParts.partProperties;

import java.util.Vector;

import cz.cvut.fel.schematicEditor.parts.PartType;
import cz.cvut.fel.schematicEditor.parts.lightweightParts.PartProperties;

/**
 * This class implements properties with are unique for inductor part.
 *
 * @author Urban Kravjansky
 */
public class InductorProperties extends PartProperties {
    /**
     * Prototype of netlist string, where codes will be replaced with values.
     */
    private final String netlistPrototype = "l<name> <connectorP> <connectorM> <value> [ic=<initial_current>]";

    /**
     * This method instantiates new instance.
     *
     * @param variant part variant.
     * @param description part description.
     */
    public InductorProperties() {
        super();
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.originalParts.PartPropertiesInterface.properties.PartProperties#getNetlist()
     */
    public String getNetlist() {
        return "";
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.originalParts.PartPropertiesInterface.properties.PartProperties#getPartPinNames()
     */
    public Vector<String> getPartPinNames() {
        return null;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.originalParts.PartPropertiesInterface.properties.PartProperties#setPartPinNames(java.util.Vector)
     */
    public void setPartPinNames(Vector<String> partPinNames) {
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.originalParts.PartPropertiesInterface.properties.PartProperties#getPartType()
     */
    public PartType getPartType() {
        return PartType.INDUCTOR;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.lightweightParts.PartProperties#setNetlist(java.lang.String)
     */
    public void setNetlist(String netlist) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see cz.cvut.fel.schematicEditor.parts.PartPropertiesInterface#getProperty(java.lang.String)
     */
    public String getProperty(String propertyName) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see cz.cvut.fel.schematicEditor.parts.PartPropertiesInterface#setProperty(java.lang.String, java.lang.String)
     */
    public void setProperty(String propertyName, String value) {
        // TODO Auto-generated method stub

    }
}
