package cz.cvut.fel.schematicEditor.support;

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
     * Static {@link Snap} singleton instance.
     */
    private static Snap snap     = new Snap();
    /**
     * Grid spacing size.
     */
    private Unit        gridSize = null;
    /**
     * Snap status.
     */
    private boolean     snappy   = false;

    /**
     * Singleton constructor used for {@link Snap} initialization.
     */
    private Snap() {
        setGridSize(new Pixel(10));
        setSnappy(false);
    }

    /**
     * Get snapped coordinate. In case snap to grid is disabled, coordinate will be unchanged.
     * 
     * @param coordinate
     *            coordinate to snap.
     * @return Snapped coordinate value.
     */
    public static Pixel getSnap(double coordinate) {
        Pixel result;

        if (!isSnappy()) {
            return new Pixel(coordinate);
        }
        result = new Pixel(
                getGridSize().doubleValue() * (int) ((coordinate + getGridSize().doubleValue() / 2) / getGridSize()
                        .doubleValue()));

        return result;
    }

    /**
     * Getter for <code>gridSize</code>.
     * 
     * @return the gridSize
     */
    public static Unit getGridSize() {
        return snap.gridSize;
    }

    /**
     * Setter for <code>gridSize</code>.
     * 
     * @param gridSize
     *            the gridSize to set
     */
    public static void setGridSize(Unit gridSize) {
        snap.gridSize = gridSize;
    }

    /**
     * Getter for <code>snappy</code>.
     * 
     * @return the isSnap
     */
    public static boolean isSnappy() {
        return snap.snappy;
    }

    /**
     * Setter for <code>snappy</code>.
     * 
     * @param snappy
     *            the snap to set
     */
    public static void setSnappy(boolean snappy) {
        snap.snappy = snappy;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "grid size: " + getGridSize() + "\tis snap: " + isSnappy();
    }
}
