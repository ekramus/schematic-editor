package cz.cvut.fel.schematicEditor.parts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

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
     * Base address for remote configuration.
     */
    private final String                      remoteConfigurationBase = "http://asinus.feld.cvut.cz/pracan3/data/part-spice/soucastky/";

    static {
        // neccessary to reinitialize logger, as it will not be set up correctly after deserialization
        logger = Logger.getLogger(SynchronizedPartProperties.class);
    }

    /**
     * This method instantiates new instance.
     *
     */
    public SynchronizedPartProperties() {
        // logger = Logger.getLogger(getClass());
        //
        setSynchronizedPartProperties(new HashSet<SynchronizedPartProperty>());
        update();
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
     * @return <code>true</code>, if properties instance was updated, <code>false</code> else.
     */
    public boolean update() {
        // // neccessary to reinitialize logger, as it will not be set up correctly after deserialization
        // logger = Logger.getLogger(getClass());

        try {
            URL url = new URL(getRemoteConfigurationBase() + getRemoteConfigurationName());
            HashMap<String, HashMap<String, String>> remoteConfiguration = load(url.openStream());
            synchronize(remoteConfiguration);
        } catch (IOException e) {
            logger.info("Configuration located on " + getRemoteConfigurationBase()
                    + getRemoteConfigurationName()
                    + "was not loaded");
            return false;
        }

        return true;
    }

    /**
     * Synchronizes remote configuration and this instance.
     *
     * @param remoteConfiguration
     */
    private void synchronize(HashMap<String, HashMap<String, String>> remoteConfiguration) {
        for (String key : remoteConfiguration.keySet()) {
            boolean keyMatched = false;
            try {
                for (SynchronizedPartProperty synchronizedPartProperty : getSynchronizedPartProperties()) {
                    if (synchronizedPartProperty.getDefinition().equalsIgnoreCase(key)) {
                        logger.trace("SPP key match");

                        keyMatched = true;
                        break;
                    }
                }
            } catch (NullPointerException e) {
                logger.trace("SPP probably null");
            }

            if (!keyMatched) {
                logger.trace("SPP key didn't match. Adding key [" + key + "]");

                SynchronizedPartProperty spp = new SynchronizedPartProperty(remoteConfiguration.get(key));
                getSynchronizedPartProperties().add(spp);
            }
        }
    }

    /**
     * Gets all part pin names.
     *
     * @return
     */
    public abstract Vector<String> getPartPinNames();

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

    /**
     * @return the remoteConfigurationName
     */
    public abstract String getRemoteConfigurationName();

    /**
     * @return the remoteConfigurationBase
     */
    public String getRemoteConfigurationBase() {
        return this.remoteConfigurationBase;
    }

    /**
     * Loads remote properties from given input stream.
     *
     * @param inputStream
     * @return {@link HashMap} of all remote properties for given part.
     */
    private HashMap<String, HashMap<String, String>> load(InputStream inputStream) {
        HashMap<String, HashMap<String, String>> result = new HashMap<String, HashMap<String, String>>();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String buf;
        String key = null;
        HashMap<String, String> propertiesBlock = new HashMap<String, String>();

        try {
            for (buf = br.readLine(); buf != null; buf = br.readLine()) {
                // named block
                if (buf.indexOf("[") == 0) {
                    // store named properties block and reinitialize properties block hash map
                    if (key != null) {
                        result.put(key, propertiesBlock);
                        propertiesBlock = new HashMap<String, String>();
                    }
                    // initialize new key
                    key = buf.replaceFirst("\\[(.*)\\]", "$1");
                }
                // property field
                else if (buf.indexOf("=") != -1) {
                    int i = buf.indexOf("=");
                    String name = buf.substring(0, i);
                    String value = buf.substring(i + 1, buf.length());
                    propertiesBlock.put(name, value);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }
}
