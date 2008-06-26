package cz.cvut.fel.schematicEditor.support;

import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.computer.Pixel;

/**
 * This class implements <em>snap to grid</em> feature. It is dependent on <code>gridSize</code> and
 * <code>snap</code> attributes.
 * 
 * @author Urban Kravjanský
 * 
 */
public class Snap {
    /**
     * Grid spacing size.
     */
    private Unit    gridSize = null;
    /**
     * Snap status.
     */
    private boolean snap     = false;

    /**
     * Constructor used for {@link Snap} initialization.
     * 
     * @param gridSize
     *            size of grid (grid is created from squares).
     * @param snap
     *            indicates, whether snap to grid is or is not active.
     */
    public Snap(Unit gridSize, boolean snap) {
        setGridSize(gridSize);
        setSnap(snap);
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

        if (!isSnap()) {
            return new Pixel(coordinate);
        }
        result = new Pixel(
                getGridSize().doubleValue() * ((int) (coordinate + getGridSize().doubleValue() / 2) / getGridSize()
                        .doubleValue()));

        return result;
    }

    /**
     * Getter for <code>gridSize</code>.
     * 
     * @return the gridSize
     */
    private Unit getGridSize() {
        return this.gridSize;
    }

    /**
     * Setter for <code>gridSize</code>.
     * 
     * @param gridSize
     *            the gridSize to set
     */
    private void setGridSize(Unit gridSize) {
        this.gridSize = gridSize;
    }

    /**
     * Getter for <code>snap</code>.
     * 
     * @return the isSnap
     */
    private boolean isSnap() {
        return this.snap;
    }

    /**
     * Setter for <code>snap</code>.
     * 
     * @param snap
     *            the snap to set
     */
    private void setSnap(boolean snap) {
        this.snap = snap;
    }
}
