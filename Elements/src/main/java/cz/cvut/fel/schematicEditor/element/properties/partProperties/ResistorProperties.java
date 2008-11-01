package cz.cvut.fel.schematicEditor.element.properties.partProperties;

import java.util.HashMap;
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
     * @see cz.cvut.fel.schematicEditor.element.properties.PartProperties#getPartPropertiesMap()
     */
    @Override
    public HashMap<String, String> getPartPropertiesMap() {
        HashMap<String, String> result = new HashMap<String, String>();

        result.putAll(super.getPartPropertiesMap());

        result.put("name", "R");
        result.put("connectorP", "R+");
        result.put("connectorM", "R-");
        result.put("value", "10");

        return result;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.element.properties.PartProperties#getNetList()
     */
    @Override
    public String getNetList() {
        return expandPrototype(this.netlistPrototype, this);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.element.properties.PartProperties#getPartConnectors()
     */
    @Override
    public Vector<String> getPartConnectors() {
        Vector<String> result = new Vector<String>();

        result.add(getPartPropertiesMap().get("connector+"));
        result.add(getPartPropertiesMap().get("connector-"));

        return result;
    }
}
