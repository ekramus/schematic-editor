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
     */
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
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationStart(MouseEvent,
     *      java.awt.geom.Rectangle2D, ManipulationQueue, GroupNode, boolean)
     */
    @Override
    public Manipulation manipulationStart(MouseEvent e, Rectangle2D r2d, ManipulationQueue manipulationQueue,
            GroupNode topNode, boolean isMouseClicked) throws UnknownManipulationException {
        // check, whether move is possible or not
        if (isActive() && getManipulatedGroup() == topNode.findHit(r2d)) {
            // add identity transformation, so it can be later changed
            getManipulatedGroup().add(new TransformationNode(Transformation.getIdentity()));

            // add two copies of same coordinates to be able to replace last one
            addManipulationCoordinates(Snap.getSnap(e.getX()), Snap.getSnap(e.getY()));
            addManipulationCoordinates(Snap.getSnap(e.getX()), Snap.getSnap(e.getY()));
        }
        // edit is not possible - fall back to Select manipulation
        else {
            // nothing to do
        }
        return this;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationStop(MouseEvent, Rectangle2D,
     *      ManipulationQueue, GroupNode, boolean) ManipulationQueue, GroupNode, boolean)
     */
    @Override
    public Manipulation manipulationStop(MouseEvent e, Rectangle2D r2d, ManipulationQueue manipulationQueue,
            GroupNode grouNode, boolean isMouseClicked) throws UnknownManipulationException {
        if (isActive()) {
            logger.trace("object EDITED");

            // replace last manipulation coordinates for delta
            replaceLastManipulationCoordinates(Snap.getSnap(e.getX()), Snap.getSnap(e.getY()));

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
        Edit e = new Edit();

        e.setManipulatedGroup(getManipulatedGroup());
        e.setManipulationCoordinates(getX(), getY());
        e.setDelta(computeDelta());

        return e;
    }

    @Override
    protected Manipulation createNext() {
        // create next manipulation after edit.
        Select s = new Select();

        // we cannot duplicate group, all manipulations are with the one selected
        s.setManipulatedGroup(getManipulatedGroup());
        // there is some manipulated group
        if (getManipulatedGroup() != null) {
            s.setActive(true);
        }

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

    /**
     * @see Manipulation#execute(GroupNode)
     */
    @Override
    protected void reexecute(GroupNode topNode) throws ManipulationExecutionException {
        Element e = getManipulatedGroup().getEditedElement();

        if (e != null) {
            int i = e.getEditedPointIndex();
            if (i != -1) {
                Pixel x = new Pixel(e.getX().get(i).doubleValue() + getDelta().getX());
                Pixel y = new Pixel(e.getY().get(i).doubleValue() + getDelta().getY());
                e.getX().set(i, x);
                e.getY().set(i, y);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#unexecute()
     */
    @Override
    protected void unexecute(GroupNode topNode) throws ManipulationExecutionException {
        Element e = getManipulatedGroup().getEditedElement();

        if (e != null) {
            int i = e.getEditedPointIndex();
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
