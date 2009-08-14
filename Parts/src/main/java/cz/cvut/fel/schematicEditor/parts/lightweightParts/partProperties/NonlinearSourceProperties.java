package cz.cvut.fel.schematicEditor.parts.lightweightParts.partProperties;

import cz.cvut.fel.schematicEditor.parts.PartType;
import cz.cvut.fel.schematicEditor.parts.lightweightParts.LightweightPartProperties;

/**
 * This class implements properties with are unique for nonlinear source part.
 *
 * @author Urban Kravjansky
 */
public class NonlinearSourceProperties extends LightweightPartProperties {

    /**
     * This method instantiates new instance.
     */
    public NonlinearSourceProperties() {
        super();

        setNetlistPrototype("B<name> <n1> <n2> V=<expression>");

        setProperty("expression", "");
        setProperty("name", "");
        setProperty("n1", "");
        setProperty("n2", "");
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.PartProperties#getPartType()
     */
    public PartType getPartType() {
        return PartType.NONLINEAR_SOURCE;
    }
}
