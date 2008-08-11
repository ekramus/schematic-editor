package cz.cvut.fel.schematicEditor.support;

import cz.cvut.fel.schematicEditor.properties.GuiConfiguration;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.computer.Pixel;

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
    public static Pixel getSnap(double coordinate) {
        GuiConfiguration configuration = GuiConfiguration.getInstance();
        Pixel result;

        if (!configuration.isSnapToGrid()) {
            return new Pixel(coordinate);
        }
        result = new Pixel(configuration.getGridSize().doubleValue() * (int) ((coordinate + configuration.getGridSize()
                .doubleValue() / 2) / configuration.getGridSize().doubleValue()));

        return result;
    }
}
