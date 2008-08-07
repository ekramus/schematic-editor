package cz.cvut.fel.schematicEditor.manipulation;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D.Double;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.TransformationNode;
import cz.cvut.fel.schematicEditor.manipulation.exception.ManipulationExecutionException;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.support.Snap;
import cz.cvut.fel.schematicEditor.support.Transformation;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;

/**
 * @author Urban Kravjansky
 */
public class Edit extends Manipulation {
    /**
     * Manipulation delta.
     */
    private Point2D.Double delta;
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger  logger;

    protected Edit() {
        super(null);
        setManipulatedGroup(null);

        logger = Logger.getLogger(this.getClass().getName());
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#getManipulationType()
     */
    @Override
    public ManipulationType getManipulationType() {
        return ManipulationType.EDIT;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationStart(MouseEvent, Rectangle2D.Double,
     *      ManipulationQueue, GroupNode, boolean)
     */
    @Override
    public Manipulation manipulationStart(MouseEvent e, Double r2d, ManipulationQueue manipulationQueue,
            GroupNode topNode, boolean isMouseClicked) throws UnknownManipulationException {
        Snap s = Snap.getInstance();

        // check, whether move is possible or not
        if (isActive() && getManipulatedGroup() == topNode.findHit(r2d)) {
            // add identity transformation, so it can be later changed
            getManipulatedGroup().add(new TransformationNode(Transformation.getIdentity()));

            // add two copies of same coordinates to be able to replace last one
            addManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));
            addManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));
        }
        // edit is not possible - fall back to Select manipulation
        else {
            // nothing to do
        }
        return this;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationStop(MouseEvent, Rectangle2D.Double,
     *      ManipulationQueue, GroupNode, boolean)
     */
    @Override
    public Manipulation manipulationStop(MouseEvent e, Double r2d, ManipulationQueue manipulationQueue,
            GroupNode grouNode, boolean isMouseClicked) throws UnknownManipulationException {
        if (isActive()) {
            logger.trace("object EDITED");

            Snap s = Snap.getInstance();

            // replace last manipulation coordinates for delta
            replaceLastManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));

            // compute delta
            setDelta(computeDelta());
        }
        return this;
    }

    /**
     * @return the delta
     */
    private Point2D.Double getDelta() {
        return this.delta;
    }

    /**
     * @param delta the delta to set
     */
    private void setDelta(Point2D.Double delta) {
        this.delta = delta;
    }

    @Override
    protected Manipulation duplicate() {
        // Edit cannot be duplicated, it is always dynamic. Instead, select is created.
        Select s = new Select();
        s.setManipulatedGroup(getManipulatedGroup());

        return s;
    }

    /**
     * @see Manipulation#execute(GroupNode)
     */
    @Override
    protected void execute(GroupNode topNode) throws ManipulationExecutionException {
        // change edited point using delta
        getManipulatedGroup().stopEdit(new UnitPoint(getDelta()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#unexecute()
     */
    @Override
    protected void unexecute(GroupNode topNode) throws ManipulationExecutionException {
        // TODO Auto-generated method stub

    }

    /**
     * Computes delta value based on <code>x</code> and <code>y</code> parameters.
     * 
     * @return delta shift parameter.
     */
    private Point2D.Double computeDelta() {
        return new Point2D.Double(getX().lastElement().doubleValue() - getX().firstElement().doubleValue(), getY()
                .lastElement().doubleValue() - getY().firstElement().doubleValue());
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#addManipulationCoordinates(Unit,Unit)
     */
    @Override
    public void addManipulationCoordinates(Unit x, Unit y) {
        super.addManipulationCoordinates(x, y);

        setDelta(computeDelta());
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#replaceLastManipulationCoordinates(cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit,
     *      cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit)
     */
    @Override
    public void replaceLastManipulationCoordinates(Unit x, Unit y) {
        super.replaceLastManipulationCoordinates(x, y);

        setDelta(computeDelta());
        getManipulatedGroup().switchEdit(new UnitPoint(getDelta()));
    }
}
