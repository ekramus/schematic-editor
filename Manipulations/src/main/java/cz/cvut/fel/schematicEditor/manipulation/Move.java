package cz.cvut.fel.schematicEditor.manipulation;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

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
 * This class implements move {@link Manipulation}. Using this manipulation user is able to move with selected groups of
 * objects. Move is achieved by creating {@link Transformation} and adding it to the selected group node.
 *
 * @author Urban Kravjansky
 */
public class Move extends Manipulation {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger  logger;
    /**
     * Manipulation delta.
     */
    private Point2D.Double delta;
    /**
     * Applied {@link Transformation}.
     */
    private Transformation appliedTransformation;

    /**
     * Default {@link Move} constructor. It initializes this {@link Manipulation}.
     *
     * @param topNode top node of scene graph.
     */
    protected Move(GroupNode topNode) {
        super(topNode);

        setAppliedTransformation(Transformation.getIdentity());

        logger = Logger.getLogger(this.getClass().getName());
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#addManipulationCoordinates(Unit,Unit)
     */
    @Override
    public void addManipulationCoordinates(Unit x, Unit y) {
        super.addManipulationCoordinates(x, y);

        setDelta(computeDelta());
        switchLastTransformation(getDelta());
    }

    /**
     * Switches last transformation node with transformation node containing delta.
     *
     * @param delta shift parameter.
     */
    private void switchLastTransformation(Point2D.Double delta) {
        Transformation tNew = Transformation.getShift(delta.getX(), delta.getY());
        Transformation t = Transformation.multiply(getAppliedTransformation().getInverse(), tNew);
        setAppliedTransformation(tNew);

        TransformationNode tn = new TransformationNode(t);

        getManipulatedGroup().add(tn);
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
     * @see cz.cvut.fel.schematicEditor.manipulation.Select#getManipulationType()
     */
    @Override
    public ManipulationType getManipulationType() {
        return ManipulationType.MOVE;
    }

    /**
     * Specific <code>manipulationStart</code> method for {@link Move} manipulation.
     *
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationStart(MouseEvent, Rectangle2D,
     *      ManipulationQueue, boolean)
     */
    @Override
    public Manipulation manipulationStart(MouseEvent e, Rectangle2D r2d, ManipulationQueue mq, boolean isMouseClicked)
            throws UnknownManipulationException {
        // check, whether move is possible or not
        if (isActive() && (getManipulatedGroup() == getTopNode().findHit(r2d))) {
            // add identity transformation, so it can be later changed
            getManipulatedGroup().add(new TransformationNode(Transformation.getIdentity()));

            // add two copies of same coordinates to be able to replace last one
            UnitPoint up = new UnitPoint(e.getX(), e.getY());
            UnitPoint snap = Snap.getSnap(up, getSnapCoordinates());
            addManipulationCoordinates(snap.getUnitX(), snap.getUnitY());
            addManipulationCoordinates(snap.getUnitX(), snap.getUnitY());
        }
        // move is not possible - fall back to Select manipulation
        else {
            // nothing to do
        }
        return this;
    }

    /**
     * Specific <code>manipulationEnd</code> method for {@link Move} manipulation.
     *
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationStop(MouseEvent, Rectangle2D,
     *      ManipulationQueue, boolean)
     */
    @Override
    public Manipulation manipulationStop(MouseEvent e, Rectangle2D r2d, ManipulationQueue manipulationQueue,
            boolean isMouseClicked) throws UnknownManipulationException {
        if (isActive()) {
            logger.debug("object MOVED");

            UnitPoint up = new UnitPoint(e.getX(), e.getY());
            UnitPoint snap = Snap.getSnap(up, getSnapCoordinates());
            replaceLastManipulationCoordinates(snap.getUnitX(), snap.getUnitY());

            // compute delta
            setDelta(computeDelta());
        }
        return this;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#replaceLastManipulationCoordinates(cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit,
     *      cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit)
     */
    @Override
    public void replaceLastManipulationCoordinates(Unit x, Unit y) {
        super.replaceLastManipulationCoordinates(x, y);

        setDelta(computeDelta());
        switchLastTransformation(getDelta());
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

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#duplicate()
     */
    @Override
    protected Manipulation duplicate() {
        Move m = new Move(getTopNode());
        // m.setManipulatedGroup((GroupNode) getManipulatedGroup().duplicate());
        m.setManipulatedGroup(getManipulatedGroup());
        m.setManipulationCoordinates(getX(), getY());
        m.setDelta(computeDelta());

        return m;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#createNext()
     */
    @Override
    protected Manipulation createNext() {
        // After move select is next manipulation..
        Select s = new Select(getTopNode());

        // we cannot duplicate group, all manipulations are with the one selected
        s.setManipulatedGroup(getManipulatedGroup());
        // there is some manipulated group
        if (getManipulatedGroup() != null) {
            s.setActive(true);
        }

        return s;
    }

    /**
     * @see Manipulation#execute()
     */
    @Override
    protected void execute() throws ManipulationExecutionException {
        switchLastTransformation(getDelta());
    }

    /**
     * @see Manipulation#reexecute()
     */
    @Override
    protected void reexecute() throws ManipulationExecutionException {
        Transformation t = Transformation.getShift(getDelta().getX(), getDelta().getY());
        TransformationNode tn = new TransformationNode(t);

        getManipulatedGroup().add(tn);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#unexecute()
     */
    @Override
    protected void unexecute() throws ManipulationExecutionException {
        Transformation t = Transformation.getShift(getDelta().getX(), getDelta().getY());
        TransformationNode tn = new TransformationNode(t.getInverse());

        getManipulatedGroup().add(tn);
    }

    /**
     * @param appliedTransformation the appliedTransformation to set
     */
    private void setAppliedTransformation(Transformation appliedTransformation) {
        this.appliedTransformation = appliedTransformation;
    }

    /**
     * @return the appliedTransformation
     */
    private Transformation getAppliedTransformation() {
        return this.appliedTransformation;
    }
}
