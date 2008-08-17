package cz.cvut.fel.schematicEditor.manipulation;

import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.manipulation.exception.ManipulationExecutionException;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;

/**
 * @author Urban Kravjansky
 */
public class Delete extends Manipulation {
    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#getManipulationType()
     */
    @Override
    public ManipulationType getManipulationType() {
        return ManipulationType.DELETE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#newInstance(cz.cvut.fel.schematicEditor
     * .manipulation.Manipulation)
     */
    @Override
    protected Manipulation duplicate() {
        Delete d = new Delete();
        return d;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#execute(GroupNode)
     */
    @Override
    protected void execute(GroupNode topNode) throws ManipulationExecutionException {
        topNode.delete(getManipulatedGroup());
    }

    @Override
    protected void reexecute(GroupNode topNode) throws ManipulationExecutionException {
        // top node is not deleted
        if (!getManipulatedGroup().isDisabled()) {
            topNode.delete(getManipulatedGroup());
        }
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#unexecute(GroupNode)
     */
    @Override
    protected void unexecute(GroupNode topNode) throws ManipulationExecutionException {
        topNode.undelete(getManipulatedGroup());
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationStop(MouseEvent, Rectangle2D,
     *      ManipulationQueue, GroupNode, boolean)
     */
    @Override
    public Manipulation manipulationStop(MouseEvent e, Rectangle2D r2d, ManipulationQueue manipulationQueue,
            GroupNode topNode, boolean isMouseClicked) throws UnknownManipulationException {
        if (isMouseClicked && isActive()) {
            setManipulatedGroup(topNode.findHit(r2d));
        }
        return this;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationStart(MouseEvent, Rectangle2D,
     *      ManipulationQueue, GroupNode, boolean)
     */
    @Override
    public Manipulation manipulationStart(MouseEvent e, Rectangle2D r2d, ManipulationQueue manipulationQueue,
            GroupNode gn, boolean isMouseClicked) throws UnknownManipulationException {
        setActive(true);

        return this;
    }
}
