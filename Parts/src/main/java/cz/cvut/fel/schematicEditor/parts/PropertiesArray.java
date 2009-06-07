package cz.cvut.fel.schematicEditor.parts;

import java.util.ArrayList;

/**
 * This class encapsulates array of property categories.
 *
 * @author Urban Kravjansky
 */
public class PropertiesArray {
    private ArrayList<PropertiesCategory> propertiesArray;

    /**
     * This method instantiates new instance.
     *
     */
    public PropertiesArray() {
        setPropertiesArray(new ArrayList<PropertiesCategory>());
    }

    /**
     * @return the propertiesArray
     */
    public ArrayList<PropertiesCategory> getPropertiesArray() {
        return this.propertiesArray;
    }

    /**
     * @param propertiesArray the propertiesArray to set
     */
    private void setPropertiesArray(ArrayList<PropertiesCategory> propertiesArray) {
        this.propertiesArray = propertiesArray;
    }
}
