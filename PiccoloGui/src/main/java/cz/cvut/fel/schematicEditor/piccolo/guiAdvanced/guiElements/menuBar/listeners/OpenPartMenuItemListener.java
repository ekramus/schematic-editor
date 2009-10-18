package cz.cvut.fel.schematicEditor.piccolo.guiAdvanced.guiElements.menuBar.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import cz.cvut.fel.schematicEditor.configuration.EnvironmentConfiguration;
import cz.cvut.fel.schematicEditor.core.Serialization;
import cz.cvut.fel.schematicEditor.original.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.original.graphNode.PartNode;
import cz.cvut.fel.schematicEditor.piccolo.guiAdvanced.ExportFileFilter;
import cz.cvut.fel.schematicEditor.piccolo.guiAdvanced.guiElements.gui.Gui;
import cz.cvut.fel.schematicEditor.piccolo.guiAdvanced.guiElements.menuBar.MenuBar;

/**
 * This class implements {@link ActionListener} for <code>openSchemeMenuItem</code> in {@link MenuBar}.
 *
 * @author Urban Kravjansky
 */
public final class OpenPartMenuItemListener implements ActionListener {

    /**
     * Default constructor. It only calls constructor of super class.
     */
    public OpenPartMenuItemListener() {
        super();
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

        JFileChooser fileChooser = new JFileChooser(env.getLastOpenFolder());
        fileChooser.setDialogTitle("Choose file to load");
        fileChooser.setFileFilter(new ExportFileFilter(ExportFileFilter.PRT, ExportFileFilter.PRTDESC));

        int retValue = fileChooser.showOpenDialog(Gui.getActiveScenePanel());

        if (retValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            env.setLastOpenFolder(file.getParent());

            PartNode pn = (PartNode) (Serialization.deserialize(Gui.getActiveScenePanel().getSceneGraph().getTopNode()
                    .getClass(), file));
            GroupNode gn = pn.getPartGroupNode();

            Gui.getInstance().getPartScenePanel().getSceneGraph().initSceneGraph(gn);
            Gui.getInstance().getPartScenePanel().sceneInvalidate(null);
        }
    }
}
