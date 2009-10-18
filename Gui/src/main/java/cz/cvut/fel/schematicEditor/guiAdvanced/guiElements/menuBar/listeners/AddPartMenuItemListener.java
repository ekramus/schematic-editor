package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.configuration.EnvironmentConfiguration;
import cz.cvut.fel.schematicEditor.core.Serialization;
import cz.cvut.fel.schematicEditor.guiAdvanced.ExportFileFilter;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.gui.Gui;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.MenuBar;
import cz.cvut.fel.schematicEditor.original.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.original.graphNode.NodeFactory;
import cz.cvut.fel.schematicEditor.original.graphNode.ParameterNode;
import cz.cvut.fel.schematicEditor.original.graphNode.PartNode;

/**
 * This class implements {@link ActionListener} for <code>importMenuItem</code> in {@link MenuBar}.
 *
 * @author Urban Kravjansky
 */
public final class AddPartMenuItemListener implements ActionListener {
    private static Logger logger;

    /**
     * Default constructor. It only calls constructor of super class.
     */
    public AddPartMenuItemListener() {
        super();

        logger = Logger.getLogger(this.getClass());
    }

    // FIXME add externalized strings

    /**
     * Method invoked as result to an action. It initializes new {@link JFileChooser} instance to select file and then
     * executes export process.
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @param e {@link ActionEvent} parameter. This parameter is only for implementing purposes, it is not used nor
     *            needed.
     */
    public void actionPerformed(ActionEvent e) {
        EnvironmentConfiguration env = EnvironmentConfiguration.getInstance();

        JFileChooser fileChooser = new JFileChooser(env.getLastImportFolder());
        fileChooser.setDialogTitle("Choose part to add");
        fileChooser.setFileFilter(new ExportFileFilter(ExportFileFilter.PRT, ExportFileFilter.PRTDESC));

        int retValue = fileChooser.showOpenDialog(Gui.getActiveScenePanel());

        if (retValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            env.setLastImportFolder(file.getParent());

            // prepare PartNode, GroupNode and ParameterNode
            PartNode partNode = (PartNode) Serialization.deserialize(PartNode.class, file);
            GroupNode groupNode = NodeFactory.createGroupNode();
            ParameterNode parameterNode = NodeFactory.createParameterNode();

            groupNode.add(partNode);
            groupNode.add(parameterNode);

            // finally add to SceneGraph
            Gui.getActiveScenePanel().getSceneGraph().getTopNode().add(groupNode);
            Gui.getActiveScenePanel().getSceneGraph().fireSceneGraphUpdateEvent();
            Gui.getActiveScenePanel().sceneInvalidate(null);
        }
    }
}
