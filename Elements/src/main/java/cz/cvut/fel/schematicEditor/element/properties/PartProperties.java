package cz.cvut.fel.schematicEditor.element.properties;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Vector;

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
    private static Logger  logger;
    /**
     * String containing part description.
     */
    private String         partDescription;
    /**
     * String containing part variant.
     */
    private String         partVariant;
    /**
     * Vector containing names of all part connectors.
     */
    private Vector<String> partConnectors;

    /**
     * Default constructor. It initializes part with default values.
     *
     * @param variant variant of part.
     * @param description desccription of part.
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
     * @param partConnectors the partConnectors to set
     */
    public void setPartConnectors(Vector<String> partConnectors) {
        this.partConnectors = partConnectors;
    }

    /**
     * Generates part connectors string. Connector names are separated using special symbol.
     *
     * @return the partConnectors
     */
    public String getPartConnectors() {
        String result = "";

        for (String connector : this.partConnectors) {
            // TODO set as configurable
            result += connector + "::";
        }

        result = result.substring(0, result.length() - 2);

        return result;
    }

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
        result.put("connectors", getPartConnectors());

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
     * {@link PartProperties}.
     *
     * @param netlistPrototype Netlist prototype to be expanded.
     * @param partProperties Part properties, which will be searched for values during expansion.
     * @return Expanded netlist {@link String}.
     */
    protected String expandPrototype(String netlistPrototype, PartProperties partProperties) {
        String result = "";

        int i = 0;
        String c = netlistPrototype.substring(i, ++i);

        try {
            while (i < netlistPrototype.length()) {

                // parameter substitution
                if (c.equals("<")) {
                    int j = netlistPrototype.indexOf(">", i);
                    String param = netlistPrototype.substring(i, j);
                    result += processParameter(param, partProperties);
                    i = j + 1;
                }
                // optional value
                else if (c.equals("[")) {
                    try {
                        int k = netlistPrototype.indexOf("]", i);
                        String buf = "";
                        c = netlistPrototype.substring(i, ++i);
                        while (i < k) {
                            if (c.equals("<")) {
                                int j = netlistPrototype.indexOf(">", i);
                                String param = netlistPrototype.substring(i, j);
                                buf += processParameter(param, partProperties);
                                i = j + 1;
                            } else {
                                buf += c;
                            }
                            c = netlistPrototype.substring(i, ++i);
                        }
                        result += buf;
                    } catch (NoSuchElementException nsee) {
                        // nothing to do
                    }
                } else {
                    result += c;
                }
                c = netlistPrototype.substring(i, ++i);
            }
        } catch (StringIndexOutOfBoundsException e) {
            // TODO fix this parser
        }

        return result;
    }

    private String processParameter(String param, PartProperties partProperties) throws NoSuchElementException {
        String result = "";

        result = partProperties.getPartPropertiesMap().get(param);
        if (result == null) {
            throw new NoSuchElementException();
        }

        return result;
    }
}
