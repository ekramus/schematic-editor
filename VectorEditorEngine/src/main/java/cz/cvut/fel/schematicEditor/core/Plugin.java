package cz.cvut.fel.schematicEditor.core;

import javax.swing.JButton;
import javax.swing.JMenu;
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
     * @param pluginsMenu menu, so custom menu items can be added as its children.
     * @param drawingToolBar drawing bar, so custom drawing buttons can be added.
     * @param sceneGraph scene graph, so data in scene can be manipulated.
     * @return <code>true</code>, if plugin was activated successfully, <code>false</code> else.
     */
    boolean activate(JMenu pluginsMenu, JToolBar drawingToolBar, SceneGraph sceneGraph);
}