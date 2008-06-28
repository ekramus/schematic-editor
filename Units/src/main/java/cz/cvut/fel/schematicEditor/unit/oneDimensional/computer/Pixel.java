package cz.cvut.fel.schematicEditor.unit.oneDimensional.computer;

import cz.cvut.fel.schematicEditor.unit.UnitType;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;

/**
 * This class defines methods for {@link Pixel} subclasses. It also encapsulates all data fields
 * necessary for correct behavior.
 * 
 * @author Urban Kravjansky
 */
public class Pixel extends Unit {
    /**
     * Default constructor. It initializes value with zero value.
     */
    public Pixel() {
        setDoubleFactor(1);
        setUnitType(UnitType.PIXEL);
        setValue(0);
    }

    /**
     * Constructor with given value. It initializes <code>value</code> parameter.
     * 
     * @param value
     *            <code>value</code> of {@link Pixel} unit.
     */
    public Pixel(final double value) {
        setDoubleFactor(1);
        setUnitType(UnitType.PIXEL);
        setValue(value);
    }
}
