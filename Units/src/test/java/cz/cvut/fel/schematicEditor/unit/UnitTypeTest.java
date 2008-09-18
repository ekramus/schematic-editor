package cz.cvut.fel.schematicEditor.unit;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.cvut.fel.schematicEditor.unit.oneDimensional.computer.Pixel;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.imperial.Inch;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.metric.Milimeter;

/**
 * @author Urban Kravjansky
 * 
 */
public class UnitTypeTest {

    /**
     * Test method for {@link cz.cvut.fel.schematicEditor.unit.UnitType#getUnitString()}.
     */
    @Test
    public void getUnitString() {
        assertEquals(UnitType.PIXEL.getUnitString(), "px");
        assertEquals(UnitType.INCH.getUnitString(), "in");
        assertEquals(UnitType.MILIMETER.getUnitString(), "mm");
    }

    /**
     * Test method for {@link cz.cvut.fel.schematicEditor.unit.UnitType#parseUnit(java.lang.String)}.
     */
    @Test
    public void parseUnit() {
        Pixel tPixel = new Pixel(1);
        Milimeter tMilimeter = new Milimeter(1);
        Inch tInch = new Inch(1);

        assertTrue(UnitType.parseUnit("1 px").equals(tPixel));
        assertTrue(UnitType.parseUnit("1 mm").equals(tMilimeter));
        assertTrue(UnitType.parseUnit("1 in").equals(tInch));
    }
}
