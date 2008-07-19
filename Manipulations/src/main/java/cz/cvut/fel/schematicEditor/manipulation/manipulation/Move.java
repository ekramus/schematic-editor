package cz.cvut.fel.schematicEditor.manipulation.manipulation;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.application.Gui;
import cz.cvut.fel.schematicEditor.core.Structures;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.TransformationNode;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationQueue;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.exception.ManipulationExecutionException;
import cz.cvut.fel.schematicEditor.support.Snap;
import cz.cvut.fel.schematicEditor.support.Transformation;

public class Move extends Manipulation {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger logger;

    protected Move() {
        super(null);
        setManipulatedGroup(null);

        logger = Logger.getLogger(this.getClass().getName());
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
        // TODO modify transformations, so they use temporary data, not real
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

    /**
     * Specific <code>manipulationEnd</code> method for {@link Move} manipulation.
     * 
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#manipulationEnd(MouseEvent, Double,
     *      ManipulationQueue, Snap)
     */
    @Override
    public void manipulationEnd(MouseEvent e, Rectangle2D.Double r2d, ManipulationQueue manipulationQueue, Snap s) {
        if (isActive()) {
            logger.debug("object MOVED");

            replaceLastManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));

            GroupNode gn = getManipulatedGroup();

            // enable manipulated group
            gn.setDisabled(false);

            // create select manipulation, so manipulation can proceed as select
            Select select = (Select) ManipulationFactory.create(ManipulationType.SELECT);
            select.setManipulatedGroup(gn);

            // TODO remove, it should be completely replaced by ManipulationQueue.
            Structures.setManipulation(select);

            // add and execute manipulation
            manipulationQueue.offerManipulation(this);
            manipulationQueue.execute();

            // TODO remove, it should be somewhere else
            Structures.getScenePanel().processFinalManipulationStep();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#manipulationStart(java.awt.event.MouseEvent,
     *      java.awt.geom.Rectangle2D.Double, cz.cvut.fel.schematicEditor.support.Snap)
     */
    @Override
    public void manipulationStart(MouseEvent e, Double r2d, Snap s) {
        // TODO Auto-generated method stub

    }
}
