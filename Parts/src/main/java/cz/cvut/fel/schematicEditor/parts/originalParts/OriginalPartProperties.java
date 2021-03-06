package cz.cvut.fel.schematicEditor.parts.originalParts;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.parts.PartType;
import cz.cvut.fel.schematicEditor.parts.PartProperty;

/**
 * This class encapsulates properties specific for any <code>Part</code>.
 *
 * @author Urban Kravjansky
 */
@Deprecated
public abstract class OriginalPartProperties implements Iterable<PartProperty<String, String>> {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger                                   logger;
    /**
     * String containing part description.
     */
    private String                                          partDescription;
    /**
     * String containing part variant.
     */
    private String                                          partVariant;
    /**
     * {@link HashMap} containing all part specific properties, used e.g. for netlist generation.
     */
    private final HashMap<String, PartProperty<String, String>> partPropertiesMap;
    /**
     * Default constructor. It initializes part with default values.
     *
     * @param variant variant of part.
     * @param description description of part.
     */
    public OriginalPartProperties(String variant, String description) {
        logger = Logger.getLogger(this.getClass().getName());

        setPartVariant(variant);
        setPartDescription(description);

        this.partPropertiesMap = new HashMap<String, PartProperty<String, String>>();
    }

    /**
     * @return the partDescription
     */
    public String getPartDescription() {
        return this.partDescription;
    }

    /**
     * @return the partVariant
     */
    public String getPartVariant() {
        return this.partVariant;
    }

    /**
     * @param partDescription the partDescription to set
     */
    private void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    /**
     * @param partVariant the partVariant to set
     */
    private void setPartVariant(String partVariant) {
        this.partVariant = partVariant;
    }

    /**
     * Retrieves part pin names, so they can be used for connector matching.
     *
     * @return the partPinNames
     */
    @Deprecated
    public abstract Vector<String> getPartPinNames();

    /**
     * Sets part pin names.
     *
     * @param partPinNames {@link Vector} of part pin names.
     */
    public abstract void setPartPinNames(Vector<String> partPinNames);

    /**
     * Returns map of all properties. Each property contains:
     * <dl>
     * <dt><code>key</code></dt>
     * <dd>name of property</dd>
     * <dt><code>value</code></dt>
     * <dd>current value of property</dd>
     *</dl>
     *
     * @return {@link HashMap} of all properties in.
     */
    protected HashMap<String, PartProperty<String, String>> getPartPropertiesMap() {
        return this.partPropertiesMap;
    }

    /**
     * Get netlist specific to part.
     *
     * @return Netlist represented by {@link String}.
     */
    public abstract String getNetlist();

    /**
     * Sets value of given property name. Properties are internally stored in {@link HashMap}.
     *
     * @param propertyName name of property to be stored.
     * @param propertyValue value of given property.
     */
    public void setProperty(String propertyName, String propertyValue) {
        setProperty(propertyName, propertyValue, "");
    }

    /**
     * Sets value of given property name. Properties are internally stored in {@link HashMap}.
     *
     * @param propertyName name of property to be stored.
     * @param propertyValue value of given property.
     * @param propertyDescription description of given property.
     */
    public void setProperty(String propertyName, String propertyValue, String propertyDescription) {
        PartProperty<String, String> p = new PartProperty<String, String>(propertyName, propertyValue, propertyDescription);
        getPartPropertiesMap().put(propertyName, p);
    }

    /**
     * Gets {@link PartProperty} assigned property name. Properties are internally stored in {@link HashMap}.
     *
     * @param propertyName name of property to be retrieved.
     * @return Instance of property.
     */
    public PartProperty<String, String> getProperty(String propertyName) {
        return getPartPropertiesMap().get(propertyName);
    }

    /**
     * Expands prototype netlist {@link String} into correct netlist representation based on given prototype and
     * {@link LightweightPartProperties}. Expansion is done using regular expressions, it is faster and more bug resistant.
     *
     * @param netlistPrototype Netlist prototype to be expanded.
     * @param partProperties Part properties, which will be searched for values during expansion.
     * @return Expanded netlist {@link String}.
     */
    protected String expandPrototype(final String netlistPrototype, final OriginalPartProperties partProperties) {
        String result = netlistPrototype;

        String mandatoryString = "<(\\S+)>";
        String optionalString = "\\[\\S*(<(\\S+)>)\\]";
        Pattern mandatoryPattern = Pattern.compile(mandatoryString);
        Pattern optionalPattern = Pattern.compile(optionalString);

        Matcher optionalMatcher = optionalPattern.matcher(result);
        while (optionalMatcher.find()) {
            String value = partProperties.getProperty(optionalMatcher.group(2)).getValue();
            // if parameter value is found
            if ((value != null) && (!value.equals(""))) {
                result = result.replaceAll(optionalMatcher.group(1), value);
            }
            // parameter value was not found
            else {
                // we have to left [ and ] characters, as they are special chars for regexp
                result = result.replaceAll(optionalMatcher.group(1), "");
            }
        }

        Matcher mandatoryMatcher = mandatoryPattern.matcher(result);
        while (mandatoryMatcher.find()) {
            String value = partProperties.getProperty(mandatoryMatcher.group(1)).getValue();
            // if parameter value is found
            if (value != null) {
                result = result.replaceAll(mandatoryMatcher.group(), value);
            }
        }

        // clean netlist string from left brackets and undefined variables
        result = result.replaceAll("\\[(?:\\S+=)?\\]", "");
        // clean netlist from brackets left with filled optional values
        result = result.replaceAll("[\\[\\]]", "");
        // replace multiple white chars with one space
        result = result.replaceAll("\\s+", " ");
        // remove space at the end of string, if present
        result = result.replaceAll(" $", "");

        return result;
    }

    /**
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<PartProperty<String, String>> iterator() {
        Vector<PartProperty<String, String>> collection = new Vector<PartProperty<String, String>>();

        for (String key : getPartPropertiesMap().keySet()) {
            collection.add(getProperty(key));
        }

        return collection.iterator();
    }

    /**
     * @return the partType
     */
    public abstract PartType getPartType();
}
