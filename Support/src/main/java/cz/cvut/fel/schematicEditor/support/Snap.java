package cz.cvut.fel.schematicEditor.support;

import java.util.Vector;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.configuration.GuiConfiguration;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
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
     * Logger instance for logging purposes.
     */
    private static Logger logger;

    /**
     * Get snapped coordinate. In case snap to grid is disabled, coordinate will be unchanged.
     *
     * @param coordinate coordinate to snap.
     * @return Snapped coordinate value.
     */
    private static UnitPoint getSnap(UnitPoint coordinate) {
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
        UnitPoint result = new UnitPoint(coordinate);
        Unit minDelta = configuration.getSnapDelta();

        // do we have list of coordinates to use for snap?
        if (snapPoints == null) {
            return getSnap(coordinate);
        }

        // try to snap to nearest given coordinate
        for (UnitPoint up : snapPoints) {
            Unit delta = Support.distance(coordinate, up);
            if (delta.compareTo(minDelta) < 0) {
                result = new UnitPoint(up);
                minDelta = new Pixel(delta.doubleValue());
            }
        }

        // snap to grid if not snapped to any of given coordinates
        if (result.equals(coordinate)) {
            result = getSnap(coordinate);
        }

        return result;
    }

    /**
     * Get snapped coordinate with rectangular snap to previous coordinate.
     *
     * @param coordinate coordinate to snap.
     * @param snapPoints {@link Vector} of {@link UnitPoint}s, from which snap point is selected.
     * @param x previous X coordinates used for snapping.
     * @param y previous Y coordinates used for snapping.
     * @return Snapped coordinate.
     */
    public static UnitPoint getSnap(UnitPoint coordinate, Vector<UnitPoint> snapPoints, Vector<Unit> x, Vector<Unit> y) {
        UnitPoint result = new UnitPoint(coordinate);
        logger = Logger.getLogger(Snap.class.getClass());

        // do we have list of coordinates to use for snap?
        if (snapPoints == null) {
            return getSnap(coordinate);
        }

        // do we have previous coordinates?
        try {
            if ((x.size() < 2) || (y.size() < 2)) {
                return getSnap(coordinate);
            }
        } catch (NullPointerException e) {
            logger.trace("no coordinates at all");
        }

        Unit previousX = x.get(x.size() - 2);
        Unit previousY = y.get(y.size() - 2);

        double deltaX = Math.abs(coordinate.getX() - previousX.doubleValue());
        double deltaY = Math.abs(coordinate.getY() - previousY.doubleValue());

        if (deltaX < deltaY) {
            result.setUnitX(previousX);
        } else {
            result.setUnitY(previousY);
        }

        return getSnap(result, snapPoints);
    }
}
