package cz.cvut.fel.schematicEditor.unit.oneDimensional.metric;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import cz.cvut.fel.schematicEditor.unit.UnitType;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;

/**
 * This class defines methods for {@link Milimeter} subclass. It also encapsulates all data fields necessary for correct
 * behavior.
 *
 * @author Urban Kravjansky
 */
@XStreamAlias("Milimeter")
public class Milimeter extends Unit {
    /**
     * Default constructor. It initializes value with zero value.
     */
    public Milimeter() {
        setDoubleFactor(1);
        setValue(0);
    }

    /**
     * Constructor with given value. It initializes <code>value</code> parameter.
     *
     * @param value <code>value</code> of {@link Milimeter} unit.
     */
    public Milimeter(final double value) {
        setDoubleFactor(1);
        setValue(value);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit#getUnitType()
     */
    @Override
    protected UnitType getUnitType() {
        return UnitType.MILIMETER;
    }
}
