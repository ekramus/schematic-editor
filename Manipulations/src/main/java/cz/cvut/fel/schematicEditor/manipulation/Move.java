package cz.cvut.fel.schematicEditor.manipulation;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.NodeFactory;
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
     * @param source object, which initiated creation of this {@link Manipulation}.
     */
    protected Move(GroupNode topNode, Object source) {
        super(topNode, source);

        setAppliedTransformation(Transformation.getIdentity());

        logger = Logger.getLogger(this.getClass().getName());
    }

    /**
     * @see Manipulation#addManipulationCoordinates(Unit, Unit)
     */
    @Override
    public void addManipulationCoordinates(Unit x, Unit y) {
        super.addManipulationCoordinates(x, y);

        setDelta(computeDelta());
        try {
            switchLastTransformation(getDelta());
        } catch (ManipulationExecutionException e) {
            logger.error("Error in manipulation");
        }
    }

    /**
     * Switches last transformation node with transformation node containing delta.
     *
     * @param delta shift parameter.
     * @throws ManipulationExecutionException In case of manipulation execution problems.
     */
    private void switchLastTransformation(Point2D.Double delta) throws ManipulationExecutionException {
        Transformation tNew = Transformation.getShift(delta.getX(), delta.getY());
        Transformation t = Transformation.multiply(getAppliedTransformation().getInverse(), tNew);
        setAppliedTransformation(tNew);

        TransformationNode tn = NodeFactory.createTransformationNode(t);

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
     * @see Manipulation#manipulationStart(MouseEvent, Rectangle2D, ManipulationQueue, double, boolean, Graphics2D)
     */
    @Override
    public Manipulation manipulationStart(MouseEvent e, Rectangle2D r2d, ManipulationQueue mq, double zoomFactor,
            boolean isMouseClicked, Graphics2D g2d) throws UnknownManipulationException {
        // check, whether move is possible or not
        if (isActive() && (getManipulatedGroup() == getTopNode().findHit(r2d, zoomFactor, g2d))) {
            // add identity transformation, so it can be later changed
            getManipulatedGroup().add(NodeFactory.createTransformationNode(Transformation.getIdentity()));

            // add two copies of same coordinates to be able to replace last one
            UnitPoint up = getScaledUnitPoint(e.getX(), e.getY(), zoomFactor);
            // Element el = getManipulatedGroup().getChildrenElementList().getFirst().getElement();
            // UnitPoint snap = Snap.getSnap(up, getSnapCoordinates(), el.getX(), el.getY());
            UnitPoint snap = Snap.getSnap(up, null, null, null);
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
     * @see Manipulation#manipulationStop(MouseEvent, Rectangle2D, ManipulationQueue, double, boolean, Graphics2D)
     */
    @Override
    public Manipulation manipulationStop(MouseEvent e, Rectangle2D r2d, ManipulationQueue manipulationQueue,
            double zoomFactor, boolean isMouseClicked, Graphics2D g2d) throws UnknownManipulationException {
        if (isActive()) {
            logger.debug("object MOVED");

            UnitPoint up = getScaledUnitPoint(e.getX(), e.getY(), zoomFactor);
            // Element el = getManipulatedGroup().getChildrenElementList().getFirst().getElement();
            // UnitPoint snap = Snap.getSnap(up, getSnapCoordinates(), el.getX(), el.getY());
            UnitPoint snap = Snap.getSnap(up, null, null, null);
            replaceLastManipulationCoordinates(snap.getUnitX(), snap.getUnitY());

            // compute delta
            setDelta(computeDelta());
        }
        return this;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#replaceLastManipulationCoordinates(cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit,
     *      cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit, double)
     */
    @Override
    public void replaceLastManipulationCoordinates(Unit x, Unit y) {
        super.replaceLastManipulationCoordinates(x, y);

        setDelta(computeDelta());
        try {
            switchLastTransformation(getDelta());
        } catch (ManipulationExecutionException e) {
            logger.error("Error in manipulation");
        }
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
        Move m = new Move(getTopNode(), getSource());
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
        Select s = new Select(getTopNode(), getSource());

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
        TransformationNode tn = NodeFactory.createTransformationNode(t);

        getManipulatedGroup().add(tn);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#unexecute()
     */
    @Override
    protected void unexecute() throws ManipulationExecutionException {
        Transformation t = Transformation.getShift(getDelta().getX(), getDelta().getY());

        TransformationNode tn = NodeFactory.createTransformationNode(t.getInverse());

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
