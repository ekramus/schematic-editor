package cz.cvut.fel.schematicEditor.support;

import java.util.Vector;

import cz.cvut.fel.schematicEditor.configuration.GuiConfiguration;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.computer.Pixel;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;

/**
 * This class implements <em>snap to grid</em> feature. It is dependent on <code>gridSize</code> and <code>snap</code>
 * attributes.
 *
 * @author Urban Kravjansky
 */
public class Snap {
    /**
     * Get snapped coordinate. In case snap to grid is disabled, coordinate will be unchanged.
     *
     * @param coordinate coordinate to snap.
     * @return Snapped coordinate value.
     */
    public static UnitPoint getSnap(UnitPoint coordinate) {
        GuiConfiguration configuration = GuiConfiguration.getInstance();
        Pixel x;
        Pixel y;

        if (!configuration.isSnapToGrid()) {
            return new UnitPoint(coordinate);
        }
        x = new Pixel(configuration.getGridSize().doubleValue() * (int) ((coordinate.getX() + configuration
                .getGridSize().doubleValue() / 2) / configuration.getGridSize().doubleValue()));
        y = new Pixel(configuration.getGridSize().doubleValue() * (int) ((coordinate.getY() + configuration
                .getGridSize().doubleValue() / 2) / configuration.getGridSize().doubleValue()));

        return new UnitPoint(x, y);
    }

    /**
     * Get snapped coordinate from the list of coordinates. In case no coordinate is inside delta circle, returns
     * original coordinate.
     *
     * @param coordinate coordinate to snap.
     * @param snapPoints {@link Vector} of {@link UnitPoint}s, from which snap point is selected.
     * @return Snapped coordinate value.
     */
    public static UnitPoint getSnap(UnitPoint coordinate, Vector<UnitPoint> snapPoints) {
        GuiConfiguration configuration = GuiConfiguration.getInstance();
        Pixel result;

        if (!configuration.isSnapToGrid()) {
            return new UnitPoint(coordinate);
        }
        result = new Pixel(configuration.getGridSize().doubleValue() * (int) ((coordinate.getX() + configuration
                .getGridSize().doubleValue() / 2) / configuration.getGridSize().doubleValue()));

        return null;
    }
}
