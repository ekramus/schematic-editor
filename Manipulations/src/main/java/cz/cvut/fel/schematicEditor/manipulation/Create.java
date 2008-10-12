package cz.cvut.fel.schematicEditor.manipulation;

import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.element.ElementModificator;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.manipulation.exception.ManipulationExecutionException;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.support.Snap;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;

/**
 * This class represents create {@link Manipulation}. It is created, when user presses any button for new shape
 * creation.
 *
 * Lifecycle of <code>active</code> and <code>finished</code> fields is presented in this table:
 * <table>
 * <th>
 * variable</th>
 * <th>initialization</th>
 * <th>creation</th>
 * <th>presentation</th>
 * <tbody>
 * <tr>
 * <td><em>active</em></td>
 * <td><code>false</code></td>
 * <td><code>true</code></td>
 * <td><code>false</code></td>
 * </tr>
 * <tr>
 * <td><em>finished</em></td>
 * <td><code>false</code></td>
 * <td><code>false</code></td>
 * <td><code>true</code></td>
 * </tr>
 * </tbody>
 * </table>
 *
 * @author Urban Kravjansky
 */
public class Create extends Manipulation {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger logger;
    /**
     * Number of points left for given shape.
     */
    private int           pointsLeft;
    /**
     * Indicates, whether {@link Create} manipulation is finished or not.
     */
    private boolean       finished;

    /**
     * Constructor used for {@link Create} initialization. It is protected so it {@link Manipulation} has to be created
     * using {@link ManipulationFactory}.
     *
     * @param manipulatedGroup {@link GroupNode}, which will be created using this {@link Create} manipulation.
     */
    protected Create(GroupNode manipulatedGroup) {
        super(manipulatedGroup);

        logger = Logger.getLogger(this.getClass().getName());

        setFinished(false);
        setPointsLeft(getManipulatedGroup().getNumberOfCoordinates());
        setElementModificator(ElementModificator.NO_MODIFICATION);
        // TODO add info text into status bar
        // Structures.getStatusBar().setSizeLockingLabel("to enable size locking, press CTRL");
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#addManipulationCoordinates(Unit,Unit)
     */
    @Override
    public void addManipulationCoordinates(Unit x, Unit y) {
        super.addManipulationCoordinates(x, y);
        if (getPointsLeft() > Element.ZERO_COORDINATES) {
            setPointsLeft(getPointsLeft() - 1);
        }

        // update manipulated group coordinates
        getManipulatedGroup().setXY(getX(), getY());

        logger.trace("added manipulation coordinates, points left: " + getPointsLeft());
    }

    /**
     * @return the elementModificator
     */
    public ElementModificator getElementModificator() {
        return getManipulatedGroup().getElementModificator();
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#getManipulationType()
     */
    @Override
    public ManipulationType getManipulationType() {
        return ManipulationType.CREATE;
    }

    /**
     * @return the pointsLeft
     */
    public int getPointsLeft() {
        return this.pointsLeft;
    }

    /**
     * @return the finished
     */
    public final boolean isFinished() {
        return this.finished;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationStart(MouseEvent, Rectangle2D,
     *      ManipulationQueue, GroupNode, boolean)
     */
    @Override
    public Manipulation manipulationStart(MouseEvent e, Rectangle2D r2d, ManipulationQueue manipulationQueue,
            GroupNode gn, boolean isMouseClicked) throws UnknownManipulationException {
        logger.trace(this + " manipulation START");

        // Create create = (Create) Structures.getManipulationQueue().peek();
        setActive(true);

        // add two pairs of coordinates (each element needs two)
        UnitPoint up = new UnitPoint(e.getX(), e.getY());
        UnitPoint snap = Snap.getSnap(up, getSnapCoordinates());
        addManipulationCoordinates(snap.getUnitX(), snap.getUnitY());
        addManipulationCoordinates(snap.getUnitX(), snap.getUnitY());

        return this;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationStop(MouseEvent, Rectangle2D,
     *      ManipulationQueue, GroupNode, boolean)
     */
    @Override
    public Manipulation manipulationStop(MouseEvent e, Rectangle2D r2d, ManipulationQueue manipulationQueue,
            GroupNode topNode, boolean isMouseClicked) throws UnknownManipulationException {
        logger.trace(this + " manipulation END");
        UnitPoint up;
        UnitPoint snap;
        // check, what to do
        switch (getPointsLeft()) {
            case Element.ZERO_COORDINATES:
                setFinished(true);
                break;
            case Element.INFINITE_COORDINATES:
            default:
                // add next coordinate
                up = new UnitPoint(e.getX(), e.getY());
                snap = Snap.getSnap(up, getSnapCoordinates());
                addManipulationCoordinates(snap.getUnitX(), snap.getUnitY());
                break;
        }

        // finalize, if possible
        if (isFinished()) {
            // GroupNode gn = getManipulatedGroup();
            //
            // logger.debug("processing final manipulation step");
            //
            // setActive(false);
            //
            // topNode.add(gn);
        }

        return isFinished() ? this : null;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#replaceLastManipulationCoordinates(cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit,
     *      cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit)
     */
    @Override
    public void replaceLastManipulationCoordinates(Unit x, Unit y) {
        super.replaceLastManipulationCoordinates(x, y);

        // update manipulated group coordinates
        getManipulatedGroup().setXY(getX(), getY());
    }

    /**
     * @param elementModificator the elementModificator to set
     */
    public void setElementModificator(ElementModificator elementModificator) {
        getManipulatedGroup().setElementModificator(elementModificator);
    }

    /**
     * @param finished the finished to set
     */
    public final void setFinished(boolean finished) {
        this.finished = finished;
    }

    /**
     * @param pointsLeft the pointsLeft to set
     */
    public void setPointsLeft(int pointsLeft) {
        this.pointsLeft = pointsLeft;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Create(" + getManipulatedGroup() + ")";
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#duplicate()
     */
    @Override
    protected Manipulation createNext() {
        return duplicate();
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#duplicate()
     */
    @Override
    protected Manipulation duplicate() {
        Create c = new Create((GroupNode) getManipulatedGroup().duplicate());
        return c;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#execute(GroupNode)
     */
    @Override
    protected void execute(GroupNode topNode) throws ManipulationExecutionException {
        GroupNode gn = getManipulatedGroup();

        logger.debug("processing final manipulation step");

        setActive(false);
        setFinished(true);

        topNode.add(gn);
        logger.trace(this + " executed");
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#reexecute(GroupNode)
     */
    @Override
    protected void reexecute(GroupNode topNode) throws ManipulationExecutionException {
        // if manipulated group was disabled by previous undo
        if (getManipulatedGroup().isDisabled()) {
            topNode.undelete(getManipulatedGroup());
        }
        // if redo is executed at the end of ManipulationQueue
        else {
            execute(topNode);
        }
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#unexecute(GroupNode)
     */
    @Override
    protected void unexecute(GroupNode topNode) throws ManipulationExecutionException {
        topNode.delete(getManipulatedGroup());

        logger.trace(this + " unexecuted");
    }
}
