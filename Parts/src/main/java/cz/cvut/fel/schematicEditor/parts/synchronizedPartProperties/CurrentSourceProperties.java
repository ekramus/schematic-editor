package cz.cvut.fel.schematicEditor.parts.synchronizedPartProperties;

import cz.cvut.fel.schematicEditor.parts.PartType;
import cz.cvut.fel.schematicEditor.parts.SynchronizedPartProperties;

/**
 * This class implements properties with are unique for current source part.
 *
 * @author Urban Kravjansky
 */
public class CurrentSourceProperties extends SynchronizedPartProperties {
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
    public CurrentSourceProperties() {
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
        return PartType.CURRENT_SOURCE;
    }
}
