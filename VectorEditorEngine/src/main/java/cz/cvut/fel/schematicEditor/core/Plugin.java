package cz.cvut.fel.schematicEditor.core;

import javax.swing.JButton;
import javax.swing.JMenuItem;

import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;
import cz.cvut.fel.schematicEditor.core.coreStructures.sceneGraph.SceneGraphUpdateListener;

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
    public boolean providesMenuItem();

    /**
     * Indicates, whether plugin provides drawing button, or not.
     *
     * @return <code>true</code>, if plugin provides drawing button, <code>false</code> else.
     */
    public boolean providesDrawingButton();

    /**
     * Indicates, whether plugin implements {@link SceneGraphUpdateListener}
     *
     * @return <code>true</code>, if plugin implements {@link SceneGraphUpdateListener}, <code>false</code> else.
     */
    public boolean implementsSceneGraphUpdateListener();

    /**
     * Getter for drawing button provided by plugin.
     *
     * @return {@link JButton} instance provided by plugin.
     */
    public JButton getDrawingButton();

    /**
     * Getter for menu item provided by plugin.
     *
     * @return {@link JMenuItem} instance provided by plugin.
     */
    public JMenuItem getMenuItem();

    /**
     * Activates plugin, so it is able to process data and be accessed by the user.
     *
     *@param sceneGraph {@link SceneGraph} for this plugin instance.
     *
     * @return <code>true</code>, if plugin was activated successfully, <code>false</code> else.
     */
    public boolean activate(SceneGraph sceneGraph);

    /**
     * Returns identificator of plugin.
     *
     * @return Identificator of plugin.
     */
    public String getIdentificator();
}
