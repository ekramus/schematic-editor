package cz.cvut.fel.schematicEditor.parts;

import java.util.ArrayList;

/**
 * @author Urban Kravjansky
 *
 */
public class PropertiesCategory {
    private ArrayList<PartProperty> propertiesCategory;

    /**
     * This method instantiates new instance.
     *
     */
    public PropertiesCategory() {
        setPropertiesCategory(new ArrayList<PartProperty>());
    }

    /**
     * @return the propertiesCategory
     */
    public ArrayList<PartProperty> getPropertiesCategory() {
        return this.propertiesCategory;
    }

    /**
     * @param propertiesCategory the propertiesCategory to set
     */
    private void setPropertiesCategory(ArrayList<PartProperty> propertiesCategory) {
        this.propertiesCategory = propertiesCategory;
    }
}
