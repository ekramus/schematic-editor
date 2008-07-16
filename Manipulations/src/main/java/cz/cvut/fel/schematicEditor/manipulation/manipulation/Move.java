package cz.cvut.fel.schematicEditor.manipulation.manipulation;

import java.awt.geom.Point2D;

import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.TransformationNode;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.exception.ManipulationExecutionException;
import cz.cvut.fel.schematicEditor.support.Transformation;

public class Move extends Manipulation {
    protected Move() {
        super(null);
        setManipulatedGroup(null);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Select#getManipulationType()
     */
    @Override
    public ManipulationType getManipulationType() {
        return ManipulationType.MOVE;
    }

    @Override
    protected Manipulation duplitate() {
        Move m = new Move();
        return m;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#isManipulatingElements()
     */
    @Override
    public boolean isManipulatingElements() {
        // TODO Auto-generated method stub
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
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#execute()
     */
    @Override
    public void execute() throws ManipulationExecutionException {
        // compute delta
        int i = getX().size() - 2;
        Point2D delta = new Point2D.Double(getX().lastElement().doubleValue() - getX().get(i).doubleValue(), getY()
                .lastElement().doubleValue() - getY().get(i).doubleValue());

        // create transformation node using delta
        TransformationNode tn = new TransformationNode(Transformation.getShift(delta.getX(), delta.getY()));
        // replace last transformation
        GroupNode gn = getManipulatedGroup();
        gn.removeLastTransformation();
        gn.add(tn);
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
