package cz.cvut.fel.schematicEditor.application.guiElements.listeners.menuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import cz.cvut.fel.schematicEditor.application.ExportFileFilter;
import cz.cvut.fel.schematicEditor.application.guiElements.MenuBar;
import cz.cvut.fel.schematicEditor.application.guiElements.ScenePanel;

/**
 * This class implements {@link ActionListener} for <code>saveAsMenuItem</code> in {@link MenuBar}.
 * 
 * @author Urban Kravjansky
 */
public final class SaveAsMenuItemListener implements ActionListener {

    /**
     * Default constructor. It only calls constructor of super class.
     */
    public SaveAsMenuItemListener() {
        super();
    }

    // FIXME add externalized strings

    /**
     * Method invoked as result to an action. It initializes ne {@link JFileChooser} instance to select file and then
     * executes export process.
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @param e
     *            {@link ActionEvent} parameter. This parameter is only for implementing purposes, it is not used nor
     *            needed.
     */
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser("C:\\");
        fileChooser.setDialogTitle("Choose file to export");
        fileChooser.setFileFilter(new ExportFileFilter(ExportFileFilter.SVG, ExportFileFilter.SVGDESC));
        fileChooser
                .addChoosableFileFilter(new ExportFileFilter(ExportFileFilter.POSTSCRIPT, ExportFileFilter.POSTDESC));

        int retValue = fileChooser.showSaveDialog(ScenePanel.getInstance());

        if (retValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (ExportFileFilter.getExtension(file).equalsIgnoreCase("svg")) {
                // TODO enable SVGExport
                // new SVGExport().export(Structures.getScenePanel().getSchemeSG(), file);

            } else if (ExportFileFilter.getExtension(file).equalsIgnoreCase("eps")) {
                // TODO enable PSExport
                // new PSExport().export(Structures.getScenePanel().getSchemeSG(), file);

            } else {
                System.out.println("Failed to export");
            }

        }
    }
}
