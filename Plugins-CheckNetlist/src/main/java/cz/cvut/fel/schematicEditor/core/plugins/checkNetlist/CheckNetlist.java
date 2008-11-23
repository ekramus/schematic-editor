package cz.cvut.fel.schematicEditor.core.plugins.checkNetlist;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;

import cz.cvut.fel.schematicEditor.core.Plugin;
import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;
import cz.cvut.fel.schematicEditor.core.plugins.checkNetlist.listeners.CheckNetlistActionListener;

/**
 * This plugin counts elements in current scene graph.
 *
 * @author Urban Kravjansky
 *
 */
public class CheckNetlist implements Plugin {
    /**
     * Menu item for elements count.
     */
    private static JMenuItem elementsCountMenuItem = null;

    /**
     * @see cz.cvut.fel.schematicEditor.core.Plugin#activate(javax.swing.JMenu, javax.swing.JToolBar,
     *      cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph)
     */
    public boolean activate(JMenu pluginsMenu, JToolBar drawingBar, SceneGraph sceneGraph) {
        pluginsMenu.add(getMenuItem(sceneGraph));
        return true;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.core.Plugin#getDrawingButton()
     */
    public JButton getDrawingButton() {
        return null;
    }

    /**
     * Getter for {@link JMenuItem}.
     *
     * @param sceneGraph global {@link SceneGraph} to be used.
     * @return {@link JMenuItem} instance.
     *
     */
    private JMenuItem getMenuItem(SceneGraph sceneGraph) {
        if (CheckNetlist.elementsCountMenuItem == null) {
            CheckNetlist.elementsCountMenuItem = new JMenuItem("Elements count");
            CheckNetlist.elementsCountMenuItem.addActionListener(new CheckNetlistActionListener(sceneGraph));
        }
        return CheckNetlist.elementsCountMenuItem;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.core.Plugin#providesDrawingButton()
     */
    public boolean providesDrawingButton() {
        return false;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.core.Plugin#providesMenuItem()
     */
    public boolean providesMenuItem() {
        return true;
    }
}
