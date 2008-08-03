package cz.cvut.fel.schematicEditor.element;

/**
 * @author Urban Kravjansky
 */
public enum ElementModificator {
    /**
     * Element is created without any modifications.
     */
    NO_MODIFICATION,
    /**
     * Element is created as symmetric element (e.g. circle instead of ellipse).
     */
    SYMMETRIC_ELEMENT;

    /**
     * Implements logical OR operation with {@link ElementModificator} parameter.
     * 
     * @param elementModificator
     * @return
     */
    public ElementModificator or(ElementModificator elementModificator) {
        if ((elementModificator == NO_MODIFICATION) || (this == NO_MODIFICATION)) {
            return NO_MODIFICATION;
        }
        return SYMMETRIC_ELEMENT;
    }
}
