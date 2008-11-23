package cz.cvut.fel.schematicEditor.element.properties.partProperties;

import java.util.Vector;

import cz.cvut.fel.schematicEditor.element.properties.PartProperties;
import cz.cvut.fel.schematicEditor.element.properties.PartType;

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
    public InductorProperties(String variant, String description) {
        super(variant, description);

        // add inductor specific values into constructor
        for (InductorPropertiesEnum inductorProperty : InductorPropertiesEnum.values()) {
            setProperty(inductorProperty.getKey(), "");
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
     * @see cz.cvut.fel.schematicEditor.element.properties.PartProperties#getPartConnectors()
     */
    @Override
    @Deprecated
    public Vector<String> getPartConnectors() {
        Vector<String> result = new Vector<String>();

        result.add(getProperty(InductorPropertiesEnum.CONNECTOR_P.getKey()).getValue());
        result.add(getProperty(InductorPropertiesEnum.CONNECTOR_M.getKey()).getValue());

        return result;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.element.properties.PartProperties#getPartType()
     */
    @Override
    public PartType getPartType() {
        return PartType.INDUCTOR;
    }
}
