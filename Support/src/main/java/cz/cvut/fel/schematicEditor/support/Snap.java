package cz.cvut.fel.schematicEditor.support;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.computer.Pixel;

/**
 * This class implements <em>snap to grid</em> feature. It is dependent on <code>gridSize</code> and
 * <code>snap</code> attributes.
 * 
 * @author Urban Kravjansky
 */
public class Snap {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger logger;
    /**
     * Static {@link Snap} singleton instance.
     */
    private static Snap   snap     = null;
    /**
     * Grid spacing size.
     */
    private Unit          gridSize = null;
    /**
     * Snap status.
     */
    private boolean       snappy   = false;

    /**
     * Singleton constructor used for {@link Snap} initialization.
     */
    private Snap() {
        logger = Logger.getLogger(this.getClass().getName());

        setGridSize(new Pixel(10));
        setSnappy(false);
    }

    /**
     * Returns singleton instance of snap.
     * 
     * @return Instance of snap.
     */
    public static Snap getInstance() {
        if (snap == null) {
            snap = new Snap();
        }
        return snap;
    }

    /**
     * Singleton override of clone method.
     * 
     * @see java.lang.Object#clone()
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    /**
     * Get snapped coordinate. In case snap to grid is disabled, coordinate will be unchanged.
     * 
     * @param coordinate
     *            coordinate to snap.
     * @return Snapped coordinate value.
     */
    public Pixel getSnap(double coordinate) {
        Pixel result;

        if (!isSnappy()) {
            return new Pixel(coordinate);
        }
        result = new Pixel(
                getGridSize().doubleValue()
                        * (int) ((coordinate + getGridSize().doubleValue() / 2) / getGridSize().doubleValue()));

        return result;
    }

    /**
     * Getter for <code>gridSize</code>.
     * 
     * @return the gridSize
     */
    public Unit getGridSize() {
        return this.gridSize;
    }

    /**
     * Setter for <code>gridSize</code>.
     * 
     * @param gridSize
     *            the gridSize to set
     */
    public void setGridSize(Unit gridSize) {
        logger.trace("Grid size: " + gridSize);
        this.gridSize = gridSize;
    }

    /**
     * Getter for <code>snappy</code>.
     * 
     * @return the isSnap
     */
    public boolean isSnappy() {
        return this.snappy;
    }

    /**
     * Setter for <code>snappy</code>.
     * 
     * @param snappy
     *            the snap to set
     */
    public void setSnappy(boolean snappy) {
        logger.trace("Is snappy: " + snappy);
        this.snappy = snappy;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "grid size: " + getGridSize() + "\tis snap: " + isSnappy();
    }
}
