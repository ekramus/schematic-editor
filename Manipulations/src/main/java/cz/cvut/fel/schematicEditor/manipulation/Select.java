package cz.cvut.fel.schematicEditor.manipulation;

import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D.Double;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.ParameterNode;
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
    private GroupNode     manipulatedGroup;

    /**
     *
     */
    protected Select() {
        super(null);
        logger = Logger.getLogger(this.getClass().getName());
        setManipulatedGroup(null);
    }

    public final void setManipulatedGroup(GroupNode manipulatedGroup) {
        this.manipulatedGroup = manipulatedGroup;
    }

    /**
     * @return the manipulatedGroup
     */
    public final GroupNode getManipulatedGroup() {
        return this.manipulatedGroup;
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
        Select s = new Select();

        // duplicate parameters
        // s.setActive(isActive());
        s.setManipulatedGroup(getManipulatedGroup());

        return s;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#execute()
     */
    @Override
    protected void execute(GroupNode topNode) throws ManipulationExecutionException {
        // activate selection
        setActive(true);

        logger.trace(this + " executed");
    }

    /*
     * (non-Javadoc)
     * 
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#unexecute()
     */
    @Override
    protected void unexecute(GroupNode topNode) throws ManipulationExecutionException {
        setActive(false);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationEnd(MouseEvent, Rectangle2D.Double,
     *      ManipulationQueue, boolean)
     */
    @Override
    public Manipulation manipulationEnd(MouseEvent e, Double r2d, ManipulationQueue manipulationQueue,
            GroupNode groupNode, boolean isMouseClicked) throws UnknownManipulationException {
        // mouse clicked and hit something
        if (isMouseClicked) {
            // some group is hit
            if (groupNode.isHit(r2d)) {
                logger.trace("object for SELECTION hitb");

                // set selected group
                GroupNode gn = groupNode.findHit(r2d);

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
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationStart(MouseEvent, Rectangle2D.Double,
     *      ManipulationQueue, boolean)
     */
    @Override
    public Manipulation manipulationStart(MouseEvent e, Double r2d, ManipulationQueue manipulationQueue,
            GroupNode groupNode, boolean isMouseClick) throws UnknownManipulationException {
        Manipulation result = this;

        // select is active AND GroupNode is already selected
        if (groupNode.findHit(r2d) == getManipulatedGroup()) {
            // select is in edit active zone
            if (getManipulatedGroup().startEditing(r2d)) {
                // create Edit manipulation
                Edit edit = (Edit) ManipulationFactory.create(ManipulationType.EDIT);
                edit.setManipulatedGroup(getManipulatedGroup());
                edit.setActive(true);

                // continue with edit manipulation start
                edit.manipulationStart(e, r2d, manipulationQueue, groupNode, isMouseClick);

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
                Move move = (Move) ManipulationFactory.create(ManipulationType.MOVE);
                move.setManipulatedGroup(getManipulatedGroup());
                move.setActive(true);

                // continue with move manipulation start
                result = move.manipulationStart(e, r2d, manipulationQueue, groupNode, isMouseClick);
            }
        }
        return result;
    }
}
