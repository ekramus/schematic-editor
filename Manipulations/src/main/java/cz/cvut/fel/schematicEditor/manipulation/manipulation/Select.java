/**
 *
 */
package cz.cvut.fel.schematicEditor.manipulation.manipulation;

import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.exception.ManipulationExecutionException;

/**
 * @author uk
 */
public class Select extends Manipulation {
    private GroupNode manipulatedGroup;

    /**
     *
     */
    protected Select() {
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
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#getManipulationType()
     */
    @Override
    public ManipulationType getManipulationType() {
        return ManipulationType.SELECT;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#isManipulatingElements()
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
    protected Manipulation duplitate() {
        Select s = new Select();

        // duplicate parameters
        s.setActive(isActive());
        s.setManipulatedGroup(getManipulatedGroup());
        // s.setManipulationCoordinates(getX(), getY());

        return s;
    }

    /* (non-Javadoc)
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#execute()
     */
    @Override
    public void execute() throws ManipulationExecutionException {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#unexecute()
     */
    @Override
    public void unexecute() throws ManipulationExecutionException {
        // TODO Auto-generated method stub
        
    }
}
