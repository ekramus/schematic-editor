package cz.cvut.fel.schematicEditor.parts.lightweightParts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.parts.PartProperties;
import cz.cvut.fel.schematicEditor.parts.PartProperty;
import cz.cvut.fel.schematicEditor.parts.PropertiesArray;
import cz.cvut.fel.schematicEditor.parts.PropertiesCategory;

/**
 * This class implements lightweight part properties.
 *
 * @author Urban Kravjansky
 *
 */
public abstract class LightweightPartProperties implements PartProperties {
    /**
     * Netlist prototype {@link String} for this part.
     */
    private String          netlistPrototype;
    /**
     * Array of part properties.
     */
    private PropertiesArray partProperties;
    /**
     * {@link Logger} for logging purposes.
     */
    private static Logger   logger;

    static {

        System.err.println("Hello world");
        logger = Logger.getLogger(LightweightPartProperties.class);
    }

    /**
     * This method instantiates new instance.
     *
     */
    public LightweightPartProperties() {
        setPartProperties(new PropertiesArray());
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.PartProperties#update()
     */
    public boolean update() {
        return true;
    }

    /**
     * @param propertiesArray the properties array to set
     */
    private void setPartProperties(PropertiesArray propertiesArray) {
        this.partProperties = propertiesArray;
    }

    /**
     * @return the partProperties
     */
    public PropertiesArray getPartPropertiesArray() {
        return this.partProperties;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.PartProperties#setProperty(java.lang.String, java.lang.Object)
     */
    public void setProperty(String propertyName, Object value) {
        String category;
        String key;

        int index = propertyName.indexOf(".");
        // category is included
        if (index > 0) {
            category = propertyName.substring(0, index);
            key = propertyName.substring(index + 1);
        }
        // category is not included
        else {
            category = "general";
            key = propertyName;
        }

        PartProperty<String, String> ppNew = new PartProperty<String, String>(key, (String) value);

        for (PropertiesCategory propertiesCategory : getPartPropertiesArray().getCategoriesForPropertiesArray()) {
            if (propertiesCategory.getKey().equalsIgnoreCase(category)) {
                for (int i = 0; i < propertiesCategory.getPropertiesForCategory().size(); i++) {
                    PartProperty<String, String> pp = propertiesCategory.getPropertiesForCategory().get(i);
                    // replace
                    if (pp.getKey().equalsIgnoreCase(key)) {
                        propertiesCategory.getPropertiesForCategory().set(i, ppNew);
                        return;
                    }
                }
                // else add at the end
                propertiesCategory.getPropertiesForCategory().add(ppNew);
                return;
            }
        }
        // category was not found
        PropertiesCategory propertiesCategory = new PropertiesCategory(category);
        propertiesCategory.getPropertiesForCategory().add(ppNew);
        getPartPropertiesArray().getCategoriesForPropertiesArray().add(propertiesCategory);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.PartProperties#getProperty(java.lang.String)
     */
    public String getProperty(String propertyName) {
        String category;
        String key;

        int index = propertyName.indexOf(".");
        // category is included
        if (index > 0) {
            category = propertyName.substring(0, index);
            key = propertyName.substring(index + 1);
        }
        // category is not included
        else {
            category = "general";
            key = propertyName;
        }

        for (PropertiesCategory propertiesCategory : getPartPropertiesArray().getCategoriesForPropertiesArray()) {
            if (propertiesCategory.getKey().equalsIgnoreCase(category)) {
                for (PartProperty<String, String> partProperty : propertiesCategory.getPropertiesForCategory()) {
                    if (partProperty.getKey().equalsIgnoreCase(key)) {
                        return partProperty.getValue();
                    }
                }
            }
        }
        return null;
    }

    /**
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<PropertiesCategory> iterator() {
        return getPartPropertiesArray().getCategoriesForPropertiesArray().iterator();
    }

    /**
     * Expands prototype netlist {@link String} into correct netlist representation based on given prototype and
     * {@link LightweightPartProperties}. Expansion is done using regular expressions, it is faster and more bug
     * resistant.
     *
     * @return Expanded netlist {@link String}.
     */
    protected String expandPrototype() {
        String result = getNetlistPrototype();

        String mandatoryString = "<(\\S+)>";
        String optionalString = "\\[\\S*(<(\\S+)>)\\]";
        Pattern mandatoryPattern = Pattern.compile(mandatoryString);
        Pattern optionalPattern = Pattern.compile(optionalString);

        Matcher optionalMatcher = optionalPattern.matcher(result);
        while (optionalMatcher.find()) {
            String value = getProperty(optionalMatcher.group(2));
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
            String value = getProperty(mandatoryMatcher.group(1));
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
     * @param netlistPrototype the netlistPrototype to set
     */
    protected void setNetlistPrototype(String netlistPrototype) {
        this.netlistPrototype = netlistPrototype;
    }

    /**
     * @return the netlistPrototype
     */
    protected String getNetlistPrototype() {
        return this.netlistPrototype;
    }

    /**
     * @see PartProperties#getNetlist()
     */
    public String getNetlist() {
        return expandPrototype();
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.PartProperties#getPartPinValues()
     */
    public ArrayList<String> getPartPinValues() {
        ArrayList<String> result = new ArrayList<String>();

        for (String name : getPartPinNames()) {
            result.add(getProperty(name));
        }

        return result;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.PartProperties#setPartPinValues(java.util.ArrayList)
     */
    public void setPartPinValues(ArrayList<String> partPinValues) {
        int i = 0;
        for (String name : getPartPinNames()) {
            setProperty(name, partPinValues.get(i++));
        }
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.PartProperties#getPartPinNames()
     */
    public ArrayList<String> getPartPinNames() {
        ArrayList<String> result = new ArrayList<String>();

        result.add("n1");
        result.add("n2");

        return result;
    }
}
