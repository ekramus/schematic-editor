package cz.cvut.fel.schematicEditor.manipulation;

import java.util.Vector;

import cz.cvut.fel.schematicEditor.element.Element;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;

/**
 * @author uk
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
     * Creates new instance of {@link Manipulation}.
     * 
     * @param manipulation
     * @return
     */
    protected abstract Manipulation duplitate();

    public abstract boolean isManipulatingElements();

    /**
     * @return the manipulatedElement
     */
    public Element getManipulatedElement() {
        this.manipulatedElement.setX(getX());
        this.manipulatedElement.setY(getY());
        return this.manipulatedElement;
    }

    public void setManipulationCoordinates(Vector<Unit> x, Vector<Unit> y) {
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
    public Vector<Unit> getX() {
        return this.x;
    }

    /**
     * @return the y
     */
    public Vector<Unit> getY() {
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
     * This method returns manipulation type of used manipulation.
     * 
     * @return Type of {@link Manipulation}.
     */
    public abstract ManipulationType getManipulationType();

    public abstract boolean isManipulatingGroups();

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
}
