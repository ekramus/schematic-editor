package cz.cvut.fel.schematicEditor.parts.lightweightParts.partProperties;

import cz.cvut.fel.schematicEditor.parts.PartType;
import cz.cvut.fel.schematicEditor.parts.lightweightParts.LightweightPartProperties;

/**
 * This class implements properties with are unique for inductor part.
 *
 * @author Urban Kravjansky
 */
public class InductorProperties extends LightweightPartProperties {

    /**
     * This method instantiates new instance.
     */
    public InductorProperties() {
        super();

        setNetlistPrototype("l<name> <n1> <n2> <value>");

        setProperty("value", "");
        setProperty("name", "");
        setProperty("n1", "");
        setProperty("n2", "");
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.PartProperties#getPartType()
     */
    public PartType getPartType() {
        return PartType.INDUCTOR;
    }
}
