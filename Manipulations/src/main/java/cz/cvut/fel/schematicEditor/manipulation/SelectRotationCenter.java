package cz.cvut.fel.schematicEditor.manipulation;

import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.element.element.part.Part;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.manipulation.exception.ManipulationExecutionException;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.support.Snap;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;

/**
 * @author Urban Kravjansky
 */
public class SelectRotationCenter extends Manipulation {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger logger;

    /**
     * Default constructor. It initializes this {@link SelectRotationCenter} {@link Manipulation}.
     *
     * @param topNode top node of scene graph.
     */
    protected SelectRotationCenter(GroupNode topNode) {
        super(topNode);
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
     *      java.awt.geom.Rectangle2D, ManipulationQueue, boolean)
     */
    @Override
    public Manipulation manipulationStart(MouseEvent e, Rectangle2D r2d, ManipulationQueue manipulationQueue,
            boolean isMouseClicked) throws UnknownManipulationException {
        // check, whether select rotation center is possible or not
        if (isActive()) {
            // add coordinate
            UnitPoint up = new UnitPoint(e.getX(), e.getY());
            UnitPoint snap = Snap.getSnap(up, getSnapCoordinates());
            addManipulationCoordinates(snap.getUnitX(), snap.getUnitY());
        }
        // select rotation center is not possible - fall back to Select manipulation
        else {
            // nothing to do
        }
        return this;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationStop(MouseEvent, Rectangle2D,
     *      ManipulationQueue, boolean) ManipulationQueue, GroupNode, boolean)
     */
    @Override
    public Manipulation manipulationStop(MouseEvent e, Rectangle2D r2d, ManipulationQueue manipulationQueue,
            boolean isMouseClicked) throws UnknownManipulationException {
        return this;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#duplicate()
     */
    @Override
    protected Manipulation duplicate() {
        SelectRotationCenter e = new SelectRotationCenter(getTopNode());

        e.setManipulatedGroup(getManipulatedGroup());
        e.setManipulationCoordinates(getX(), getY());

        return e;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#createNext()
     */
    @Override
    protected Manipulation createNext() {
        // create next manipulation after edit.
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
        Part part = (Part) getManipulatedGroup().getChildrenElementList().getFirst().getElement();
        part.setRotationCenter(new UnitPoint(getX().firstElement(), getY().firstElement()));
    }

    /**
     * @see Manipulation#reexecute()
     */
    @Override
    protected void reexecute() throws ManipulationExecutionException {
        // nothing to do
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#unexecute()
     */
    @Override
    protected void unexecute() throws ManipulationExecutionException {
        // nothing to do
    }
}