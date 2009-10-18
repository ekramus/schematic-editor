package cz.cvut.fel.schematicEditor.piccolo.guiAdvanced.guiElements.menuBar.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.configuration.EnvironmentConfiguration;
import cz.cvut.fel.schematicEditor.core.Serialization;
import cz.cvut.fel.schematicEditor.element.element.part.Part;
import cz.cvut.fel.schematicEditor.original.graphNode.NodeFactory;
import cz.cvut.fel.schematicEditor.original.graphNode.PartNode;
import cz.cvut.fel.schematicEditor.parts.PartType;
import cz.cvut.fel.schematicEditor.parts.lightweightParts.LightweightPartProperties;
import cz.cvut.fel.schematicEditor.parts.lightweightParts.partProperties.CapacitorProperties;
import cz.cvut.fel.schematicEditor.parts.lightweightParts.partProperties.CurrentSourceProperties;
import cz.cvut.fel.schematicEditor.parts.lightweightParts.partProperties.InductorProperties;
import cz.cvut.fel.schematicEditor.parts.lightweightParts.partProperties.ResistorProperties;
import cz.cvut.fel.schematicEditor.parts.lightweightParts.partProperties.VoltageSourceProperties;
import cz.cvut.fel.schematicEditor.piccolo.guiAdvanced.ExportFileFilter;
import cz.cvut.fel.schematicEditor.piccolo.guiAdvanced.guiElements.gui.Gui;
import cz.cvut.fel.schematicEditor.piccolo.guiAdvanced.guiElements.menuBar.MenuBar;
import cz.cvut.fel.schematicEditor.piccolo.guiAdvanced.guiElements.propertiesPanel.PartPropertiesPanel;

/**
 * This class implements {@link ActionListener} for <code>saveAsMenuItem</code> in {@link MenuBar}.
 *
 * @author Urban Kravjansky
 */
public final class SaveAsPartMenuItemListener implements ActionListener {
    private static Logger logger;

    /**
     * Default constructor. It only calls constructor of super class.
     */
    public SaveAsPartMenuItemListener() {
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

        JFileChooser fileChooser = new JFileChooser(env.getLastSaveFolder());
        fileChooser.setDialogTitle("Choose file to save part");
        fileChooser.setFileFilter(new ExportFileFilter(ExportFileFilter.PRT, ExportFileFilter.PRTDESC));

        int retValue = fileChooser.showSaveDialog(Gui.getActiveScenePanel());

        if (retValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            env.setLastSaveFolder(file.getParent());

            // FIXME rewrite, so it is generated automatically
            LightweightPartProperties pp = null;
            switch ((PartType) PartPropertiesPanel.getInstance().getPartTypeComboBox().getSelectedItem()) {
                case RESISTOR:
                    pp = new ResistorProperties();
                    break;
                case CAPACITOR:
                    pp = new CapacitorProperties();
                    break;
                case INDUCTOR:
                    pp = new InductorProperties();
                    break;
                case VOLTAGE_SOURCE:
                    pp = new VoltageSourceProperties();
                    break;
                case CURRENT_SOURCE:
                    pp = new CurrentSourceProperties();
                    break;
                default:
                    break;
            }

            Part p = new Part(pp);

            PartNode pn = NodeFactory.createPartNode(p, Gui.getActiveScenePanel().getSceneGraph().getTopNode()
                    .getEnabledOnly());
            Serialization.serialize(pn, file);
        }
    }
}
