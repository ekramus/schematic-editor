package cz.cvut.fel.schematicEditor.manipulation.manipulation;

import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.Vector;

import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationQueue;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.exception.ManipulationExecutionException;
import cz.cvut.fel.schematicEditor.support.Snap;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;

/**
 * @author Urban Kravjansky
 */
public abstract class Manipulation {
    private GroupNode    manipulatedGroup;
    private Element      manipulatedElement;
    /**
     * This field represents x coordinates of manipulated element.
     */
    private Vector<Unit> x;
    /**
     * This field represents y coordinates of manipulated element.
     */
    private Vector<Unit> y;

    private boolean      active;

    /**
     * Creates new instance of {@link GeneralManipulation} by duplicating its values.
     */
    protected abstract Manipulation duplitate();

    public abstract boolean isManipulatingElements();

    /**
     * This method returns manipulation type of used manipulation.
     * 
     * @return Type of {@link Manipulation}.
     */
    public abstract ManipulationType getManipulationType();

    public abstract boolean isManipulatingGroups();

    /**
     * Executes manipulation.
     * 
     * @throws ManipulationExecutionException
     *             in case of some error while executing manipulation.
     */
    public abstract void execute() throws ManipulationExecutionException;

    /**
     * Unexecutes (undoes) manipulation.
     * 
     * @throws ManipulationExecutionException
     *             in case of some error while removing manipulation.
     */
    public abstract void unexecute() throws ManipulationExecutionException;

    /**
     * Finalizes initialization.
     */
    private void finalizeInit() {
        setActive(false);

        this.x = new Vector<Unit>();
        this.y = new Vector<Unit>();

        setManipulatedGroup(null);
    }

    /**
     * 
     */
    protected Manipulation() {
        setManipulatedElement(null);

        finalizeInit();
    }

    /**
     * 
     */
    public Manipulation(Element manipulatedElement) {
        setManipulatedElement(manipulatedElement);

        finalizeInit();
    }

    /**
     * @return the manipulatedElement
     */
    public Element getManipulatedElement() {
        this.manipulatedElement.setX(getX());
        this.manipulatedElement.setY(getY());
        return this.manipulatedElement;
    }

    protected void setManipulationCoordinates(Vector<Unit> x, Vector<Unit> y) {
        this.x = x;
        this.y = y;
    }

    public void addManipulationCoordinates(Unit x, Unit y) {
        this.x.add(x);
        this.y.add(y);
    }

    public void replaceLastManipulationCoordinates(Unit x, Unit y) {
        try {
            this.x.set(this.x.size() - 1, x);
            this.y.set(this.y.size() - 1, y);
        } catch (ArrayIndexOutOfBoundsException e) {
            addManipulationCoordinates(x, y);
        }
    }

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
     * Manipulation active state. If is manipulation active, it means, it is being currently processed.
     * 
     * @return active state of current manipulation.
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * @param active
     *            the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return the manipulatedGroup
     */
    public GroupNode getManipulatedGroup() {
        return this.manipulatedGroup;
    }

    /**
     * @param manipulatedGroup
     *            the manipulatedGroup to set
     */
    public void setManipulatedGroup(GroupNode manipulatedGroup) {
        this.manipulatedGroup = manipulatedGroup;
    }

    /**
     * @param manipulatedElement
     *            the manipulatedElement to set
     */
    private void setManipulatedElement(Element manipulatedElement) {
        this.manipulatedElement = manipulatedElement;
    }

    /**
     * Initializes all necessary structures at the beginning of manipulation correctly.
     * 
     * @param e
     *            {@link MouseEvent}, that invoked this method.
     * @param r2d
     *            Rectangle, which contains mouse pointer.
     * @param manipulationQueue
     *            used for {@link Manipulation} history and execution.
     * @param s
     *            Snap to grid property.
     */
    public abstract void manipulationStart(MouseEvent e, Rectangle2D.Double r2d, Snap s);

    /**
     * Finishes everything at the end of manipulation correctly.
     * 
     * @param e
     *            {@link MouseEvent}, that invoked this method.
     * @param r2d
     *            Rectangle, which contains mouse pointer.
     * @param manipulationQueue
     *            used for {@link Manipulation} history and execution.
     * @param s
     *            Snap to grid property.
     */
    public abstract void manipulationEnd(MouseEvent e, Rectangle2D.Double r2d, ManipulationQueue manipulationQueue,
            Snap s);
}
