/**
 *
 */
package cz.cvut.fel.schematicEditor.manipulation;

import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import cz.cvut.fel.schematicEditor.element.Element;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;

/**
 * @author uk
 */
public abstract class Manipulation {
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
     *
     */
    public Manipulation() {
        setActive(false);

        this.x = new Vector<Unit>();
        this.y = new Vector<Unit>();
    }

    /**
     *
     */
    public Manipulation(Element manipulatedElement) {
        this.active = false;
        this.manipulatedElement = manipulatedElement;

        this.x = new Vector<Unit>();
        this.y = new Vector<Unit>();
    }

    /**
     * Creates new instance of {@link Manipulation}.
     * 
     * @param manipulation
     * @return
     */
    public abstract Manipulation newInstance(Manipulation manipulation);
    
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
     * Manipulation active state. If is manipulation active, it means, it is being currently
     * processed.
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
}
