package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import cz.cvut.fel.schematicEditor.configuration.EnvironmentConfiguration;
import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;
import cz.cvut.fel.schematicEditor.element.element.part.Part;
import cz.cvut.fel.schematicEditor.element.properties.PartProperties;
import cz.cvut.fel.schematicEditor.element.properties.PartType;
import cz.cvut.fel.schematicEditor.element.properties.partProperties.CapacitorProperties;
import cz.cvut.fel.schematicEditor.element.properties.partProperties.CurrentSourceProperties;
import cz.cvut.fel.schematicEditor.element.properties.partProperties.InductorProperties;
import cz.cvut.fel.schematicEditor.element.properties.partProperties.ResistorProperties;
import cz.cvut.fel.schematicEditor.element.properties.partProperties.VoltageSourceProperties;
import cz.cvut.fel.schematicEditor.graphNode.PartNode;
import cz.cvut.fel.schematicEditor.guiAdvanced.ExportFileFilter;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.MenuBar;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesToolBar.PartPropertiesPanel;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.scenePanel.ScenePanel;

/**
 * This class implements {@link ActionListener} for <code>saveAsMenuItem</code> in {@link MenuBar}.
 *
 * @author Urban Kravjansky
 */
public final class SaveAsPartMenuItemListener implements ActionListener {

    /**
     * Default constructor. It only calls constructor of super class.
     */
    public SaveAsPartMenuItemListener() {
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
        if (!PartPropertiesPanel.getInstance().getEditingPartCheckBox().isSelected()) {
            JOptionPane.showMessageDialog(null, "Part creation is not enabled", "Unable to save as part",
                                          JOptionPane.WARNING_MESSAGE);
            return;
        }

        EnvironmentConfiguration env = EnvironmentConfiguration.getInstance();

        JFileChooser fileChooser = new JFileChooser(env.getLastSaveFolder());
        fileChooser.setDialogTitle("Choose file to save part");
        fileChooser.setFileFilter(new ExportFileFilter(ExportFileFilter.PRT, ExportFileFilter.PRTDESC));

        int retValue = fileChooser.showSaveDialog(ScenePanel.getInstance());

        if (retValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            env.setLastSaveFolder(file.getParent());

            // FIXME rewrite, so it is generated automatically
            PartProperties pp = null;
            switch ((PartType) PartPropertiesPanel.getInstance().getPartTypeComboBox().getSelectedItem()) {
                case RESISTOR:
                    pp = new ResistorProperties("variant", "variant description");
                    break;
                case CAPACITOR:
                    pp = new CapacitorProperties("variant", "variant description");
                    break;
                case INDUCTOR:
                    pp = new InductorProperties("variant", "variant description");
                    break;
                case VOLTAGE_SOURCE:
                    pp = new VoltageSourceProperties("variant", "variant description");
                    break;
                case CURRENT_SOURCE:
                    pp = new CurrentSourceProperties("variant", "variant description");
                    break;
                default:
                    break;
            }

            Part p = new Part(pp);

            PartNode pn = new PartNode(p, ScenePanel.getInstance().getSchemeSG().getTopNode().getEnabledOnly());
            serialize(pn, file);
        }
    }

    /**
     * Serializes given {@link PartNode} into given file.
     *
     * @param partNode {@link PartNode} file to serialize.
     * @param file Path to file, where should be {@link PartNode} serialized.
     */
    protected static void serialize(PartNode partNode, File file) {
        XStream xstream = new XStream(new DomDriver());

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            processAnnotations(xstream, partNode.getClass());
            xstream.toXML(partNode, bw);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Processes all {@link XStream} annotations in entered classes.
     *
     * @param xstream {@link XStream} instance to configure.
     * @param clazz Class of {@link SceneGraph} object.
     */
    private static void processAnnotations(XStream xstream, Class<? extends PartNode> clazz) {
        xstream.processAnnotations(clazz);
        // xstream.processAnnotations(Unit.class);
        // xstream.processAnnotations(Pixel.class);
    }
}
