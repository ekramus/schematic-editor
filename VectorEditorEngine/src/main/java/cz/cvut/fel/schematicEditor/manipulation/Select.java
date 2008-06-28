/**
 * 
 */
package cz.cvut.fel.schematicEditor.manipulation;

import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.types.Transformation;

/**
 * @author uk
 */
public class Select extends Manipulation {
    private GroupNode manipulatedGroup;

    /**
     * 
     */
    public Select() {
        super(null);
        setManipulatedGroup(null);
    }

    public final void setManipulatedGroup(GroupNode manipulatedGroup) {
        this.manipulatedGroup = manipulatedGroup;
    }

    /**
     * @return the manipulatedGroup
     */
    public final GroupNode getManipulatedGroup() {
        return this.manipulatedGroup;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#getManipulationType()
     */
    @Override
    public ManipulationType getManipulationType() {
        return ManipulationType.SELECT;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#isManipulatingElements()
     */
    @Override
    public boolean isManipulatingElements() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#isManipulatingGroups()
     */
    @Override
    public boolean isManipulatingGroups() {
        // TODO Auto-generated method stub
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#newInstance(cz.cvut.fel.schematicEditor.manipulation.Manipulation)
     */
    @Override
    public Manipulation newInstance(Manipulation manipulation) {
        Select s = new Select();
        return s;
    }
}
