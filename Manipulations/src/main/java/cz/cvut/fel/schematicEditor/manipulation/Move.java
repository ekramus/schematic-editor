package cz.cvut.fel.schematicEditor.manipulation;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import javax.swing.RepaintManager;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.TransformationNode;
import cz.cvut.fel.schematicEditor.manipulation.exception.ManipulationExecutionException;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
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
     * @see cz.cvut.fel.schematicEditor.manipulation.Select#getManipulationType()
     */
    @Override
    public ManipulationType getManipulationType() {
        return ManipulationType.MOVE;
    }

    @Override
    protected Manipulation duplicate() {
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
    protected void execute() throws ManipulationExecutionException {
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
    protected void unexecute() throws ManipulationExecutionException {
        // TODO Auto-generated method stub

    }

    /**
     * Specific <code>manipulationEnd</code> method for {@link Move} manipulation.
     * 
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationEnd(MouseEvent, Double, ManipulationQueue,
     *      GroupNode, boolean)
     */
    @Override
    public boolean manipulationEnd(MouseEvent e, Rectangle2D.Double r2d, ManipulationQueue manipulationQueue,
            GroupNode groupNode, boolean isMouseClicked) throws UnknownManipulationException {
        if (isActive()) {
            logger.debug("object MOVED");

            Snap s = Snap.getInstance();

            replaceLastManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));

            GroupNode gn = getManipulatedGroup();

            // enable manipulated group
            gn.setDisabled(false);

            // create select manipulation, so manipulation can proceed as select
            Select select = (Select) ManipulationFactory.create(ManipulationType.SELECT);
            select.setManipulatedGroup(gn);

            // processing final manipulation step
            logger.trace("processing final SELECT step");

            // schemeInvalidate(gn.getBounds());
        }
        return true;
    }

    /**
     * Specific <code>manipulationStart</code> method for {@link Move} manipulation.
     * 
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationStart(MouseEvent, Double,
     *      ManipulationQueue, GroupNode, boolean)
     */
    @Override
    public void manipulationStart(MouseEvent e, Double r2d, ManipulationQueue mq, GroupNode groupNode,
            boolean isMouseClicked) throws UnknownManipulationException {
        Snap s = Snap.getInstance();

        // check, whether move is possible or not
        if (isActive() && getManipulatedGroup() == groupNode.findHit(r2d)) {
            // add identity transformation, so it can be later changed
            groupNode.add(new TransformationNode(Transformation.getIdentity()));

            // add two copies of same coordinates to be able to replace last one
            addManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));
            addManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));

            // set manipulated group disabled
            groupNode.setDisabled(true);
            // ScenePanel.getInstance().schemeInvalidate(gn.getBounds());
        }
        // move is not possible - fall back to Select manipulation
        else {
            // mq.add(ManipulationFactory.create(ManipulationType.SELECT));
        }
    }
}
