package cz.cvut.fel.schematicEditor.parts.synchronizedPartProperties;

import cz.cvut.fel.schematicEditor.parts.PartType;
import cz.cvut.fel.schematicEditor.parts.SynchronizedPartProperties;

/**
 * This class implements properties with are unique for capacitor part.
 *
 * @author Urban Kravjansky
 */
public class CapacitorProperties extends SynchronizedPartProperties {
    /**
     * Prototype of netlist string, where codes will be replaced with values.
     */
    private final String netlistPrototype = "c<name> <connectorP> <connectorM> [<value>] [<model>] [l=<length>] [w=<width>] [ic=<initial_voltage>]";

    /**
     * This method instantiates new instance.
     */
    public CapacitorProperties() {
        super();
    }

    /**
     * @see SynchronizedPartProperties#getNetlist()
     */
    @Override
    public String getNetlist() {
        return expandPrototype(this.netlistPrototype, this);
    }

    /**
     * @see SynchronizedPartProperties#getPartType()
     */
    @Override
    public PartType getPartType() {
        return PartType.CAPACITOR;
    }
}
