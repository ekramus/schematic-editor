package cz.cvut.fel.schematicEditor.unit.oneDimensional.imperial;

import cz.cvut.fel.schematicEditor.unit.UnitType;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;

/**
 * This class defines methods for {@link Inch} subclass. It also encapsulates all data fields necessary for correct
 * behavior.
 *
 * @author Urban Kravjansky
 */
public class Inch extends Unit {
    /**
     * Number of pixels per one inch.
     */
    private static final double PIXEL_PER_INCH = 72;

    /**
     * Default constructor. It initializes value with zero value.
     */
    public Inch() {
        setDoubleFactor(PIXEL_PER_INCH);
        setValue(0);
    }

    /**
     * Constructor with given value. It initializes <code>value</code> parameter.
     *
     * @param value <code>value</code> of {@link Inch} unit.
     */
    public Inch(final double value) {
        setDoubleFactor(PIXEL_PER_INCH);
        setValue(value);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit#getUnitType()
     */
    @Override
    protected UnitType getUnitType() {
        return UnitType.INCH;
    }
}
