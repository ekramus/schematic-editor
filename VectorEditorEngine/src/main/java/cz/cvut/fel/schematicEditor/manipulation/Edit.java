/**
 *
 */
package cz.cvut.fel.schematicEditor.manipulation;

import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.types.Transformation;

/**
 * @author uk
 */
public class Edit extends Manipulation {
    private GroupNode manipulatedGroup;

    /**
     *
     */
    protected Edit() {
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
        return ManipulationType.EDIT;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#isManipulatingElements()
     */
    @Override
    public boolean isManipulatingElements() {
        // TODO Auto-generated method stub
        return true;
    }

    /*
     * (non-Javadoc)
     *
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#isManipulatingGroups()
     */
    @Override
    public boolean isManipulatingGroups() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#newInstance(cz.cvut.fel.schematicEditor.manipulation.Manipulation)
     */
    @Override
    protected Manipulation duplitate() {
        Edit e = new Edit();
        return e;
    }
}
