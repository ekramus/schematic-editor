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
     * {@link SceneGraph} instance.
     */
    private SceneGraph       sceneGraph;

    /**
     * @see cz.cvut.fel.schematicEditor.core.Plugin#activate(JMenu, JToolBar)
     */
    public boolean activate(SceneGraph sg, JMenu pluginsMenu, JToolBar drawingBar) {
        pluginsMenu.add(getMenuItem());
        setSceneGraph(sg);
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
     * @return {@link JMenuItem} instance.
     */
    private JMenuItem getMenuItem() {
        if (CheckNetlist.elementsCountMenuItem == null) {
            CheckNetlist.elementsCountMenuItem = new JMenuItem("Check netlist");
            CheckNetlist.elementsCountMenuItem.addActionListener(new CheckNetlistActionListener(getSceneGraph()));
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

    /**
     * @see cz.cvut.fel.schematicEditor.core.Plugin#implementsSceneGraphUpdateListener()
     */
    public boolean implementsSceneGraphUpdateListener() {
        return false;
    }

    /**
     * @return the sceneGraph
     */
    private SceneGraph getSceneGraph() {
        return this.sceneGraph;
    }

    /**
     * @param sceneGraph the sceneGraph to set
     */
    private void setSceneGraph(SceneGraph sceneGraph) {
        this.sceneGraph = sceneGraph;
    }
}
