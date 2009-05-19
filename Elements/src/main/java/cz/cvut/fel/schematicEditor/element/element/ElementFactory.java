package cz.cvut.fel.schematicEditor.element.element;

import cz.cvut.fel.schematicEditor.element.ElementType;

/**
 * This class represents element factory used to create elements according to given
 * {@link ElementType}.
 *
 * @author Urban Kravjansky
 */
public class ElementFactory {
    /**
     * This is default constructor. It is private to force static use of {@link ElementFactory}.
     */
    private ElementFactory() {
        // nothing to do
    }

    public static Element create(ElementType type) {
        // switch (type) {
        // case
        // }

        return null;
    }
}
