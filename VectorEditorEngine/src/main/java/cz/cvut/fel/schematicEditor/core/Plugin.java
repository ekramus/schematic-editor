package cz.cvut.fel.schematicEditor.core;

import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;

import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;

/**
 * This interface describes methods, that should be provided by any plugin to be able to work with
 * <em>SchematicEditor</em> data structures and user interface.
 *
 * @author Urban Kravjansky
 */
public interface Plugin {
    /**
     * Indicates, whether plugin provides menu item, or not.
     *
     * @return <code>true</code>, if plugin provides menu item, <code>false</code> else.
     */
    boolean providesMenuItem();

    /**
     * Getter for menu item provided by plugin.
     *
     * @return {@link JMenuItem} instance provided by plugin.
     */
    JMenuItem getMenuItem();

    /**
     * Indicates, whether plugin provides drawing button, or not.
     *
     * @return <code>true</code>, if plugin provides drawing button, <code>false</code> else.
     */
    boolean providesDrawingButton();

    /**
     * Getter for drawing button provided by plugin.
     *
     * @return {@link JButton} instance provided by plugin.
     */
    JButton getDrawingButton();

    /**
     * Activates plugin, so it is able to process data and be accessed by the user.
     *
     * @param menuBar menu bar, so custom menu items can be added.
     * @param drawingBar drawing bar, so custom drawing buttons can be added.
     * @param sceneGraph scene graph, so data in scene can be manipulated.
     */
    void activate(JMenuBar menuBar, JToolBar drawingBar, SceneGraph sceneGraph);
}
