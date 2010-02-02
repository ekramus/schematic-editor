package cz.cvut.fel.schematicEditor.manipulation;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.NodeFactory;
import cz.cvut.fel.schematicEditor.manipulation.exception.ManipulationExecutionException;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;

/**
 * This class implements Copy manipulation.
 *
 * @author Urban Kravjansky
 */
public class Paste extends Manipulation {
    /**
     * This method instantiates new instance.
     *
     * @param topNode top node of scene graph.
     * @param source object, which initiated creation of this {@link Manipulation}.
     */
    protected Paste(GroupNode topNode, Object source) {
        super(topNode, source);
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
     * @see Manipulation#manipulationStop(MouseEvent, Rectangle2D, ManipulationQueue, double, boolean,
     *      java.awt.Graphics2D)
     */
    @Override
    public Manipulation manipulationStart(MouseEvent e, Rectangle2D r2d, ManipulationQueue manipulationQueue,
            double zoomFactor, boolean isMouseClicked, Graphics2D g2d) throws UnknownManipulationException {
        // mothing to do
        return this;
    }

    /**
     * @see Manipulation#manipulationStop(MouseEvent, Rectangle2D, ManipulationQueue, double, boolean, Graphics2D)
     */
    @Override
    public Manipulation manipulationStop(MouseEvent e, Rectangle2D r2d, ManipulationQueue manipulationQueue,
            double zoomFactor, boolean isMouseClicked, Graphics2D g2d) throws UnknownManipulationException {
        setManipulationQueue(manipulationQueue);

        return this;
    }

    /**
     * @return the {@link ManipulationQueue} instance.
     */
    private ManipulationQueue getManipulationQueue() {
        return this.manipulationQueue;
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
        Paste paste = new Paste(getTopNode(), getSource());

        paste.setManipulatedGroup(getManipulatedGroup());
        paste.setManipulationQueue(getManipulationQueue());

        return paste;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#execute()
     */
    @Override
    protected void execute() throws ManipulationExecutionException {
        setManipulatedGroup((GroupNode) NodeFactory.duplicate(getManipulationQueue().getClipboard()));
        getTopNode().add(getManipulatedGroup());

        // if original GroupNode was deleted, undelete it
        getTopNode().undelete(getManipulatedGroup());

    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#unexecute()
     */
    @Override
    protected void unexecute() throws ManipulationExecutionException {
        getTopNode().delete(getManipulatedGroup());
    }
}
