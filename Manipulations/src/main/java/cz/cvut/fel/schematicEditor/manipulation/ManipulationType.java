package cz.cvut.fel.schematicEditor.manipulation;

/**
 * This enumeration contains manipulation types, which are recognized by application.
 *
 * @author Urban Kravjansky
 */
public enum ManipulationType {
    /**
     * Create manipulation.
     */
    CREATE,
    /**
     * Select manipulation.
     */
    SELECT,
    /**
     * Edit manipulation.
     */
    EDIT,
    /**
     * Delete manipulation.
     */
    DELETE,
    /**
     * Move manipulation.
     */
    MOVE,
    /**
     * Copy manipulation.
     */
    COPY,
    /**
     * Cut manipulation.
     */
    CUT,
    /**
     * Paste manipulation.
     */
    PASTE,
    /**
     * Rotate manipulation.
     */
    ROTATE,
    /**
     * Mirror manipulation.
     */
    MIRROR,
    /**
     * Select rotation center manipulation.
     */
    SELECT_ROTATION_CENTER,
}
