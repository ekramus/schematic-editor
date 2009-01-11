package cz.cvut.fel.schematicEditor.core.plugins.elementsCount;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;

import cz.cvut.fel.schematicEditor.core.Plugin;
import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;
import cz.cvut.fel.schematicEditor.core.plugins.elementsCount.listeners.ElementsCountActionListener;

/**
 * This plugin counts elements in current scene graph.
 *
 * @author Urban Kravjansky
 *
 */
public class ElementsCount implements Plugin {
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
    public boolean activate(SceneGraph sg) {
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
     *
     */
    public JMenuItem getMenuItem() {
        if (ElementsCount.elementsCountMenuItem == null) {
            ElementsCount.elementsCountMenuItem = new JMenuItem("Elements count");
            ElementsCount.elementsCountMenuItem.addActionListener(new ElementsCountActionListener(getSceneGraph()));
        }
        return ElementsCount.elementsCountMenuItem;
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
     * @param sceneGraph the sceneGraph to set
     */
    private void setSceneGraph(SceneGraph sceneGraph) {
        this.sceneGraph = sceneGraph;
    }

    /**
     * @return the sceneGraph
     */
    private SceneGraph getSceneGraph() {
        return this.sceneGraph;
    }
}
