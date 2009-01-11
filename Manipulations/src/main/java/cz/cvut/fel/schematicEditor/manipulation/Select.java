package cz.cvut.fel.schematicEditor.manipulation;

import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.manipulation.exception.ManipulationExecutionException;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;

/**
 * @author Urban Kravjansky
 */
public class Select extends Manipulation {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger logger;

    /**
     * @param topNode top node of scene graph.
     * @param source object, which initiated creation of this {@link Manipulation}.
     */
    protected Select(GroupNode topNode, Object source) {
        super(topNode, source);
        logger = Logger.getLogger(this.getClass().getName());
        setManipulatedGroup(null);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#getManipulationType()
     */
    @Override
    public ManipulationType getManipulationType() {
        return ManipulationType.SELECT;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#duplicate()
     */
    @Override
    protected Manipulation duplicate() {
        Select s = new Select(getTopNode(), getSource());

        // duplicate parameters
        s.setManipulatedGroup(getManipulatedGroup());

        // there is some manipulated group
        if (getManipulatedGroup() != null) {
            s.setActive(true);
        }

        return s;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#execute ()
     */
    @Override
    protected void execute() throws ManipulationExecutionException {
        // activate selection
        setActive(true);

        logger.trace(this + " executed");
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#unexecute ()
     */
    @Override
    protected void unexecute() throws ManipulationExecutionException {
        setActive(false);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationStop(MouseEvent, Rectangle2D,
     *      ManipulationQueue, double, boolean)
     */
    @Override
    public Manipulation manipulationStop(MouseEvent e, Rectangle2D r2d, ManipulationQueue manipulationQueue,
            double zoomFactor, boolean isMouseClicked) throws UnknownManipulationException {
        // mouse clicked and hit something
        if (isMouseClicked) {
            // some group is hit
            if (getTopNode().isHit(r2d, zoomFactor)) {
                logger.trace("object for SELECTION hit");

                // set selected group
                GroupNode gn = getTopNode().findHit(r2d, zoomFactor);

                setManipulatedGroup(gn);
            }
            // no group is hit
            else {
                // nothing to do
            }
        }
        return this;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationStart(MouseEvent, Rectangle2D,
     *      ManipulationQueue, double, boolean)
     */
    @Override
    public Manipulation manipulationStart(MouseEvent e, Rectangle2D r2d, ManipulationQueue manipulationQueue,
            double zoomFactor, boolean isMouseClick) throws UnknownManipulationException {
        Manipulation result = this;

        // select is active AND GroupNode is already selected
        if ((getManipulatedGroup() != null) && ((getTopNode().findHit(r2d, zoomFactor) == getManipulatedGroup()) || (getManipulatedGroup().isHit(r2d, zoomFactor)))) {
            // select is in edit active zone
            if (getManipulatedGroup().startEdit(r2d, zoomFactor)) {
                // create Edit manipulation
                Edit edit = (Edit) ManipulationFactory.create(ManipulationType.EDIT, getTopNode(), getSource());
                edit.setManipulatedGroup(getManipulatedGroup());
                edit.setActive(true);

                // continue with edit manipulation start
                edit.manipulationStart(e, r2d, manipulationQueue, zoomFactor, isMouseClick);

                result = edit;
            }
            // select is in rotate active zone
            else if (getManipulatedGroup().isRotateZone(r2d)) {
                // TODO implement rotate active zone
                result = null;
            }
            // select can be used for move
            else {
                logger.trace("creating MOVE manipulation");

                // create Move manipulation
                Move move = (Move) ManipulationFactory.create(ManipulationType.MOVE, getTopNode(), getSource());
                move.setManipulatedGroup(getManipulatedGroup());
                move.setActive(true);

                // continue with move manipulation start
                result = move.manipulationStart(e, r2d, manipulationQueue, zoomFactor, isMouseClick);
            }
        }
        return result;
    }
}
