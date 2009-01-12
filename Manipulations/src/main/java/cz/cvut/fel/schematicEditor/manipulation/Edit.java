package cz.cvut.fel.schematicEditor.manipulation;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.TransformationNode;
import cz.cvut.fel.schematicEditor.manipulation.exception.ManipulationExecutionException;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.support.Snap;
import cz.cvut.fel.schematicEditor.support.Transformation;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.computer.Pixel;
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

    /**
     * Default constructor. It initializes this {@link Edit} {@link Manipulation}.
     *
     * @param topNode top node of scene graph.
     * @param source object, which initiated creation of this {@link Manipulation}.
     */
    protected Edit(GroupNode topNode, Object source) {
        super(topNode, source);
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
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationStart(MouseEvent,
     *      java.awt.geom.Rectangle2D, ManipulationQueue, double, boolean)
     */
    @Override
    public Manipulation manipulationStart(MouseEvent e, Rectangle2D r2d, ManipulationQueue manipulationQueue,
            double zoomFactor, boolean isMouseClicked) throws UnknownManipulationException {
        // check, whether move is possible or not
        if (isActive() && (getManipulatedGroup() == getTopNode().findHit(r2d, zoomFactor))) {
            // add identity transformation, so it can be later changed
            getManipulatedGroup().add(new TransformationNode(Transformation.getIdentity()));

            // add two copies of same coordinates to be able to replace last one
            UnitPoint up = new UnitPoint(e.getX(), e.getY());
            Element el = getManipulatedGroup().getChildrenElementList().getFirst().getElement();
            UnitPoint snap = Snap.getSnap(up, getSnapCoordinates(), el.getX(), el.getY());
            addManipulationCoordinates(snap.getUnitX(), snap.getUnitY(), zoomFactor);
            addManipulationCoordinates(snap.getUnitX(), snap.getUnitY(), zoomFactor);
        }
        // edit is not possible - fall back to Select manipulation
        else {
            // nothing to do
        }
        return this;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationStop(MouseEvent, Rectangle2D,
     *      ManipulationQueue, double, boolean) ManipulationQueue, GroupNode, boolean)
     */
    @Override
    public Manipulation manipulationStop(MouseEvent e, Rectangle2D r2d, ManipulationQueue manipulationQueue,
            double zoomFactor, boolean isMouseClicked) throws UnknownManipulationException {
        if (isActive()) {
            logger.trace("object EDITED");

            // replace last manipulation coordinates for delta
            UnitPoint up = new UnitPoint(e.getX(), e.getY());
            Element el = getManipulatedGroup().getChildrenElementList().getFirst().getElement();
            UnitPoint snap = Snap.getSnap(up, getSnapCoordinates(), el.getX(), el.getY());
            replaceLastManipulationCoordinates(snap.getUnitX(), snap.getUnitY(), zoomFactor);

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

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#duplicate()
     */
    @Override
    protected Manipulation duplicate() {
        Edit e = new Edit(getTopNode(), getSource());

        e.setManipulatedGroup(getManipulatedGroup());
        e.setManipulationCoordinates(getX(), getY());
        e.setDelta(computeDelta());

        return e;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#createNext()
     */
    @Override
    protected Manipulation createNext() {
        // create next manipulation after edit.
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
        // change edited point using delta
        getManipulatedGroup().stopEdit(new UnitPoint(getDelta()));
    }

    /**
     * @see Manipulation#reexecute()
     */
    @Override
    protected void reexecute() throws ManipulationExecutionException {
        Element e = getManipulatedGroup().getEditedElement();

        if (e != null) {
            int i = e.getEditedCoordinateIndex();
            if (i != -1) {
                Pixel x = new Pixel(e.getX().get(i).doubleValue() + getDelta().getX());
                Pixel y = new Pixel(e.getY().get(i).doubleValue() + getDelta().getY());
                e.getX().set(i, x);
                e.getY().set(i, y);
            }
        }
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#unexecute()
     */
    @Override
    protected void unexecute() throws ManipulationExecutionException {
        Element e = getManipulatedGroup().getEditedElement();

        if (e != null) {
            int i = e.getEditedCoordinateIndex();
            if (i != -1) {
                Pixel x = new Pixel(e.getX().get(i).doubleValue() - getDelta().getX());
                Pixel y = new Pixel(e.getY().get(i).doubleValue() - getDelta().getY());
                e.getX().set(i, x);
                e.getY().set(i, y);
            }
        }
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
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#addManipulationCoordinates(Unit, Unit, double)
     */
    @Override
    public void addManipulationCoordinates(Unit x, Unit y, double zoomFactor) {
        super.addManipulationCoordinates(x, y, zoomFactor);

        setDelta(computeDelta());
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#replaceLastManipulationCoordinates(cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit,
     *      cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit, double)
     */
    @Override
    public void replaceLastManipulationCoordinates(Unit x, Unit y, double zoomFactor) {
        super.replaceLastManipulationCoordinates(x, y, zoomFactor);

        setDelta(computeDelta());
        getManipulatedGroup().switchEdit(new UnitPoint(getDelta()));
    }
}
