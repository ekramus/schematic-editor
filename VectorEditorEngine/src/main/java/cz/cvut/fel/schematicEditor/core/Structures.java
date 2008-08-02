package cz.cvut.fel.schematicEditor.core;

import java.util.Properties;

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
    private static SceneProperties   sceneProperties    = null;
    /**
     * Application wide properties.
     */
    private static Properties        properties         = null;
    /**
     * Reference to <code>ManipulationQueue</code> instance.
     */
    private static ManipulationQueue manipulationQueue  = null;
    /**
     * Reference to active {@link Manipulation} instance.
     */
    private static Manipulation      activeManipulation = null;

    /**
     * Getter for properties.
     * 
     * @return Properties defined in external file.
     */
    public static Properties getProperties() {
        return properties;
    }

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
     * Setter for properties
     * 
     * @param properties properties to save.
     */
    public static void setProperties(Properties properties) {
        Structures.properties = properties;
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
}
