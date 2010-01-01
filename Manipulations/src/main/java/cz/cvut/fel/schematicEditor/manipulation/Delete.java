package cz.cvut.fel.schematicEditor.manipulation;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import cz.cvut.fel.schematicEditor.manipulation.exception.ManipulationExecutionException;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.original.graphNode.GroupNode;

/**
 * @author Urban Kravjansky
 */
public class Delete extends Manipulation {
    /**
     * This method instantiates new instance.
     *
     * @param topNode top node of scene graph.
     * @param source object, which initiated creation of this {@link Manipulation}.
     */
    protected Delete(GroupNode topNode, Object source) {
        super(topNode, source);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#getManipulationType()
     */
    @Override
    public ManipulationType getManipulationType() {
        return ManipulationType.DELETE;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#duplicate()
     */
    @Override
    protected Manipulation duplicate() {
        Delete d = new Delete(getTopNode(), getSource());

        return d;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#execute()
     */
    @Override
    protected void execute() throws ManipulationExecutionException {
        getTopNode().delete(getManipulatedGroup());
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#reexecute()
     */
    @Override
    protected void reexecute() throws ManipulationExecutionException {
        // top node is not deleted
        if (!getManipulatedGroup().isDisabled()) {
            getTopNode().delete(getManipulatedGroup());
        }
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#unexecute()
     */
    @Override
    protected void unexecute() throws ManipulationExecutionException {
        getTopNode().undelete(getManipulatedGroup());
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationStop(MouseEvent, Rectangle2D,
     *      ManipulationQueue, double, boolean, Graphics2D)
     */
    @Override
    public Manipulation manipulationStop(MouseEvent e, Rectangle2D r2d, ManipulationQueue manipulationQueue,
            double zoomFactor, boolean isMouseClicked, Graphics2D g2d) throws UnknownManipulationException {
        if (isMouseClicked && isActive()) {
            setManipulatedGroup(getTopNode().findHit(r2d, zoomFactor, g2d));
        }
        return this;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationStart(MouseEvent, Rectangle2D,
     *      ManipulationQueue, double, boolean, Graphics2D)
     */
    @Override
    public Manipulation manipulationStart(MouseEvent e, Rectangle2D r2d, ManipulationQueue manipulationQueue,
            double zoomFactor, boolean isMouseClicked, Graphics2D g2d) throws UnknownManipulationException {
        setActive(true);

        return this;
    }
}
