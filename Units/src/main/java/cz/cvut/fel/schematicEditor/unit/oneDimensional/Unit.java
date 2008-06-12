package cz.cvut.fel.schematicEditor.unit.oneDimensional;

import cz.cvut.fel.schematicEditor.unit.UnitType;

/**
 * This class defines methods for {@link Unit} subclasses. It also encapsulates data fields used for
 * unit conversions.
 *
 * @author Urban Kravjansky
 */
public abstract class Unit {
    /**
     * Value of given unit.
     */
    private double   value;
    /**
     * Factor used for conversion into double. Value means, how many dots are in one unit.
     */
    private double   doubleFactor;
    /**
     * <code>unitType</code> represents {@link String}, which appears after unit value for unit
     * specification (e.g. px for pixels, mm for milimeters, etc.).
     */
    private UnitType unitType;

    /**
     * Default constructor used for correct initialization of fields.
     */
    public Unit() {
        setDoubleFactor(0);
        setValue(0);
        setUnitType(UnitType.PIXEL);
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
     * @param doubleFactor
     *            value of <code>doubleFactor</code> to set.
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
     * @param doubleValue
     *            value of <code>double</code> to set.
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
     * @param value
     *            {@link Unit} <code>value</code>.
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
     * Getter for <code>unitType</code>.
     *
     * @return <code>unitType</code> value.
     */
    private UnitType getUnitType() {
        return this.unitType;
    }

    /**
     * Setter for <code>unitType</code>.
     *
     * @param unitType
     *            <code>unitType</code> value.
     */
    protected final void setUnitType(final UnitType unitType) {
        this.unitType = unitType;
    }
}
