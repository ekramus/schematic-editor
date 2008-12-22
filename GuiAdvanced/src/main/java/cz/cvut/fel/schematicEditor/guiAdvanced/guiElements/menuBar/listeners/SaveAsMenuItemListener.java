package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import cz.cvut.fel.schematicEditor.export.NetListExport;
import cz.cvut.fel.schematicEditor.export.PSExport;
import cz.cvut.fel.schematicEditor.export.SVGExport;
import cz.cvut.fel.schematicEditor.guiAdvanced.ExportFileFilter;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.guiAdvanced.GuiAdvanced;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.MenuBar;

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
     * Method invoked as result to an action. It initializes new {@link JFileChooser} instance to select file and then
     * executes export process.
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @param e {@link ActionEvent} parameter. This parameter is only for implementing purposes, it is not used nor
     *            needed.
     */
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose file to export");
        fileChooser.setFileFilter(new ExportFileFilter(ExportFileFilter.SVG, ExportFileFilter.SVGDESC));
        fileChooser
                .addChoosableFileFilter(new ExportFileFilter(ExportFileFilter.POSTSCRIPT, ExportFileFilter.POSTDESC));
        fileChooser.addChoosableFileFilter(new ExportFileFilter(ExportFileFilter.NET, ExportFileFilter.NETDESC));

        int retValue = fileChooser.showSaveDialog(GuiAdvanced.getActiveScenePanel());

        if (retValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (ExportFileFilter.getExtension(file).equalsIgnoreCase("svg")) {
                new SVGExport().export(GuiAdvanced.getActiveScenePanel().getSceneGraph(), GuiAdvanced
                        .getActiveScenePanel().getZoomFactor(), file);

            } else if (ExportFileFilter.getExtension(file).equalsIgnoreCase("eps")) {
                new PSExport().export(GuiAdvanced.getActiveScenePanel().getSceneGraph(), GuiAdvanced
                        .getActiveScenePanel().getZoomFactor(), file);

            } else if (ExportFileFilter.getExtension(file).equalsIgnoreCase("net")) {
                new NetListExport().export(GuiAdvanced.getActiveScenePanel().getSceneGraph(), GuiAdvanced
                        .getActiveScenePanel().getZoomFactor(), file);

            } else {
                System.out.println("Failed to export");
            }

        }
    }
}
