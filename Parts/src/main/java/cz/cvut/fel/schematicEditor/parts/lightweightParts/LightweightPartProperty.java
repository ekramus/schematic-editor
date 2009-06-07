package cz.cvut.fel.schematicEditor.parts.lightweightParts;

import cz.cvut.fel.schematicEditor.parts.PartProperty;

/**
 * @author Urban Kravjansky
 *
 */
public class LightweightPartProperty extends PartProperty {

    /**
     * This method instantiates new instance.
     *
     * @param key key of part property.
     * @param value value of part property.
     */
    public LightweightPartProperty(String key, String value) {
        super(key, value);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.PartProperty#getValue()
     */
    @Override
    public String getValue() {
        // TODO Auto-generated method stub
        return (String) super.getValue();
    }
}
