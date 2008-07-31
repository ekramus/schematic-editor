package cz.cvut.fel.schematicEditor.core;

import java.util.Properties;

import cz.cvut.fel.schematicEditor.core.coreStructures.SceneProperties;
import cz.cvut.fel.schematicEditor.manipulation.Manipulation;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationFactory;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationQueue;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;

/**
 * This class represents the application core structures.
 * 
 * @author Urban Kravjansky
 */
public class Structures {
    /**
     * Reference to <code>SceneProperties</code> instance.
     */
    private static SceneProperties   sceneProperties   = null;
    /**
     * Application wide properties.
     */
    private static Properties        properties        = null;
    /**
     * Reference to <code>ManipulationQeue</code> instance.
     */
    private static ManipulationQueue manipulationQueue = null;

    /**
     * Getter for properties.
     * 
     * @return Properties defined in external file.
     */
    public static Properties getProperties() {
        return properties;
    }

    /**
     * Setter for properties
     * 
     * @param properties
     *            properties to save.
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
     * Getter for {@link ManipulationQeue}.
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
