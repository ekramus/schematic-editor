package cz.cvut.fel.schematicEditor.parts;

import java.util.HashMap;
import java.util.HashSet;

import org.apache.log4j.Logger;

/**
 * @author Urban Kravjansky
 *
 *
 */
public abstract class SynchronizedPartProperties {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger                     logger;
    /**
     * {@link HashMap} containing all relevant properties.
     */
    private HashSet<SynchronizedPartProperty> synchronizedPartProperties;

    /**
     * This method instantiates new instance.
     *
     */
    public SynchronizedPartProperties() {
        setSynchronizedPartProperties(new HashSet<SynchronizedPartProperty>());
    }

    /**
     * Get netlist specific to part.
     *
     * @return Netlist represented by {@link String}.
     */
    public abstract String getNetlist();

    /**
     * @return the partType
     */
    public abstract PartType getPartType();

    /**
     * Updates this instance with given {@link SynchronizedPartProperties}.
     *
     * @param spp {@link SynchronizedPartProperties} to use for update.
     */
    public void update(SynchronizedPartProperties spp) {
        // TODO implement
    }

    /**
     * @return the synchronizedPartProperties
     */
    private HashSet<SynchronizedPartProperty> getSynchronizedPartProperties() {
        return this.synchronizedPartProperties;
    }

    /**
     * @param synchronizedPartProperties the synchronizedPartProperties to set
     */
    private void setSynchronizedPartProperties(HashSet<SynchronizedPartProperty> synchronizedPartProperties) {
        this.synchronizedPartProperties = synchronizedPartProperties;
    }

    /**
     * Expands prototype netlist {@link String} into correct netlist representation based on given prototype and
     * {@link PartProperties}. Expansion is done using regular expressions, it is faster and more bug resistant.
     *
     * @param netlistPrototype Netlist prototype to be expanded.
     * @param synchronizedPartProperties Part properties, which will be searched for values during expansion.
     * @return Expanded netlist {@link String}.
     */
    protected String expandPrototype(final String netlistPrototype,
            final SynchronizedPartProperties synchronizedPartProperties) {
        // String result = netlistPrototype;
        //
        // String mandatoryString = "<(\\S+)>";
        // String optionalString = "\\[\\S*(<(\\S+)>)\\]";
        // Pattern mandatoryPattern = Pattern.compile(mandatoryString);
        // Pattern optionalPattern = Pattern.compile(optionalString);
        //
        // Matcher optionalMatcher = optionalPattern.matcher(result);
        // while (optionalMatcher.find()) {
        // String value = synchronizedPartProperties.getProperty(optionalMatcher.group(2)).getValue();
        // // if parameter value is found
        // if ((value != null) && (!value.equals(""))) {
        // result = result.replaceAll(optionalMatcher.group(1), value);
        // }
        // // parameter value was not found
        // else {
        // // we have to left [ and ] characters, as they are special chars for regexp
        // result = result.replaceAll(optionalMatcher.group(1), "");
        // }
        // }
        //
        // Matcher mandatoryMatcher = mandatoryPattern.matcher(result);
        // while (mandatoryMatcher.find()) {
        // String value = synchronizedPartProperties.getProperty(mandatoryMatcher.group(1)).getValue();
        // // if parameter value is found
        // if (value != null) {
        // result = result.replaceAll(mandatoryMatcher.group(), value);
        // }
        // }
        //
        // // clean netlist string from left brackets and undefined variables
        // result = result.replaceAll("\\[(?:\\S+=)?\\]", "");
        // // clean netlist from brackets left with filled optional values
        // result = result.replaceAll("[\\[\\]]", "");
        // // replace multiple white chars with one space
        // result = result.replaceAll("\\s+", " ");
        // // remove space at the end of string, if present
        // result = result.replaceAll(" $", "");
        //
        // return result;

        return "";
    }
}
