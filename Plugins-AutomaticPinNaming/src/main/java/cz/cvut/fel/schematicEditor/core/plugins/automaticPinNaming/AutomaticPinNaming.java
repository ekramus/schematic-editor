package cz.cvut.fel.schematicEditor.core.plugins.automaticPinNaming;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JToolBar;

import cz.cvut.fel.schematicEditor.core.Plugin;
import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;
import cz.cvut.fel.schematicEditor.core.coreStructures.sceneGraph.SceneGraphUpdateEvent;
import cz.cvut.fel.schematicEditor.core.coreStructures.sceneGraph.SceneGraphUpdateListener;
import cz.cvut.fel.schematicEditor.element.element.part.Part;
import cz.cvut.fel.schematicEditor.graphNode.Node;
import cz.cvut.fel.schematicEditor.graphNode.PartNode;

/**
 * This plugin provides automatic naming of SceneGraph PINs.
 *
 * @author Urban Kravjansky
 */
public class AutomaticPinNaming implements Plugin, SceneGraphUpdateListener {

    /**
     * @see cz.cvut.fel.schematicEditor.core.Plugin#activate(javax.swing.JMenu, javax.swing.JToolBar)
     */
    public boolean activate(JMenu pluginsMenu, JToolBar drawingToolBar) {
        return false;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.core.Plugin#getDrawingButton()
     */
    public JButton getDrawingButton() {
        return null;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.core.Plugin#implementsSceneGraphUpdateListener()
     */
    public boolean implementsSceneGraphUpdateListener() {
        return true;
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
        return false;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.core.coreStructures.sceneGraph.SceneGraphUpdateListener#sceneGraphUpdateOccured(cz
     *      .cvut.fel.schematicEditor.core.coreStructures.sceneGraph.SceneGraphUpdateEvent)
     */
    public void sceneGraphUpdateOccured(SceneGraphUpdateEvent e) {
        SceneGraph sg = (SceneGraph) e.getSource();

        ArrayList<Node> nal = sg.getSceneGraphArray();
        for (Node node : nal) {
            if (node instanceof PartNode) {
                Part p = (Part) ((PartNode) node).getElement();
                System.err.println("here we are, automatic PIN names");
            }
        }
    }

}
