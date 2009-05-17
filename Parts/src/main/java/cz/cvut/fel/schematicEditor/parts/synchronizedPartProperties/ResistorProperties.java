package cz.cvut.fel.schematicEditor.parts.synchronizedPartProperties;

import java.util.Vector;

import cz.cvut.fel.schematicEditor.parts.PartProperties;
import cz.cvut.fel.schematicEditor.parts.PartType;

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
     *
     * @param variant part variant.
     * @param description part description.
     */
    public ResistorProperties(String variant, String description) {
        super(variant, description);

        // add resistor specific values into constructor
        for (ResistorPropertiesEnum resistorProperty : ResistorPropertiesEnum.values()) {
            setProperty(resistorProperty.getKey(), "");
        }
    }

    /**
     * @see cz.cvut.fel.schematicEditor.element.properties.PartProperties#getNetlist()
     */
    @Override
    public String getNetlist() {
        return expandPrototype(this.netlistPrototype, this);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.element.properties.PartProperties#getPartPinNames()
     */
    @Override
    @Deprecated
    public Vector<String> getPartPinNames() {
        Vector<String> result = new Vector<String>();

        result.add(getProperty(ResistorPropertiesEnum.CONNECTOR_M.getKey()).getValue());
        result.add(getProperty(ResistorPropertiesEnum.CONNECTOR_P.getKey()).getValue());

        return result;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.element.properties.PartProperties#setPartPinNames(java.util.Vector)
     */
    @Override
    public void setPartPinNames(Vector<String> partPinNames) {
        setProperty(ResistorPropertiesEnum.CONNECTOR_M.getKey(), partPinNames.get(0));
        setProperty(ResistorPropertiesEnum.CONNECTOR_P.getKey(), partPinNames.get(1));
    }

    /**
     * @see cz.cvut.fel.schematicEditor.element.properties.PartProperties#getPartType()
     */
    @Override
    public PartType getPartType() {
        return PartType.RESISTOR;
    }
}
