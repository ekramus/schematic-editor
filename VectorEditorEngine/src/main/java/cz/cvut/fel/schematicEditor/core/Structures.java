package cz.cvut.fel.schematicEditor.core;

import java.util.Properties;
import java.util.Vector;

/**
 * This class represents the application core structures.
 *
 * @author Urban Kravjansky
 */
public class Structures {
    /**
     * {@link Vector} of loaded plugin properties.
     */
    private static Vector<Properties> loadedPluginProperties = null;
    /**
     * Counter of last part number for automatic Part and PIN naming.
     */
    private static int                lastPartNumber         = 0;

    /**
     * Getter for <code>loadedPluginProperties</code>.
     *
     * @return Instance of <code>loadedPluginProperties</code> {@link Vector}.
     */
    public static Vector<Properties> getLoadedPluginProperties() {
        if (loadedPluginProperties == null) {
            loadedPluginProperties = new Vector<Properties>();
        }
        return loadedPluginProperties;
    }

    /**
     * @return the lastPartNumber
     */
    public static int getLastPartNumber() {
        return lastPartNumber;
    }

    /**
     * @param lastPartNumber the lastPartNumber to set
     */
    public static void setLastPartNumber(int lastPartNumber) {
        Structures.lastPartNumber = lastPartNumber;
    }
}
