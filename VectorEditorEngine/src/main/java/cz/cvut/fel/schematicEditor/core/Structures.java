package cz.cvut.fel.schematicEditor.core;

import java.util.Properties;
import java.util.Vector;

import cz.cvut.fel.schematicEditor.core.coreStructures.SceneProperties;
import cz.cvut.fel.schematicEditor.manipulation.Manipulation;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationQueue;

/**
 * This class represents the application core structures.
 *
 * @author Urban Kravjansky
 */
public class Structures {
    /**
     * Reference to <code>SceneProperties</code> instance.
     */
    private static SceneProperties    sceneProperties        = null;
    /**
     * Reference to <code>ManipulationQueue</code> instance.
     */
    private static ManipulationQueue  manipulationQueue      = null;
    /**
     * Reference to active {@link Manipulation} instance.
     */
    private static Manipulation       activeManipulation     = null;
    /**
     * {@link Vector} of loaded plugin properties.
     */
    private static Vector<Properties> loadedPluginProperties = null;
    /**
     * Counter of last part number for automatic Part and PIN naming.
     */
    private static int                lastPartNumber         = 0;

    /**
     * Getter for <code>activeManipulation</code> instance.
     *
     * @return Instance of <code>activeManipulation</code>.
     */
    public static Manipulation getActiveManipulation() {
        return Structures.activeManipulation;
    }

    /**
     * Setter for <code>activeManipulation</code> instance.
     *
     * @param activeManipulation instance of <code>activeManipulation</code>.
     */
    public static void setActiveManipulation(Manipulation activeManipulation) {
        Structures.activeManipulation = activeManipulation;
    }

    /**
     * Getter for {@link SceneProperties}.
     *
     * @return the sceneProperties
     */
    public static SceneProperties getSceneProperties() {
        if (sceneProperties == null) {
            sceneProperties = new SceneProperties();
        }
        return sceneProperties;
    }

    /**
     * Getter for {@link ManipulationQueue}.
     *
     * @return the manipulationQueue
     */
    public static ManipulationQueue getManipulationQueue() {
        if (manipulationQueue == null) {
            manipulationQueue = new ManipulationQueue();
        }
        return manipulationQueue;
    }

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
