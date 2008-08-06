package cz.cvut.fel.schematicEditor.manipulation;

import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D.Double;

import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.manipulation.exception.ManipulationExecutionException;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;

/**
 * @author Urban Kravjansky
 */
public class Delete extends Manipulation {
    private GroupNode deleteNode;

    /**
     * @param manipulatedElement
     */
    protected Delete() {
        super(null);
    }

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
        topNode.delete(getDeleteNode());
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#unexecute(GroupNode)
     */
    @Override
    protected void unexecute(GroupNode topNode) throws ManipulationExecutionException {
        topNode.add(getDeleteNode());
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationEnd(MouseEvent, Rectangle2D.Double,
     *      ManipulationQueue, GroupNode, boolean)
     */
    @Override
    public Manipulation manipulationEnd(MouseEvent e, Double r2d, ManipulationQueue manipulationQueue,
            GroupNode topNode, boolean isMouseClicked) throws UnknownManipulationException {
        if (isMouseClicked && isActive()) {
            setDeleteNode(topNode.findHit(r2d));
        }
        return this;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationStart(MouseEvent, Rectangle2D.Double,
     *      ManipulationQueue, GroupNode, boolean)
     */
    @Override
    public Manipulation manipulationStart(MouseEvent e, Double r2d, ManipulationQueue manipulationQueue, GroupNode gn,
            boolean isMouseClicked) throws UnknownManipulationException {
        setActive(true);

        return this;
    }

    /**
     * @param deleteNode the deleteNode to set
     */
    private void setDeleteNode(GroupNode deleteNode) {
        this.deleteNode = deleteNode;
    }

    /**
     * @return the deleteNode
     */
    private GroupNode getDeleteNode() {
        return deleteNode;
    }
}
