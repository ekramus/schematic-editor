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

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#isManipulatingElements()
     */
    @Override
    public boolean isManipulatingElements() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#isManipulatingGroups()
     */
    @Override
    public boolean isManipulatingGroups() {
        // TODO Auto-generated method stub
        return false;
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
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#execute()
     */
    @Override
    protected void execute() throws ManipulationExecutionException {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#unexecute()
     */
    @Override
    protected void unexecute() throws ManipulationExecutionException {
        // TODO Auto-generated method stub

    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationEnd(MouseEvent, Rectangle2D.Double,
     *      ManipulationQueue, GroupNode, boolean)
     */
    @Override
    public boolean manipulationEnd(MouseEvent e, Double r2d, ManipulationQueue manipulationQueue, GroupNode groupNode,
            boolean isMouseClicked) throws UnknownManipulationException {
        if (isMouseClicked && isActive()) {
            if (groupNode.deleteHit(r2d)) {
                // TODO process final manipulation step
                // ScenePanel.getInstance().processFinalManipulationStep();
            }
        }
        return true;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationStart(MouseEvent, Rectangle2D.Double,
     *      ManipulationQueue, GroupNode, boolean)
     */
    @Override
    public void manipulationStart(MouseEvent e, Double r2d, ManipulationQueue manipulationQueue, GroupNode gn,
            boolean isMouseClicked) throws UnknownManipulationException {
        setActive(true);
    }
}
