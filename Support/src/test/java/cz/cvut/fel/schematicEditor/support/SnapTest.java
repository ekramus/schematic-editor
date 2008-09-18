package cz.cvut.fel.schematicEditor.support;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.cvut.fel.schematicEditor.configuration.GuiConfiguration;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.computer.Pixel;

/**
 * JUnit test class for {@link Snap}.
 * 
 * @author Urban Kravjansky
 */
public class SnapTest {
    /**
     * Test method for {@link Snap#getSnap(double)} method.
     */
    @Test
    public void getSnap() {
        GuiConfiguration configuration = GuiConfiguration.getInstance();

        double d = 0;
        double gridSize = 5;
        configuration.setGridSize(new Pixel(gridSize));

        // from 0 to 9, no snap
        configuration.setSnapToGrid(false);
        while (d < 2 * gridSize) {
            assertEquals(Snap.getSnap(d), new Pixel(d));
            d++;
        }

        // from 0 to 9, snap
        configuration.setSnapToGrid(true);
        d = 0;
        while (d < 2 * gridSize) {
            assertEquals((int) (Snap.getSnap(d).doubleValue() / gridSize), (int) ((d + gridSize / 2) / gridSize));
            d++;
        }
    }
}
