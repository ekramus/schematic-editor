package cz.cvut.fel.schematicEditor.unit.oneDimensional;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import cz.cvut.fel.schematicEditor.unit.UnitType;

/**
 * This class defines methods for {@link Unit} subclasses. It also encapsulates data fields used for unit conversions.
 *
 * @author Urban Kravjansky
 */
@XStreamAlias("unit")
public abstract class Unit implements Comparable<Unit> {
    /**
     * Value of given unit.
     */
    private double   value;
    /**
     * Factor used for conversion into double. Value means, how many dots are in one unit.
     */
    private double   doubleFactor;
    /**
     * <code>unitType</code> represents {@link String}, which appears after unit value for unit specification (e.g. px
     * for pixels, mm for milimeters, etc.).
     */
    @XStreamOmitField
    private UnitType unitType;

    /**
     * Default constructor used for correct initialization of fields.
     */
    public Unit() {
        setDoubleFactor(0);
        setValue(0);
    }

    /**
     * Getter for <code>doubleFactor</code>. Value means, how many dots are in one unit.
     *
     * @return <code>doubleFactor</code> value.
     */
    private double getDoubleFactor() {
        return this.doubleFactor;
    }

    /**
     * Setter for <code>doubleFactor</code>. Value means, how many dots are in one unit.
     *
     * @param doubleFactor value of <code>doubleFactor</code> to set.
     */
    protected final void setDoubleFactor(final double doubleFactor) {
        this.doubleFactor = doubleFactor;
    }

    /**
     * Getter for <code>double</code> value of this {@link Unit}.
     *
     * @return <code>double</code> value.
     */
    public final double doubleValue() {
        return getValue() * getDoubleFactor();
    }

    /**
     * Getter for <code>int</code> value of this {@link Unit}.
     *
     * @return <code>int</code> value.
     */
    public final int intValue() {
        return (int) doubleValue();
    }

    /**
     * Getter for <code>float</code> value of this {@link Unit}.
     *
     * @return <code>float</code> value.
     */
    public final float floatValue() {
        return (float) doubleValue();
    }

    /**
     * Setter of <code>double</code> value of this {@link Unit}.
     *
     * @param doubleValue value of <code>double</code> to set.
     */
    public final void setDouble(final double doubleValue) {
        setValue(doubleValue / getDoubleFactor());
    }

    /**
     * Getter for unit <code>value</code>.
     *
     * @return {@link Unit} <code>value</code>.
     */
    public final double getValue() {
        return this.value;
    }

    /**
     * Setter for unit <code>value</code>.
     *
     * @param value {@link Unit} <code>value</code>.
     */
    public final void setValue(final double value) {
        this.value = value;
    }

    /**
     * Method used for conversion of this class into {@link String}.
     *
     * @return {@link String} representation of class.
     */
    @Override
    public final String toString() {
        return getValue() + " " + getUnitType().getUnitString();
    }

    /**
     * Returns {@link UnitType} code of {@link Unit}.
     *
     * @return <code>unitType</code> value.
     */
    protected abstract UnitType getUnitType();

    /**
     * @param unit
     * @return
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Unit unit) {
        double delta = getValue() - unit.getValue();
        if (delta < 0) {
            return -1;
        }
        if (delta > 0) {
            return 1;
        }
        return 0;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (compareTo((Unit) obj) == 0) {
            return true;
        }
        return false;
    }
}
