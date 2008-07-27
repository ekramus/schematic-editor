package cz.cvut.fel.schematicEditor.manipulation.manipulation;

import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationQueue;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.exception.ManipulationExecutionException;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;

/**
 * This is parental class for all manipulations. It implements basic methods necessary for every
 * manipulation.
 * 
 * @author Urban Kravjansky
 */
public abstract class Manipulation {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger logger;
    /**
     * Contains {@link GroupNode} manipulated by {@link Manipulation}.
     */
    private GroupNode     manipulatedGroup;
    /**
     * Contains {@link Element} manipulated by {@link Manipulation}.
     */
    private Element       manipulatedElement;
    /**
     * This field represents x coordinates of manipulated element.
     */
    private Vector<Unit>  x;
    /**
     * This field represents y coordinates of manipulated element.
     */
    private Vector<Unit>  y;
    /**
     * Indicates, whether manipulation is active or not.
     */
    private boolean       active;

    /**
     * Default constructor. It is private because of {@link Manipulation}s are created using
     * {@link ManipulationFactory}.
     */
    protected Manipulation() {
        setManipulatedElement(null);

        finalizeInit();
    }

    /**
     * Constructor with {@link Element} parameter. It is private because of {@link Manipulation}s
     * are created using {@link ManipulationFactory}.
     * 
     * @param manipulatedElement
     *            {@link Element} to be manipulated.
     */
    public Manipulation(Element manipulatedElement) {
        setManipulatedElement(manipulatedElement);

        finalizeInit();
    }

    public void addManipulationCoordinates(Unit x, Unit y) {
        this.x.add(x);
        this.y.add(y);

        logger.trace("added manipulation coordinates");
    }

    /**
     * Creates new instance of {@link Manipulation} by duplicating its values.
     * 
     * @return Duplicated instance of {@link Manipulation}.
     */
    protected abstract Manipulation duplicate();

    /**
     * Executes manipulation.
     * 
     * @throws ManipulationExecutionException
     *             in case of some error while executing manipulation.
     */
    public abstract void execute() throws ManipulationExecutionException;

    /**
     * Finalizes {@link Manipulation} initialization.
     */
    private void finalizeInit() {
        logger = Logger.getLogger(this.getClass().getName());

        setActive(false);

        this.x = new Vector<Unit>();
        this.y = new Vector<Unit>();

        setManipulatedGroup(null);
    }

    /**
     * Getter for <code>manipulatedElement</code>.
     * 
     * @return the manipulatedElement
     */
    public Element getManipulatedElement() {
        this.manipulatedElement.setX(getX());
        this.manipulatedElement.setY(getY());
        return this.manipulatedElement;
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
     * Manipulation active state. If is manipulation active, it means, it is being currently
     * processed.
     * 
     * @return active state of current manipulation.
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * Indicates, whether {@link Manipulation} manipulates elements.
     * 
     * @return <code>true</code>, if {@link Manipulation} manipulates elements, <code>false</code>
     *         else.
     */
    public abstract boolean isManipulatingElements();

    /**
     * Indicates, whether {@link Manipulation} manipulates groups of elements.
     * 
     * @return <code>true</code>, if {@link Manipulation} manipulates groups of elements,
     *         <code>false</code> else.
     */
    public abstract boolean isManipulatingGroups();

    /**
     * Finishes everything at the end of manipulation correctly.
     * 
     * @param e
     *            {@link MouseEvent}, that invoked this method.
     * @param r2d
     *            Rectangle, which contains mouse pointer.
     * @param manipulationQueue
     *            used for {@link Manipulation} history and execution.
     * @param gn
     *            TopNode of SchemeSG.
     * @param isMouseClicked
     *            Indicates, whether mouse was clicked or not.
     * @return <code>true</code>, if manipulation ended, <code>false</code> else.
     * @throws UnknownManipulationException
     *             In case of unknown {@link Manipulation}.
     */
    public abstract boolean manipulationEnd(MouseEvent e, Rectangle2D.Double r2d,
            ManipulationQueue manipulationQueue, GroupNode gn, boolean isMouseClicked)
            throws UnknownManipulationException;

    /**
     * Initializes all necessary structures at the beginning of manipulation correctly.
     * 
     * @param e
     *            {@link MouseEvent}, that invoked this method.
     * @param r2d
     *            Rectangle, which contains mouse pointer.
     * @param manipulationQueue
     *            used for {@link Manipulation} history and execution.
     * @param gn
     *            TopNode of SchemeSG.
     * @param isMouseClicked
     *            Indicates, whether mouse was clicked or not.
     * @throws UnknownManipulationException
     *             In case of unknown {@link Manipulation}.
     */
    public abstract void manipulationStart(MouseEvent e, Rectangle2D.Double r2d,
            ManipulationQueue manipulationQueue, GroupNode gn, boolean isMouseClicked)
            throws UnknownManipulationException;

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
     * @param active
     *            the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @param manipulatedElement
     *            the manipulatedElement to set
     */
    private void setManipulatedElement(Element manipulatedElement) {
        this.manipulatedElement = manipulatedElement;
    }

    /**
     * @param manipulatedGroup
     *            the manipulatedGroup to set
     */
    public void setManipulatedGroup(GroupNode manipulatedGroup) {
        this.manipulatedGroup = manipulatedGroup;
    }

    protected void setManipulationCoordinates(Vector<Unit> x, Vector<Unit> y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Unexecutes (undoes) manipulation.
     * 
     * @throws ManipulationExecutionException
     *             in case of some error while removing manipulation.
     */
    public abstract void unexecute() throws ManipulationExecutionException;
}
