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
     * 
     */
    public Snap(Unit gridSize, boolean snap) {
        setGridSize(gridSize);
        setSnap(snap);
    }

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
     * @return the gridSize
     */
    public Unit getGridSize() {
        return this.gridSize;
    }

    /**
     * @param gridSize
     *            the gridSize to set
     */
    public void setGridSize(Unit gridSize) {
        this.gridSize = gridSize;
    }

    /**
     * @return the isSnap
     */
    public boolean isSnap() {
        return this.snap;
    }

    /**
     * @param snap
     *            the snap to set
     */
    public void setSnap(boolean snap) {
        this.snap = snap;
    }
}
