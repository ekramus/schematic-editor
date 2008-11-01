package cz.cvut.fel.schematicEditor.element.properties.partProperties;

import java.util.Vector;

import cz.cvut.fel.schematicEditor.element.properties.PartProperties;

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

    // private final String netlistPrototype = "r<name> <connector+> <connector-> [<value>]";

    /**
     * This method instantiates new instance.
     *
     * @param variant part variant.
     * @param description part description.
     */
    public ResistorProperties(String variant, String description) {
        super(variant, description);

        // TODO add resistor specific values into constructor
    }

    /**
     * @see cz.cvut.fel.schematicEditor.element.properties.PartProperties#getNetlist()
     */
    @Override
    public String getNetlist() {
        return expandPrototype(this.netlistPrototype, this);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.element.properties.PartProperties#getPartConnectors()
     */
    @Override
    public Vector<String> getPartConnectors() {
        Vector<String> result = new Vector<String>();

        result.add(getPartPropertiesMap().get("connectorP"));
        result.add(getPartPropertiesMap().get("connectorM"));

        return result;
    }
}
