package cz.cvut.fel.schematicEditor.parts.lightweightParts;

import java.util.Iterator;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.parts.PartProperties;
import cz.cvut.fel.schematicEditor.parts.PropertiesArray;
import cz.cvut.fel.schematicEditor.parts.PropertiesCategory;

/**
 * This class implements lightweight part properties.
 *
 * @author Urban Kravjansky
 *
 */
public abstract class LightweightPartProperties implements PartProperties {
    private PropertiesArray partProperties;
    private static Logger   logger;

    static {
        logger = Logger.getLogger(LightweightPartProperties.class);
    }

    /**
     * This method instantiates new instance.
     *
     */
    public LightweightPartProperties() {

    }

    public boolean update() {
        return true;
    }

    /**
     * @param partProperties the partProperties to set
     */
    private void setPartProperties(PropertiesArray propertiesArray) {
        this.partProperties = propertiesArray;
    }

    /**
     * @return the partProperties
     */
    public PropertiesArray getPartProperties() {
        return this.partProperties;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.PartProperties#setProperty(java.lang.String, java.lang.Object)
     */
    public void setProperty(String propertyName, Object value) {
        for (PropertiesCategory categoryArray : getPartProperties().getPropertiesArray()) {

        }
    }

    /*
     * (non-Javadoc)
     *
     * @see cz.cvut.fel.schematicEditor.parts.PartProperties#getProperty(java.lang.String)
     */
    public String getProperty(String propertyName) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<PropertiesCategory> iterator() {
        return getPartProperties().getPropertiesArray().iterator();
    }
}
