package cz.cvut.fel.schematicEditor.parts;

import java.util.Vector;

/**
 * This interface defines PartProperties in general.
 *
 * @author Urban Kravjansky
 *
 */
public interface PartPropertiesInterface extends Iterable<String> {
    /**
     * Set netlist {@link String}
     *
     * @param netlist netlist {@link String}.
     */
    public void setNetlist(String netlist);

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

    public String getProperty(String propertyName);

    public void setProperty(String propertyName, String value);

    public int getSize();

    public Object[] getPropertiesTable();
}
