package cz.cvut.fel.schematicEditor.unit.oneDimensional.imperial;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import cz.cvut.fel.schematicEditor.unit.UnitType;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;

/**
 * This class defines methods for {@link Mil} subclass. It also encapsulates all data fields necessary for correct
 * behavior.
 *
 * @author Urban Kravjansky
 */
@XStreamAlias("Mil")
public class Mil extends Unit {
    /**
     * Number of pixels per one mil (one thousandth of inch).
     */
    private static final double PIXEL_PER_MIL = 72 * 0.001;

    /**
     * Default constructor. It initializes value with zero value.
     */
    public Mil() {
        setDoubleFactor(PIXEL_PER_MIL);
        setValue(0);
    }

    /**
     * Constructor with given value. It initializes <code>value</code> parameter.
     *
     * @param value <code>value</code> of {@link Mil} unit.
     */
    public Mil(final double value) {
        setDoubleFactor(PIXEL_PER_MIL);
        setValue(value);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit#getUnitType()
     */
    @Override
    protected UnitType getUnitType() {
        return UnitType.MIL;
    }
}
