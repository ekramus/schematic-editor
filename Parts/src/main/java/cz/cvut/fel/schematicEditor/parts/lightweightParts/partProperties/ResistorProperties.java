package cz.cvut.fel.schematicEditor.parts.lightweightParts.partProperties;

import cz.cvut.fel.schematicEditor.parts.PartProperties;
import cz.cvut.fel.schematicEditor.parts.PartType;
import cz.cvut.fel.schematicEditor.parts.lightweightParts.LightweightPartProperties;

/**
 * This class implements properties with are unique for resistor part.
 *
 * @author Urban Kravjansky
 */
public class ResistorProperties extends LightweightPartProperties {
    /**
     * This method instantiates new instance.
     */
    public ResistorProperties() {
        super();

        setNetlistPrototype("r<name> <n1> <n2> <value>");

        setProperty("value", "");
        setProperty("name", "");
        setProperty("n1", "");
        setProperty("n2", "");
    }

    /**
     * @see PartProperties#getPartType()
     */
    public PartType getPartType() {
        return PartType.RESISTOR;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.PartProperties#duplicate()
     */
    public PartProperties duplicate() {
        return new ResistorProperties();
    }
}
