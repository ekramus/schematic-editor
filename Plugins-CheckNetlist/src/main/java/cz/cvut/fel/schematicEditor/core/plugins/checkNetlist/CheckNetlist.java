package cz.cvut.fel.schematicEditor.core.plugins.checkNetlist;

import javax.swing.JButton;
import javax.swing.JMenuItem;

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
    private JMenuItem  elementsCountMenuItem = null;
    /**
     * {@link SceneGraph} instance.
     */
    private SceneGraph sceneGraph;

    /**
     * @see cz.cvut.fel.schematicEditor.core.Plugin#activate(SceneGraph)
     */
    public boolean activate(SceneGraph sceneGraph) {
        setSceneGraph(sceneGraph);
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
    public JMenuItem getMenuItem() {
        if (this.elementsCountMenuItem == null) {
            this.elementsCountMenuItem = new JMenuItem("Check netlist");
            this.elementsCountMenuItem.addActionListener(new CheckNetlistActionListener(getSceneGraph()));
        }
        return this.elementsCountMenuItem;
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

    /**
     * @see cz.cvut.fel.schematicEditor.core.Plugin#getIdentificator()
     */
    public String getIdentificator() {
        // TODO Auto-generated method stub
        return "CheckNetlist";
    }
}
