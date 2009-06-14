package cz.cvut.fel.schematicEditor.parts;

import java.util.Vector;

/**
 * This interface defines LightweightPartProperties in general.
 *
 * @author Urban Kravjansky
 *
 */
public interface PartProperties extends Iterable<PropertiesCategory> {
    /**
     * Get netlist {@link String}
     *
     * @return netlist {@link String}.
     */
    public String getNetlist();

    /**
     * Retrieves part pin names, so they can be used for connector matching.
     *
     * @return the partPinNames
     */
    public Vector<String> getPartPinNames();

    /**
     * Sets part pin names.
     *
     * @param partPinNames {@link Vector} of part pin names.
     */
    public void setPartPinNames(Vector<String> partPinNames);

    /**
     * @return the partType
     */
    public PartType getPartType();

    public boolean update();

    /**
     * Get value of property. PartProperty name has to be in dot form (e.g. color.foreground), where word before dot is
     * category name. If category name is missing, <code>general</code> will be assigned.
     *
     * @param propertyName
     * @return value of property.
     */
    public String getProperty(String propertyName);

    /**
     * Set value of property. PartProperty name has to be in dot form (e.g. color.foreground), where word before dot is
     * category name. If category name is missing, <code>general</code> will be assigned.
     *
     * @param propertyName
     * @param value
     */
    public void setProperty(String propertyName, Object value);

    public PropertiesArray getPartProperties();
}
