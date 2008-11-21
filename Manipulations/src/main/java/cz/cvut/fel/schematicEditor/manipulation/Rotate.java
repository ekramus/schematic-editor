package cz.cvut.fel.schematicEditor.manipulation;

import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.TransformationNode;
import cz.cvut.fel.schematicEditor.manipulation.exception.ManipulationExecutionException;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.support.Transformation;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;

/**
 * This class implements <em>rotate</em> manipulation. <br/>
 * Coordinates are in following order:
 * <dl>
 * <dt>first coordinate</dt>
 * <dd>center of rotation</dd>
 * <dt>second coordinate</dt>
 * <dd>start point of rotation</dd>
 * <dt>third coordinate</dt>
 * <dd>end point of rotation</dd>
 * </dl>
 *
 * @author Urban Kravjansky
 */
public class Rotate extends Manipulation {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger logger;

    /**
     * Default constructor. It initializes this {@link Rotate} {@link Manipulation}.
     *
     * @param topNode top node of scene graph.
     */
    protected Rotate(GroupNode topNode) {
        super(topNode);
        setManipulatedGroup(null);

        logger = Logger.getLogger(this.getClass().getName());
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#getManipulationType()
     */
    @Override
    public ManipulationType getManipulationType() {
        return ManipulationType.ROTATE;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationStart(MouseEvent,
     *      java.awt.geom.Rectangle2D, ManipulationQueue, boolean)
     */
    @Override
    public Manipulation manipulationStart(MouseEvent e, Rectangle2D r2d, ManipulationQueue manipulationQueue,
            boolean isMouseClicked) throws UnknownManipulationException {
        // check, whether rotate is possible or not
        if (isActive()) {
            // add identity transformation, so it can be later changed
            getManipulatedGroup().add(new TransformationNode(Transformation.getIdentity()));
        }
        // rotate is not possible - fall back to Select manipulation
        else {
            // nothing to do
        }
        return this;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationStop(MouseEvent, Rectangle2D,
     *      ManipulationQueue, boolean) ManipulationQueue, GroupNode, boolean)
     */
    @Override
    public Manipulation manipulationStop(MouseEvent e, Rectangle2D r2d, ManipulationQueue manipulationQueue,
            boolean isMouseClicked) throws UnknownManipulationException {
        if (isActive()) {
            logger.trace("object ROTATED");
        }
        return this;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#duplicate()
     */
    @Override
    protected Manipulation duplicate() {
        Rotate r = new Rotate(getTopNode());

        r.setManipulatedGroup(getManipulatedGroup());
        r.setManipulationCoordinates(new Vector<Unit>(getX()), new Vector<Unit>(getY()));

        return r;
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
     * @see Manipulation#execute()
     */
    @Override
    protected void execute() throws ManipulationExecutionException {
        // TODO finalize
        // UnitPoint center = new UnitPoint(getX().get(0), getY().get(0));
        // UnitPoint first = new UnitPoint(getX().get(1), getY().get(1));
        // UnitPoint last = new UnitPoint(getX().get(2), getY().get(2));

        double angle = -90;

        // get reference coordinate
        UnitPoint rc = getManipulatedGroup().getChildrenElementList().getFirst().getElement().getRotationCenter();

        // move so that reference is in point 0,0
        Transformation initialTransformation = Transformation.getShift(rc);
        getManipulatedGroup().add(new TransformationNode(initialTransformation.getInverse()));

        // rotate
        Transformation rotate = Transformation.getRotation(angle);
        getManipulatedGroup().add(new TransformationNode(rotate));

        // move back
        getManipulatedGroup().add(new TransformationNode(initialTransformation));
    }

    /**
     * @see Manipulation#reexecute()
     */
    @Override
    protected void reexecute() throws ManipulationExecutionException {
        execute();
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#unexecute()
     */
    @Override
    protected void unexecute() throws ManipulationExecutionException {
        // TODO finalize
        // UnitPoint center = new UnitPoint(getX().get(0), getY().get(0));
        // UnitPoint first = new UnitPoint(getX().get(1), getY().get(1));
        // UnitPoint last = new UnitPoint(getX().get(2), getY().get(2));

        double angle = -Math.PI;

        Transformation rotate = Transformation.getRotation(angle);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#addManipulationCoordinates(Unit,Unit)
     */
    @Override
    public void addManipulationCoordinates(Unit x, Unit y) {
        super.addManipulationCoordinates(x, y);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#replaceLastManipulationCoordinates(cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit,
     *      cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit)
     */
    @Override
    public void replaceLastManipulationCoordinates(Unit x, Unit y) {
        super.replaceLastManipulationCoordinates(x, y);
    }
}
