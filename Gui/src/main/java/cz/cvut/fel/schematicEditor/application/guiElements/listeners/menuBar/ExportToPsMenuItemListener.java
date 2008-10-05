package cz.cvut.fel.schematicEditor.application.guiElements.listeners.menuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import cz.cvut.fel.schematicEditor.application.ExportFileFilter;
import cz.cvut.fel.schematicEditor.application.guiElements.MenuBar;
import cz.cvut.fel.schematicEditor.application.guiElements.ScenePanel;

/**
 * This class implements {@link ActionListener} for <code>exportToPsMenuItem</code> in
 * {@link MenuBar}.
 *
 * @author Urban Kravjansky
 */
public final class ExportToPsMenuItemListener implements ActionListener {

    /**
     * Default constructor. It only calls constructor of super class.
     */
    public ExportToPsMenuItemListener() {
        super();
    }

    /**
     * Method invoked as result to an action. It initializes new {@link JFileChooser} instance and
     * then initializes export to PS.
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     *
     * @param ae
     *            {@link ActionEvent} parameter. This parameter is only for implementing purposes,
     *            it is not used nor needed.
     */
    public void actionPerformed(final ActionEvent ae) {
        JFileChooser fileChooser = new JFileChooser("C:\\");
        fileChooser.setDialogTitle("Choose file to export");
        fileChooser.setFileFilter(new ExportFileFilter(ExportFileFilter.POSTSCRIPT,
                ExportFileFilter.POSTDESC));
        // fileChooser.addChoosableFileFilter(new
        // ExportFileFilter(ExportFileFilter.POSTSCRIPT,ExportFileFilter.POSTDESC));

        int retValue = fileChooser.showSaveDialog(ScenePanel.getInstance());

        if (retValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            // TODO enable PSExport
            // new PSExport().export(ScenePanel.getInstance().getSchemeSG(), file);

            // Check for error
            // System.out.println("Failde Export");
        }

    }
}
