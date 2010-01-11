package cz.cvut.fel.schematicEditor.parts.lightweightParts.partProperties;

import cz.cvut.fel.schematicEditor.parts.PartProperties;
import cz.cvut.fel.schematicEditor.parts.PartType;
import cz.cvut.fel.schematicEditor.parts.lightweightParts.LightweightPartProperties;

/**
 * This class implements properties with are unique for diode part.
 *
 * @author Urban Kravjansky
 */
public class DiodeProperties extends LightweightPartProperties {

    /**
     * This method instantiates new instance.
     */
    public DiodeProperties() {
        super();

        setNetlistPrototype("");

        setProperty("value", "");
        setProperty("name", "");
        setProperty("n1", "");
        setProperty("n2", "");
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.PartProperties#getPartType()
     */
    public PartType getPartType() {
        return PartType.DIODE;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.PartProperties#duplicate()
     */
    public PartProperties duplicate() {
        return new DiodeProperties();
    }
}
