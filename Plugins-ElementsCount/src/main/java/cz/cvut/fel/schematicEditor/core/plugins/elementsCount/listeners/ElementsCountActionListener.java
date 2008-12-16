package cz.cvut.fel.schematicEditor.core.plugins.elementsCount.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;
import cz.cvut.fel.schematicEditor.graphNode.ElementNode;
import cz.cvut.fel.schematicEditor.graphNode.Node;

/**
 * This class implements action listener for Elements count plugin menu item.
 *
 * @author Urban Kravjansky
 *
 */
public class ElementsCountActionListener implements ActionListener {
    /**
     * {@link SceneGraph} instance.
     */
    private SceneGraph sceneGraph;

    /**
     * This method instantiates new instance.
     *
     * @param sceneGraph {@link SceneGraph} instance of application.
     *
     */
    public ElementsCountActionListener(SceneGraph sceneGraph) {
        super();

        setSceneGraph(sceneGraph);
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent arg0) {
        int elementCount = 0;

        for (Node node : getSceneGraph().getSceneGraphArray()) {
            if (node instanceof ElementNode) {
                elementCount++;
            }
        }
        JOptionPane.showMessageDialog(null, "Number of element Nodes: " + elementCount);
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
