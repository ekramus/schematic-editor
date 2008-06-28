package cz.cvut.fel.schematicEditor.core;

import java.util.Properties;
import java.util.Stack;

import cz.cvut.fel.schematicEditor.application.Gui;
import cz.cvut.fel.schematicEditor.application.StatusBar;
import cz.cvut.fel.schematicEditor.application.guiElements.ScenePanel;
import cz.cvut.fel.schematicEditor.core.coreStructures.SceneProperties;
import cz.cvut.fel.schematicEditor.element.shape.Line;
import cz.cvut.fel.schematicEditor.manipulation.Create;
import cz.cvut.fel.schematicEditor.manipulation.Manipulation;
import cz.cvut.fel.schematicEditor.manipulation.Select;

/**
 * This class represents the application core structures.
 *
 * @author urban.kravjansky
 */
public class Structures {
    /**
     * Reference to <code>SceneJPanel</code> instance.
     */
    private static ScenePanel      scenePanel      = null;
    /**
     * Reference to <code>SceneProperties</code> instance.
     */
    private static SceneProperties sceneProperties = null;
    /**
     * Application wide properties.
     */
    private static Properties      properties      = null;
    /**
     * Reference to <code>Gui</code> instance.
     */
    private static Gui             gui             = null;
    /**
     * Current manipulation object.
     */
    private static Manipulation    manipulation    = null;
    /**
     * Status bar object.
     */
    private static StatusBar       statusBar       = null;

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
     * Getter for scenePanel
     *
     * @return Singleton instance of scenePanel.
     */
    public static ScenePanel getScenePanel() {
        if (scenePanel == null) {
            scenePanel = ScenePanel.getInstance();
        }
        return scenePanel;
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
     * Getter for {@link Gui}.
     *
     * @return the gui
     */
    public static Gui getGui() {
        if (gui == null) {
            gui = new Gui();
        }
        return gui;
    }

    /**
     * Getter for {@link Manipulation}.
     *
     * @return the manipulation
     */
    public static Manipulation getManipulation() {
        if (manipulation == null) {
            manipulation = new Select();
        }
        return manipulation;
    }

    /**
     * Getter for {@link Manipulation}.
     *
     * @param manipulation
     *            the manipulation to set
     */
    public static void setManipulation(Manipulation manipulation) {
        Structures.manipulation = manipulation;
    }

    public static StatusBar getStatusBar() {
        if (statusBar == null) {
            statusBar = statusBar.getInstance();
        }
        return statusBar;
    }
}
