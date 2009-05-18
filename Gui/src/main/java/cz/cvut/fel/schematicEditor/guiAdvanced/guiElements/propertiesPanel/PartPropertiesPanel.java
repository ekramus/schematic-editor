package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.element.element.part.Part;
import cz.cvut.fel.schematicEditor.graphNode.PartNode;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.gui.Gui;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.partPropertiesDialog.PartPropertiesDialogPanel;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.listeners.PartRotationCenterButtonActionListener;
import cz.cvut.fel.schematicEditor.parts.PartType;

/**
 * @author urban.kravjansky
 *
 */
/**
 * This class implements properties tool bar.
 *
 * @author Urban Kravjansky
 */
public class PartPropertiesPanel extends JPanel {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger              logger;
    /**
     * Singleton instance of {@link PartPropertiesPanel}.
     */
    private static PartPropertiesPanel partPropertiesPanel      = null;

    /**
     * Line width {@link JComboBox} instance.
     */
    private JComboBox                  partTypeComboBox         = null;
    /**
     * {@link JButton} for setting part rotation center.
     */
    private JButton                    partRotationCenterButton = null;
    /**
     * {@link JLabel} displaying part rotation center.
     */
    private JLabel                     partRotationCenterLabel  = null;
    /**
     * Basic part properties JPanel.
     */
    private JPanel                     basicPropertiesPanel     = null;
    /**
     * Part netlist JLable.
     */
    private JTextArea                  partNetlistTextArea      = null;
    /**
     * Part netlist {@link JScrollPane}.
     */
    private JScrollPane                partNetlistScrollPane    = null;

    /**
     * Getter for <code>basicPropertiesPanel</code>.
     *
     * @return <code>basicPropertiesPanel</code> instance.
     */
    private JPanel getBasicPropertiesPanel() {
        if (this.basicPropertiesPanel == null) {
            // create and set JPanel instance
            this.basicPropertiesPanel = new JPanel();
            this.basicPropertiesPanel.setBorder(BorderFactory.createTitledBorder("Basic properties"));

            // attach MiG layout to panel
            this.basicPropertiesPanel.setLayout(new MigLayout("wrap 2"));

            // add components in left to right order
            this.basicPropertiesPanel.add(getPartTypeComboBox(), "wrap");
            this.basicPropertiesPanel.add(getPartRotationCenterButton(), "wrap");
            this.basicPropertiesPanel.add(new JLabel("rot. centre: "));
            this.basicPropertiesPanel.add(getPartRotationCenterLabel());
            this.basicPropertiesPanel.add(new JLabel("netlist: "), "wrap");
            this.basicPropertiesPanel.add(getPartNetlistScrollPane(), "span");
        }
        return this.basicPropertiesPanel;
    }

    /**
     * Getter for <code>partNetlistTextArea</code>.
     *
     * @return <code>partNetlistTextArea</code> instance.
     */
    private JTextArea getPartNetlistTextArea() {
        if (this.partNetlistTextArea == null) {
            // create part netlist label instance and set properties
            this.partNetlistTextArea = new JTextArea(4, 50);
            this.partNetlistTextArea.setEditable(false);
        }
        return this.partNetlistTextArea;
    }

    /**
     * Getter for <code>partNetlistTextArea</code>.
     *
     * @return <code>partNetlistTextArea</code> instance.
     */
    private JScrollPane getPartNetlistScrollPane() {
        if (this.partNetlistScrollPane == null) {
            // create part netlist scroll pane instance and set properties
            this.partNetlistScrollPane = new JScrollPane(getPartNetlistTextArea());
            this.partNetlistScrollPane.setPreferredSize(new Dimension(200, 80));
        }
        return this.partNetlistScrollPane;
    }

    /**
     * Default constructor. It is private for {@link PartPropertiesDialogPanel} singleton instance.
     */
    private PartPropertiesPanel() {
        super();

        logger = Logger.getLogger(Gui.class.getName());
    }

    /**
     * Getter for {@link PartPropertiesDialogPanel} singleton instance.
     *
     * @return {@link PartPropertiesDialogPanel} singleton instance.
     */
    public static PartPropertiesPanel getInstance() {
        if (partPropertiesPanel == null) {
            partPropertiesPanel = new PartPropertiesPanel();
            partPropertiesPanel.setLayout(new MigLayout("wrap 1"));

            // add elements
            partPropertiesPanel.add(partPropertiesPanel.getBasicPropertiesPanel(), "width 210");
        }
        return partPropertiesPanel;
    }

    /**
     * Updates {@link PartPropertiesDialogPanel} according to scene or selected element properties.
     */
    public void refresh() {
        try {
            if (Gui.getActiveScenePanel().getActiveManipulation().getManipulatedGroup().getElementType() == ElementType.T_PART) {
                PartNode partNode = (PartNode) Gui.getActiveScenePanel().getActiveManipulation().getManipulatedGroup()
                        .getChildrenElementList().getFirst();
                Part part = (Part) partNode.getElement();
                getInstance().getPartRotationCenterLabel().setText(partNode.getRotationCenter().toString());
                getInstance().getPartNetlistTextArea().setText(part.getPartProperties().getNetlist());
            }
        } catch (NullPointerException npe) {
            logger.error("Probably no manipulation.");
        }
    }

    /**
     * Getter for <code>lineWidthComboBox</code>.
     *
     * @return <code>lineWidthComboBox</code> instance.
     */
    public final JComboBox getPartTypeComboBox() {
        if (this.partTypeComboBox == null) {
            this.partTypeComboBox = new JComboBox(PartType.values());
            this.partTypeComboBox.setEditable(false);
        }
        return this.partTypeComboBox;
    }

    /**
     * @return the partRotationCenterButton
     */
    private JButton getPartRotationCenterButton() {
        if (this.partRotationCenterButton == null) {
            this.partRotationCenterButton = new JButton("Rotation Center");
            this.partRotationCenterButton.addActionListener(new PartRotationCenterButtonActionListener());
        }
        return this.partRotationCenterButton;
    }

    /**
     * @return the partRotationCenterLabel
     */
    private JLabel getPartRotationCenterLabel() {
        if (this.partRotationCenterLabel == null) {
            this.partRotationCenterLabel = new JLabel();
            this.partRotationCenterLabel.setPreferredSize(new Dimension(100, 20));
        }
        return this.partRotationCenterLabel;
    }
}
