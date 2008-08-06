package cz.cvut.fel.schematicEditor.manipulation;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D.Double;

import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.TransformationNode;
import cz.cvut.fel.schematicEditor.manipulation.exception.ManipulationExecutionException;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.support.Snap;
import cz.cvut.fel.schematicEditor.support.Transformation;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;

/**
 * @author Urban Kravjansky
 */
public class Edit extends Manipulation {
    /**
     * 
     */
    protected Edit() {
        super(null);
        setManipulatedGroup(null);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#getManipulationType()
     */
    @Override
    public ManipulationType getManipulationType() {
        return ManipulationType.EDIT;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#isManipulatingElements()
     */
    @Override
    public boolean isManipulatingElements() {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#isManipulatingGroups()
     */
    @Override
    public boolean isManipulatingGroups() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    protected Manipulation duplicate() {
        Edit e = new Edit();
        return e;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#execute()
     */
    @Override
    protected void execute(GroupNode topNode) throws ManipulationExecutionException {
        // compute delta
        Point2D delta = new Point2D.Double(getX().lastElement().doubleValue() - getX().firstElement().doubleValue(),
                getY().lastElement().doubleValue() - getY().firstElement().doubleValue());

        // change edited point using delta
        getManipulatedGroup().stopEditing(new UnitPoint(delta));
    }

    /*
     * (non-Javadoc)
     * 
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#unexecute()
     */
    @Override
    protected void unexecute(GroupNode topNode) throws ManipulationExecutionException {
        // TODO Auto-generated method stub

    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationEnd(MouseEvent, Rectangle2D.Double,
     *      ManipulationQueue, GroupNode, boolean)
     */
    @Override
    public Manipulation manipulationEnd(MouseEvent e, Double r2d, ManipulationQueue manipulationQueue,
            GroupNode grouNode, boolean isMouseClicked) throws UnknownManipulationException {
        if (isActive()) {
            Snap s = Snap.getInstance();

            // logger.trace("object EDITED");

            // replace last manipulation coordinates for delta
            replaceLastManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));

            // get manipulated group
            GroupNode gn = getManipulatedGroup();

            // enable manipulated group
            gn.setDisabled(false);

            // create select manipulation, so manipulation can proceed as select
            Select select = (Select) ManipulationFactory.create(ManipulationType.SELECT);
            select.setManipulatedGroup(gn);
        }
        return this;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationStart(MouseEvent, Rectangle2D.Double,
     *      ManipulationQueue, GroupNode, boolean)
     */
    @Override
    public Manipulation manipulationStart(MouseEvent e, Double r2d, ManipulationQueue manipulationQueue,
            GroupNode groupNode, boolean isMouseClicked) throws UnknownManipulationException {
        GroupNode gn = groupNode.findHit(r2d);
        Snap s = Snap.getInstance();

        // check, whether move is possible or not
        if (isActive() && getManipulatedGroup() == gn) {
            // add identity transformation, so it can be later changed
            gn.add(new TransformationNode(Transformation.getIdentity()));

            // add two copies of same coordinates to be able to replace last one
            addManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));
            addManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));

            // set manipulated group disabled
            gn.setDisabled(true);
            // ScenePanel.getInstance().schemeInvalidate(gn.getBounds());
        }
        // move is not possible - fall back to Select manipulation
        else {
            // manipulationQueue.add(ManipulationFactory.create(ManipulationType.SELECT));
        }

        return this;
    }
}
