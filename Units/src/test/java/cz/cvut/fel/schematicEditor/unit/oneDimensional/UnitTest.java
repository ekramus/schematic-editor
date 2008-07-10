package cz.cvut.fel.schematicEditor.unit.oneDimensional;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.cvut.fel.schematicEditor.unit.oneDimensional.computer.Pixel;

/**
 * @author Urban Kravjansky
 * 
 */
public class UnitTest {
    @Test
    public void doubleValue() {
        Pixel tPixel = new Pixel(1.25);

        assertEquals(1.25, tPixel.doubleValue(), 0);
    }

    @Test
    public void intValue() {
        Pixel tPixel = new Pixel(1.25);

        assertEquals(1, tPixel.intValue(), 0);
    }
}
