package cz.cvut.fel.schematicEditor.manipulation;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import cz.cvut.fel.schematicEditor.manipulation.exception.ManipulationExecutionException;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.original.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.original.graphNode.NodeFactory;

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
     * @param source object, which initiated creation of this {@link Manipulation}.
     */
    protected Copy(GroupNode topNode, Object source) {
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
     * @see Manipulation#manipulationStart(MouseEvent, Rectangle2D, ManipulationQueue, double, boolean, Graphics2D)
     */
    @Override
    public Manipulation manipulationStart(MouseEvent e, Rectangle2D r2d, ManipulationQueue manipulationQueue,
            double zoomFactor, boolean isMouseClicked, Graphics2D g2d) throws UnknownManipulationException {

        // nothing to do
        return this;
    }

    /**
     * @see Manipulation#manipulationStop(MouseEvent, Rectangle2D, ManipulationQueue, double, boolean, Graphics2D)
     */
    @Override
    public Manipulation manipulationStop(MouseEvent e, Rectangle2D r2d, ManipulationQueue manipulationQueue,
            double zoomFactor, boolean isMouseClicked, Graphics2D g2d) throws UnknownManipulationException {
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
        Copy copy = new Copy(getTopNode(), getSource());

        copy.setManipulatedGroup(getManipulatedGroup());
        copy.setManipulationQueue(getManipulationQueue());

        return copy;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#execute()
     */
    @Override
    protected void execute() throws ManipulationExecutionException {
        getManipulationQueue().setClipboard((GroupNode) NodeFactory.duplicate(getManipulatedGroup()));
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#unexecute()
     */
    @Override
    protected void unexecute() throws ManipulationExecutionException {
        getManipulationQueue().setClipboard(null);
    }

}
