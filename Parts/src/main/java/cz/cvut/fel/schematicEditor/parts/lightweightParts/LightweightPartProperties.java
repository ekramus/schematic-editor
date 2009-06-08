package cz.cvut.fel.schematicEditor.parts.lightweightParts;

import java.util.Iterator;

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
    public PropertiesArray getPartProperties() {
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

        for (PropertiesCategory propertiesCategory : getPartProperties().getCategoriesForPropertiesArray()) {
            if (propertiesCategory.getKey().equalsIgnoreCase(category)) {
                for (int i = 0; i < propertiesCategory.getPropertiesForCategory().size(); i++) {
                    PartProperty<String, String> pp = propertiesCategory.getPropertiesForCategory().get(i);
                    // replace
                    if (pp.getKey().equalsIgnoreCase(key)) {
                        propertiesCategory.getPropertiesForCategory().set(i, ppNew);
                        return;
                    }
                    // else add at the end
                    propertiesCategory.getPropertiesForCategory().add(ppNew);
                    return;
                }
            }
        }
        // category was not found
        PropertiesCategory propertiesCategory = new PropertiesCategory(category);
        propertiesCategory.getPropertiesForCategory().add(ppNew);
        getPartProperties().getCategoriesForPropertiesArray().add(propertiesCategory);
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

        for (PropertiesCategory propertiesCategory : getPartProperties().getCategoriesForPropertiesArray()) {
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
        return getPartProperties().getCategoriesForPropertiesArray().iterator();
    }
}
