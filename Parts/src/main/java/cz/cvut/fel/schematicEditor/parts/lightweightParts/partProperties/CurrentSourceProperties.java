package cz.cvut.fel.schematicEditor.parts.lightweightParts.partProperties;

import cz.cvut.fel.schematicEditor.parts.PartType;
import cz.cvut.fel.schematicEditor.parts.lightweightParts.LightweightPartProperties;

/**
 * This class implements properties with are unique for current source part.
 *
 * @author Urban Kravjansky
 */
public class CurrentSourceProperties extends LightweightPartProperties {

    /**
     * This method instantiates new instance.
     */
    public CurrentSourceProperties() {
        super();

        setNetlistPrototype("i<name> <n1> <n2> [dc <dc_value>] [ac <ac_value>] <tran_value>");

        setProperty("tran_value", "");
        setProperty("ac_value", "");
        setProperty("dc_value", "");
        setProperty("name", "");
        setProperty("n1", "");
        setProperty("n2", "");
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.PartProperties#getPartType()
     */
    public PartType getPartType() {
        return PartType.CURRENT_SOURCE;
    }
}
