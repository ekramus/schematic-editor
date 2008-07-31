package cz.cvut.fel.schematicEditor.manipulation;

import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D.Double;

import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.ParameterNode;
import cz.cvut.fel.schematicEditor.manipulation.exception.ManipulationExecutionException;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.support.Snap;

/**
 * @author Urban Kravjansky
 */
public class Select extends Manipulation {
    private GroupNode manipulatedGroup;

    /**
     *
     */
    protected Select() {
        super(null);
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
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#isManipulatingElements()
     */
    @Override
    public boolean isManipulatingElements() {
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
        return true;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#duplicate()
     */
    @Override
    protected Manipulation duplicate() {
        Select s = new Select();

        // duplicate parameters
        s.setActive(isActive());
        s.setManipulatedGroup(getManipulatedGroup());
        // s.setManipulationCoordinates(getX(), getY());

        return s;
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
     *      ManipulationQueue, boolean)
     */
    @Override
    public boolean manipulationEnd(MouseEvent e, Double r2d, ManipulationQueue manipulationQueue, GroupNode groupNode,
            boolean isMouseClicked) throws UnknownManipulationException {
        Select select = (Select) manipulationQueue.peek();

        // mouse clicked and hit something
        if (isMouseClicked) {
            // some group is hit
            if (groupNode.isHit(r2d)) {
                // logger.debug("object SELECTED");

                // create new select object
                manipulationQueue.offer(ManipulationFactory.create(ManipulationType.SELECT));
                select = (Select) manipulationQueue.peek();
                // activate selection
                select.setActive(true);
                // set selected group
                GroupNode gn = groupNode.findHit(r2d);
                select.setManipulatedGroup(gn);

                // ScenePanel.getInstance().schemeInvalidate(select.getManipulatedGroup().getBounds()
                // );

                // get parameter node and set properties panel according to it
                ParameterNode pn = gn.getChildrenParameterNode();
                // TODO ensure properties refresh
                // Structures.getSceneProperties().setSelectedElementProperties(pn.getProperties());
                // PropertiesToolBar.refresh();
            }
            // no group is hit
            else {
                // logger.debug("nothing SELECTED");

                // create new select object
                manipulationQueue.offer(ManipulationFactory.create(ManipulationType.SELECT));
                select = (Select) manipulationQueue.peek();

                // ScenePanel.getInstance().schemeInvalidate(null);
                // TODO ensure properties refresh
                // Structures.getSceneProperties().setSelectedElementProperties(null);
                // PropertiesToolBar.refresh();
            }
        }
        return true;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationStart(MouseEvent, Rectangle2D.Double,
     *      ManipulationQueue, boolean)
     */
    @Override
    public void manipulationStart(MouseEvent e, Double r2d, ManipulationQueue manipulationQueue, GroupNode groupNode,
            boolean isMouseClick) throws UnknownManipulationException {

        // manipulation is not active
        if (!isActive()) {
            // nothing to do
        }
        // select is active AND GroupNode is already selected
        else if (groupNode.findHit(r2d) == getManipulatedGroup()) {
            // select is in edit active zone
            if (getManipulatedGroup().startEditing(r2d)) {
                // create Edit manipulation
                Edit edit = (Edit) ManipulationFactory.create(ManipulationType.EDIT);
                edit.setManipulatedGroup(getManipulatedGroup());
                edit.setActive(true);

                // set active manipulation edit
                manipulationQueue.offer(edit);

                // continue with edit manipulation start
                edit.manipulationStart(e, r2d, manipulationQueue, groupNode, isMouseClick);
            }
            // select is in rotate active zone
            else if (getManipulatedGroup().isRotateZone(r2d)) {
                // TODO implement rotate active zone
            }
            // select can be used for move
            else {
                // logger.trace("creating MOVE manipulation");

                // create Move manipulation
                Move move = (Move) ManipulationFactory.create(ManipulationType.MOVE);
                move.setManipulatedGroup(getManipulatedGroup());
                move.setActive(true);

                // set active manipulation move
                manipulationQueue.offer(move);

                // continue with move manipulation start
                move.manipulationStart(e, r2d, manipulationQueue, groupNode, isMouseClick);
            }
        }
    }
}
