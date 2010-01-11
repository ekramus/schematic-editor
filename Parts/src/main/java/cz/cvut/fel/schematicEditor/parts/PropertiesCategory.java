package cz.cvut.fel.schematicEditor.parts;

import java.util.ArrayList;

/**
 * @author Urban Kravjansky
 *
 */
public class PropertiesCategory {
    private String                                  key;
    private ArrayList<PartProperty<String, String>> propertiesForCategory;

    /**
     * This method instantiates new instance.
     *
     * @param key
     *
     */
    public PropertiesCategory(String key) {
        setKey(key);
        setPropertiesForCategory(new ArrayList<PartProperty<String, String>>());
    }

    /**
     * @param key the key to set
     */
    private void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return this.key;
    }

    /**
     * @return the propertiesForCategory
     */
    public ArrayList<PartProperty<String, String>> getPropertiesForCategory() {
        return this.propertiesForCategory;
    }

    /**
     * @param propertiesForCategory the propertiesForCategory to set
     */
    private void setPropertiesForCategory(ArrayList<PartProperty<String, String>> propertiesForCategory) {
        this.propertiesForCategory = propertiesForCategory;
    }
}
