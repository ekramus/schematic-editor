package cz.cvut.fel.schematicEditor.parts.lightweightParts;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.parts.PartPropertiesInterface;

/**
 * This class implements lightweight part properties.
 *
 * @author Urban Kravjansky
 *
 */
public abstract class PartProperties implements PartPropertiesInterface {
    private HashMap<String, String> properties;
    private static Logger           logger;

    static {
        logger = Logger.getLogger(PartProperties.class);
    }

    /**
     * This method instantiates new instance.
     *
     */
    public PartProperties() {
        setProperties(new HashMap<String, String>());
    }

    /**
     * @see PartPropertiesInterface#getProperty(java.lang.String)
     */
    public String getProperty(String propertyName) {
        return getProperties().get(propertyName);
    }

    /**
     * @see PartPropertiesInterface#setProperty(String, String)
     */
    public void setProperty(String propertyName, String value) {
        getProperties().put(propertyName, value);
    }

    public boolean update() {
        return true;
    }

    /**
     * @return the properties
     */
    private HashMap<String, String> getProperties() {
        return this.properties;
    }

    /**
     * @param properties the properties to set
     */
    private void setProperties(HashMap<String, String> properties) {
        this.properties = properties;
    }

    /**
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<String> iterator() {
        return getProperties().keySet().iterator();
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.PartPropertiesInterface#getSize()
     */
    public int getSize() {
        return getProperties().size();
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.PartPropertiesInterface#getPropertiesTable()
     */
    public Object[] getPropertiesTable() {
        String[] result = new String[getProperties().size() * 2];

        int i = 0;
        for (String key : getProperties().keySet()) {
            result[2 * i] = key;
            result[2 * i + 1] = getProperties().get(key);
            logger.debug("i: " + i + "\t\tkey: " + key + "\t\tvalue: " + getProperties().get(key));
            i++;
        }

        return result;
    }
}
