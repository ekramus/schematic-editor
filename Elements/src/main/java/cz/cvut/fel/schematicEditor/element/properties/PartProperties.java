package cz.cvut.fel.schematicEditor.element.properties;

import java.util.HashMap;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.element.element.part.Part;

/**
 * This class encapsulates properties specific for any {@link Part}.
 *
 * @author Urban Kravjansky
 */
public abstract class PartProperties {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger logger;
    /**
     * String containing part description.
     */
    private String        partDescription;
    /**
     * String containing part variant.
     */
    private String        partVariant;

    /**
     * Default constructor. It initializes part with default values.
     *
     * @param variant variant of part.
     * @param description description of part.
     */
    public PartProperties(String variant, String description) {
        logger = Logger.getLogger(this.getClass().getName());

        setPartDescription(description);
        setPartVariant(variant);
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
     * Retrieves part connector values, so they can be used for connector matching.
     *
     * @return the partConnectors
     */
    public abstract Vector<String> getPartConnectors();

    /**
     * Returns map of all properties in form of {@link Vector}. Each property is also {@link Vector} containing:
     * <dl>
     * <dt><code>name</code></dt>
     * <dd>name of property</dd>
     * <dt><code>value</code></dt>
     * <dd>current value of property</dd>
     *</dl>
     *
     * @return {@link HashMap} of all properties in form of {@link Vector}.
     */
    public HashMap<String, String> getPartPropertiesMap() {
        HashMap<String, String> result = new HashMap<String, String>();

        result.put("variant", getPartVariant());
        result.put("description", getPartDescription());

        return result;
    }

    /**
     * Get netlist specific to part.
     *
     * @return Netlist represented by {@link String}.
     */
    public abstract String getNetList();

    /**
     * Expands prototype netlist {@link String} into correct netlist representation based on given prototype and
     * {@link PartProperties}. Expansion is done using regular expressions, it is faster and more bug resistant.
     *
     * @param netlistPrototype Netlist prototype to be expanded.
     * @param partProperties Part properties, which will be searched for values during expansion.
     * @return Expanded netlist {@link String}.
     */
    protected String expandPrototype(final String netlistPrototype, final PartProperties partProperties) {
        String result = netlistPrototype;

        String mandatoryString = "<(\\S+)>";
        String optionalString = "\\[\\S*(<(\\S+)>)\\]";
        Pattern mandatoryPattern = Pattern.compile(mandatoryString);
        Pattern optionalPattern = Pattern.compile(optionalString);

        Matcher optionalMatcher = optionalPattern.matcher(result);
        while (optionalMatcher.find()) {
            String value = partProperties.getPartPropertiesMap().get(optionalMatcher.group(2));
            // if parameter value is found
            if (value != null) {
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
            String value = partProperties.getPartPropertiesMap().get(mandatoryMatcher.group(1));
            // if parameter value is found
            if (value != null) {
                result = result.replaceAll(mandatoryMatcher.group(), value);
            }
        }

        // clean netlist string from left brackets and undefined variables
        result = result.replaceAll("\\[(?:\\S+=)?\\]", "");
        // clean netlist from brackets left with filled optional values
        result = result.replaceAll("[\\[\\]]", "");

        return result;
    }
}
