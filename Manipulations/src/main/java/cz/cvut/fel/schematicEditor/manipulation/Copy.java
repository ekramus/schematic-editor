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
     * This method instantiates new instance.
     *
     * @param topNode top node of scene graph.
     */
    protected Copy(GroupNode topNode) {
        super(topNode);
    }

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
     *      ManipulationQueue, boolean)
     */
    @Override
    public Manipulation manipulationStart(MouseEvent e, Rectangle2D r2d, ManipulationQueue manipulationQueue,
            boolean isMouseClicked) throws UnknownManipulationException {

        // nothing to do
        return this;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationStop(MouseEvent, Rectangle2D,
     *      ManipulationQueue, boolean)
     */
    @Override
    public Manipulation manipulationStop(MouseEvent e, Rectangle2D r2d, ManipulationQueue manipulationQueue,
            boolean isMouseClicked) throws UnknownManipulationException {
        setManipulationQueue(manipulationQueue);

        // nothing to do
        return this;
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
        Copy copy = new Copy(getTopNode());

        copy.setManipulatedGroup(getManipulatedGroup());
        copy.setManipulationQueue(getManipulationQueue());

        return copy;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#execute()
     */
    @Override
    protected void execute() throws ManipulationExecutionException {
        getManipulationQueue().setClipboard((GroupNode) getManipulatedGroup().duplicate());
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#unexecute()
     */
    @Override
    protected void unexecute() throws ManipulationExecutionException {
        getManipulationQueue().setClipboard(null);
    }

}
