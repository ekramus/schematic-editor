package cz.cvut.fel.schematicEditor.parts.lightweightParts.partProperties;

import java.util.ArrayList;

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
     * @see cz.cvut.fel.schematicEditor.parts.PartProperties#setPartPinNames(java.util.ArrayList)
     */
    public void setPartPinNames(ArrayList<String> partPinNames) {
        getPartPinNames().add("n1");
        getPartPinNames().add("n2");
    }

    /**
     * @see PartProperties#getPartType()
     */
    public PartType getPartType() {
        return PartType.RESISTOR;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.lightweightParts.LightweightPartProperties#getPartPinNames()
     */
    @Override
    public ArrayList<String> getPartPinNames() {
        // TODO Auto-generated method stub
        return super.getPartPinNames();
    }
}
