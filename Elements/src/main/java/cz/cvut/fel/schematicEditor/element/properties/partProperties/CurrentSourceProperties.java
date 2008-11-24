package cz.cvut.fel.schematicEditor.element.properties.partProperties;

import java.util.Vector;

import cz.cvut.fel.schematicEditor.element.properties.PartProperties;
import cz.cvut.fel.schematicEditor.element.properties.PartType;

/**
 * This class implements properties with are unique for current source part.
 *
 * @author Urban Kravjansky
 */
public class CurrentSourceProperties extends PartProperties {
    /**
     * Prototype of netlist string, where codes will be replaced with values.
     */
    private final String netlistPrototype = "i<name> <connectorP> <connectorM> [dc <dc_analysis>]";

    /**
     * This method instantiates new instance.
     *
     * @param variant part variant.
     * @param description part description.
     */
    public CurrentSourceProperties(String variant, String description) {
        super(variant, description);

        // add voltage source specific values into constructor
        for (CapacitorPropertiesEnum capacitorProperty : CapacitorPropertiesEnum.values()) {
            setProperty(capacitorProperty.getKey(), "");
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
     * @see cz.cvut.fel.schematicEditor.element.properties.PartProperties#getPartConnectorNames()
     */
    @Override
    @Deprecated
    public Vector<String> getPartConnectorNames() {
        Vector<String> result = new Vector<String>();

        result.add(getProperty(CurrentSourcePropertiesEnum.CONNECTOR_P.getKey()).getValue());
        result.add(getProperty(CurrentSourcePropertiesEnum.CONNECTOR_M.getKey()).getValue());

        return result;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.element.properties.PartProperties#getPartType()
     */
    @Override
    public PartType getPartType() {
        return PartType.CURRENT_SOURCE;
    }
}
