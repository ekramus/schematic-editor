package cz.cvut.fel.schematicEditor.parts;

import java.util.ArrayList;

/**
 * This class encapsulates array of property categories.
 *
 * @author Urban Kravjansky
 */
public class PropertiesArray {
    private ArrayList<PropertiesCategory> categoriesForPropertiesArray;

    /**
     * This method instantiates new instance.
     *
     */
    public PropertiesArray() {
        setCategoriesForPropertiesArray(new ArrayList<PropertiesCategory>());
    }

    /**
     * @return the categoriesForPropertiesArray
     */
    public ArrayList<PropertiesCategory> getCategoriesForPropertiesArray() {
        return this.categoriesForPropertiesArray;
    }

    /**
     * @param categoriesForPropertiesArray the categoriesForPropertiesArray to set
     */
    private void setCategoriesForPropertiesArray(ArrayList<PropertiesCategory> categoriesForPropertiesArray) {
        this.categoriesForPropertiesArray = categoriesForPropertiesArray;
    }
}
