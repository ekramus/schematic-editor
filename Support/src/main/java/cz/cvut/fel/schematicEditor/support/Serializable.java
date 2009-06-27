package cz.cvut.fel.schematicEditor.support;

/**
 * This interface defines methods necessary for class to implement in order to be serializable using XStream.
 *
 * @author Urban Kravjansky
 *
 */
public interface Serializable {
    /**
     * Getter for serializable class array.
     *
     * @return Array of serializable classes.
     */
    public Class[] getClassArray();
}
