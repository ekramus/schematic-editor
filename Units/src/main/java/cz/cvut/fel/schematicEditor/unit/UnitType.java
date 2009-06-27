package cz.cvut.fel.schematicEditor.unit;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.computer.Pixel;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.imperial.Inch;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.imperial.Mil;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.metric.Milimeter;

/**
 * @author Urban Kravjansky
 */
@XStreamAlias("UnitType")
public enum UnitType {
    /**
     * {@link Pixel} {@link Unit} type.
     */
    PIXEL("px"),
    /**
     * {@link Inch} {@link Unit} type.
     */
    INCH("in"),
    /**
     * {@link Mil} {@link Unit} type.
     */
    MIL("mil"),
    /**
     * {@link Milimeter} {@link Unit} type.
     */
    MILIMETER("mm");

    /**
     * {@link Unit} string, which identifies type of {@link Unit}.
     */
    private String        unitString;
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger logger;

    /**
     * Constructor with <code>unitString</code> initialization.
     *
     * @param unitString <code>unitString</code> value.
     */
    private UnitType(final String unitString) {
        setUnitString(unitString);
    }

    /**
     * @param unitString the unitString to set
     */
    private void setUnitString(final String unitString) {
        this.unitString = unitString;
    }

    /**
     * @return the unitString
     */
    public String getUnitString() {
        return this.unitString;
    }

    /**
     * Method for parsing {@link Unit} input. This method takes input string, parses it and returns correct {@link Unit}
     * instance. Value should be further validated e.g. to be grater than zero.
     *
     * @param string {@link Unit} string representation
     * @return new {@link Unit} instance.
     */
    public static Unit parseUnit(final String string) {
        Unit result = null;
        logger = Logger.getLogger(Unit.class.getName());

        String s = string.replaceAll("(\\d+\\.?\\d+)", "$1 ");
        String[] parsedString = s.split(" +");
        try {
            Double value = Double.parseDouble(parsedString[0]);
            logger.debug("replaceAll: " + string + " -> " + s);

            if (parsedString[1].equalsIgnoreCase(PIXEL.getUnitString())) {
                result = new Pixel(value);
            } else if (parsedString[1].equalsIgnoreCase(MILIMETER.getUnitString())) {
                result = new Milimeter(value);
            } else if (parsedString[1].equalsIgnoreCase(INCH.getUnitString())) {
                result = new Inch(value);
            } else if (parsedString[1].equalsIgnoreCase(MIL.getUnitString())) {
                result = new Mil(value);
            } else {
                result = new Pixel(1);
            }
        } catch (NumberFormatException e) {
            // fall back to 1 px unit
            result = new Pixel(1);
        }

        return result;
    }
}
