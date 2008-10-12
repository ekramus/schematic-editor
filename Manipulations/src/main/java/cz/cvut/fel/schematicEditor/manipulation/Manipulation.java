package cz.cvut.fel.schematicEditor.manipulation;

import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.manipulation.exception.ManipulationExecutionException;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;

/**
 * This is parental class for all manipulations. It implements basic methods necessary for every manipulation.
 *
 * @author Urban Kravjansky
 */
public abstract class Manipulation {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger     logger;
    /**
     * Contains {@link GroupNode} manipulated by {@link Manipulation}.
     */
    private GroupNode         manipulatedGroup;
    /**
     * This field represents x coordinates of manipulated element.
     */
    private Vector<Unit>      x;
    /**
     * This field represents y coordinates of manipulated element.
     */
    private Vector<Unit>      y;
    /**
     * Indicates, whether manipulation is active or not.
     */
    private boolean           active;
    /**
     * {@link Vector} of coordinates used for snap.
     */
    private Vector<UnitPoint> snapCoordinates;

    /**
     * Default constructor. It is private because of {@link Manipulation}s are created using {@link ManipulationFactory}
     * .
     */
    protected Manipulation() {
        setManipulatedGroup(null);

        finalizeInit();
    }

    /**
     * Constructor with {@link GroupNode} parameter. It is protected because of {@link Manipulation}s are created using
     * {@link ManipulationFactory}.
     *
     * @param manipulatedGroup instance of manipulated group.
     */
    protected Manipulation(GroupNode manipulatedGroup) {
        setManipulatedGroup(manipulatedGroup);

        finalizeInit();
    }

    /**
     * Add given manipulation coordinates.
     *
     * @param x <code>x</code> to add.
     * @param y <code>y</code> to add
     */
    public void addManipulationCoordinates(Unit x, Unit y) {
        this.x.add(x);
        this.y.add(y);

        logger.trace("added manipulation coordinates");
    }

    /**
     * @return the manipulatedGroup
     */
    public GroupNode getManipulatedGroup() {
        return this.manipulatedGroup;
    }

    /**
     * This method returns manipulation type of used manipulation.
     *
     * @return Type of {@link Manipulation}.
     */
    public abstract ManipulationType getManipulationType();

    /**
     * Manipulation active state. If is manipulation active, it means, it is being currently processed.
     *
     * @return active state of current manipulation.
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * Initializes all necessary structures at the beginning of manipulation correctly.
     *
     * @param e {@link MouseEvent}, that invoked this method.
     * @param r2d Rectangle, which contains mouse pointer.
     * @param manipulationQueue used for {@link Manipulation} history and execution.
     * @param topNode TopNode of SchemeSG.
     * @param isMouseClicked Indicates, whether mouse was clicked or not.
     * @return Pointer to manipulation or <code>null</code>, if manipulation was unsuccessful.
     * @throws UnknownManipulationException In case of unknown {@link Manipulation}.
     */
    public abstract Manipulation manipulationStart(MouseEvent e, Rectangle2D r2d, ManipulationQueue manipulationQueue,
            GroupNode topNode, boolean isMouseClicked) throws UnknownManipulationException;

    /**
     * Finishes everything at the end of manipulation correctly.
     *
     * @param e {@link MouseEvent}, that invoked this method.
     * @param r2d Rectangle, which contains mouse pointer.
     * @param manipulationQueue used for {@link Manipulation} history and execution.
     * @param topNode TopNode of SchemeSG.
     * @param isMouseClicked Indicates, whether mouse was clicked or not.
     * @return {@link Manipulation}, if manipulation ended successfully, <code>null</code> else.
     * @throws UnknownManipulationException In case of unknown {@link Manipulation}.
     */
    public abstract Manipulation manipulationStop(MouseEvent e, Rectangle2D r2d, ManipulationQueue manipulationQueue,
            GroupNode topNode, boolean isMouseClicked) throws UnknownManipulationException;

    /**
     * Replaces last manipulation <code>x</code>, <code>y</code> coordinates with given ones.
     *
     * @param x new <code>x</code> coordinate.
     * @param y new <code>y</code> coordinate.
     */
    public void replaceLastManipulationCoordinates(Unit x, Unit y) {
        try {
            this.x.set(this.x.size() - 1, x);
            this.y.set(this.y.size() - 1, y);
        } catch (ArrayIndexOutOfBoundsException e) {
            addManipulationCoordinates(x, y);
        }

        logger.trace("replacing last manipulation coordinates");
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @param manipulatedGroup the manipulatedGroup to set
     */
    public void setManipulatedGroup(GroupNode manipulatedGroup) {
        this.manipulatedGroup = manipulatedGroup;
    }

    /**
     * Finalizes {@link Manipulation} initialization.
     */
    private void finalizeInit() {
        logger = Logger.getLogger(this.getClass().getName());

        setActive(false);

        this.x = new Vector<Unit>();
        this.y = new Vector<Unit>();
    }

    /**
     * Creates next {@link Manipulation} by duplicating its values according to create order.
     *
     * @return Creates next instance of {@link Manipulation}.
     */
    protected Manipulation createNext() {
        return duplicate();
    }

    /**
     * Creates duplicate instance of {@link Manipulation} by duplicating its values.
     *
     * @return Duplicated instance of {@link Manipulation}.
     */
    protected abstract Manipulation duplicate();

    /**
     * Executes manipulation.
     *
     * @param topNode top {@link GroupNode} of SceneGraph.
     *
     * @throws ManipulationExecutionException in case of some error while executing manipulation.
     */
    protected abstract void execute(GroupNode topNode) throws ManipulationExecutionException;

    /**
     * @return the x
     */
    protected Vector<Unit> getX() {
        return this.x;
    }

    /**
     * @return the y
     */
    protected Vector<Unit> getY() {
        return this.y;
    }

    /**
     * Reexecutes manipulation. Mostly it only executes it, for special purposes it needs to be overwritten.
     *
     * @param topNode top {@link GroupNode} of SceneGraph.
     *
     * @throws ManipulationExecutionException in case of some error while reexecuting manipulation.
     */
    protected void reexecute(GroupNode topNode) throws ManipulationExecutionException {
        execute(topNode);
    }

    /**
     * Sets manipulation coordinates according to given ones.
     *
     * @param x new <code>x</code> coordinates.
     * @param y new <code>y</code> coordinates.
     */
    protected void setManipulationCoordinates(Vector<Unit> x, Vector<Unit> y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Unexecutes (undoes) manipulation.
     *
     * @param topNode reference to top node of scene graph.
     *
     * @throws ManipulationExecutionException in case of some error while removing manipulation.
     */
    protected abstract void unexecute(GroupNode topNode) throws ManipulationExecutionException;

    /**
     * @param snapCoordinates the snapCoordinates to set
     */
    public void setSnapCoordinates(Vector<UnitPoint> snapCoordinates) {
        this.snapCoordinates = snapCoordinates;
    }

    /**
     * @return the snapCoordinates
     */
    public Vector<UnitPoint> getSnapCoordinates() {
        return this.snapCoordinates;
    }
}
