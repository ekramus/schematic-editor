package cz.cvut.fel.schematicEditor.manipulation.manipulation;

import java.awt.geom.Point2D;

import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.exception.ManipulationExecutionException;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;

/**
 * @author Urban Kravjansky
 */
public class Edit extends Manipulation {
    /**
     * 
     */
    protected Edit() {
        super(null);
        setManipulatedGroup(null);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#getManipulationType()
     */
    @Override
    public ManipulationType getManipulationType() {
        return ManipulationType.EDIT;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#isManipulatingElements()
     */
    @Override
    public boolean isManipulatingElements() {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#isManipulatingGroups()
     */
    @Override
    public boolean isManipulatingGroups() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    protected Manipulation duplitate() {
        Edit e = new Edit();
        return e;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#execute()
     */
    @Override
    public void execute() throws ManipulationExecutionException {
        // compute delta
        Point2D delta = new Point2D.Double(getX().lastElement().doubleValue() - getX().firstElement().doubleValue(),
                getY().lastElement().doubleValue() - getY().firstElement().doubleValue());

        // change edited point using delta
        getManipulatedGroup().stopEditing(new UnitPoint(delta));
    }

    /*
     * (non-Javadoc)
     * 
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#unexecute()
     */
    @Override
    public void unexecute() throws ManipulationExecutionException {
        // TODO Auto-generated method stub

    }
}
