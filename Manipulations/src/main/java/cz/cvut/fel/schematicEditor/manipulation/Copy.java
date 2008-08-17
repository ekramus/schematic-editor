package cz.cvut.fel.schematicEditor.manipulation;

import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.manipulation.exception.ManipulationExecutionException;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;

/**
 * This class implements Copy manipulation.
 * 
 * @author Urban Kravjansky
 */
public class Copy extends Manipulation {
    /**
     * Instance of {@link ManipulationQueue} for accessing clippboard.
     */
    private ManipulationQueue manipulationQueue;

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#getManipulationType()
     */
    @Override
    public ManipulationType getManipulationType() {
        return ManipulationType.COPY;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationStart(MouseEvent, Rectangle2D,
     *      ManipulationQueue, GroupNode, boolean)
     */
    @Override
    public Manipulation manipulationStart(MouseEvent e, Rectangle2D r2d, ManipulationQueue manipulationQueue,
            GroupNode gn, boolean isMouseClicked) throws UnknownManipulationException {
        // mothing to do
        return this;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationStop(MouseEvent, Rectangle2D,
     *      ManipulationQueue, GroupNode, boolean)
     */
    @Override
    public Manipulation manipulationStop(MouseEvent e, Rectangle2D r2d, ManipulationQueue manipulationQueue,
            GroupNode topNode, boolean isMouseClicked) throws UnknownManipulationException {
        setManipulationQueue(manipulationQueue);

        return this;
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
     * @return the {@link ManipulationQueue} instance.
     */
    private ManipulationQueue getManipulationQueue() {
        return this.manipulationQueue;
    }

    /**
     * @param manipulationQueue the {@link ManipulationQueue} to set
     */
    private void setManipulationQueue(ManipulationQueue manipulationQueue) {
        this.manipulationQueue = manipulationQueue;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#duplicate()
     */
    @Override
    protected Manipulation duplicate() {
        Copy copy = new Copy();

        copy.setManipulatedGroup(getManipulatedGroup());
        copy.setManipulationQueue(getManipulationQueue());

        return copy;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#execute(cz.cvut.fel.schematicEditor.graphNode.GroupNode)
     */
    @Override
    protected void execute(GroupNode topNode) throws ManipulationExecutionException {
        getManipulationQueue().setClipboard((GroupNode) getManipulatedGroup().duplicate());
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#unexecute(cz.cvut.fel.schematicEditor.graphNode.GroupNode)
     */
    @Override
    protected void unexecute(GroupNode topNode) throws ManipulationExecutionException {
        getManipulationQueue().setClipboard(null);
    }

}
