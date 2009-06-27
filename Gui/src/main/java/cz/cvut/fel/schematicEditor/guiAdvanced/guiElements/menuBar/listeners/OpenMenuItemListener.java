package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import cz.cvut.fel.schematicEditor.configuration.EnvironmentConfiguration;
import cz.cvut.fel.schematicEditor.core.Serialization;
import cz.cvut.fel.schematicEditor.guiAdvanced.ExportFileFilter;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.gui.Gui;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.MenuBar;

/**
 * This class implements {@link ActionListener} for <code>openMenuItem</code> in {@link MenuBar}.
 *
 * @author Urban Kravjansky
 */
public final class OpenMenuItemListener implements ActionListener {

    /**
     * Default constructor. It only calls constructor of super class.
     */
    public OpenMenuItemListener() {
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
        fileChooser.setFileFilter(new ExportFileFilter(ExportFileFilter.SEF, ExportFileFilter.SEFDESC));

        int retValue = fileChooser.showOpenDialog(Gui.getActiveScenePanel());

        if (retValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            env.setLastOpenFolder(file.getParent());
            Gui.getInstance().getSchemeScenePanel().getSceneGraph().initSceneGraph(
                                                                                   Serialization.deserialize(Gui
                                                                                           .getActiveScenePanel()
                                                                                           .getSceneGraph()
                                                                                           .getTopNode().getClass(),
                                                                                                          file));

            Gui.getActiveScenePanel().sceneInvalidate(null);
        }
    }
}
