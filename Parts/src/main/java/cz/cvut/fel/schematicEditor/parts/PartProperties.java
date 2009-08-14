package cz.cvut.fel.schematicEditor.parts;

import java.util.ArrayList;

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
    public ArrayList<String> getPartPinNames();

    /**
     * Sets part pin values.
     *
     * @param partPinValues {@link ArrayList} of part pin values.
     */
    public void setPartPinValues(ArrayList<String> partPinValues);

    /**
     * Gets {@link ArrayList} of part pin values.
     *
     * @return {@link ArrayList} of part pin values.
     */
    public ArrayList<String> getPartPinValues();

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

    public PropertiesArray getPartPropertiesArray();

    public String getPartVariant();

    public String getPartDescription();
}
