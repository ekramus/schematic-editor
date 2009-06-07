package cz.cvut.fel.schematicEditor.parts.originalParts.partProperties;

import java.util.Vector;

import cz.cvut.fel.schematicEditor.parts.PartType;
import cz.cvut.fel.schematicEditor.parts.originalParts.OriginalPartProperties;

/**
 * This class implements properties with are unique for capacitor part.
 *
 * @author Urban Kravjansky
 */
public class CapacitorProperties extends OriginalPartProperties {
    /**
     * Prototype of netlist string, where codes will be replaced with values.
     */
    private final String netlistPrototype = "c<name> <connectorP> <connectorM> [<value>] [<model>] [l=<length>] [w=<width>] [ic=<initial_voltage>]";

    /**
     * This method instantiates new instance.
     *
     * @param variant part variant.
     * @param description part description.
     */
    public CapacitorProperties(String variant, String description) {
        super(variant, description);

        // add capacitor specific values into constructor
        for (CapacitorPropertiesEnum capacitorProperty : CapacitorPropertiesEnum.values()) {
            setProperty(capacitorProperty.getKey(), "");
        }
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.originalParts.OriginalPartProperties.properties.SynchronizedPartProperties#getNetlist()
     */
    @Override
    public String getNetlist() {
        return expandPrototype(this.netlistPrototype, this);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.originalParts.OriginalPartProperties.properties.SynchronizedPartProperties#getPartPinNames()
     */
    @Override
    @Deprecated
    public Vector<String> getPartPinNames() {
        Vector<String> result = new Vector<String>();

        result.add(getProperty(CapacitorPropertiesEnum.CONNECTOR_M.getKey()).getValue());
        result.add(getProperty(CapacitorPropertiesEnum.CONNECTOR_P.getKey()).getValue());

        return result;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.originalParts.OriginalPartProperties.properties.SynchronizedPartProperties#setPartPinNames(java.util.Vector)
     */
    @Override
    public void setPartPinNames(Vector<String> partPinNames) {
        setProperty(CapacitorPropertiesEnum.CONNECTOR_M.getKey(), partPinNames.get(0));
        setProperty(CapacitorPropertiesEnum.CONNECTOR_P.getKey(), partPinNames.get(1));
    }

    /*
     * (non-Javadoc)
     *
     * @see cz.cvut.fel.schematicEditor.element.properties.PartProperties#getPartType()
     */
    @Override
    public PartType getPartType() {
        return PartType.CAPACITOR;
    }
}
