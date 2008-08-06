package cz.cvut.fel.schematicEditor.manipulation;

import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.element.ElementModificator;
import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.element.element.shape.Shape;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.ParameterNode;
import cz.cvut.fel.schematicEditor.graphNode.ShapeNode;
import cz.cvut.fel.schematicEditor.manipulation.exception.ManipulationExecutionException;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.support.Snap;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;

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
     * @param manipulatedElement {@link Element}, which will be created using this {@link Create} manipulation.
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
     * @return the pointsLeft
     */
    public int getPointsLeft() {
        return this.pointsLeft;
    }

    /**
     * @param pointsLeft the pointsLeft to set
     */
    public void setPointsLeft(int pointsLeft) {
        this.pointsLeft = pointsLeft;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#getManipulationType()
     */
    @Override
    public ManipulationType getManipulationType() {
        return ManipulationType.CREATE;
    }

    /**
     * @return
     */
    @Override
    @Deprecated
    public boolean isManipulatingElements() {
        // TODO Auto-generated method stub
        return true;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#isManipulatingGroups()
     */
    @Override
    public boolean isManipulatingGroups() {
        // TODO Auto-generated method stub
        return true;
    }

    /**
     * @return the elementModificator
     */
    public ElementModificator getElementModificator() {
        return getManipulatedGroup().getElementModificator();
    }

    /**
     * @param elementModificator the elementModificator to set
     */
    public void setElementModificator(ElementModificator elementModificator) {
        getManipulatedGroup().setElementModificator(elementModificator);
    }

    /**
     * @return the finished
     */
    public final boolean isFinished() {
        return this.finished;
    }

    /**
     * @param finished the finished to set
     */
    public final void setFinished(boolean finished) {
        this.finished = finished;
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
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#execute()
     */
    @Override
    protected void execute(GroupNode topNode) throws ManipulationExecutionException {
        GroupNode gn = getManipulatedGroup();

        logger.debug("processing final manipulation step");

        setActive(false);

        topNode.add(gn);
        logger.trace(this + " executed");
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#unexecute()
     */
    @Override
    public void unexecute(GroupNode topNode) throws ManipulationExecutionException {
        logger.trace(this + " unexecuted");
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationEnd(MouseEvent, Rectangle2D.Double,
     *      ManipulationQueue, GroupNode, boolean)
     */
    @Override
    public Manipulation manipulationEnd(MouseEvent e, Rectangle2D.Double r2d, ManipulationQueue manipulationQueue,
            GroupNode topNode, boolean isMouseClicked) throws UnknownManipulationException {
        logger.trace(this + " manipulation END");

        Snap s = Snap.getInstance();

        // check, what to do
        switch (getPointsLeft()) {
            case Element.ZERO_COORDINATES:
                setFinished(true);
                break;
            case Element.INFINITE_COORDINATES:
                // add next coordinate
                addManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));
                break;
            default:
                // add next coordinate
                addManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));
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
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#manipulationStart(MouseEvent, Rectangle2D.Double,
     *      ManipulationQueue, GroupNode, boolean)
     */
    @Override
    public Manipulation manipulationStart(MouseEvent e, Rectangle2D.Double r2d, ManipulationQueue manipulationQueue,
            GroupNode gn, boolean isMouseClicked) throws UnknownManipulationException {
        logger.trace(this + " manipulation START");

        Snap s = Snap.getInstance();

        // Create create = (Create) Structures.getManipulationQueue().peek();
        setActive(true);

        // add two pairs of coordinates (each element needs two)
        addManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));
        addManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));

        return this;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Create(" + getManipulatedGroup() + ")";
    }
}
