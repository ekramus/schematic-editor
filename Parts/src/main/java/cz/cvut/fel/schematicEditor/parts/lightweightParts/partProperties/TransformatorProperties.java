package cz.cvut.fel.schematicEditor.parts.lightweightParts.partProperties;

import java.util.ArrayList;

import cz.cvut.fel.schematicEditor.parts.PartType;
import cz.cvut.fel.schematicEditor.parts.lightweightParts.LightweightPartProperties;

/**
 * This class implements properties with are unique for inductor part.
 *
 * @author Urban Kravjansky
 */
public class TransformatorProperties extends LightweightPartProperties {

    /**
     * This method instantiates new instance.
     */
    public TransformatorProperties() {
        super();

        setNetlistPrototype("l<name_Lx1> <n1> <n2> <value_Lx1>\nl<name_Lx2> n3 n4 <value_Lx2>\nk<name_Kx> <Lx1> <Lx2> <value_Kx>");

        setProperty("name_Lx1", "");
        setProperty("name_Lx2", "");
        setProperty("name_Kx", "");
        setProperty("value_Lx1", "");
        setProperty("value_Lx2", "");
        setProperty("value_Kx", "");
        setProperty("n1", "");
        setProperty("n2", "");
        setProperty("n3", "");
        setProperty("n4", "");
        setProperty("Lx1", "");
        setProperty("Lx2", "");
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.PartProperties#getPartPinValues()
     */
    @Override
    public ArrayList<String> getPartPinValues() {
        ArrayList<String> result = new ArrayList<String>();

        result.add("n1");
        result.add("n2");
        result.add("n3");
        result.add("n4");
        result.add("Lx1");
        result.add("Lx2");


        return result;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.PartProperties#getPartType()
     */
    public PartType getPartType() {
        return PartType.TRANSFORMATOR;
    }
}
