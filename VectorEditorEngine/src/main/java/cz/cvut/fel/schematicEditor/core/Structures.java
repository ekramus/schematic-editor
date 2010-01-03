package cz.cvut.fel.schematicEditor.core;

import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JMenuItem;

import cz.cvut.fel.schematicEditor.core.coreStructures.SceneTabbedPaneTabs;

/**
 * This class represents the application core structures.
 *
 * @author Urban Kravjansky
 */
public class Structures {
    /**
     * {@link Vector} of loaded plugin properties.
     */
    private static Vector<Properties>                              loadedPluginProperties = null;
    /**
     * Hash map of menu items of plugins for each scene tab.
     */
    private static HashMap<SceneTabbedPaneTabs, Vector<JMenuItem>> pluginMenuItems        = null;

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
     * @return the pluginMenuItems
     */
    public static HashMap<SceneTabbedPaneTabs, Vector<JMenuItem>> getPluginMenuItems() {
        if (pluginMenuItems == null) {
            pluginMenuItems = new HashMap<SceneTabbedPaneTabs, Vector<JMenuItem>>();
            for (SceneTabbedPaneTabs sceneTab : SceneTabbedPaneTabs.values()) {
                pluginMenuItems.put(sceneTab, new Vector<JMenuItem>());
            }
        }
        return pluginMenuItems;
    }
}
